package com.app.JDBC;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.JDBC.exception.CustomDatabaseException;

import java.sql.*;
import java.util.*;

import com.app.JDBC.exception.CustomDatabaseException;

public class EmployeePayrollService {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "V@an123&";

    public void getSalaryStatisticsByGender() throws CustomDatabaseException {
        String query = "SELECT gender, SUM(salary) AS total_salary, " +
                       "AVG(salary) AS average_salary, MIN(salary) AS min_salary, " +
                       "MAX(salary) AS max_salary, COUNT(*) AS count_employees " +
                       "FROM employee_payroll " +
                       "GROUP BY gender";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String gender = rs.getString("gender");
                double sum = rs.getDouble("total_salary");
                double avg = rs.getDouble("average_salary");
                double min = rs.getDouble("min_salary");
                double max = rs.getDouble("max_salary");
                int count = rs.getInt("count_employees");

                System.out.println("Gender: " + gender);
                System.out.println("Total Salary: " + sum);
                System.out.println("Average Salary: " + avg);
                System.out.println("Minimum Salary: " + min);
                System.out.println("Maximum Salary: " + max);
                System.out.println("Number of Employees: " + count);
                System.out.println("----------------------------");
            }

        } catch (SQLException e) {
            throw new CustomDatabaseException("Error retrieving salary statistics by gender", e);
        }
    }
}
