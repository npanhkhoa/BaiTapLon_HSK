package dao;

import connectDB.ConnectDataBase;
import entity.LoaiSanPham;
import entity.SanPham;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPham_Dao {

    public List<SanPham> getDsachSanPham() {
      List<SanPham> dsachSanPham = new ArrayList<>();
      try {
          String sql = "SELECT * FROM SanPham "
                  + "INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham";

          ConnectDataBase.getConnection();
          Statement stmt = ConnectDataBase.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);


          while (rs.next()) {
              String maSP = rs.getString("maSanPham");
              String tenSP = rs.getString("tenSanPham");
              double giaBan = rs.getDouble("giaBan");
              int soLuong = rs.getInt("soLuong");
              // Tạo đối tượng LoaiSanPham từ dữ liệu trong bảng
              String maLoai = rs.getString("maLoaiSanPham");
              String tenLoai = rs.getString("tenLoaiSanPham");
              LoaiSanPham loaiSanPham = new LoaiSanPham(maLoai, tenLoai);
                String hinhAnh = rs.getString("hinhAnh");

              SanPham sanPham = new SanPham(maSP, tenSP, giaBan, soLuong, loaiSanPham,hinhAnh);
              dsachSanPham.add(sanPham);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
        return dsachSanPham;
    }

    // src/dao/SanPham_Dao.java
    public SanPham getThongTinSanPham(String maSanPham) {
        String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSanPham); // Set the value for the placeholder

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SanPham sanPham = new SanPham();
                    sanPham.setMaSanPham(rs.getString("maSanPham"));
                    sanPham.setTenSanPham(rs.getString("tenSanPham"));
                    sanPham.setGiaBan(rs.getDouble("giaBan"));
                    // Set other fields as needed
                    return sanPham;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product information: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if no product is found
    }

    public LoaiSanPham getLoaiSanPham(String maSanPham) {
        LoaiSanPham loaiSanPham = null;
        String sql = "SELECT lsp.maLoaiSanPham, lsp.tenLoaiSanPham FROM LoaiSanPham lsp "
                + "INNER JOIN SanPham sp ON lsp.maLoaiSanPham = sp.maLoaiSanPham " // Đảm bảo tên cột JOIN đúng
                + "WHERE sp.maSanPham = ?";


        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSanPham);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String maLoai = rs.getString("maLoaiSanPham");
                    String tenLoai = rs.getString("tenLoaiSanPham");
                    loaiSanPham = new LoaiSanPham(maLoai, tenLoai); // Tạo đối tượng
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi lấy loại sản phẩm: " + e.getMessage(), e);

        }
        return loaiSanPham;
    }

    public List<SanPham> getSanPhamTheoLoaiSanPham(String loai){
        List<SanPham> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM SanPham "
                + "INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham "
                + "WHERE LoaiSanPham.tenLoaiSanPham = ?";

        try(Connection conn = ConnectDataBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loai);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String maSP = rs.getString("maSanPham");
                String tenSP = rs.getString("tenSanPham");
                double giaBan = rs.getDouble("giaBan");
                int soLuong = rs.getInt("soLuong");
                // Tạo đối tượng LoaiSanPham từ dữ liệu trong bảng
                String maLoai = rs.getString("maLoaiSanPham");
                String tenLoai = rs.getString("tenLoaiSanPham");
                LoaiSanPham loaiSanPham = new LoaiSanPham(maLoai, tenLoai);
                String hinhAnh = rs.getString("hinhAnh");

                SanPham sanPham = new SanPham(maSP, tenSP, giaBan, soLuong, loaiSanPham, hinhAnh);
                danhSach.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean themSanPham(SanPham sp){
        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsAffected = 0;

        try{
            conn = ConnectDataBase.getConnection();
            String sql = "INSERT INTO SanPham (maSanPham, tenSanPham, giaBan, soLuong, maLoaiSanPham, hinhAnh) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, sp.getMaSanPham());
            stmt.setString(2, sp.getTenSanPham());
            stmt.setDouble(3, sp.getGiaBan());
            stmt.setInt(4, sp.getSoLuong());
            stmt.setString(5, sp.getLoaiSanPham().getMaLoaiSanPham());
            stmt.setString(6, sp.getHinhAnh());

            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResources(conn, stmt,null);
        }
        return rowsAffected > 0;
    }

    public boolean xoaSanPham(String maSP){
        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsAffected = 0;
        try{
            conn = ConnectDataBase.getConnection();
            String sql = "DELETE FROM SanPham WHERE maSanPham = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maSP);
            rowsAffected = stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResources(conn, stmt,null);
        }
        return rowsAffected > 0;
    }

    public boolean suaSanPham(SanPham sp) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsAffected = 0;
        try {
            conn = ConnectDataBase.getConnection();
            String sql = "UPDATE SanPham SET tenSanPham = ?, giaBan = ?, soLuong = ?, maLoaiSanPham = ?, hinhAnh = ? WHERE maSanPham = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sp.getTenSanPham());
            stmt.setDouble(2, sp.getGiaBan());
            stmt.setInt(3, sp.getSoLuong());
            stmt.setString(4, sp.getLoaiSanPham().getMaLoaiSanPham());
            stmt.setString(5, sp.getHinhAnh());
            stmt.setString(6, sp.getMaSanPham());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return rowsAffected > 0;
    }

    // Hàm đóng kết nối để tránh lặp lại nhiều
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isMaSanPhamExists(String maSanPham) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE maSanPham = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSanPham);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu mã sản phẩm đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
