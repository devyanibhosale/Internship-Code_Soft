package com.example.atminterface.database;

import java.sql.*;

public class DBConnection {
    private final static String DB_URL = "jdbc:postgresql://localhost:5432/atm_interface";
    private final static String DB_USERNAME = "postgres";
    private final static String DB_PASSWORD = "postgres";


    public Connection getConnection() {


        try {

            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static void rollBack(Connection connection){
        try {
            if (null != connection){
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection, PreparedStatement ps, ResultSet rs) {
        try {
            if (null != connection) {
                connection.close();
            }
            if (null != ps) {
                ps.close();
            }
            if (null != rs) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
