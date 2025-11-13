package com.app.JDBC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.app.JDBC.exception.CustomDatabaseException;

@SpringBootTest
class JdbcApplicationTests {

	@Test
	public void givenNewEmployee_WhenAdded_ShouldInsertIntoBothTables() throws CustomDatabaseException {
	    EmployeePayrollService service = new EmployeePayrollService();
	    EmployeePayroll emp = service.addNewEmployee("Kavi", 50000.00, LocalDate.of(2024, 3, 1), "F", "IT");

	    assertNotNull(emp);
	    assertEquals("Kavi", emp.getName());
	}

}
