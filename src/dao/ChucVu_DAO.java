package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChucVu;

public class ChucVu_DAO {
    private Connection con;

    public ChucVu_DAO() {
    	 try {
             con = ConnectDB.getInstance().getConnection();
         } catch (SQLException e) {
             e.printStackTrace();
             // Nếu muốn dừng chương trình ngay khi kết nối lỗi:
             // throw new RuntimeException(e);
         }
    }

    // ---------------- LẤY TOÀN BỘ CHỨC VỤ ----------------
    public List<ChucVu> layTatCa() {
        List<ChucVu> ds = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                ChucVu cv = new ChucVu(
                    rs.getString("maCV"),
                    rs.getString("tenCV"),
                    rs.getDouble("phuCap")
                );
                ds.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ---------------- THÊM CHỨC VỤ ----------------
    public boolean themChucVu(ChucVu cv) {
        String sql = "INSERT INTO ChucVu (maCV, tenCV, phuCap) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, cv.getMaCV());
            pstm.setString(2, cv.getTenCV());
            pstm.setDouble(3, cv.getPhuCap());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- CẬP NHẬT CHỨC VỤ ----------------
    public boolean capNhatChucVu(ChucVu cv) {
        String sql = "UPDATE ChucVu SET tenCV = ?, phuCap = ? WHERE maCV = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, cv.getTenCV());
            pstm.setDouble(2, cv.getPhuCap());
            pstm.setString(3, cv.getMaCV());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- TÌM THEO MÃ ----------------
    public ChucVu timTheoMa(String maCV) {
        String sql = "SELECT * FROM ChucVu WHERE maCV = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maCV);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return new ChucVu(
                    rs.getString("maCV"),
                    rs.getString("tenCV"),
                    rs.getDouble("phuCap")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- TÌM THEO TÊN ----------------
    public List<ChucVu> timTheoTen(String tenCV) {
        List<ChucVu> dsCV = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu WHERE tenCV LIKE ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, "%" + tenCV + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ChucVu cv = new ChucVu(
                    rs.getString("maCV"),
                    rs.getString("tenCV"),
                    rs.getDouble("phuCap")
                );
                dsCV.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCV;
    }

    // ---------------- XOÁ CHỨC VỤ ----------------
    public boolean xoaChucVu(String maCV) {
        String sql = "DELETE FROM ChucVu WHERE maCV = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maCV);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}