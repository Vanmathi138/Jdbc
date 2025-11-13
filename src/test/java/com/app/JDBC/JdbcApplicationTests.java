package com.app.JDBC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.app.JDBC.exception.CustomDatabaseException;

@SpringBootTest
class JdbcApplicationTests {

    @Test
    public void givenNewEmployee_WhenAdded_ShouldMatchWithDB() throws CustomDatabaseException {
        EmployeePayrollService service = new EmployeePayrollService();
        EmployeePayroll actual = service.addNewEmployee(
                "Priya",
                55000.00,
                LocalDate.of(2024, 6, 12),
                "F",
                "HR"
        );

        assertEquals("Priya", actual.getName());
        assertEquals(55000.00, actual.getSalary());
        assertEquals("F", actual.getGender());
        assertEquals(LocalDate.of(2024, 6, 12), actual.getStartDate());
    }
}
