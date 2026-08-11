# Architecture — Online Examination & Student Management System

Java Servlet/JSP web application, built with Apache Maven, deployed on Apache Tomcat, backed by the MySQL `exam_system` database (see `DATABASE_SCHEMA.md` for the authoritative schema). UI is a browser-rendered Glassmorphism theme (see `UI_DESIGN_SYSTEM.md`).

This is a rebuild target for the existing "Stark ERP System" (a JavaFX desktop app) as a web application — feature parity plus the additions below, constrained strictly to what the actual `exam_system` schema supports today.

---

## 1. Layered Structure

```
src/main/java/
├── model/          → POJOs mapped 1:1 to tables (User, Student, Faculty, Exam, Question, ...)
├── dao/             → JDBC data-access classes, one per table or per closely related table group
├── service/         → business logic: exam lifecycle, scoring, proctoring rules, approval workflow
├── controller/       → Servlets — one per functional area, mapped via web.xml or annotations
├── filter/          → auth/session filters, role-based access control
├── util/            → DBConnection (connection pool), PasswordUtil, DateUtil
└── listener/        → ServletContextListener for connection pool init/teardown

src/main/webapp/
├── WEB-INF/
│   ├── web.xml
│   └── views/        → JSPs, organized by role (student/, teacher/, author/, shared/)
├── static/
│   ├── css/          → glassmorphism.css + per-page styles
│   ├── js/
│   └── img/
└── index.jsp
```

Standard MVC: Servlets act as controllers (handle request, call service layer, forward to JSP), JSPs are pure view (no direct DB access), DAOs are the only layer that touches JDBC/SQL.

---

## 2. Roles & Access

`users.role` supports exactly three values: **`student`**, **`teacher`**, **`author`**. There is no `admin` role in the schema. Design the access-control layer around these three roles only:

| Role | Primary responsibilities (schema-supported) |
|---|---|
| `student` | Take exams, view own results/grades/attendance/fees, submit assignments, raise grievances, view announcements/notices/calendar, request exam access |
| `teacher` | Create/manage exams and questions, mark attendance, grade assignments, post announcements/notices, view student results |
| `author` | Question-bank authoring (`question_bank`), exam content creation — functionally overlaps with `teacher`; the schema does not distinguish their permissions beyond the enum value itself |

`announcements.target_audience` includes an `'admin'` option that has no corresponding `users.role` — either treat admin-facing announcements as visible to `teacher`+`author`, or add an `admin` role via migration before relying on it. Document this decision explicitly wherever the app checks `target_audience`.

Session-based auth: on login, store `user.id`, `user.role`, and `user.full_name` in the HTTP session. A single `AuthFilter` checks session presence; a `RoleFilter` (or per-servlet check) restricts controllers by role. There is no email column, so **password reset via email is not implementable** without a schema migration — plan for an admin-reset or security-question fallback instead.

---

## 3. Functional Modules (mapped to real tables)

| Module | Tables involved |
|---|---|
| **Auth & Identity** | `users`, `students`, `faculty`, `departments` |
| **Exam Engine** | `exams`, `questions`, `question_bank`, `exam_requests`, `admit_cards`, `live_codes` |
| **Live Proctoring** | `exam_live_status`, `exam_logs`, `exam_broadcasts` |
| **Scoring & Results** | `student_responses`, `results` |
| **Academics (non-exam)** | `courses`, `subjects`, `grades`, `attendance`, `assignments`, `submissions` |
| **Communication** | `announcements`, `notices`, `calendar_events` |
| **Student Services** | `fees`, `grievances`, `uploads` |

### Exam lifecycle (as the schema models it)
1. Teacher/author creates an `exams` row (`status = 'scheduled'`), adds `questions` (optionally copied from `question_bank` — there's no FK, so this is an app-level copy).
2. If `requires_approval = 1`, students submit an `exam_requests` row (`status = 'Requested'`); teacher approves/rejects.
3. Admit gating uses `admit_cards.status` (`Blocked`/`Released`) — note this is per-student, not per-exam (see schema limitations).
4. On start, `exams.status` moves to `'active'`; a `live_codes` entry can gate entry via a shared code.
5. During the exam: `exam_live_status` tracks per-student heartbeat/warnings/status; `exam_logs` records discrete events (free-text `event_type`); `exam_broadcasts` lets a teacher push messages to all active takers.
6. Student answers are written to `student_responses` (one row per question answered).
7. On submit, the service layer computes `score`/`total_marks` and writes a `results` row; `exam_live_status.status` moves to `'Submitted'`.
8. Teacher can mark `exams.status = 'completed'` once finished. There is no `'cancelled'` state in the enum — cancellation must be modeled another way (e.g. a broadcast message + leaving it `'scheduled'`) unless the enum is migrated.

---

## 4. Additive Features — Feasibility Against Current Schema

| Requested feature | Schema support today | What's needed |
|---|---|---|
| Timed exams | ✅ `exams.duration_minutes`, `start_time` already exist | Just enforce timer client/server-side |
| Multiple question types | ❌ `questions`/`question_bank` are single-answer 4-option MCQ only | Schema migration: new `question_type` column + restructured option storage |
| Result analytics | ⚠️ Partial — `results`, `grades`, `student_responses` give raw data | Build aggregation queries/views in the service layer; no new tables strictly required |
| Email notifications | ❌ No email column anywhere | Add `users.email`, plus a mail-sending service (e.g. Jakarta Mail via SMTP) |
| Role-based session auth | ✅ `users.role` supports this for the 3 existing roles | Implement `AuthFilter`/`RoleFilter` as described above |

Do not present email notifications or multi-type questions as "already working" in any generated documentation or UI copy — they require schema changes that have not been made.

---

## 5. Request Flow (typical)

```
Browser → Servlet (Controller) → Service (business logic) → DAO (JDBC) → MySQL
                                                                  ↓
Browser ← JSP (View, glassmorphism CSS) ← forward/redirect ← Servlet
```

- Connection pooling via a `DataSource` configured in Tomcat's `context.xml` (see `SETUP_GUIDE.md`), looked up via JNDI in `util.DBConnection`.
- All SQL lives in DAO classes using `PreparedStatement` exclusively — no string-concatenated queries, given the amount of user-supplied text (`question_text`, `description`, `content`, etc.) flowing into free-text columns.
- JSPs use JSTL for logic-light templating; avoid embedding raw Java/scriptlets beyond trivial output.

---

## 6. Deployment Topology

```
[Browser] → [Apache Tomcat :8080] → [exam_system MySQL DB]
                   │
                   └── webapp deployed as .war (or exploded directory during dev)
```

Single Tomcat instance is sufficient for the target scale implied by the current data volumes (hundreds of students, low-thousands of rows per table per the `AUTO_INCREMENT` values observed in the schema dump). No load balancer, caching layer, or message queue is assumed necessary at this stage.
