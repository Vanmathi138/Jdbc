package com.app.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.JDBC.exception.CustomDatabaseException;

public class EmployeePayrollService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "V@an123&";

    private List<EmployeePayroll> employeeList = new ArrayList<>();

    public EmployeePayroll addNewEmployee(String name, double salary, LocalDate startDate, String gender, String department)
            throws CustomDatabaseException {

        String insertEmployee = "INSERT INTO employee_payroll (name, salary, start_date, gender, department) VALUES (?, ?, ?, ?, ?)";
        String insertPayroll = "INSERT INTO payroll_details (employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            connection.setAutoCommit(false);

            int generatedId = 0;
            try (PreparedStatement empStmt = connection.prepareStatement(insertEmployee, Statement.RETURN_GENERATED_KEYS)) {
                empStmt.setString(1, name);
                empStmt.setDouble(2, salary);
                empStmt.setDate(3, java.sql.Date.valueOf(startDate));
                empStmt.setString(4, gender);
                empStmt.setString(5, department);

                int empRows = empStmt.executeUpdate();
                if (empRows == 0) throw new SQLException("Employee insert failed");

                try (ResultSet rs = empStmt.getGeneratedKeys()) {
                    if (rs.next()) generatedId = rs.getInt(1);
                }
            }

            double deductions = salary * 0.2;
            double taxablePay = salary - deductions;
            double tax = taxablePay * 0.1;
            double netPay = salary - tax;
            try (PreparedStatement payStmt = connection.prepareStatement(insertPayroll)) {
                payStmt.setInt(1, generatedId);
                payStmt.setDouble(2, salary);
                payStmt.setDouble(3, deductions);
                payStmt.setDouble(4, taxablePay);
                payStmt.setDouble(5, tax);
                payStmt.setDouble(6, netPay);

                int payRows = payStmt.executeUpdate();
                if (payRows == 0) throw new SQLException("Payroll insert failed");
            }

            connection.commit();

            EmployeePayroll newEmployee = new EmployeePayroll(generatedId, name, salary, startDate, gender, department);
            employeeList.add(newEmployee);

            System.out.println("Employee & Payroll added successfully: " + newEmployee);
            return newEmployee;

        } catch (SQLException e) {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new CustomDatabaseException("Error adding new employee and payroll details", e);
        }
    }

    public List<EmployeePayroll> getEmployeeList() {
        return employeeList;
    }
}
