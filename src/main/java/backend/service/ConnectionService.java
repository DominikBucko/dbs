package backend.service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private static ConnectionService connectionService = new ConnectionService();
    private static Connection conn;

    private ConnectionService() {
        createConnection();
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/asset_manager","postgres","BoboJeSmutny");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns JDBC Connection, opens one if none are open
     * @return JDBC Connection
     */
    public Connection getConnection() {
        try {
            if (conn.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Returns DataSource for creating JDBC templates, handles closed connections
     * @return SQL DataSource
     */
    public DataSource getCustomDataSource() {
        try {
            if (conn.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new CustomDataSource(conn);
    }



    public static ConnectionService getConnectionService() {
        return connectionService;
    }
}
