package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection con = null;
	private static final ConnectDB instance = new ConnectDB();

	public static ConnectDB getInstance() {
		return instance;
	}

	public static Connection getConnection() {
		return con;
	}

	public void connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=QUANLIQUANCAPHE";
		String user = "sa";
		String password = "sapassword";
		con = DriverManager.getConnection(url, user, password);
	}

	public void disconnect() throws SQLException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}