package backend.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class LogService {
    public static void log(int UserID, String action) {
        Connection connection = ConnectionService.getConnectionService().getConnection();
        Date date = new Date(new java.util.Date().getTime());
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO log(user_info, date, instruction) " +
                    "VALUES (?, ?, ?)");
            statement.setInt(1, UserID);
            statement.setDate(2, date);
            statement.setString(3, action);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}