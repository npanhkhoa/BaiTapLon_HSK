package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {

    public KhachHang_DAO() {
    }

    public List<KhachHang> getAllKhachHang() throws Exception {
        Connection conn = ConnectDB.getConnection();
        List<KhachHang> dsKhachHang = new ArrayList<>();

        String sql = "SELECT * FROM KhachHang";
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String maKH = rs.getString("maKhachHang");
            String tenKH = rs.getString("tenKhachHang");
            String diaChi = rs.getString("diaChi");
            String sdt = rs.getString("soDienThoai");

            KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt);
            dsKhachHang.add(kh);
        }

        return dsKhachHang;
    }

    public KhachHang getKhachHangByID(String maKH) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maKH);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String tenKH = rs.getString("tenKhachHang");
            String diaChi = rs.getString("diaChi");
            String sdt = rs.getString("soDienThoai");

            return new KhachHang(maKH, tenKH, diaChi, sdt);
        }

        return null;
    }

    public boolean checkKhachHangByID(String maKH) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT 1 FROM KhachHang WHERE maKhachHang = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maKH);
        ResultSet rs = stm.executeQuery();

        return rs.next();
    }

    public void addKhachHang(KhachHang kh) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, diaChi, soDienThoai) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, kh.getMaKhachHang());
        stm.setString(2, kh.getTenKhachHang());
        stm.setString(3, kh.getDiaChi());
        stm.setString(4, kh.getSoDienThoai());
        stm.executeUpdate();
    }

    public void updateKhachHang(KhachHang kh) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "UPDATE KhachHang SET tenKhachHang = ?, diaChi = ?, soDienThoai = ? WHERE maKhachHang = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, kh.getTenKhachHang());
        stm.setString(2, kh.getDiaChi());
        stm.setString(3, kh.getSoDienThoai());
        stm.setString(4, kh.getMaKhachHang());
        stm.executeUpdate();
    }

    public void deleteKhachHang(String maKH) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maKH);
        stm.executeUpdate();
    }
}
