package com.app.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.JDBC.exception.CustomDatabaseException;

public class EmployeePayrollService {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "V@an123&";

    private List<EmployeePayroll> employeeList = new ArrayList<>();

    public EmployeePayroll addNewEmployee(String name, double salary, LocalDate startDate, String gender, String department)
            throws CustomDatabaseException {

        String insertQuery = "INSERT INTO employee_payroll (name, salary, start_date, gender, department) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setDouble(2, salary);
            ps.setDate(3, java.sql.Date.valueOf(startDate));
            ps.setString(4, gender);
            ps.setString(5, department);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new CustomDatabaseException("Failed to insert new employee into database", null);
            }

            ResultSet rs = ps.getGeneratedKeys();
            int generatedId = 0;
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

            EmployeePayroll newEmployee = new EmployeePayroll(generatedId, name, salary, startDate, gender, department);
            employeeList.add(newEmployee);

            System.out.println(" Employee added successfully: " + newEmployee);
            return newEmployee;

        } catch (SQLException e) {
            throw new CustomDatabaseException("Error adding new employee", e);
        }
    }

    public List<EmployeePayroll> getEmployeeList() {
        return employeeList;
    }
}
