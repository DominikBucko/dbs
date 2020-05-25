package backend.service;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionService {
    private static final Logger LOGGER = Logger.getLogger(ConnectionService.class.getName());

    private static ConnectionService connectionService = new ConnectionService();
    private static Connection conn;

    private ConnectionService() {
        createConnection();
    }

    private void createConnection() {
        LOGGER.info("CREATING CONNECTION TO DATABASE");
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("db.properties");
            props.load(in);
            in.close();
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            conn = DriverManager.getConnection(url, username, password);
//            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/asset_manager","postgres","BoboJeSmutny");
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns JDBC Connection, opens one if none are open
     * @return JDBC Connection
     */
    public Connection getConnection() {
        LOGGER.info("GETTING CONNECTION TO DATABASE");
        try {
            if (conn.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
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
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return new CustomDataSource(conn);
    }



    public static ConnectionService getConnectionService() {
        return connectionService;
    }
}
