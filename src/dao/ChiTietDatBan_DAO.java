package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDatBan;

public class ChiTietDatBan_DAO {
    public ChiTietDatBan_DAO() {
    }

    public List<ChiTietDatBan> getAllChiTietDatBan() throws Exception {
        Connection conn = ConnectDB.getConnection();
        List<ChiTietDatBan> dsChiTietDatBan = new ArrayList<>();

        String sql = "SELECT * FROM ChiTietDatBan";
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String maCTDB = rs.getString("maCTDB");
            String maKhachHang = rs.getString("maKhachHang");
            String maBan = rs.getString("maBan");
            String maNhanVien = rs.getString("maNhanVien");
            LocalDate ngayDat = rs.getDate("ngayDat").toLocalDate();
            LocalTime gioDat = rs.getTime("gioDat").toLocalTime();
            int soNguoi = rs.getInt("soNguoi");
            String trangThai = rs.getString("trangThai");
            String ghiChu = rs.getString("ghiChu");

            ChiTietDatBan ct = new ChiTietDatBan(maCTDB, maKhachHang, maBan, maNhanVien,
                    ngayDat, gioDat, soNguoi, trangThai, ghiChu);
            dsChiTietDatBan.add(ct);
        }

        return dsChiTietDatBan;
    }

    public ChiTietDatBan getChiTietDatBanByID(String maCTDB) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM ChiTietDatBan WHERE maCTDB = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maCTDB);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String maKhachHang = rs.getString("maKhachHang");
            String maBan = rs.getString("maBan");
            String maNhanVien = rs.getString("maNhanVien");
            LocalDate ngayDat = rs.getDate("ngayDat").toLocalDate();
            LocalTime gioDat = rs.getTime("gioDat").toLocalTime();
            int soNguoi = rs.getInt("soNguoi");
            String trangThai = rs.getString("trangThai");
            String ghiChu = rs.getString("ghiChu");

            return new ChiTietDatBan(maCTDB, maKhachHang, maBan, maNhanVien,
                    ngayDat, gioDat, soNguoi, trangThai, ghiChu);
        }

        return null;
    }

    public List<ChiTietDatBan> getChiTietDatBanByTrangThai(String trangThai) throws Exception {
        Connection conn = ConnectDB.getConnection();
        List<ChiTietDatBan> dsChiTietDatBan = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietDatBan WHERE trangThai = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, trangThai);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String maCTDB = rs.getString("maCTDB");
            String maKhachHang = rs.getString("maKhachHang");
            String maBan = rs.getString("maBan");
            String maNhanVien = rs.getString("maNhanVien");
            LocalDate ngayDat = rs.getDate("ngayDat").toLocalDate();
            LocalTime gioDat = rs.getTime("gioDat").toLocalTime();
            int soNguoi = rs.getInt("soNguoi");
            String trangThaiDB = rs.getString("trangThai");
            String ghiChu = rs.getString("ghiChu");

            ChiTietDatBan ct = new ChiTietDatBan(maCTDB, maKhachHang, maBan, maNhanVien,
                    ngayDat, gioDat, soNguoi, trangThaiDB, ghiChu);
            dsChiTietDatBan.add(ct);
        }

        return dsChiTietDatBan;
    }

    public boolean checkChiTietDatBanByID(String maCTDB) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM ChiTietDatBan WHERE maCTDB = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maCTDB);
        ResultSet rs = stm.executeQuery();
        return rs.next();
    }

    public void addChiTietDatBan(ChiTietDatBan ct) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "INSERT INTO ChiTietDatBan (maCTDB, maKhachHang, maBan, maNhanVien, ngayDat, gioDat, soNguoi, trangThai, ghiChu) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, ct.getMaCTDB());
        stm.setString(2, ct.getMaKhachHang());
        stm.setString(3, ct.getMaBan());
        stm.setString(4, ct.getMaNhanVien());
        stm.setDate(5, Date.valueOf(ct.getNgayDat()));
        stm.setTime(6, Time.valueOf(ct.getGioDat()));
        stm.setInt(7, ct.getSoNguoi());
        stm.setString(8, ct.getTrangThai());
        stm.setString(9, ct.getGhiChu());
        stm.executeUpdate();
    }

    public void updateChiTietDatBan(ChiTietDatBan ct) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "UPDATE ChiTietDatBan SET maKhachHang = ?, maBan = ?, maNhanVien = ?, ngayDat = ?, gioDat = ?, "
                   + "soNguoi = ?, trangThai = ?, ghiChu = ? WHERE maCTDB = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, ct.getMaKhachHang());
        stm.setString(2, ct.getMaBan());
        stm.setString(3, ct.getMaNhanVien());
        stm.setDate(4, Date.valueOf(ct.getNgayDat()));
        stm.setTime(5, Time.valueOf(ct.getGioDat()));
        stm.setInt(6, ct.getSoNguoi());
        stm.setString(7, ct.getTrangThai());
        stm.setString(8, ct.getGhiChu());
        stm.setString(9, ct.getMaCTDB());
        stm.executeUpdate();
    }

    public void deleteChiTietDatBan(String maCTDB) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "DELETE FROM ChiTietDatBan WHERE maCTDB = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maCTDB);
        stm.executeUpdate();
    }
}
