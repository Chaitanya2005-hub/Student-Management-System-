package com.stark.exam.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DataSource dataSource = null;
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/exam_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = "root123";

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/examSystemDB");
        } catch (NamingException e) {
            // Not in Tomcat container context, fallback to DriverManager mode for standalone run/tests
            dataSource = null;
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found on classpath", e);
        }
        return DriverManager.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASS);
    }
}
