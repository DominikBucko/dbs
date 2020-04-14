package backend;

import java.sql.*;

public class Mapping {
    public static Connection getConnection (String USERNAME, String PASSWORD) {
//        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        String URL = "jdbc:postgresql://127.0.0.1/asset_manager";
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}