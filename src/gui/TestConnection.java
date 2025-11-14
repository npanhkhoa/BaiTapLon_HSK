package gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import connectDB.ConnectDB;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Lấy connection trực tiếp, tự đảm bảo conn không null
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT TOP 5 maSanPham, tenSanPham FROM SanPham";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("maSanPham") + " - " + rs.getString("tenSanPham"));
            }

            // Đóng connection
            ConnectDB.getInstance().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
