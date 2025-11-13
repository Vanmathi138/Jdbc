package com.app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PseudoColumnUsage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.JDBC.exception.CustomDatabaseException;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
		
		 EmployeePayrollService service = new EmployeePayrollService();
	        try {
	            System.out.println("Fetching employee payroll data...");
	            service.getEmployeePayrollData().forEach(System.out::println);
	        } catch (CustomDatabaseException e) {
	            e.printStackTrace();
	        }
	}

}
