package com.devresearch.model;

public class InputDataObject {

    private Integer empId;
    private String firstName;
    private String lastName;
    private Double salary;
    private Integer managerId;

    public InputDataObject(Integer empId, String firstName, String lastName, double salary, Integer managerId) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public Integer getManagerId() {
        return managerId;
    }

    @Override
    public String toString() {
        return "InputDataObject{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", managerId=" + managerId +
                '}';
    }
}
