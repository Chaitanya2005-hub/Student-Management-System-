# Backend Rules

## Purpose
Maintain clean and reliable backend code for the SMS / Capsule Online Exam System.

## General
- Follow the existing Java/Spring architecture.
- Keep controllers thin.
- Put business logic in services.
- Keep database access in repositories.
- Use DTOs where the project already uses them.
- Avoid exposing entities directly when inappropriate.

## Services
Services should contain:
- Business rules
- Validation
- Transaction boundaries
- Coordination between repositories

Do not put large business logic inside controllers.

## Controllers
Controllers should:
- Validate request-level input
- Authenticate/authorize requests
- Call services
- Return appropriate responses

Avoid database queries directly inside controllers.

## APIs
- Follow existing endpoint naming.
- Use appropriate HTTP methods.
- Return consistent response structures.
- Use correct status codes.
- Validate request bodies and parameters.
- Do not expose internal implementation details.

## Error Handling
Use the project's existing exception-handling mechanism.

Do not:
- Return stack traces
- Ignore exceptions
- Use empty catch blocks
- Expose sensitive information

## Transactions
Use transactions for multi-step operations that must remain consistent.

## Code Quality
- Avoid duplicated business logic.
- Reuse existing services.
- Avoid unnecessary classes.
- Avoid unnecessary dependencies.
- Prefer clear and maintainable Java.

## Final Check
Check compilation, imports, exception handling, API behavior, authorization, and database interactions.
