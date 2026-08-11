<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login — Online Examination System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/glassmorphism.css">
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .login-card {
            width: 100%;
            max-width: 440px;
        }
        .login-logo {
            text-align: center;
            margin-bottom: 24px;
        }
        .login-logo h1 {
            font-size: 1.8rem;
            color: #3b82f6;
        }
        .theme-toggle-container {
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .theme-toggle-btn {
            background: var(--glass-bg);
            border: 1px solid var(--glass-border);
            border-radius: 50%;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            font-size: 1.2rem;
            transition: all 0.2s ease;
        }
        .theme-toggle-btn:hover {
            background: var(--glass-bg-strong);
            transform: scale(1.1);
        }
    </style>
    <script>
        // Theme management
        (function() {
            const savedTheme = localStorage.getItem('theme') || 'dark';
            document.documentElement.setAttribute('data-theme', savedTheme);
        })();
    </script>
</head>
<body>
    <div class="theme-toggle-container">
        <button id="theme-toggle" class="theme-toggle-btn" title="Toggle Theme">🌙</button>
    </div>

    <div class="glass-panel glass-panel--strong login-card page-enter">
        <div class="login-logo">
            <h1>🎓 Stark Exam Portal</h1>
            <p>Online Examination & Student Management System</p>
        </div>

        <%
            String error = request.getParameter("error");
            String msg = request.getParameter("msg");
            if (error != null) {
        %>
            <div style="background: rgba(248, 113, 113, 0.2); border: 1px solid var(--danger); padding: 12px; border-radius: 10px; margin-bottom: 16px; color: #f87171; text-align: center; font-size: 0.9rem;">
                <% if ("invalid_credentials".equals(error)) { %>
                    ❌ Invalid username or password. Please try again.
                <% } else if ("please_login".equals(error)) { %>
                    ⚠️ Please log in to access the system.
                <% } else if ("unauthorized".equals(error)) { %>
                    🚫 Access Denied: You do not have permission for that area.
                <% } else { %>
                    An error occurred. Please try again.
                <% } %>
            </div>
        <% } %>

        <% if (msg != null && "logged_out".equals(msg)) { %>
            <div style="background: rgba(52, 211, 153, 0.2); border: 1px solid var(--success); padding: 12px; border-radius: 10px; margin-bottom: 16px; color: #34d399; text-align: center; font-size: 0.9rem;">
                ✅ You have been logged out successfully.
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">Username / ERP ID</label>
                <input type="text" id="username" name="username" class="form-control" placeholder="Enter your username" required autofocus>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn btn--primary" style="width: 100%; padding: 12px; font-size: 1rem; margin-top: 8px;">
                Sign In →
            </button>
        </form>
    </div>

    <script>
        // Theme toggle functionality for login page
        document.getElementById('theme-toggle').addEventListener('click', function() {
            const html = document.documentElement;
            const currentTheme = html.getAttribute('data-theme');
            const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
            
            html.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
            
            // Update button icon
            this.textContent = newTheme === 'dark' ? '🌙' : '☀️';
        });

        // Set initial button icon based on current theme
        document.addEventListener('DOMContentLoaded', function() {
            const currentTheme = document.documentElement.getAttribute('data-theme');
            const themeToggle = document.getElementById('theme-toggle');
            if (themeToggle) {
                themeToggle.textContent = currentTheme === 'dark' ? '🌙' : '☀️';
            }
        });
    </script>
</body>
</html>
