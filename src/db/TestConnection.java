package db;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("Connection Test Successful");
            } else {
                System.out.println("Connection Test Failed");
            }
        } catch (Exception e) {
            System.out.println("Connection Test Failed due to SQLException");
            e.printStackTrace();
        }
    }
}