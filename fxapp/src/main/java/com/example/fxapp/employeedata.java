package com.example.fxapp;

public class employeedata {
    private int employee_id;
    private String firstName;
    private String lastName;
    private String gender;
    private String PhoneNumber;
    private int DepartmentId;
    private int Project_id;
    private int manager_id;
    private String email;

    private int salary;
    private String position;


    public employeedata(int employee_id, String firstName, String lastName, String gender, String PhoneNumber, int DepartmentId, int Project_id, int manager_id, String email, int salary, String position) {
        this.employee_id = employee_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.PhoneNumber = PhoneNumber;
        this.DepartmentId = DepartmentId;
        this.Project_id = Project_id;
        this.manager_id = manager_id;
        this.email = email;
        this.salary = salary;
        this.position = position;
    }
    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public int getProject_id() {
        return Project_id;
    }

    public void setProject_id(int project_id) {
        Project_id = project_id;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }




}