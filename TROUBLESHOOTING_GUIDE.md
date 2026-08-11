# 🔧 Troubleshooting Guide for Admit Card & Exam Display Issues

## 🚨 Current Issues
1. **Admit Card Download** - Not working for students
2. **Exam Display** - Exams not showing in student dashboard

## 🔍 Root Cause Analysis

### Issue 1: Admit Card Download Not Working
**Possible Causes:**
- No admit card record exists for the student in the database
- Admit card status is not "Released"
- Student record is not properly linked to admit_cards table
- Database connection issues

### Issue 2: Exams Not Displaying
**Possible Causes:**
- No exams exist in the database
- Exam status is not "active" or "scheduled"
- No questions associated with exams
- Database connection issues

## 🛠️ Step-by-Step Fixes

### Step 1: Check Your Database Data

Connect to MySQL and run the diagnostic script:

```bash
mysql -u root -p
USE exam_system;
source E:\SMS\check_and_fix_data.sql;
```

This will:
- Check if tables exist
- Show all users in the system
- Show students and their admit card status
- Show exams and their status
- Automatically create admit cards for students who don't have them

### Step 2: Add Sample Data (If Database is Empty)

If your database is empty or has no data, run:

```bash
mysql -u root -p
USE exam_system;
source E:\SMS\setup_sample_data.sql;
```

This will create:
- 3 sample exams (1 active, 2 scheduled)
- Sample questions for the active exam
- Sample admit card records

### Step 3: Manual Data Fixes

If the automated scripts don't work, you can manually fix the data:

#### Fix Admit Cards:
```sql
-- Check current admit cards
SELECT * FROM admit_cards;

-- Create admit card for a specific student (replace STUDENT_ID with actual ID)
INSERT INTO admit_cards (student_id, status) VALUES (1, 'Released');

-- Update existing admit card to Released
UPDATE admit_cards SET status = 'Released' WHERE student_id = 1;

-- Check which students don't have admit cards
SELECT u.id, u.username, u.full_name
FROM users u
WHERE u.role = 'student'
AND u.id NOT IN (SELECT student_id FROM admit_cards);
```

#### Fix Exams:
```sql
-- Check current exams
SELECT * FROM exams;

-- Create a sample exam
INSERT INTO exams (title, exam_date, start_time, duration_minutes, status, requires_approval)
VALUES ('Sample Exam', '2026-08-15', '10:00:00', 60, 'active', 0);

-- Activate an existing exam
UPDATE exams SET status = 'active' WHERE id = 1;

-- Create sample questions for exam ID 1
INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer)
VALUES 
(1, 'What is 2+2?', '3', '4', '5', '6', 'b'),
(1, 'What is the capital of India?', 'Mumbai', 'Delhi', 'Kolkata', 'Chennai', 'b');
```

### Step 4: Check User Session and Authentication

Make sure you're logged in correctly:

1. Login to the application
2. Check the debug information on the student dashboard
3. Verify your user ID and role are correct

### Step 5: Check Tomcat Logs

If issues persist, check the Tomcat logs for errors:

```bash
# Windows
type E:\Apache\logs\catalina.out

# Or open the file in a text editor
notepad E:\Apache\logs\catalina.out
```

Look for:
- SQL errors
- NullPointerException
- Database connection errors
- Any exceptions related to admit cards or exams

## 🧪 Testing the Fixes

### Test Admit Card Download:
1. Login as a student
2. Navigate to "Admit Card" section
3. Check the debug information:
   - User ID should be shown
   - Admit Card status should be shown
4. If status is "Released", click "Download Admit Card"
5. The file should download as `admit_card_[USER_ID].html`

### Test Exam Display:
1. Login as a student
2. Navigate to Student Dashboard
3. Check the debug information:
   - Total Exams count
   - Active Exams count
   - Scheduled Exams count
4. If counts are 0, run the data setup scripts
5. If counts show data but exams don't display, check exam status

## 📋 Debug Information Added

I've added comprehensive debug information to both pages:

### Admit Card Page Debug Info:
- User ID
- Admit Card Found/Not Found
- Admit Card Status (if found)
- Error Parameters

### Student Dashboard Debug Info:
- Total Exams count
- Active Exams count
- Scheduled Exams count
- Announcements count

## 🎯 Expected Behavior After Fixes

### Admit Card Page:
- **If admit card exists and is Released**: Show "ADMIT CARD RELEASED" with download button
- **If admit card exists but is Blocked**: Show "ADMIT CARD BLOCKED" with contact info
- **If no admit card exists**: Show "ADMIT CARD NOT FOUND" with instructions

### Student Dashboard:
- **If active exams exist**: Show active exams with "Start Exam" buttons
- **If scheduled exams exist**: Show scheduled exams in table format
- **If no exams exist**: Show helpful message with total exam count

## 🔧 Common Issues and Solutions

### Issue: "No admit card found"
**Solution**: Run the `check_and_fix_data.sql` script to create admit cards for all students

### Issue: "No active exams right now"
**Solution**: 
1. Check if exams exist: `SELECT * FROM exams;`
2. If no exams, run `setup_sample_data.sql`
3. If exams exist but not active: `UPDATE exams SET status = 'active' WHERE id = 1;`

### Issue: Download button not working
**Solution**:
1. Check browser console for JavaScript errors
2. Check Tomcat logs for server errors
3. Verify admit card status is "Released" (case-sensitive)

### Issue: Database connection errors
**Solution**:
1. Check MySQL is running: `net start MySQL80`
2. Verify database credentials in `DBConnection.java`
3. Test connection: `mysql -u root -p -e "USE exam_system; SHOW TABLES;"`

## 📞 Getting Help

If issues persist after following these steps:

1. **Check Tomcat Logs**: `E:\Apache\logs\catalina.out`
2. **Run Database Diagnostics**: `check_and_fix_data.sql`
3. **Verify User Session**: Make sure you're properly logged in
4. **Check Browser Console**: Look for JavaScript errors
5. **Test Database Connection**: Ensure MySQL is accessible

## 🚀 Quick Fix Command

If you want to fix everything at once, run this:

```bash
mysql -u root -p exam_system < E:\SMS\check_and_fix_data.sql
mysql -u root -p exam_system < E:\SMS\setup_sample_data.sql
```

Then restart the application and test again.