# Exam Security Rules

## Purpose

Apply exam-specific security and integrity rules to the SMS / Capsule Online Exam System.

## Exam Access

Before allowing an exam operation, verify:
- User is authenticated.
- User has the correct role.
- User is enrolled/authorized for the exam.
- Exam is currently available.
- User is allowed to attempt the exam.

## Timer

Never trust a client-side timer as the source of truth.

The server should determine:
- Exam start time
- Exam end time
- Allowed duration
- Whether the attempt has expired

The frontend timer is for user experience only.

## Attempts

Verify server-side:
- Maximum attempts
- Current attempt
- Attempt ownership
- Attempt status
- Submission status

A student must never be able to modify another student's attempt.

## Answers

- Validate that the question belongs to the exam.
- Validate that the attempt belongs to the authenticated student.
- Do not trust marks or correctness values supplied by the client.
- Calculate scoring using trusted server-side logic.

## Submission

Exam submission must be safe against:
- Duplicate submissions
- Partial submissions
- Unauthorized submissions
- Expired attempts
- Race conditions

Where necessary, use transactions and idempotent operations.

## Results

Students may only access their own results unless their role explicitly permits broader access.

Do not trust student-provided marks, scores, result IDs, or attempt IDs.

## Question Security

Do not expose:
- Correct answers unnecessarily
- Answer keys before submission
- Questions belonging to unauthorized exams
- Internal question-management metadata

## Exam Logs

Use the existing logging/audit mechanism for important events such as:
- Exam started
- Answer submitted
- Exam submitted
- Attempt expired
- Suspicious activity

Never log passwords or authentication tokens.

## Final Check

Before completing exam-related functionality, verify:
- Authorization
- Attempt ownership
- Server-side timing
- Answer validation
- Submission integrity
- Result access
- Audit logging
