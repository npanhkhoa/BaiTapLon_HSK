package dao;

import connectDB.ConnectDataBase;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;

public class NhanVien_Dao {

                public NhanVien getNhanVienByTenDangNhap(String tenDangNhap) {
            try {
                Connection conn = ConnectDataBase.getConnection();
                String sql = "SELECT * FROM NhanVien "
                           + "INNER JOIN TaiKhoan ON NhanVien.tenDangNhap = TaiKhoan.tenDangNhap "
                           + "WHERE TaiKhoan.tenDangNhap = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tenDangNhap);
                ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    String maNV = rs.getString("maNhanVien");
                    String tenNV = rs.getString("tenNhanVien");
                    int tuoi = rs.getInt("tuoi");
                    String diaChi = rs.getString("diaChi");
                    String sdt = rs.getString("soDienThoai");
                    String tenDangNhapNV = rs.getString("tenDangNhap");
                    String matKhau = rs.getString("matKhau");
                    boolean quyenHan = rs.getBoolean("quyen");
        
                    TaiKhoan taiKhoan = new TaiKhoan(tenDangNhapNV, matKhau, quyenHan);
                    return new NhanVien(maNV, tenNV, tuoi, diaChi, sdt, taiKhoan);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Trả về null nếu không tìm thấy
        }

    public boolean isMaNhanVienExists(String maNhanVien) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu mã nhân viên đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
