# Automated Build Script for Online Examination & Student Management System
# Uses Java JDK (javac + jar) to package target/OnlineExaminationSystem.war

$ErrorActionPreference = "Stop"

Write-Host "=========================================="
Write-Host " Building OnlineExaminationSystem.war"
Write-Host "=========================================="

$cp = "C:\Users\CHAITANYA SRI\.m2\repository\jakarta\servlet\jakarta.servlet-api\6.0.0\jakarta.servlet-api-6.0.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\jakarta\servlet\jsp\jakarta.servlet.jsp-api\3.1.0\jakarta.servlet.jsp-api-3.1.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\jakarta\annotation\jakarta.annotation-api\3.0.0\jakarta.annotation-api-3.0.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\json\json\20240303\json-20240303.jar;C:\Users\CHAITANYA SRI\.m2\repository\com\mysql\mysql-connector-j\8.4.0\mysql-connector-j-8.4.0.jar;C:\Users\CHAITANYA SRI\.m2\repository\org\apache\tomcat\embed\tomcat-embed-core\11.0.22\tomcat-embed-core-11.0.22.jar"
$files = Get-ChildItem -Path "e:\SMS\src\main\java" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName

# Clean and recreate build directories
if (Test-Path "e:\SMS\target\build") { Remove-Item -Recurse -Force "e:\SMS\target\build" }
New-Item -ItemType Directory -Force -Path "e:\SMS\target\build\WEB-INF\classes" | Out-Null
New-Item -ItemType Directory -Force -Path "e:\SMS\target\build\WEB-INF\lib" | Out-Null

Write-Host "1. Compiling Java source files..."
& "E:\Java 21.0\bin\javac.exe" --release 21 -cp $cp -d "e:\SMS\target\build\WEB-INF\classes" $files

Write-Host "2. Copying web application resources..."
Copy-Item -Recurse -Force "e:\SMS\src\main\webapp\*" "e:\SMS\target\build\"

Write-Host "3. Bundling runtime libraries into WEB-INF/lib..."
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\org\json\json\20240303\json-20240303.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\com\mysql\mysql-connector-j\8.4.0\mysql-connector-j-8.4.0.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\jakarta\servlet\jsp\jstl\jakarta.servlet.jsp.jstl-api\3.0.0\jakarta.servlet.jsp.jstl-api-3.0.0.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\org\glassfish\web\jakarta.servlet.jsp.jstl\3.0.1\jakarta.servlet.jsp.jstl-3.0.1.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\jakarta\annotation\jakarta.annotation-api\3.0.0\jakarta.annotation-api-3.0.0.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\org\eclipse\jdt\ecj\3.33.0\ecj-3.33.0.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\org\apache\pdfbox\pdfbox\2.0.29\pdfbox-2.0.29.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\com\google\zxing\core\3.5.2\core-3.5.2.jar" "e:\SMS\target\build\WEB-INF\lib\"
Copy-Item "C:\Users\CHAITANYA SRI\.m2\repository\com\google\zxing\javase\3.5.2\javase-3.5.2.jar" "e:\SMS\target\build\WEB-INF\lib\"

Write-Host "4. Packaging WAR archive..."
Set-Location "e:\SMS\target\build"
& "E:\Java 21.0\bin\jar.exe" -cvf "e:\SMS\target\OnlineExaminationSystem.war" * | Out-Null
Set-Location "e:\SMS"

Write-Host ""
Write-Host "✅ SUCCESS: Built e:\SMS\target\OnlineExaminationSystem.war"
Write-Host "=========================================="
