package com.app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PseudoColumnUsage;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.JDBC.exception.CustomDatabaseException;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
		EmployeePayrollService dbService = new EmployeePayrollService();
	    try {
	        dbService.getSalaryStatisticsByGender();
	    } catch (CustomDatabaseException e) {
	        e.printStackTrace();
	    }
	}

}
