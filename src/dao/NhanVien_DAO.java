package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.ChucVu;
import entity.CaLamViec;

public class NhanVien_DAO {
    private Connection con;

    public NhanVien_DAO() {
        con = ConnectDB.getInstance().getConnection();
    }

    // ---------------- LẤY TOÀN BỘ NHÂN VIÊN ----------------
    public List<NhanVien> layTatCa() {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                ChucVu chucVu = new ChucVu(rs.getString("maCV"), null, 0);
                CaLamViec caLamViec = new CaLamViec(rs.getString("maCa"), null, null, null);
                
                NhanVien nv = new NhanVien(
                    rs.getString("maNhanVien"),
                    rs.getString("tenNhanVien"),
                    rs.getInt("tuoi"),
                    rs.getString("diaChi"),
                    rs.getString("soDienThoai"),
                    chucVu,
                    rs.getFloat("luongNV"),
                    rs.getDate("ngayVaoLam").toLocalDate(),
                    rs.getString("gioiTinh"),
                    caLamViec
                );
                ds.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ---------------- THÊM NHÂN VIÊN ----------------
    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, tuoi, diaChi, soDienThoai, maCV, luongNV, ngayVaoLam, gioiTinh, maCa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, nv.getMaNhanVien());
            pstm.setString(2, nv.getTenNhanVien());
            pstm.setInt(3, nv.getTuoi());
            pstm.setString(4, nv.getDiaChi());
            pstm.setString(5, nv.getSoDienThoai());
            pstm.setString(6, nv.getChucVu().getMaCV());
            pstm.setFloat(7, nv.getLuongNV());
            pstm.setDate(8, Date.valueOf(nv.getNgayVaoLam()));
            pstm.setString(9, nv.getGioiTinh());
            pstm.setString(10, nv.getCaLamViec().getMaCa());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- CẬP NHẬT NHÂN VIÊN ----------------
    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET tenNhanVien = ?, tuoi = ?, diaChi = ?, soDienThoai = ?, maCV = ?, luongNV = ?, ngayVaoLam = ?, gioiTinh = ?, maCa = ? WHERE maNhanVien = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, nv.getTenNhanVien());
            pstm.setInt(2, nv.getTuoi());
            pstm.setString(3, nv.getDiaChi());
            pstm.setString(4, nv.getSoDienThoai());
            pstm.setString(5, nv.getChucVu().getMaCV());
            pstm.setFloat(6, nv.getLuongNV());
            pstm.setDate(7, Date.valueOf(nv.getNgayVaoLam()));
            pstm.setString(8, nv.getGioiTinh());
            pstm.setString(9, nv.getCaLamViec().getMaCa());
            pstm.setString(10, nv.getMaNhanVien());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- TÌM THEO MÃ ----------------
    public NhanVien timTheoMa(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maNV);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                ChucVu chucVu = new ChucVu(rs.getString("maCV"), null, 0);
                CaLamViec caLamViec = new CaLamViec(rs.getString("maCa"), null, null, null);
                
                return new NhanVien(
                    rs.getString("maNhanVien"),
                    rs.getString("tenNhanVien"),
                    rs.getInt("tuoi"),
                    rs.getString("diaChi"),
                    rs.getString("soDienThoai"),
                    chucVu,
                    rs.getFloat("luongNV"),
                    rs.getDate("ngayVaoLam").toLocalDate(),
                    rs.getString("gioiTinh"),
                    caLamViec
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- TÌM THEO TÊN ----------------
    public List<NhanVien> timTheoTen(String tenNhanVien) {
        List<NhanVien> dsNV = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE tenNhanVien LIKE ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, "%" + tenNhanVien + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ChucVu chucVu = new ChucVu(rs.getString("maCV"), null, 0);
                CaLamViec caLamViec = new CaLamViec(rs.getString("maCa"), null, null, null);
                
                NhanVien nv = new NhanVien(
                    rs.getString("maNhanVien"),
                    rs.getString("tenNhanVien"),
                    rs.getInt("tuoi"),
                    rs.getString("diaChi"),
                    rs.getString("soDienThoai"),
                    chucVu,
                    rs.getFloat("luongNV"),
                    rs.getDate("ngayVaoLam").toLocalDate(),
                    rs.getString("gioiTinh"),
                    caLamViec
                );
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    // ---------------- XOÁ NHÂN VIÊN ----------------
    public boolean xoaNhanVien(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maNhanVien);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}