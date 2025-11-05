package gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import connectDB.ConnectDB;

public class TestConnection {
    public static void main(String[] args) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getInstance().getConnection();

            String sql = "SELECT TOP 5 maSanPham, tenSanPham FROM SanPham";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("maSanPham") + " - " + rs.getString("tenSanPham"));
            }

            ConnectDB.getInstance().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
