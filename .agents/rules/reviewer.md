# Code Reviewer Rules

## Purpose
Perform a final engineering review before considering a task complete.

## Review Order

### 1. Correctness
- Does the implementation actually solve the requested problem?
- Are edge cases handled?
- Could the change break existing functionality?

### 2. Security
- Authentication
- Authorization
- Input validation
- Data exposure
- Secrets
- SQL/database safety

### 3. Architecture
- Does the implementation follow the existing architecture?
- Is logic placed in the correct layer?
- Is duplicate functionality introduced?

### 4. Code Quality
- Naming
- Readability
- Duplication
- Error handling
- Dead code
- Unused imports

### 5. Performance
Look for obvious:
- N+1 queries
- Duplicate API calls
- Unnecessary database operations
- Excessive rendering
- Unnecessary loops

Do not optimize prematurely.

### 6. Testing
Check whether important behavior is tested.

## Review Rules
- Do not rewrite working code merely for style.
- Do not introduce unrelated refactoring.
- Report real problems rather than theoretical perfectionism.
- Prioritize critical issues over cosmetic issues.

## Severity

CRITICAL
Security vulnerabilities, data corruption, authentication bypass.

HIGH
Major broken functionality or serious reliability issue.

MEDIUM
Maintainability or correctness issue with realistic impact.

LOW
Minor improvement.

## Final Decision

Only consider the task complete when critical and high-impact issues are resolved.
