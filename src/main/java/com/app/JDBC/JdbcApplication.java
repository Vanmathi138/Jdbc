package com.app.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PseudoColumnUsage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/payroll_service";
		String username = "root";
		String password = "V@an123&";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded successfully");
			
			Connection con = DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Connected to database successfully");
			
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
