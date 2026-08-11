# Run Script for Online Examination & Student Management System
# Starts embedded Tomcat server on http://localhost:8080/

Write-Host "=========================================="
Write-Host " Starting Online Examination System Server"
Write-Host "=========================================="

# Build the project first
& ".\build.ps1"

$cp = "e:\SMS\target\build\WEB-INF\classes;C:\Users\CHAITANYA SRI\.m2\repository\org\apache\tomcat\embed\tomcat-embed-core\11.0.22\tomcat-embed-core-11.0.22.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\apache\tomcat\embed\tomcat-embed-jasper\11.0.22\tomcat-embed-jasper-11.0.22.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\apache\tomcat\embed\tomcat-embed-el\11.0.22\tomcat-embed-el-11.0.22.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\apache\tomcat\embed\tomcat-embed-websocket\11.0.22\tomcat-embed-websocket-11.0.22.jar;C:\Users\CHAITANYA SRI\.m2\repository\jakarta\servlet\jakarta.servlet-api\6.0.0\jakarta.servlet-api-6.0.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\jakarta\servlet\jsp\jakarta.servlet.jsp-api\3.1.0\jakarta.servlet.jsp-api-3.1.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\jakarta\annotation\jakarta.annotation-api\3.0.0\jakarta.annotation-api-3.0.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\json\json\20240303\json-20240303.jar;C:\Users\CHAITANYA SRI\.m2\repository\com\mysql\mysql-connector-j\8.4.0\mysql-connector-j-8.4.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\jakarta\servlet\jsp\jstl\jakarta.servlet.jsp.jstl-api\3.0.0\jakarta.servlet.jsp.jstl-api-3.0.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\glassfish\web\jakarta.servlet.jsp.jstl\3.0.1\jakarta.servlet.jsp.jstl-3.0.1.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\eclipse\jdt\ecj\3.33.0\ecj-3.33.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\apache\pdfbox\pdfbox\2.0.29\pdfbox-2.0.29.jar;C:\Users\CHAITANYA SRI\.m2\repository\com\google\zxing\core\3.5.2\core-3.5.2.jar;C:\Users\CHAITANYA SRI\.m2\repository\com\google\zxing\javase\3.5.2\javase-3.5.2.jar"

# Compile ServerRunner
& "E:\Java 21.0\bin\javac.exe" --release 21 -cp $cp -d "e:\SMS\target\build\WEB-INF\classes" "e:\SMS\src\main\java\com\stark\exam\ServerRunner.java"

Write-Host ""
Write-Host "Starting Web Server at http://localhost:8080/ ..."
& "E:\Java 21.0\bin\java.exe" -cp $cp "com.stark.exam.ServerRunner" 8080
