package dao;

import db.DBConnection;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // =======================
    // ADD STUDENT
    // =======================
    public static void addStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_no, department, email, phone, marks) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getRollNo());
            ps.setString(3, student.getDepartment());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setInt(6, student.getMarks());

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student added: " + student.getName());
            else
                System.out.println("❌ Failed to add student!");

        } catch (Exception e) {
            System.out.println("Add Error: " + e.getMessage());
        }
    }

    // =======================
    // GET ALL STUDENTS
    // =======================
    public static List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("marks")
                );
                list.add(s);
            }

        } catch (Exception e) {
            System.out.println("Get Error: " + e.getMessage());
        }
        return list;
    }

    // =======================
    // VIEW ALL STUDENTS (Print to console)
    // =======================
    public static void viewAllStudents() {
        List<Student> list = getAllStudents();
        if (list.isEmpty()) {
            System.out.println("❌ No students found!");
            return;
        }
        System.out.println("\n========================================");
        System.out.printf("%-5s %-20s %-10s %-15s %-25s %-15s %-6s%n",
                "ID", "Name", "Roll No", "Department", "Email", "Phone", "Marks");
        System.out.println("========================================");
        for (Student s : list) {
            System.out.printf("%-5d %-20s %-10s %-15s %-25s %-15s %-6d%n",
                    s.getId(), s.getName(), s.getRollNo(),
                    s.getDepartment(), s.getEmail(),
                    s.getPhone(), s.getMarks());
        }
        System.out.println("========================================\n");
    }

    // =======================
    // SEARCH BY ROLL NO
    // =======================
    public static void searchByRollNo(String rollNo) {
        String sql = "SELECT * FROM students WHERE roll_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n✅ Student Found:");
                System.out.println("ID         : " + rs.getInt("id"));
                System.out.println("Name       : " + rs.getString("name"));
                System.out.println("Roll No    : " + rs.getString("roll_no"));
                System.out.println("Department : " + rs.getString("department"));
                System.out.println("Email      : " + rs.getString("email"));
                System.out.println("Phone      : " + rs.getString("phone"));
                System.out.println("Marks      : " + rs.getInt("marks"));
            } else {
                System.out.println("❌ No student found with Roll No: " + rollNo);
            }

        } catch (Exception e) {
            System.out.println("Search Error: " + e.getMessage());
        }
    }

    // =======================
    // SEARCH BY DEPARTMENT
    // =======================
    public static void searchByDepartment(String department) {
        String sql = "SELECT * FROM students WHERE department LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + department + "%");
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            System.out.println("\n===== Students in Department: " + department + " =====");
            while (rs.next()) {
                found = true;
                System.out.println("Name: " + rs.getString("name") +
                        " | Roll No: " + rs.getString("roll_no") +
                        " | Marks: " + rs.getInt("marks"));
            }
            if (!found)
                System.out.println("❌ No students found in department: " + department);

        } catch (Exception e) {
            System.out.println("Search Dept Error: " + e.getMessage());
        }
    }

    // =======================
    // SEARCH BY MARKS RANGE
    // =======================
    public static void searchByMarksRange(int minMarks, int maxMarks) {
        String sql = "SELECT * FROM students WHERE marks BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, minMarks);
            ps.setInt(2, maxMarks);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            System.out.println("\n===== Students with Marks between " + minMarks + " and " + maxMarks + " =====");
            while (rs.next()) {
                found = true;
                System.out.println("Name: " + rs.getString("name") +
                        " | Roll No: " + rs.getString("roll_no") +
                        " | Marks: " + rs.getInt("marks"));
            }
            if (!found)
                System.out.println("❌ No students found in this marks range!");

        } catch (Exception e) {
            System.out.println("Search Marks Error: " + e.getMessage());
        }
    }

    // =======================
    // UPDATE STUDENT
    // =======================
    public static void updateStudent(Student s) {
        String sql = "UPDATE students SET name=?, department=?, email=?, phone=?, marks=? WHERE roll_no=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            ps.setInt(5, s.getMarks());
            ps.setString(6, s.getRollNo());

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student updated successfully!");
            else
                System.out.println("❌ No student found with roll no: " + s.getRollNo());

        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }

    // =======================
    // UPDATE MARKS ONLY
    // =======================
    public static void updateMarks(String rollNo, int newMarks) {
        String sql = "UPDATE students SET marks=? WHERE roll_no=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newMarks);
            ps.setString(2, rollNo);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Marks updated successfully!");
            else
                System.out.println("❌ No student found with roll no: " + rollNo);

        } catch (Exception e) {
            System.out.println("Update Marks Error: " + e.getMessage());
        }
    }

    // =======================
    // DELETE STUDENT
    // =======================
    public static void deleteStudent(String rollNo) {
        String sql = "DELETE FROM students WHERE roll_no=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rollNo);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student deleted successfully!");
            else
                System.out.println("❌ No student found with roll no: " + rollNo);

        } catch (Exception e) {
            System.out.println("Delete Error: " + e.getMessage());
        }
    }

    // =======================
    // SEARCH STUDENTS (returns list)
    // =======================
    public static List<Student> searchStudents(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ? OR roll_no LIKE ? OR department LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("marks")
                );
                list.add(s);
            }

        } catch (Exception e) {
            System.out.println("Search Error: " + e.getMessage());
        }
        return list;
    }
}