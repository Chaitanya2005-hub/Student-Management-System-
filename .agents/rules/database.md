# Database Rules

## Purpose
Maintain a reliable and normalized database for the SMS / Capsule Online Exam System.

## Core Rules
- Inspect the existing database schema before creating tables.
- Reuse existing tables and relationships.
- Do not create duplicate entities.
- Follow existing naming conventions.
- Preserve foreign-key relationships.
- Maintain referential integrity.
- Avoid unnecessary schema changes.

## Exam Data Model
Respect relationships between concepts such as:

Student
Faculty
Course
Subject
Exam
Question
Question Option
Exam Attempt
Student Answer
Result
Attendance
Assignment
Announcement
Notification

Do not invent relationships without checking the existing schema.

## Queries
- Use parameterized queries.
- Avoid SELECT * when unnecessary.
- Select only required fields.
- Avoid N+1 query patterns where applicable.
- Use indexes for frequently searched/joined fields.
- Do not remove existing indexes without justification.

## Data Integrity
Use appropriate:
- Primary keys
- Foreign keys
- Unique constraints
- NOT NULL constraints
- Check constraints where supported
- Proper data types

## Transactions
Use transactions for operations that must succeed or fail together.

Examples:
- Exam submission
- Result generation
- Bulk answer submission
- Important enrollment operations

## Migrations
- Never manually modify production schema without a migration.
- Keep schema changes reproducible.
- Do not delete data casually.
- Never use destructive migration operations unless explicitly requested.

## Performance
Check indexes and query efficiency before optimizing application code.

## Final Check
Verify:
- Foreign keys
- Constraints
- Indexes
- Duplicate data risks
- Query correctness
- Transaction boundaries
