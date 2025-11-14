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
		String url = "jdbc:sqlserver://LAPTOP-UAD09BFR:1455;"
		           + "databaseName=QUANLIQUANCAPHE;"
		           + "encrypt=false;"
		           + "trustServerCertificate=true;";

		String user = "sa";
		String password = "huyenpassword";
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
