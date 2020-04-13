package backend.transactions;

import backend.service.CustomDataSource;

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
            conn = DriverManager.getConnection("jdbc:postgresql://dbs.fiit.uk.to/asset_manager","postgres","BoboJeSmutny");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public DataSource getCustomDataSource() {
        return new CustomDataSource(conn);
    }



    public static ConnectionService getConnectionService() {
        return connectionService;
    }
}
