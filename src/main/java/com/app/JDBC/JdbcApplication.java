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

        LocalDate startDate = LocalDate.of(2022, 12, 31);
        LocalDate endDate = LocalDate.of(2024, 8, 13);

        try {
            List<EmployeePayroll> employees = dbService.getEmployeesInDateRange(startDate, endDate);

            System.out.println("Employees who joined between " + startDate + " and " + endDate + ":");
            for (EmployeePayroll emp : employees) {
                System.out.println(emp);
            }

        } catch (CustomDatabaseException e) {
            e.printStackTrace();
        }
	}

}
