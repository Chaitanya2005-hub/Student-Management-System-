# Setup Guide

Target stack: Java (11+ recommended), Apache Maven, Apache Tomcat 9 or 10, MySQL 8.x/9.x, VS Code with the "Extension Pack for Java" + "Community Server Connectors" (or Tomcat/MicroProfile equivalent).

> ⚠️ Tomcat version note: if targeting **Tomcat 10+**, the app must use the `jakarta.servlet.*` package namespace, not `javax.servlet.*`. Pick one and be consistent across `pom.xml` dependencies, `web.xml`, and all servlet imports.

---

## 1. Prerequisites

- JDK 11 or later on PATH (`java -version`)
- Apache Maven 3.8+ (`mvn -version`)
- Apache Tomcat 9.x (javax) or 10.x (jakarta)
- MySQL Server 8.0+ (the source dump was taken from 9.5) with a user that can create/read the `exam_system` database
- MySQL Connector/J (JDBC driver) — add as a Maven dependency, do not manually drop the jar into Tomcat's `lib/` unless you intend a shared driver across all deployed apps

---

## 2. Database Setup

The database already exists as `exam_system` with 27 tables and live data (see `DATABASE_SCHEMA.md`). To set up a fresh environment from a dump:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS exam_system CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;"
mysql -u root -p exam_system < exam_system_dump.sql
```

If you only have `SHOW CREATE TABLE` output (as documented in `DATABASE_SCHEMA.md`) rather than a full dump, reconstruct the schema by running each `CREATE TABLE` statement in dependency order — parent tables first: `users` → `departments` → `students`/`faculty` → `courses`/`subjects` → everything else.

To confirm the connection and table count:

```sql
USE exam_system;
SHOW TABLES;  -- should return 27 rows
```

---

## 3. `pom.xml` — Core Dependencies

```xml
<dependencies>
    <!-- Servlet API — provided by Tomcat at runtime -->
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSTL for JSP -->
    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
        <version>3.0.1</version>
    </dependency>

    <!-- MySQL JDBC driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.4.0</version>
    </dependency>
</dependencies>
```

Use `javax.servlet`/`javax.servlet.jsp.jstl` groupIds instead if targeting Tomcat 9.

Packaging must be `war`:

```xml
<packaging>war</packaging>
```

---

## 4. Datasource Configuration (Tomcat JNDI)

In `META-INF/context.xml` (or the Tomcat-level `conf/context.xml` for a shared pool):

```xml
<Context>
    <Resource name="jdbc/examSystemDB"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="20"
              maxIdle="10"
              minIdle="2"
              maxWaitMillis="10000"
              username="exam_app_user"
              password="CHANGE_ME"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/exam_system?useSSL=false&amp;serverTimezone=UTC&amp;allowPublicKeyRetrieval=true" />
</Context>
```

Declare the matching resource-ref in `web.xml`:

```xml
<resource-ref>
    <res-ref-name>jdbc/examSystemDB</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```

Never use the MySQL `root` account for the application datasource — create a dedicated `exam_app_user` scoped to `exam_system` only:

```sql
CREATE USER 'exam_app_user'@'localhost' IDENTIFIED BY 'CHANGE_ME';
GRANT SELECT, INSERT, UPDATE, DELETE ON exam_system.* TO 'exam_app_user'@'localhost';
FLUSH PRIVILEGES;
```

---

## 5. VS Code Setup

1. Install extensions: **Extension Pack for Java**, **Community Server Connectors** (Tomcat support), **Tomcat for Java**.
2. Open the project root (the folder containing `pom.xml`).
3. Add a Tomcat server via the Servers view (Community Server Connectors) pointing at your local Tomcat install directory.
4. Build the WAR: `mvn clean package` — output lands in `target/*.war`.
5. Right-click the server → **Add or Remove Deployment** → select the built WAR (or add the project directly for exploded-directory deployment during development).
6. Start the server from the Servers view; check the Tomcat console output/log for startup errors.

---

## 6. Running & Verifying

```bash
mvn clean package
# copy target/*.war to $TOMCAT_HOME/webapps/, or deploy via VS Code Servers view
$TOMCAT_HOME/bin/startup.sh    # or startup.bat on Windows
```

Visit `http://localhost:8080/<context-path>/` — should resolve to `index.jsp` (login page). Confirm DB connectivity by logging in with a known row from `users` (a `role = 'student'` or `role = 'teacher'` account).

Common failure points:
- **`ClassNotFoundException: com.mysql.cj.jdbc.Driver`** — driver jar not on Tomcat's classpath; confirm it's packaged in `WEB-INF/lib` via Maven (do not mark it `provided`).
- **`Access denied for user`** — check `context.xml` credentials against the MySQL user created in step 4.
- **`javax` vs `jakarta` mismatch** — mixing servlet API versions between `web.xml`, imports, and Tomcat major version is the most common cause of a blank 404/500 on deploy.

---

## 7. Logging & Config Hygiene

- Never commit `context.xml` with real credentials — use a `context.xml.example` template and gitignore the real file, or externalize credentials to environment variables read at startup via a `ServletContextListener`.
- Route all logging through `java.util.logging` or SLF4J+Logback rather than `System.out.println`, especially in DAO/service layers handling exam scoring and proctoring events.
