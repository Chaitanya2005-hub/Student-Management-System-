# Database Schema — `exam_system`

MySQL 9.5 (Community), InnoDB, `utf8mb4` / `utf8mb4_0900_ai_ci`. 27 tables. This document reflects the **actual live schema** exported via `SHOW CREATE TABLE`, not an idealized design. Anything not in this file does not exist in the database — do not invent columns, tables, or relationships when building against this spec.

---

## 1. Core Identity

### `users`
Central identity table for every person in the system.

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `username` | varchar(50) | UNIQUE, NOT NULL |
| `password` | varchar(100) | NOT NULL — stored as plain varchar; hashing must be enforced at the application layer, the column has no constraint enforcing it |
| `full_name` | varchar(100) | nullable |
| `role` | enum('student','teacher','author') | NOT NULL — **only three roles exist**, see Limitations |
| `erp_id` | varchar(20) | UNIQUE, nullable |
| `year` | int | nullable |
| `department` | varchar(50) | nullable — **free text, not a FK** to `departments.id` |
| `section` | char(1) | nullable |
| `photo_path` | varchar(255) | nullable |

**No `email` column exists on `users` or anywhere else in the schema.**

### `students`
Extended profile for users with `role = 'student'`.

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `user_id` | int, UNIQUE, NOT NULL | FK → `users.id`, ON DELETE CASCADE |
| `roll_no` | varchar(50), UNIQUE, NOT NULL | |
| `full_name` | varchar(120), NOT NULL | |
| `department_id` | int, NOT NULL | FK → `departments.id` (no constraint declared in dump, but semantically references it) |
| `semester` | int, NOT NULL | |
| `batch_year` | int, NOT NULL | |

### `faculty`
Extended profile for users with `role = 'teacher'` or `'author'`.

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `user_id` | int, UNIQUE, NOT NULL | FK → `users.id`, ON DELETE CASCADE |
| `employee_id` | varchar(50), UNIQUE, NOT NULL | |
| `full_name` | varchar(120), NOT NULL | |
| `department_id` | int, NOT NULL | FK → `departments.id` |
| `designation` | varchar(100) | nullable |
| `phone` | varchar(20) | nullable |
| `created_at` | timestamp | DEFAULT CURRENT_TIMESTAMP |

### `departments`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `name` | varchar(100), UNIQUE, NOT NULL | |
| `code` | varchar(20), UNIQUE, NOT NULL | |
| `created_at` | timestamp | DEFAULT CURRENT_TIMESTAMP |

---

## 2. Academics

### `courses`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `code` | varchar(20), UNIQUE, NOT NULL | |
| `title` | varchar(200), NOT NULL | |
| `department_id` | int, NOT NULL | references `departments.id` |
| `semester` | int, NOT NULL | |
| `credits` | int, NOT NULL, DEFAULT 3 | |

### `subjects`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `name` | varchar(100) | nullable |
| `code` | varchar(20) | nullable |
| `department` | varchar(50) | nullable — free text, **not** a FK to `departments` |

`subjects` and `courses` are two separate, unlinked catalogs — `subjects` is used by `question_bank`, `courses` is used for grading/enrollment context. There is no FK between them.

### `grades`
Course-level grading (separate from online-exam results).

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int, NOT NULL | FK → `students.id`, ON DELETE CASCADE |
| `course_id` | int, NOT NULL | references `courses.id` (no FK constraint declared) |
| `exam_type` | enum('midterm','final','assignment','quiz') | NOT NULL |
| `marks_obtained` | decimal(5,2) | nullable |
| `max_marks` | decimal(5,2), NOT NULL | |
| `grade` | varchar(2) | nullable |
| `semester` | int, NOT NULL | |

### `attendance`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `date` | date | nullable |
| `status` | enum('Present','Absent') | nullable |
| `marked_by` | int | nullable — **no FK constraint declared**, though it semantically references `users.id` |

### `assignments`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `title` | varchar(100) | nullable |
| `description` | text | nullable |
| `due_date` | date | nullable |
| `created_by` | int | FK → `users.id` |

### `submissions`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `assignment_id` | int | FK → `assignments.id` |
| `student_id` | int | FK → `users.id` |
| `submission_text` | text | nullable — text-only, no file column here (see `uploads`) |
| `submission_date` | datetime | DEFAULT CURRENT_TIMESTAMP |

---

## 3. Online Examination Engine

### `exams`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `title` | varchar(100) | nullable |
| `exam_date` | date | nullable |
| `start_time` | time | nullable |
| `duration_minutes` | int | nullable |
| `status` | enum('scheduled','active','completed') | DEFAULT 'scheduled' — **no `'cancelled'` or `'archived'` state exists** |
| `requires_approval` | tinyint(1) | DEFAULT 1 |

### `questions`
Questions attached to a specific exam.

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `exam_id` | int | FK → `exams.id`, ON DELETE CASCADE |
| `question_text` | text | nullable |
| `option_a` / `option_b` / `option_c` / `option_d` | varchar(255) | nullable — **exactly four options, hard-coded as columns** |
| `correct_answer` | char(1) | nullable — single character, e.g. `'a'`–`'d'` |

### `question_bank`
Reusable question pool, structurally identical to `questions` plus difficulty/subject tagging.

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `subject` | varchar(100) | nullable — free text, not FK'd to `subjects.id` |
| `difficulty` | enum('Easy','Medium','Hard') | nullable |
| `question_text` | text | nullable |
| `option_a`–`option_d` | varchar(255) | nullable |
| `correct_answer` | char(1) | nullable |

**There is no FK between `question_bank` and `questions`.** Pulling a bank question into a live exam requires the application to copy the row's data into a new `questions` row — the schema does not track provenance.

### `exam_requests`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `exam_id` | int | FK → `exams.id` |
| `status` | enum('Requested','Approved','Rejected') | DEFAULT 'Requested' |

### `admit_cards`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `status` | enum('Blocked','Released') | DEFAULT 'Blocked' |

Note: `admit_cards` is keyed only by `student_id`, not by `(student_id, exam_id)` — as modeled it represents a single blanket admit status per student, not a per-exam admit card.

### `live_codes`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK (no AUTO_INCREMENT) | caller must supply the id |
| `code` | varchar(10) | nullable |
| `updated_at` | timestamp | ON UPDATE CURRENT_TIMESTAMP |

Used as a shared exam-entry code, typically a single active row.

### `exam_live_status`
Real-time proctoring state, one row per (student, exam) pair.

| Column | Type | Notes |
|---|---|---|
| `student_id` | int | PK (composite), FK → `users.id` |
| `exam_id` | int | PK (composite), FK → `exams.id` |
| `current_question` | int | DEFAULT 1 |
| `warnings_count` | int | DEFAULT 0 |
| `status` | enum('Active','Idle','Disconnected','Terminated','Submitted') | DEFAULT 'Active' |
| `last_heartbeat` | datetime | DEFAULT CURRENT_TIMESTAMP |

### `exam_logs`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `exam_id` | int | FK → `exams.id` |
| `event_type` | varchar(50) | nullable — free-text event label, no enum |
| `event_time` | datetime | DEFAULT CURRENT_TIMESTAMP |

### `exam_broadcasts`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `exam_id` | int | FK → `exams.id` |
| `message` | text | nullable |
| `sent_at` | datetime | DEFAULT CURRENT_TIMESTAMP |

### `student_responses`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `exam_id` | int | FK → `exams.id` |
| `question_id` | int | FK → `questions.id` |
| `selected_option` | char(1) | nullable |
| `is_correct` | tinyint(1) | nullable |

### `results`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `exam_id` | int | FK → `exams.id` |
| `score` | int | nullable |
| `total_marks` | int | nullable |
| `security_warnings` | int | DEFAULT 0 |

`results` (exam-level score) and `grades` (course-level grading) are **independent tables with no link between them.**

---

## 4. Communication & Admin

### `announcements`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `title` | varchar(200), NOT NULL | |
| `content` | text, NOT NULL | |
| `target_audience` | enum('all','students','teacher','admin') | DEFAULT 'all' — **includes `'admin'`, which is not a valid `users.role` value** |
| `author_id` | int, NOT NULL | no declared FK constraint, semantically → `users.id` |
| `is_active` | tinyint(1), NOT NULL | DEFAULT 1 |
| `created_at` | timestamp | DEFAULT CURRENT_TIMESTAMP |

### `notices`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `title` | varchar(100) | nullable |
| `message` | text | nullable |
| `posted_by` | int | nullable, no declared FK |
| `date` | datetime | DEFAULT CURRENT_TIMESTAMP |

`notices` and `announcements` are two separate, overlapping tables — no FK links them. Treat as distinct features unless consolidated at the application layer.

### `calendar_events`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `user_id` | int, NOT NULL | no declared FK, semantically → `users.id` |
| `role` | varchar(50) | nullable — free text |
| `title` | varchar(255), NOT NULL | |
| `event_date` | date, NOT NULL | |
| `start_time` | time | nullable |
| `location` | varchar(255) | nullable |
| `event_type` | varchar(100) | nullable |
| `created_at` | timestamp | DEFAULT CURRENT_TIMESTAMP |

### `grievances`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int | FK → `users.id` |
| `category` | varchar(50) | nullable, free text |
| `description` | text | nullable |
| `status` | enum('Pending','Resolved') | DEFAULT 'Pending' |
| `submission_date` | date | DEFAULT (curdate()) |

### `fees`
| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `student_id` | int, NOT NULL | FK → `students.id` |
| `fee_type` | varchar(100), NOT NULL | |
| `amount` | decimal(10,2), NOT NULL | |
| `status` | enum('paid','unpaid','partial') | DEFAULT 'unpaid' |
| `due_date` | date | nullable |
| `created_at` | timestamp | DEFAULT CURRENT_TIMESTAMP |

### `uploads`
Generic file-attachment table used across features.

| Column | Type | Notes |
|---|---|---|
| `id` | int, PK, AUTO_INCREMENT | |
| `user_id` | int, NOT NULL | no declared FK |
| `role` | varchar(50) | nullable |
| `category` | varchar(100) | nullable |
| `original_name` | varchar(255) | nullable |
| `stored_name` | varchar(255) | nullable |
| `mime_type` | varchar(100) | nullable |
| `size_bytes` | bigint | nullable |
| `path` | varchar(500) | nullable |
| `related_type` | varchar(100) | nullable — polymorphic link, e.g. `'assignment'`, `'submission'` |
| `related_id` | int | nullable — paired with `related_type` to form a polymorphic FK; not enforced by the DB |
| `access_level` | varchar(50) | DEFAULT 'Private' |
| `created_at` | timestamp | DEFAULT CURRENT_TIMESTAMP |

---

## 5. Known Schema Limitations (do not build around assumptions past these)

1. **Only three roles exist**: `student`, `teacher`, `author` (in `users.role`). There is **no `admin` role**, despite `announcements.target_audience` including an `'admin'` option — that value currently has no matching user role. Any admin-only UI must be built on top of `teacher`/`author`, or the enum needs an explicit migration.
2. **No email column anywhere in the schema.** Email notifications cannot be implemented without adding a column (e.g. `users.email`) and a migration.
3. **Questions are strictly single-answer, 4-option multiple choice** (`option_a`–`option_d`, single `correct_answer` char). No support for multi-select, short answer, essay, true/false-as-distinct-type, or numeric-entry questions without a schema change.
4. **`question_bank` and `questions` are disconnected** — no FK, no "sourced from bank" tracking.
5. **`grades` (course-level) and `results` (exam-level) are entirely separate** — a unified "student performance" view must be built at the application/reporting layer by joining on `student_id` only.
6. Several tables carry **unenforced foreign keys** (`attendance.marked_by`, `announcements.author_id`, `notices.posted_by`, `calendar_events.user_id`, `uploads.user_id`, `grades.course_id`) — MySQL will not reject orphaned values in these columns; integrity must be enforced in application code.
7. `users.department` and `subjects.department` are **free-text varchar, not FKs** to `departments.id`, while `students.department_id`, `faculty.department_id`, and `courses.department_id` are proper (if unconstrained) integer references. Treat department data as inconsistently normalized across the schema.
8. `admit_cards` is one row per student, not per (student, exam) — it cannot represent "released for exam A, blocked for exam B" without a schema change.
9. `password` in `users` has no column-level indication of hashing — assume plaintext unless the application layer proves otherwise; never document or build a feature assuming hashed storage exists today.
