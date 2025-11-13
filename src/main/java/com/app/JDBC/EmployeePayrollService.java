package com.app.JDBC;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.JDBC.exception.CustomDatabaseException;

public class EmployeePayrollService {
    
    public List<EmployeePayroll> getEmployeePayrollData() throws CustomDatabaseException {
        List<EmployeePayroll> employeeList = new ArrayList<>();
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
        String username = "root";
        String password = "V@an123&";
        
        String query = "SELECT * FROM employee_payroll;";
        
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
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
