package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.SanPham;
import entity.NhaCungCap;

public class SanPham_DAO {
    private Connection con;

    public SanPham_DAO() {
        con = ConnectDB.getInstance().getConnection();
    }

    // ---------------- LẤY TOÀN BỘ SẢN PHẨM ----------------
    public List<SanPham> layTatCa() {
        List<SanPham> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(rs.getString("maNCC"), null, null, null);
                SanPham sp = new SanPham(
                        rs.getString("maSanPham"),
                        rs.getString("tenSanPham"),
                        rs.getBigDecimal("giaBan"),
                        rs.getInt("soLuong"),
                        ncc,
                        rs.getString("donViTinh")
                );
                ds.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ---------------- THÊM SẢN PHẨM ----------------
    public boolean themSanPham(SanPham sp) {
        String sql = "INSERT INTO SanPham (maSanPham, tenSanPham, giaBan, soLuong, maNCC, donViTinh) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, sp.getMaSanPham());
            pstm.setString(2, sp.getTenSanPham());
            pstm.setBigDecimal(3, sp.getGiaBan());
            pstm.setInt(4, sp.getSoLuong());
            pstm.setString(5, sp.getNhaCungCap().getMaNCC());
            pstm.setString(6, sp.getDonViTinh());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- CẬP NHẬT SẢN PHẨM ----------------
    public boolean capNhatSanPham(SanPham sp) {
        String sql = "UPDATE SanPham SET tenSanPham = ?, giaBan = ?, soLuong = ?, maNCC = ?, donViTinh = ? WHERE maSanPham = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, sp.getTenSanPham());
            pstm.setBigDecimal(2, sp.getGiaBan());
            pstm.setInt(3, sp.getSoLuong());
            pstm.setString(4, sp.getNhaCungCap().getMaNCC());
            pstm.setString(5, sp.getDonViTinh());
            pstm.setString(6, sp.getMaSanPham());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- TÌM THEO MÃ ----------------
    public SanPham timTheoMa(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maSP);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(rs.getString("maNCC"), null, null, null);
                return new SanPham(
                        rs.getString("maSanPham"),
                        rs.getString("tenSanPham"),
                        rs.getBigDecimal("giaBan"),
                        rs.getInt("soLuong"),
                        ncc,
                        rs.getString("donViTinh")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- TÌM THEO TÊN ----------------
    public List<SanPham> timTheoTen(String tenSanPham) {
        List<SanPham> dsSP = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE tenSanPham LIKE ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, "%" + tenSanPham + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(rs.getString("maNCC"), null, null, null);
                SanPham sp = new SanPham(
                        rs.getString("maSanPham"),
                        rs.getString("tenSanPham"),
                        rs.getBigDecimal("giaBan"),
                        rs.getInt("soLuong"),
                        ncc,
                        rs.getString("donViTinh")
                );
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    // ---------------- XOÁ SẢN PHẨM ----------------
    public boolean xoaSanPham(String maSanPham) {
        String sql = "DELETE FROM SanPham WHERE maSanPham = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maSanPham);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
