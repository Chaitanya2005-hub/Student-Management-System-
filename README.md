# Online Examination & Student Management System

A Java Servlet/JSP web application with Apache Maven packaging, Apache Tomcat 10+ deployment, MySQL `exam_system` database (27 tables), and Glassmorphism UI.

Rebuilt from the Stark ERP desktop system into a high-performance web platform adhering strictly to database schemas, architectural separation, exam security, and live proctoring.

---

## � Prerequisites

Before running this project, ensure you have the following installed:

- **Java 21** (or Java 17+)
  - Download: https://www.oracle.com/java/technologies/downloads/
  - Set `JAVA_HOME` environment variable
  - Verify: `java -version`

- **Apache Tomcat 10.0.27** (or Tomcat 10+)
  - Download: https://tomcat.apache.org/download-10.cgi
  - Extract to your preferred location (e.g., `E:\Apache`)
  - Set `CATALINA_HOME` environment variable (optional)

- **MySQL Server 8.0+**
  - Download: https://dev.mysql.com/downloads/mysql/
  - Install and start MySQL service
  - Default credentials: `root` / `root123` (configurable)

- **PowerShell** (for Windows build scripts)
  - Built into Windows 10/11

---

## 🚀 Complete Setup & Run Guide

### Step 1: Database Setup

1. **Start MySQL Server**
   ```bash
   # Windows
   net start MySQL80

   # Or use MySQL Workbench to start the service
   ```

2. **Create Database**
   ```sql
   CREATE DATABASE IF NOT EXISTS exam_system
   CHARACTER SET utf8mb4
   COLLATE utf8mb4_0900_ai_ci;
   ```

3. **Import Database Schema**
   - Check [`DATABASE_SCHEMA.md`](./DATABASE_SCHEMA.md) for the complete schema
   - Run the SQL scripts to create all 27 tables
   - Default database name: `exam_system`
   - Default credentials: `root` / `root123`

### Step 2: Configure Database Connection

The database connection is configured in two places:

**Option A: Using JNDI DataSource (Recommended for Tomcat)**
- Edit: `src/main/webapp/META-INF/context.xml`
- Update credentials:
  ```xml
  username="your_username"
  password="your_password"
  url="jdbc:mysql://localhost:3306/exam_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
  ```

**Option B: Using Direct Connection (Fallback)**
- Edit: `src/main/java/com/stark/exam/util/DBConnection.java`
- Update these lines:
  ```java
  private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/exam_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
  private static final String DEFAULT_USER = "root";
  private static final String DEFAULT_PASS = "root123";
  ```

### Step 3: Build the Project

**Using PowerShell Scripts (Recommended):**

```powershell
# Build the WAR file
.\build.ps1
```

This will:
1. Compile all Java source files
2. Copy web application resources
3. Bundle runtime libraries
4. Create `target/OnlineExaminationSystem.war`

**Manual Build (Alternative):**

```powershell
# Set Java home
$env:JAVA_HOME = "E:\Java 21.0"

# Compile
javac --release 21 -cp "lib/*" -d target/classes src/main/java/**/*.java

# Create WAR
cd target/classes
jar -cvf ../OnlineExaminationSystem.war *
cd ../..
```

### Step 4: Deploy & Run

#### Option A: Using Apache Tomcat (Recommended)

1. **Deploy WAR to Tomcat**
   ```powershell
   # Copy WAR file to Tomcat webapps
   Copy-Item "target\OnlineExaminationSystem.war" "E:\Apache\webapps\"
   ```

2. **Start Tomcat**
   ```powershell
   # Set CATALINA_HOME if not set
   $env:CATALINA_HOME = "E:\Apache"

   # Start Tomcat
   & "E:\Apache\bin\startup.bat"
   ```

3. **Access the Application**
   - URL: `http://localhost:8080/OnlineExaminationSystem/`
   - Default login page will appear

4. **Stop Tomcat**
   ```powershell
   $env:CATALINA_HOME = "E:\Apache"
   & "E:\Apache\bin\shutdown.bat"
   ```

#### Option B: Using Embedded Tomcat (Development)

```powershell
# Run with embedded Tomcat
.\run.ps1
```

This will:
1. Build the project
2. Start embedded Tomcat server
3. Deploy the application
4. Access at: `http://localhost:8080/`

---

## 🔐 Default Credentials

The system comes with default user credentials. **Change these immediately after first login!**

### Default Admin/Author User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `author` (Full system access)

### Database Credentials
- **Username**: `root`
- **Password**: `root123`
- **Database**: `exam_system`

**⚠️ SECURITY NOTE**: These are default credentials meant for development. Change them before production deployment!

---

## 🌐 Access URLs

After successful deployment:

- **Main Application**: `http://localhost:8080/OnlineExaminationSystem/`
- **Login Page**: `http://localhost:8080/OnlineExaminationSystem/index.jsp`
- **Student Dashboard**: `http://localhost:8080/OnlineExaminationSystem/student/dashboard`
- **Teacher Dashboard**: `http://localhost:8080/OnlineExaminationSystem/teacher/dashboard`
- **Author Dashboard**: `http://localhost:8080/OnlineExaminationSystem/author/dashboard`

---

## 🛠️ Troubleshooting

### Common Issues & Solutions

#### 1. Port 8080 Already in Use
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process
taskkill /F /PID <PID_FROM_ABOVE_COMMAND>

# Or change Tomcat port in server.xml (default: 8080)
```

#### 2. Database Connection Failed
- Verify MySQL is running: `net start MySQL80`
- Check credentials in `DBConnection.java` and `context.xml`
- Ensure database `exam_system` exists
- Test connection: `mysql -u root -p -e "USE exam_system;"`

#### 3. Java Version Issues
```powershell
# Check Java version
java -version

# Set JAVA_HOME temporarily
$env:JAVA_HOME = "E:\Java 21.0"

# Or set permanently in System Environment Variables
```

#### 4. Build Failures
- Ensure all Maven dependencies are available in `.m2/repository`
- Check network connectivity for dependency downloads
- Verify Java compiler is accessible

#### 5. Tomcat Startup Issues
- Check `E:\Apache\logs\catalina.out` for error logs
- Verify `CATALINA_HOME` is set correctly
- Ensure Java 21 is in PATH

---

## 📁 Project Structure

```
SMS/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/stark/exam/
│   │   │       ├── controller/     # Servlet controllers
│   │   │       ├── dao/            # Data Access Objects
│   │   │       ├── model/          # Data models
│   │   │       ├── service/        # Business logic
│   │   │       ├── filter/         # Security filters
│   │   │       └── util/           # Utilities (DB connection)
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/          # JSP pages
│   │       │   ├── web.xml         # Web configuration
│   │       │   └── lib/            # Runtime libraries
│   │       ├── static/             # CSS, JS, images
│   │       └── META-INF/
│   │           └── context.xml     # Tomcat context config
├── target/                          # Build output
├── pom.xml                          # Maven configuration
├── build.ps1                        # PowerShell build script
├── run.ps1                          # PowerShell run script
└── README.md                        # This file
```

---

## 🔄 Development Workflow

### Making Changes

1. **Edit source files** in `src/main/java/` or `src/main/webapp/`
2. **Rebuild**: `.\build.ps1`
3. **Redeploy**: Copy WAR to Tomcat webapps
4. **Restart Tomcat**: Stop and start Tomcat service

### Hot Deployment (Development)

For faster development during active coding:

```powershell
# Use embedded Tomcat for faster restarts
.\run.ps1

# Or configure Tomcat for auto-reload
# Edit conf/server.xml and set:
# <Context path="/OnlineExaminationSystem" docBase="path/to/expanded/war" reloadable="true" />
```

---

## 🧪 Testing the Application

### Manual Testing Checklist

1. **Login Functionality**
   - [ ] Test with valid credentials
   - [ ] Test with invalid credentials
   - [ ] Test role-based redirects

2. **Student Features**
   - [ ] View dashboard
   - [ ] Take an exam
   - [ ] View results
   - [ ] Submit assignments
   - [ ] Check attendance

3. **Teacher Features**
   - [ ] Create exam
   - [ ] Add questions
   - [ ] Monitor live exam
   - [ ] Mark attendance
   - [ ] View student results

4. **Author Features**
   - [ ] Manage users
   - [ ] Post notices
   - [ ] Manage subjects
   - [ ] Generate admit cards
   - [ ] Create fee invoices

---

## 📊 Database Schema Reference

Complete database schema documentation is available in [`DATABASE_SCHEMA.md`](./DATABASE_SCHEMA.md).

Key tables include:
- `users` - User authentication and profiles
- `students` - Student academic records
- `faculty` - Teacher information
- `exams` - Exam schedules and configuration
- `questions` - Question bank
- `results` - Student exam results
- `attendance` - Attendance records
- `fees` - Fee payment tracking
- `assignments` - Assignment management
- `notices` - System announcements

---

## 🔧 Configuration Files

### Key Configuration Files

- **`pom.xml`** - Maven dependencies and build configuration
- **`web.xml`** - Servlet mapping and filters
- **`context.xml`** - Database connection pool configuration
- **`DBConnection.java`** - Database utility (fallback connection)

### Environment Variables (Optional)

```powershell
# Set these for production deployment
$env:DB_URL="jdbc:mysql://localhost:3306/exam_system"
$env:DB_USER="your_username"
$env:DB_PASSWORD="your_password"
$env:CATALINA_HOME="E:\Apache"
$env:JAVA_HOME="E:\Java 21.0"
```

---

## 🚀 Production Deployment

### Production Checklist

- [ ] Change all default passwords
- [ ] Use environment variables for sensitive data
- [ ] Enable HTTPS/SSL
- [ ] Configure proper logging
- [ ] Set up database backups
- [ ] Configure firewall rules
- [ ] Enable connection pooling
- [ ] Set up monitoring
- [ ] Implement error handling
- [ ] Test thoroughly with production data

### Tomcat Production Configuration

1. **Edit `conf/server.xml`**:
   - Change default port from 8080 to 80 or 443
   - Configure SSL/TLS
   - Set proper memory settings

2. **Edit `conf/context.xml`**:
   - Configure session timeout
   - Enable security constraints

3. **Memory Settings** (in `catalina.bat` or `setenv.bat`):
   ```batch
   set CATALINA_OPTS=-Xms512m -Xmx1024m -XX:MaxPermSize=256m
   ```

---

## 📚 Additional Documentation

- [`ARCHITECTURE.md`](./ARCHITECTURE.md) — Layered structure, security filters, exam lifecycle
- [`DATABASE_SCHEMA.md`](./DATABASE_SCHEMA.md) — Documentation for all 27 tables, columns, keys, and constraints
- [`SETUP_GUIDE.md`](./SETUP_GUIDE.md) — Tomcat, JNDI datasource, and development configuration
- [`UI_DESIGN_SYSTEM.md`](./UI_DESIGN_SYSTEM.md) — Design tokens, translucent glass panels, typography, and badges

---

## 👥 Role & Permission Matrix

The system strictly supports 3 roles defined in `users.role`:

| Role | Access URL | Capabilities |
|---|---|---|
| `student` | `/student/dashboard` | Online exams with proctoring timer, performance & grade summaries, attendance records, assignment submission, fee details, grievance submission, admit card status. |
| `teacher` | `/teacher/dashboard` | Create/schedule exams, 4-option MCQ question bank management, live proctoring desk & broadcasts, student score analytics, mark attendance, post assignments. |
| `author` | `/author/dashboard` | Full system user management, admit card clearance release/hold, campus notices & announcements, subject/course catalog management, fee invoice creation, system audit reports. |

---

## 🎨 Glassmorphism UI & Dynamic Search

- **Role Accents**: Visual theme dynamically adjusts per user context (`--accent-student: #7f9cf5`, `--accent-teacher: #34d399`, `--accent-author: #f59e0b`).
- **Interactive Search Filter**: Every scrollable table and list view includes a real-time search input box + button (`filterTable()` in `main.js`) for instant live filtering across rows.
- **Exam Security**: Tab switching and window focus loss trigger visual warning indicators (`pulseWarning`) and log proctoring heartbeats to the teacher live monitor.

---

## 📁 Key Documentation Files

- [`ARCHITECTURE.md`](./ARCHITECTURE.md) — Layered structure, security filters, exam lifecycle.
- [`DATABASE_SCHEMA.md`](./DATABASE_SCHEMA.md) — Documentation for all 27 tables, columns, keys, and constraints.
- [`SETUP_GUIDE.md`](./SETUP_GUIDE.md) — Tomcat, JNDI datasource, and development configuration.
- [`UI_DESIGN_SYSTEM.md`](./UI_DESIGN_SYSTEM.md) — Design tokens, translucent glass panels, typography, and badges.
#   S t u d e n t - M a n a g e m e n t - S y s t e m -  
 