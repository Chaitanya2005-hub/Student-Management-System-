<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-role="${sessionScope.userRole}" data-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title != null ? param.title : 'Portal'} — Stark Exam System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/glassmorphism.css">
    <script src="${pageContext.request.contextPath}/static/js/main.js" defer></script>
    <script>
        // Theme management
        (function() {
            const savedTheme = localStorage.getItem('theme') || 'dark';
            document.documentElement.setAttribute('data-theme', savedTheme);
        })();
    </script>
</head>
<body>
<div class="app-container">
