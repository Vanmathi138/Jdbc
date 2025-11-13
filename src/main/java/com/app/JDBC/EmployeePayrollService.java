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
	
	 // Method to update salary
    public int updateEmployeeSalary(String name, double newSalary) throws CustomDatabaseException {
        String updateQuery = "UPDATE employee_payroll SET salary = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, newSalary);
            preparedStatement.setString(2, name);

            return preparedStatement.executeUpdate(); 

        } catch (SQLException e) {
            throw new CustomDatabaseException("Error updating salary in database", e);
        }
    }

    // Method to verify if salary is updated correctly
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
            throw new CustomDatabaseException("Error reading salary from database", e);
        }
    }
        
    //use case 1
    
    public List<EmployeePayroll> getEmployeePayrollData() throws CustomDatabaseException {
        List<EmployeePayroll> employeeList = new ArrayList<>();
        
        String query = "SELECT * FROM employee_payroll;";
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                
                EmployeePayroll emp = new EmployeePayroll(id, name, salary, startDate);
                employeeList.add(emp);
            }
            
        } catch (SQLException e) {
            throw new CustomDatabaseException("Error reading employee payroll data", e);
        }
        return employeeList;
    }
}
