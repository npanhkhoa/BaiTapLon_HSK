package dao;

import connectDB.ConnectDataBase;
import entity.NhanVien;
import entity.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan_DAO {

    public List<NhanVien> getAllThongTinTaiKhoan() {
        List<NhanVien> dsThongTinNhanVien = new ArrayList<>();

        try (Connection conn = ConnectDataBase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT nv.maNhanVien, nv.tenNhanVien, nv.tuoi, nv.diaChi, "
                             + "nv.soDienThoai, nv.tenDangNhap, tk.matKhau, tk.quyen "
                             + "FROM [dbo].[NhanVien] nv "
                             + "JOIN [dbo].[TaiKhoan] tk ON nv.tenDangNhap = tk.tenDangNhap")) {

            while (rs.next()) {
                String maNV = rs.getString(1);
                String tenNV = rs.getString(2);
                int tuoi = rs.getInt(3);
                String diaChi = rs.getString(4);
                String soDienThoai = rs.getString(5);
                String tenDangNhap = rs.getString(6);
                String pw = rs.getString(7);
                boolean quyen = rs.getBoolean(8);

                TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, pw, quyen);
                NhanVien nv = new NhanVien(maNV, tenNV, tuoi, diaChi, soDienThoai, taiKhoan);
                dsThongTinNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsThongTinNhanVien;
    }

    public boolean addTaiKhoan(NhanVien nv) {
        Connection conn = null;
        PreparedStatement stmtNV = null;
        PreparedStatement stmtTK = null;
        boolean success = false;

        try {
            conn = ConnectDataBase.getConnection();
            if (conn == null) return false;

            conn.setAutoCommit(false);

            // Thêm tài khoản
            String sqlTK = "INSERT INTO [dbo].[TaiKhoan] (tenDangNhap, matKhau, quyen) VALUES (?, ?, ?)";
            stmtTK = conn.prepareStatement(sqlTK);
            stmtTK.setString(1, nv.getTaiKhoan().getTenDangNhap());
            stmtTK.setString(2, nv.getTaiKhoan().getMatKhau());
            stmtTK.setBoolean(3, nv.getTaiKhoan().isQuyenHan());
            stmtTK.executeUpdate();

            // Thêm nhân viên
            String sqlNV = "INSERT INTO [dbo].[NhanVien] (maNhanVien, tenNhanVien, tuoi, "
                    + "diaChi, soDienThoai, tenDangNhap) VALUES (?, ?, ?, ?, ?, ?)";
            stmtNV = conn.prepareStatement(sqlNV);
            stmtNV.setString(1, nv.getMaNhanVien());
            stmtNV.setString(2, nv.getTenNhanVien());
            stmtNV.setInt(3, nv.getTuoi());
            stmtNV.setString(4, nv.getDiaChi());
            stmtNV.setString(5, nv.getSoDienThoai());
            stmtNV.setString(6, nv.getTaiKhoan().getTenDangNhap());
            stmtNV.executeUpdate();



            conn.commit();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // Đóng tất cả tài nguyên
            try {
                if (stmtNV != null) stmtNV.close();
                if (stmtTK != null) stmtTK.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }


    public List<TaiKhoan> printAllTaiKhoan() {
        List<TaiKhoan> dsTk = new ArrayList<>();

        try (Connection conn = ConnectDataBase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT tenDangNhap, matKhau, quyen FROM [dbo].[TaiKhoan]")) {

            while (rs.next()) {
                String tenDangNhap = rs.getString(1);
                String matKhau = rs.getString(2);
                boolean quyenHan = rs.getBoolean(3);
                dsTk.add(new TaiKhoan(tenDangNhap, matKhau, quyenHan));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsTk;
    }

    //xóa tài khoản (bảng nhân viên và bảng tài khoản)
    //giải thích cách hoạt động của phương thức này nhé:

    public boolean deleteTaiKhoan(String tenDangNhap) {
        Connection conn = null;
        PreparedStatement stmtNV = null;
        PreparedStatement stmtTK = null;
        boolean success = false;

        try {
            conn = ConnectDataBase.getConnection();
            if (conn == null) return false;

            conn.setAutoCommit(false);

            // Xóa nhân viên
            String sqlNV = "DELETE FROM [dbo].[NhanVien] WHERE tenDangNhap = ?";
            stmtNV = conn.prepareStatement(sqlNV);
            stmtNV.setString(1, tenDangNhap);
            stmtNV.executeUpdate();

            // Xóa tài khoản
            String sqlTK = "DELETE FROM [dbo].[TaiKhoan] WHERE tenDangNhap = ?";
            stmtTK = conn.prepareStatement(sqlTK);
            stmtTK.setString(1, tenDangNhap);
            stmtTK.executeUpdate();


            conn.commit();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmtNV != null) stmtNV.close();
                if (stmtTK != null) stmtTK.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    //update tai khoan (bảng nhân viên và bảng tài khoản)
    public boolean updateTaiKhoan(NhanVien nv) {
        Connection conn = null;
        PreparedStatement stmtNV = null;
        PreparedStatement stmtTK = null;
        boolean success = false;

        try {
            conn = ConnectDataBase.getConnection();
            if (conn == null) return false;

            conn.setAutoCommit(false);


            // Cập nhật tài khoản
            String sqlTK = "UPDATE [dbo].[TaiKhoan] SET matKhau = ?, quyen = ? WHERE tenDangNhap = ?";
            stmtTK = conn.prepareStatement(sqlTK);
            stmtTK.setString(1, nv.getTaiKhoan().getMatKhau());
            stmtTK.setBoolean(2, nv.getTaiKhoan().isQuyenHan());
            stmtTK.setString(3, nv.getTaiKhoan().getTenDangNhap());
            stmtTK.executeUpdate();

            // Cập nhật nhân viên
            String sqlNV = "UPDATE [dbo].[NhanVien] SET tenNhanVien = ?, tuoi = ?, "
                    + "diaChi = ?, soDienThoai = ? WHERE maNhanVien = ?";
            stmtNV = conn.prepareStatement(sqlNV);
            stmtNV.setString(1, nv.getTenNhanVien());
            stmtNV.setInt(2, nv.getTuoi());
            stmtNV.setString(3, nv.getDiaChi());
            stmtNV.setString(4, nv.getSoDienThoai());
            stmtNV.setString(5, nv.getMaNhanVien());
            stmtNV.executeUpdate();


            conn.commit();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmtNV != null) stmtNV.close();
                if (stmtTK != null) stmtTK.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    //tim kiem nhan vien
    public NhanVien searchTaiKhoan(String tenDangNhap) {

        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT nv.maNhanVien, nv.tenNhanVien, nv.tuoi, nv.diaChi, "
                             + "nv.soDienThoai, tk.tenDangNhap, tk.matKhau, tk.quyen "
                             + "FROM [dbo].[NhanVien] nv "
                             + "JOIN [dbo].[TaiKhoan] tk ON nv.tenDangNhap = tk.tenDangNhap "
                             + "WHERE tk.tenDangNhap LIKE ?")) {

            stmt.setString(1, "%" + tenDangNhap + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maNV = rs.getString(1);
                String tenNV = rs.getString(2);
                int tuoi = rs.getInt(3);
                String diaChi = rs.getString(4);
                String soDienThoai = rs.getString(5);
                String tenDangNhapResult = rs.getString(6);
                String pw = rs.getString(7);
                boolean quyen = rs.getBoolean(8);

                TaiKhoan taiKhoan = new TaiKhoan(tenDangNhapResult, pw, quyen);
                NhanVien nv = new NhanVien(maNV, tenNV, tuoi, diaChi, soDienThoai, taiKhoan);
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}