package com.app.JDBC;
import java.sql.*;

import com.app.JDBC.exception.CustomDatabaseException;

public class EmployeePayrollService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "V@an123&";

    // Update salary using PreparedStatement
    public int updateEmployeeSalaryUsingPreparedStatement(String name, double newSalary) throws CustomDatabaseException {
        String updateQuery = "UPDATE employee_payroll SET salary = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, newSalary);
            preparedStatement.setString(2, name);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new CustomDatabaseException("Error updating salary using PreparedStatement", e);
        }
    }

    // Fetch salary to verify sync
    public double getSalary(String name) throws CustomDatabaseException {
        String query = "SELECT salary FROM employee_payroll WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("salary");
            } else {
                throw new CustomDatabaseException("Employee not found: " + name, null);
            }

        } catch (SQLException e) {
            throw new CustomDatabaseException("Error reading salary from DB", e);
        }
    }
}
