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

    public EmployeePayroll addNewEmployee(String name, double salary, LocalDate startDate, String gender, List<String> departments)
            throws CustomDatabaseException {

    	String insertEmployee = "INSERT INTO employee_payroll (name, salary, start_date, gender) VALUES (?, ?, ?, ?)";
    	String insertPayroll = "INSERT INTO payroll_details (employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) VALUES (?, ?, ?, ?, ?, ?)";

    	// IMPORTANT FIXES
    	String insertDepartment = "INSERT INTO department (department_name) VALUES (?)";
    	String findDepartment = "SELECT department_id FROM department WHERE department_name = ?";
    	String insertEmpDept = "INSERT INTO employee_department (employee_id, department_id) VALUES (?, ?)";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false); 

            int empId = 0;
            try (PreparedStatement empStmt = connection.prepareStatement(insertEmployee, Statement.RETURN_GENERATED_KEYS)) {
                empStmt.setString(1, name);
                empStmt.setDouble(2, salary);
                empStmt.setDate(3, java.sql.Date.valueOf(startDate));
                empStmt.setString(4, gender);

                empStmt.executeUpdate();

                try (ResultSet rs = empStmt.getGeneratedKeys()) {
                    if (rs.next()) empId = rs.getInt(1);
                }
            }

            double deductions = salary * 0.2;
            double taxablePay = salary - deductions;
            double tax = taxablePay * 0.1;
            double netPay = salary - tax;

            try (PreparedStatement payStmt = connection.prepareStatement(insertPayroll)) {
                payStmt.setInt(1, empId);
                payStmt.setDouble(2, salary);
                payStmt.setDouble(3, deductions);
                payStmt.setDouble(4, taxablePay);
                payStmt.setDouble(5, tax);
                payStmt.setDouble(6, netPay);
                payStmt.executeUpdate();
            }

            for (String deptName : departments) {
                int deptId = 0;

                try (PreparedStatement findStmt = connection.prepareStatement(findDepartment)) {
                    findStmt.setString(1, deptName);
                    try (ResultSet rs = findStmt.executeQuery()) {
                        if (rs.next()) {
                            deptId = rs.getInt("dept_id");
                        }
                    }
                }

                if (deptId == 0) {
                	try (PreparedStatement findStmt = connection.prepareStatement(findDepartment)) {
                	    findStmt.setString(1, deptName);
                	    try (ResultSet rs = findStmt.executeQuery()) {
                	        if (rs.next()) {
                	            deptId = rs.getInt("department_id");
                	        }
                	    }
                	}

                }

                try (PreparedStatement linkStmt = connection.prepareStatement(insertEmpDept)) {
                    linkStmt.setInt(1, empId);
                    linkStmt.setInt(2, deptId);
                    linkStmt.executeUpdate();
                }
            }
            connection.commit();

            EmployeePayroll newEmp = new EmployeePayroll(empId, name, salary, startDate, gender, departments);
            employeeList.add(newEmp);

            System.out.println(" Employee with departments added successfully: " + newEmp);
            return newEmp;

        } catch (SQLException e) {
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new CustomDatabaseException("Error adding new employee with payroll & department details", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<EmployeePayroll> getEmployeeList() {
        return employeeList;
    }
}
