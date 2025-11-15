package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDataBase {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLiCoffee;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8";
    private static final String USER = "sa";
    private static final String PASSWORD = "sapassword";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Connect to SQL Server
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static ConnectDataBase getInstance(){
        return new ConnectDataBase();
    }
}