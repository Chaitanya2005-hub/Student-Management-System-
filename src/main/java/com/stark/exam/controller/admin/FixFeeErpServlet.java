package com.stark.exam.controller.admin;

import com.stark.exam.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/admin/fix-fee-erp")
public class FixFeeErpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            out.println("<h2>Fee ERP ID Column Fix</h2>");
            
            // 1. Add student_erp_id column if it doesn't exist
            try {
                String addColumn = "ALTER TABLE fees ADD COLUMN IF NOT EXISTS student_erp_id VARCHAR(50) NULL AFTER student_id";
                stmt.execute(addColumn);
                out.println("<p>✓ student_erp_id column added (or already exists)</p>");
            } catch (SQLException e) {
                out.println("<p>✗ Error adding column: " + e.getMessage() + "</p>");
            }
            
            // 2. Update existing fee records with ERP IDs
            String updateData = "UPDATE fees f " +
                               "LEFT JOIN students s ON f.student_id = s.id " +
                               "LEFT JOIN users u ON s.user_id = u.id " +
                               "SET f.student_erp_id = u.erp_id " +
                               "WHERE f.student_erp_id IS NULL";
            int updated = stmt.executeUpdate(updateData);
            out.println("<p>✓ Updated " + updated + " fee records with ERP IDs</p>");
            
            // 3. Verify the update
            var rs = stmt.executeQuery("SELECT COUNT(*) as total, COUNT(student_erp_id) as with_erp FROM fees");
            if (rs.next()) {
                out.println("<p>✓ Total fees: " + rs.getInt("total") + "</p>");
                out.println("<p>✓ Fees with ERP ID: " + rs.getInt("with_erp") + "</p>");
            }
            
            out.println("<p><strong>Fee ERP ID column fix completed successfully!</strong></p>");
            out.println("<p><a href='/author/admin-fees'>Return to Admin Fees</a></p>");
            
        } catch (SQLException e) {
            out.println("<p>Error fixing fee ERP ID column: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
