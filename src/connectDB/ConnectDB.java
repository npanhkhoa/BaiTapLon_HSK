package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private Connection conn = null;  // non-static
    private static final ConnectDB instance = new ConnectDB();

    private ConnectDB() { }

    public static ConnectDB getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {  // instance method
        if (conn == null || conn.isClosed()) {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QUANLIQUANCAPHE";
            String user = "sa";
            String password = "sapassword";
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}
