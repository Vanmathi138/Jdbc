package com.app.JDBC;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayroll {
    private int id;
    private String name;
    private String gender;
    private double salary;
    private LocalDate startDate;
    private List<String> departments;

    public EmployeePayroll(int id, String name, double salary, LocalDate startDate, String gender, List<String> departments) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
        this.gender = gender;
        this.departments = departments;
    }


	public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public LocalDate getStartDate() { return startDate; }
    public String getGender() { return gender; }
    public List<String> getDepartments() { return departments; }

    @Override
    public String toString() {
        return "EmployeePayroll [id=" + id + ", name=" + name + ", gender=" + gender + 
               ", salary=" + salary + ", startDate=" + startDate + 
               ", departments=" + departments + "]";
    }
}
