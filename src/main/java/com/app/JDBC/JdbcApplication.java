package com.app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PseudoColumnUsage;
import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.JDBC.exception.CustomDatabaseException;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
		
		 EmployeePayrollService dbService = new EmployeePayrollService();
	        EmployeePayroll terisa = new EmployeePayroll(2, "Sai", 250000.00, LocalDate.of(2020, 1, 10));

	        try {
	            int result = dbService.updateEmployeeSalaryUsingPreparedStatement("Sai", 300000.00);
	            if (result > 0) {
	                System.out.println("Salary updated successfully in database using PreparedStatement.");
	            }

	            
	            double updatedSalary = dbService.getSalary("Sai");

	            terisa.setSalary(updatedSalary);

	            if (terisa.getSalary() == updatedSalary) {
	                System.out.println(" EmployeePayroll object is in sync with database.");
	            } else {
	                System.out.println("Mismatch between object and database salary.");
	            }

	        } catch (CustomDatabaseException e) {
	            e.printStackTrace();
	        }
	    
	}

}
