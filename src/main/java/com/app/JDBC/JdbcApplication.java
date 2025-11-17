package com.app.JDBC;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.app.JDBC.exception.CustomDatabaseException;

@SpringBootApplication
public class JdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);


        EmployeePayrollService dbService = new EmployeePayrollService();

        try {
            dbService.addNewEmployee("Priya", 55000.00, LocalDate.of(2024, 6, 12), "F", Arrays.asList("HR", "Finance"));
            dbService.addNewEmployee("Arun", 65000.00, LocalDate.of(2023, 4, 5), "M", Arrays.asList("IT", "Admin"));
        } catch (CustomDatabaseException e) {
            e.printStackTrace();
        }
    }
}
