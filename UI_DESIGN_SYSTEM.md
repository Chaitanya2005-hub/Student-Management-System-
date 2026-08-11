# UI Design System — Glassmorphism

Design language for all JSP-rendered views: frosted-glass panels, soft depth, subtle motion. Applies uniformly across `student/`, `teacher/`, and `author/` view folders so the app feels like one product regardless of role.

---

## 1. Core Visual Principles

1. **Translucent layered panels** over a rich, softly animated background — never over flat white/gray.
2. **Blur + border, not shadow alone** — the glass effect comes from `backdrop-filter: blur()` plus a 1px semi-transparent border, with shadow used sparingly for lift.
3. **Restrained color** — one saturated accent hue per role context (see palette), everything else neutral/translucent.
4. **Motion with purpose** — transitions communicate state change (loading, success, error, navigation), never decorative for its own sake.

---

## 2. Color Tokens

```css
:root {
  /* Background gradient */
  --bg-gradient-start: #0f0c29;
  --bg-gradient-mid:   #302b63;
  --bg-gradient-end:   #24243e;

  /* Glass surface */
  --glass-bg: rgba(255, 255, 255, 0.10);
  --glass-bg-strong: rgba(255, 255, 255, 0.16);
  --glass-border: rgba(255, 255, 255, 0.25);
  --glass-shadow: rgba(0, 0, 0, 0.25);

  /* Text */
  --text-primary: #f5f6fa;
  --text-secondary: rgba(245, 246, 250, 0.7);
  --text-muted: rgba(245, 246, 250, 0.45);

  /* Accent (default / student context) */
  --accent: #7f9cf5;
  --accent-hover: #6c86e0;

  /* Role accents */
  --accent-student: #7f9cf5;   /* soft blue */
  --accent-teacher: #34d399;   /* emerald */
  --accent-author:  #f59e0b;   /* amber */

  /* Semantic */
  --success: #34d399;
  --warning: #f59e0b;
  --danger:  #f87171;
}
```

Apply `--accent-{role}` as the active `--accent` value at the top-level body/container based on the logged-in user's role, so student/teacher/author areas are visually distinguishable at a glance without needing separate stylesheets.

---

## 3. Typography

```css
--font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;

--fs-xs: 0.75rem;
--fs-sm: 0.875rem;
--fs-base: 1rem;
--fs-lg: 1.125rem;
--fs-xl: 1.5rem;
--fs-2xl: 2rem;

--fw-regular: 400;
--fw-medium: 500;
--fw-semibold: 600;
--fw-bold: 700;
```

Headings use `--fw-semibold`/`--fw-bold`; body text `--fw-regular`. Avoid more than 3 weight/size combinations per screen.

---

## 4. Glass Panel Component

```css
.glass-panel {
  background: var(--glass-bg);
  border: 1px solid var(--glass-border);
  border-radius: 16px;
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  box-shadow: 0 8px 32px var(--glass-shadow);
  padding: 24px;
}

.glass-panel--strong {
  background: var(--glass-bg-strong);
}

.glass-panel:hover {
  border-color: rgba(255, 255, 255, 0.35);
  transition: border-color 0.25s ease;
}
```

Use `.glass-panel` for cards, modals, the exam-taking question card, and the sidebar. Use `.glass-panel--strong` for anything requiring more contrast/readability — long question text, data tables, forms.

---

## 5. Buttons

```css
.btn {
  padding: 10px 20px;
  border-radius: 10px;
  border: 1px solid var(--glass-border);
  background: var(--glass-bg);
  color: var(--text-primary);
  font-weight: var(--fw-medium);
  cursor: pointer;
  transition: transform 0.15s ease, background 0.2s ease;
}

.btn:hover {
  background: var(--glass-bg-strong);
  transform: translateY(-1px);
}

.btn--primary {
  background: var(--accent);
  border-color: var(--accent);
  color: #0f0c29;
}

.btn--primary:hover {
  background: var(--accent-hover);
}

.btn--danger {
  background: var(--danger);
  border-color: var(--danger);
  color: #1a0f0f;
}
```

---

## 6. Animation Guidelines

```css
@keyframes fadeSlideUp {
  from { opacity: 0; transform: translateY(12px); }
  to   { opacity: 1; transform: translateY(0); }
}

.page-enter {
  animation: fadeSlideUp 0.35s ease-out;
}

@keyframes pulseWarning {
  0%, 100% { box-shadow: 0 0 0 0 rgba(248, 113, 113, 0.5); }
  50%      { box-shadow: 0 0 0 8px rgba(248, 113, 113, 0); }
}

.exam-warning-indicator {
  animation: pulseWarning 1.6s ease-in-out infinite;
}
```

- Page/panel entrances: `fadeSlideUp`, 300–400ms, ease-out.
- Proctoring warnings (tied to `exam_live_status.warnings_count`): use `pulseWarning` on the on-screen indicator so it reads as urgent without being jarring.
- Timer components (tied to `exams.duration_minutes`): no animation by default; switch the accent color to `--warning` under 5 minutes remaining and `--danger` under 1 minute, with a slow pulse only in the final minute.
- Respect `prefers-reduced-motion`: wrap all non-essential animation in `@media (prefers-reduced-motion: no-preference)`.

---

## 7. Background Setup

```css
body {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--bg-gradient-start), var(--bg-gradient-mid), var(--bg-gradient-end));
  background-attachment: fixed;
  font-family: var(--font-family);
  color: var(--text-primary);
}
```

Optional: layer 2–3 large, low-opacity blurred circles (`position: fixed`, `filter: blur(80px)`, `opacity: 0.3`) behind content for depth — keep them static or very slowly drifting (60s+ loop) so they never distract from foreground content, especially during a timed exam.

---

## 8. Layout Conventions

- **Sidebar navigation** (glass panel, fixed) + **main content area** (glass panels for each content block) — consistent across all three role areas.
- **Exam-taking screen** is the one exception: minimal chrome, single glass card centered, timer and warning indicator persistently visible, sidebar hidden to reduce distraction and surface area for cheating via navigation.
- Forms use `.glass-panel--strong` with generous spacing (16px+ between fields) — translucent backgrounds hurt input legibility at low contrast, so favor the stronger variant for anything with text inputs.
- Data tables (results, attendance, fees) use `.glass-panel` as the table container with solid (non-translucent) row backgrounds on hover for readability: `background: rgba(255,255,255,0.06)`.

---

## 9. Accessibility Notes

- Maintain a minimum 4.5:1 contrast ratio for body text against the glass surface — test `--text-secondary` against `--glass-bg-strong` specifically, translucent-on-translucent is the easiest place for this system to fail contrast checks.
- Every interactive glass element needs a visible focus state (not just hover) — add `outline: 2px solid var(--accent); outline-offset: 2px;` on `:focus-visible`.
- Do not rely on color alone for exam warnings/timer states — pair with icon and text label, since the amber/red distinction is not reliable for color-blind users.
