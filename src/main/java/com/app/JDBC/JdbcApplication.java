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
        EmployeePayroll emp = new EmployeePayroll("Sai", 0);

		 try {
	          
	            int result = dbService.updateEmployeeSalary("Sai", 3000000.00);
	            if (result > 0) {
	                System.out.println("Salary updated successfully in DB!");
	            }

	          
	            double updatedSalary = dbService.getSalary("Sai");
	            emp.setSalary(updatedSalary);

	            if (emp.getSalary() == updatedSalary) {
	                System.out.println("EmployeePayroll object synced with DB successfully!");
	            }

	        } catch (CustomDatabaseException e) {
	            e.printStackTrace();
	        }

	}

}
