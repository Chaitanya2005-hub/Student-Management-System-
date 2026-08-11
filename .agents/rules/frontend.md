# Frontend Rules

## Purpose
Keep the SMS / Capsule Online Exam System frontend clean, consistent, responsive, and maintainable.

## Components
- Reuse existing components.
- Do not create duplicate UI components.
- Keep components focused.
- Move reusable logic into services/utilities where appropriate.
- Avoid huge components.

## Forms
- Validate user input.
- Show useful validation messages.
- Prevent invalid submissions.
- Do not rely only on frontend validation for security.

## API Communication
- Reuse the existing API service pattern.
- Do not duplicate HTTP logic.
- Handle loading, success, and error states.
- Do not expose backend secrets.

## Authentication
- Follow the existing authentication mechanism.
- Protect restricted routes.
- Do not store sensitive credentials insecurely.
- Never treat frontend role checks as real authorization.

## UI
- Follow existing design patterns.
- Maintain responsive layouts.
- Avoid unnecessary animations.
- Avoid duplicate styles.
- Reuse existing theme variables and components.

## State
- Follow the existing state-management approach.
- Do not introduce a new state library for a small feature.
- Avoid unnecessary subscriptions or state duplication.

## Performance
- Avoid unnecessary API calls.
- Avoid unnecessary re-renders.
- Reuse cached/shared data where appropriate.
- Do not add optimization complexity without evidence.

## Final Check
Verify:
- UI behavior
- Validation
- API integration
- Loading states
- Error states
- Responsive layout
- No console errors
