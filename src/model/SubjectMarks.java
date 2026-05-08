package model;

public class SubjectMarks {

    // =======================
    // FIELDS
    // =======================
    private int id;
    private String rollNo;
    private String subjectName;
    private int marks;
    private String grade;

    // =======================
    // NO-ARG CONSTRUCTOR
    // =======================
    public SubjectMarks() {}

    // =======================
    // CONSTRUCTOR WITHOUT ID
    // =======================
    public SubjectMarks(String rollNo, String subjectName, int marks) {
        this.rollNo      = rollNo;
        this.subjectName = subjectName;
        this.marks       = marks;
        this.grade       = calculateGrade(marks);
    }

    // =======================
    // CONSTRUCTOR WITH ID
    // =======================
    public SubjectMarks(int id, String rollNo, String subjectName, int marks) {
        this.id          = id;
        this.rollNo      = rollNo;
        this.subjectName = subjectName;
        this.marks       = marks;
        this.grade       = calculateGrade(marks);
    }

    // =======================
    // GRADE CALCULATOR
    // =======================
    public static String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    // =======================
    // GETTERS
    // =======================
    public int getId()            { return id; }
    public String getRollNo()     { return rollNo; }
    public String getSubjectName(){ return subjectName; }
    public int getMarks()         { return marks; }
    public String getGrade()      { return grade; }

    // =======================
    // SETTERS
    // =======================
    public void setId(int id)                    { this.id = id; }
    public void setRollNo(String rollNo)         { this.rollNo = rollNo; }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public void setMarks(int marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }
    public void setGrade(String grade)           { this.grade = grade; }

    // =======================
    // TO STRING
    // =======================
    @Override
    public String toString() {
        return "SubjectMarks{" +
                "id=" + id +
                ", rollNo='" + rollNo + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", marks=" + marks +
                ", grade='" + grade + '\'' +
                '}';
    }
}