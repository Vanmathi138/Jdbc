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
	
	public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded!");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connection Established!");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!");
        }
        return connection;
    }

	private List<EmployeePayroll> employeeList = new ArrayList<>();

	public void addEmployeeFullTransaction(String name, String gender, double salary, LocalDate startDate,
			List<String> deptNames) throws SQLException {
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			String empQuery = "INSERT INTO employee_payroll (name, gender, salary, start_date) VALUES (?, ?, ?, ?)";
			PreparedStatement empStmt = connection.prepareStatement(empQuery, Statement.RETURN_GENERATED_KEYS);
			empStmt.setString(1, name);
			empStmt.setString(2, gender);
			empStmt.setDouble(3, salary);
			empStmt.setDate(4, Date.valueOf(startDate));
			empStmt.executeUpdate();
			ResultSet rs = empStmt.getGeneratedKeys();
			rs.next();
			int empId = rs.getInt(1);

			double deductions = salary * 0.2;
			double taxable = salary - deductions;
			double tax = taxable * 0.1;
			double net = salary - tax;

			String payQuery = "INSERT INTO payroll_details (employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement payStmt = connection.prepareStatement(payQuery);
			payStmt.setInt(1, empId);
			payStmt.setDouble(2, salary);
			payStmt.setDouble(3, deductions);
			payStmt.setDouble(4, taxable);
			payStmt.setDouble(5, tax);
			payStmt.setDouble(6, net);
			payStmt.executeUpdate();

			for (String dept : deptNames) {
				PreparedStatement deptStmt = connection.prepareStatement(
						"INSERT INTO employee_department (employee_id, dept_id) VALUES (?, (SELECT dept_id FROM department WHERE dept_name = ?))");
				deptStmt.setInt(1, empId);
				deptStmt.setString(2, dept);
				deptStmt.executeUpdate();
			}

			connection.commit();
			System.out.println("Employee added successfully across all tables.");

		} catch (SQLException e) {
			if (connection != null)
				connection.rollback();
			throw e;
		} finally {
			if (connection != null)
				connection.setAutoCommit(true);
		}
	}

	public List<EmployeePayroll> getEmployeeList() {
		return employeeList;
	}
}
