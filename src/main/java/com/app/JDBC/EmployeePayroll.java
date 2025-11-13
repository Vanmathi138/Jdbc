package com.app.JDBC;

import java.time.LocalDate;

public class EmployeePayroll {
    private int id;
    private String name;
    private char gender;
    private double salary;
    private LocalDate startDate;

    public EmployeePayroll(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayroll(int id, String name, double salary, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }


    public void setSalary(double salary) {
        this.salary = salary;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public LocalDate getStartDate() { return startDate; }
    public char getGender() {return gender;}
    
    @Override
    public String toString() {
        return "EmployeePayroll [id=" + id + ", name=" + name + ", gender" + gender + ", salary=" + salary + ", startDate=" + startDate + "]";
    }

}
