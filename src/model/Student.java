package model;

public class Student {
    private int id;
    private String name;
    private String rollNo;
    private String department;
    private String email;
    private String phone;
    private int marks;

    // No-argument constructor
    public Student() {
    }

    // Constructor without id (for new student insert)
    public Student(String name, String rollNo, String department, String email, String phone, int marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.marks = marks;
    }

    // Constructor with id (for existing student update, fetch)
    public Student(int id, String name, String rollNo, String department, String email, String phone, int marks) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.marks = marks;
    }

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}