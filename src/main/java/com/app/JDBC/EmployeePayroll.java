package com.app.JDBC;

import java.time.LocalDate;

public class EmployeePayroll {
    private int id;
    private String name;
    private String gender;
    private double salary;
    private LocalDate startDate;
    private String department;

    public EmployeePayroll(int id, String name, double salary, LocalDate startDate, String gender, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
        this.gender = gender;
        this.department = department;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public double getSalary() { return salary; }
    public LocalDate getStartDate() { return startDate; }
    public String getDepartment() { return department; }

    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EmployeePayroll)) return false;
        EmployeePayroll other = (EmployeePayroll) obj;
        return Double.compare(salary, other.salary) == 0 &&
               name.equals(other.name) &&
               gender.equals(other.gender) &&
               startDate.equals(other.startDate) &&
               department.equals(other.department);
    }

    @Override
    public String toString() {
        return "EmployeePayroll [id=" + id + ", name=" + name + ", gender=" + gender +
               ", salary=" + salary + ", startDate=" + startDate +
               ", department=" + department + "]";
    }
}
