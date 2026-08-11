package com.stark.exam.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FixFeeErpColumn {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Starting fee ERP ID column fix...");
            
            // 1. Add student_erp_id column if it doesn't exist
            try {
                String addColumn = "ALTER TABLE fees ADD COLUMN IF NOT EXISTS student_erp_id VARCHAR(50) NULL AFTER student_id";
                stmt.execute(addColumn);
                System.out.println("✓ student_erp_id column added (or already exists)");
            } catch (SQLException e) {
                System.out.println("✗ Error adding column: " + e.getMessage());
            }
            
            // 2. Update existing fee records with ERP IDs
            String updateData = "UPDATE fees f " +
                               "LEFT JOIN students s ON f.student_id = s.id " +
                               "LEFT JOIN users u ON s.user_id = u.id " +
                               "SET f.student_erp_id = u.erp_id " +
                               "WHERE f.student_erp_id IS NULL";
            int updated = stmt.executeUpdate(updateData);
            System.out.println("✓ Updated " + updated + " fee records with ERP IDs");
            
            // 3. Verify the update
            var rs = stmt.executeQuery("SELECT COUNT(*) as total, COUNT(student_erp_id) as with_erp FROM fees");
            if (rs.next()) {
                System.out.println("✓ Total fees: " + rs.getInt("total"));
                System.out.println("✓ Fees with ERP ID: " + rs.getInt("with_erp"));
            }
            
            System.out.println("Fee ERP ID column fix completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error fixing fee ERP ID column: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
