package controller;

import java.util.Date;

public class Employee {

    private Integer salarie;
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String position;
    private String phone;
    private Date date;
    private String image;
    
    public Employee(Integer employeeId, String firstName, String lastName, String position, Integer employeeSalary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salarie = employeeSalary;
    }
    public Employee(Integer employeeId, String firstName, String lastName, String gender, String position, String phone,
            Date date, String image) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.position = position;
        this.date = date;
        this.image = image;
    }


    public Integer getEmployeeId() {
        return this.employeeId;
    }

    public Integer getSalarie(){
        return this.salarie;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPosition() {
        return this.position;
    }

    public Date getDate() {
        return this.date;
    }

    public String getImage() {
        return this.image;
    }

}
