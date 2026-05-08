import dao.StudentDAO;
import model.Student;
import ui.AdminLogin;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("----- Welcome to SmartStudent Management System -----");

        // Admin login
        if (!AdminLogin.login()) {
            System.out.println("Invalid login! Exiting program...");
            sc.close();
            System.exit(0);
        }

        int choice;

        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add model.Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update model.Student");
            System.out.println("4. Delete model.Student");
            System.out.println("5. Search model.Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter roll number: ");
                    String rollNo = sc.nextLine();
                    System.out.print("Enter department: ");
                    String department = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine(); // consume newline

                    Student student = new Student(name, rollNo, department, email, phone, marks);
                    StudentDAO.addStudent(student);
                    break;

                case 2:
                    // View All Students
                    StudentDAO.viewAllStudents();
                    break;

                case 3:
                    // Update model.Student
                    System.out.print("Enter roll number of student to update: ");
                    String updateRollNo = sc.nextLine();

                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new department: ");
                    String newDept = sc.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = sc.nextLine();
                    System.out.print("Enter new phone: ");
                    String newPhone = sc.nextLine();
                    System.out.print("Enter new marks: ");
                    int newMarks = sc.nextInt();
                    sc.nextLine();

                    Student updatedStudent = new Student(newName, updateRollNo, newDept, newEmail, newPhone, newMarks);
                    StudentDAO.updateStudent(updatedStudent);
                    break;

                case 4:
                    // Delete model.Student
                    System.out.print("Enter roll number of student to delete: ");
                    String deleteRollNo = sc.nextLine();
                    StudentDAO.deleteStudent(deleteRollNo);
                    break;

                case 5:
                    // Search model.Student
                    System.out.println("Search by:");
                    System.out.println("a. Roll Number");
                    System.out.println("b. Department");
                    System.out.println("c. Marks Range");
                    System.out.print("Enter choice (a/b/c): ");
                    String searchChoice = sc.nextLine();

                    switch (searchChoice.toLowerCase()) {
                        case "a":
                            System.out.print("Enter roll number: ");
                            String searchRollNo = sc.nextLine();
                            StudentDAO.searchByRollNo(searchRollNo);
                            break;

                        case "b":
                            System.out.print("Enter department: ");
                            String searchDept = sc.nextLine();
                            StudentDAO.searchByDepartment(searchDept);
                            break;

                        case "c":
                            System.out.print("Enter minimum marks: ");
                            int minMarks = sc.nextInt();
                            System.out.print("Enter maximum marks: ");
                            int maxMarks = sc.nextInt();
                            sc.nextLine();
                            StudentDAO.searchByMarksRange(minMarks, maxMarks);
                            break;

                        default:
                            System.out.println("Invalid search option.");
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using SmartStudent. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}