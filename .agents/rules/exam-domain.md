# Exam Domain Rules

## Purpose

Understand and preserve the business workflow of the SMS / Capsule Online Exam System.

## Core Domain

The system may contain concepts such as:

Student
Faculty
Admin
Department
Course
Subject
Exam
Question
Question Option
Exam Request
Exam Attempt
Student Answer
Result
Attendance
Assignment
Announcement
Calendar Event
Admit Card
Exam Broadcast
Exam Live Status
Exam Log

Always inspect the actual database and existing source code before assuming a specific relationship.

## Typical Exam Flow

Faculty/Admin
    ↓
Create Exam
    ↓
Configure Exam
    ↓
Add Questions
    ↓
Publish/Approve Exam
    ↓
Student Gets Access
    ↓
Student Starts Attempt
    ↓
Student Answers Questions
    ↓
Timer Runs
    ↓
Student Submits
    ↓
Server Validates Attempt
    ↓
Evaluation
    ↓
Result
    ↓
Analytics

## Business Rules

### Student
- Students can access only resources they are authorized to access.
- Student data must remain isolated from other students.

### Faculty
- Faculty can manage only exams/courses permitted by their role.
- Faculty should not access unrelated student data.

### Admin
- Admin operations must respect the existing RBAC system.
- Administrative operations should be auditable.

### Exam
Before allowing a student to start an exam, verify:
- Exam exists.
- Exam is published/available.
- Student is authorized.
- Current time is within the permitted window.
- Attempt limit has not been exceeded.

### Attempt
An attempt belongs to one student and one exam.

Do not assume a student can create unlimited attempts.

### Answers
Answers belong to the current attempt.

Never allow an answer to be attached to another student's attempt.

### Evaluation
Evaluation must use trusted server-side rules.

Never accept a client-provided final score as authoritative.

### Results
Results should be generated from trusted attempt/answer data.

Do not duplicate score calculations in multiple unrelated places.

## Existing Database Is Authoritative

The actual project database schema is the source of truth.

Before creating or modifying domain logic:
1. Inspect existing tables.
2. Inspect foreign keys.
3. Inspect existing entities/models.
4. Inspect existing services.
5. Preserve existing relationships.

Do not invent database structures when existing structures already solve the requirement.

## Final Check

Every exam feature should preserve:
- Correct student ownership
- Correct exam ownership
- Correct attempt ownership
- Correct timing
- Correct scoring
- Correct authorization
