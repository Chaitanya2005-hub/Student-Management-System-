# 🚀 Quick Start Guide

Get the Online Examination System running in 5 minutes!

## ⚡ Fastest Setup (If you have everything ready)

```powershell
# 1. Start MySQL
net start MySQL80

# 2. Build and deploy
.\build.ps1
Copy-Item "target\OnlineExaminationSystem.war" "E:\Apache\webapps\"

# 3. Start Tomcat
$env:CATALINA_HOME = "E:\Apache"
& "E:\Apache\bin\startup.bat"

# 4. Open browser
# Go to: http://localhost:8080/OnlineExaminationSystem/
```

**Default Login:**
- Username: `admin`
- Password: `admin123`

---

## 📋 Step-by-Step Setup

### 1. Verify Prerequisites

```powershell
# Check Java version (should be 17+)
java -version

# Check if MySQL is running
net start | findstr MySQL

# Check if Tomcat directory exists
Test-Path "E:\Apache"
```

### 2. Database Setup

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS exam_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

-- Create user (if needed)
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'root123';
GRANT ALL PRIVILEGES ON exam_system.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Build the Project

```powershell
# Navigate to project directory
cd E:\SMS

# Run build script
.\build.ps1
```

**Expected Output:**
```
==========================================
 Building OnlineExaminationSystem.war
==========================================
1. Compiling Java source files...
2. Copying web application resources...
3. Bundling runtime libraries into WEB-INF/lib...
4. Packaging WAR archive...

✅ SUCCESS: Built e:\SMS\target\OnlineExaminationSystem.war
```

### 4. Deploy to Tomcat

```powershell
# Copy WAR file to Tomcat webapps
Copy-Item "target\OnlineExaminationSystem.war" "E:\Apache\webapps\"
```

### 5. Start Tomcat

```powershell
# Set environment variable
$env:CATALINA_HOME = "E:\Apache"

# Start Tomcat
& "E:\Apache\bin\startup.bat"
```

**Expected Output:**
```
Using CATALINA_BASE:   "E:\Apache"
Using CATALINA_HOME:   "E:\Apache"
Using CATALINA_TMPDIR: "E:\Apache\temp"
Using JRE_HOME:        "E:\Java 21.0"
Using CLASSPATH:       "E:\Apache\bin\bootstrap.jar;E:\Apache\bin\tomcat-juli.jar"
```

### 6. Access the Application

Open your browser and navigate to:
```
http://localhost:8080/OnlineExaminationSystem/
```

---

## 🔐 First Login

1. **Login Page**: You'll see the login page
2. **Enter Credentials**:
   - Username: `admin`
   - Password: `admin123`
3. **Dashboard**: You'll be redirected to the Author Dashboard

---

## 🛑 Stopping the Application

```powershell
# Stop Tomcat
$env:CATALINA_HOME = "E:\Apache"
& "E:\Apache\bin\shutdown.bat"

# Or kill process if shutdown doesn't work
netstat -ano | findstr :8080
taskkill /F /PID <PID>
```

---

## 🔧 Common Issues

### Port 8080 Already in Use
```powershell
# Find and kill process
netstat -ano | findstr :8080
taskkill /F /PID <PID>
```

### Database Connection Failed
```powershell
# Test MySQL connection
mysql -u root -p -e "USE exam_system; SHOW TABLES;"

# Check if database exists
mysql -u root -p -e "SHOW DATABASES LIKE 'exam_system';"
```

### Build Fails
```powershell
# Check Java version
java -version

# Set JAVA_HOME if needed
$env:JAVA_HOME = "E:\Java 21.0"

# Verify Maven dependencies
Test-Path "C:\Users\YOUR_USERNAME\.m2\repository"
```

---

## 📱 Testing Your Setup

### Quick Health Check

1. **Application Access**: Open `http://localhost:8080/OnlineExaminationSystem/`
2. **Login**: Use admin credentials
3. **Dashboard**: Verify you see the Author Dashboard
4. **Logout**: Test logout functionality

### Create Test User

1. Go to Author Dashboard
2. Navigate to "Manage Users"
3. Create a test student user
4. Try logging in as the student

---

## 🎯 Next Steps

After successful setup:

1. **Change default passwords** - Update admin password immediately
2. **Import full database schema** - See `DATABASE_SCHEMA.md`
3. **Configure email settings** - For notifications
4. **Set up backup strategy** - Database backups
5. **Review security settings** - See main README

---

## 📞 Need Help?

- **Full Documentation**: See `README.md`
- **Database Schema**: See `DATABASE_SCHEMA.md`
- **Architecture**: See `ARCHITECTURE.md`
- **Setup Guide**: See `SETUP_GUIDE.md`

---

## ✅ Setup Checklist

- [ ] Java 21 installed and configured
- [ ] MySQL Server running
- [ ] Database `exam_system` created
- [ ] Project built successfully
- [ ] WAR file deployed to Tomcat
- [ ] Tomcat started without errors
- [ ] Application accessible at http://localhost:8080/OnlineExaminationSystem/
- [ ] Able to login with admin credentials
- [ ] Dashboard loads correctly
- [ ] Changed default password

**Estimated Time**: 5-10 minutes for initial setup