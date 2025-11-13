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

    public List<EmployeePayroll> getEmployeesInDateRange(LocalDate start, LocalDate end) throws CustomDatabaseException {
        List<EmployeePayroll> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employee_payroll WHERE startDate BETWEEN ? AND ?"; 

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(start));
            preparedStatement.setDate(2, java.sql.Date.valueOf(end));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();

                employeeList.add(new EmployeePayroll(id, name, salary, startDate));
            }

        } catch (SQLException e) {
            throw new CustomDatabaseException("Error retrieving employees by date range", e);
        }

        return employeeList;
    }

}
