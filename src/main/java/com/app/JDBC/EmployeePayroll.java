package com.app.JDBC;

import java.time.LocalDate;

public class EmployeePayroll {
    private int id;
    private String name;
    private double salary;
    private LocalDate startDate;

    public EmployeePayroll(int id, String name, double salary, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmployeePayroll [id=" + id + ", name=" + name + ", salary=" + salary + ", startDate=" + startDate + "]";
    }
}
