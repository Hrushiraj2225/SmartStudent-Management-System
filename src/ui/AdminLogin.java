package ui;

import java.util.Scanner;

public class AdminLogin {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    public static boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String user = sc.nextLine().trim();  // trim removes extra spaces
        System.out.print("Enter password: ");
        String pass = sc.nextLine().trim();

        if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password!");
            return false;
        }
    }
}