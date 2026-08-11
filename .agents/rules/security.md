# Security Rules

## Purpose
Protect the SMS / Capsule Online Exam System against authentication, authorization, data exposure, and API security problems.

## Authentication
- Never bypass authentication.
- Never trust client-provided identity.
- Never store plaintext passwords.
- Never log passwords or authentication tokens.
- Protected endpoints must verify authentication.

## Authorization
- Enforce authorization on the backend.
- Never rely only on frontend route guards.
- Never rely only on hidden or disabled UI controls.
- Verify the authenticated user's permissions server-side.
- Respect existing roles such as ADMIN, FACULTY, and STUDENT.

## Input Validation
Treat all client input as untrusted.

Validate:
- Request bodies
- Query parameters
- Path parameters
- IDs
- Uploaded files
- Exam answers
- Pagination values
- Search parameters

## Database
- Never build SQL using unsafe string concatenation.
- Use parameterized queries or the project's safe ORM/repository mechanism.
- Never expose database credentials.
- Never return unnecessary sensitive database fields.

## Secrets
Never hardcode:
- Passwords
- JWT secrets
- API keys
- Database credentials
- Private tokens

Use environment variables or the project's existing secret mechanism.

## API Security
Every protected operation must verify:
1. Authentication
2. Authorization
3. Resource ownership where applicable

## Data Isolation
A student must never be able to access:
- Another student's answers
- Another student's exam attempt
- Another student's marks
- Another student's private information

Faculty must only access resources permitted by their role.

## Error Handling
Never expose:
- Stack traces
- SQL statements
- Credentials
- Tokens
- Internal filesystem paths
- Sensitive user information

## Final Security Review
Check authentication, authorization, validation, database access, secrets, logging, and data isolation before finishing.
