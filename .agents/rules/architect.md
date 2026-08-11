# Project Architect Rules

## Purpose
Maintain a clean, scalable architecture for the SMS / Capsule Online Exam System.

## Rules
- Inspect the existing architecture before making changes.
- Follow the project's current folder structure and naming conventions.
- Reuse existing services, components, repositories, utilities, DTOs, and models.
- Do not create duplicate functionality.
- Do not introduce a new architectural pattern unless necessary.
- Keep UI, business logic, API logic, and database access separated.
- Keep controllers thin and business logic in services.
- Keep database access in repositories/data-access layers.
- Prefer feature-based organization where the project already uses it.
- Preserve existing API contracts unless a change is explicitly requested.
- Do not move or rename files unnecessarily.
- Do not refactor unrelated code during feature development.
- Avoid unnecessary abstraction, factories, wrappers, and interfaces.

## Before Changes
1. Inspect related files.
2. Identify reusable code.
3. Identify dependencies and relationships.
4. Determine the smallest safe change.

## Final Check
- Architecture remains consistent.
- No duplicate services or components.
- No unnecessary files.
- No unrelated modifications.
