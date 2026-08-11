# Testing Rules

## Purpose
Prevent regressions and verify important functionality in the SMS / Capsule Online Exam System.

## Testing Priority

Prioritize tests for:
1. Authentication
2. Authorization
3. Exam creation
4. Exam access
5. Exam attempt
6. Timer behavior
7. Answer submission
8. Exam submission
9. Evaluation
10. Result generation
11. Student data isolation

## Unit Tests
Test:
- Business logic
- Validation
- Calculations
- Exam scoring
- Permission checks
- Important service methods

## Integration Tests
Test:
- API endpoints
- Database interactions
- Authentication
- Authorization
- Exam workflow

## Edge Cases
Consider:
- Empty input
- Invalid IDs
- Expired exams
- Unauthorized users
- Duplicate submissions
- Multiple attempts
- Missing questions
- Invalid answers
- Exam timeout
- Network/API failures

## Rules
- Do not create meaningless tests just to increase coverage.
- Test behavior, not implementation details.
- Keep tests deterministic.
- Do not depend on external services unnecessarily.
- Do not weaken production security to make tests pass.

## Regression
When fixing a bug:
1. Reproduce the problem.
2. Add or update a test where appropriate.
3. Fix the issue.
4. Verify existing functionality.

## Final Check
Run the relevant test suite after significant changes.
