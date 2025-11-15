package dao;

import connectDB.ConnectDataBase;
import entity.HoaDon;
import entity.LoaiSanPham;
import entity.SanPham;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HoaDon_Dao {

    /**
     * Phương thức này sẽ lấy danh sách 8 món bán chạy nhất trong khoảng thời gian từ ngày bắt đầu đến ngày kết thúc
     * @param ngayBatDau
     * @param ngayKetThuc
     * @return dsMonBanChay
     */
    public Map<String, Integer> get8MonBanChayNhat(LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
    // Sử dụng LinkedHashMap để giữ thứ tự sắp xếp từ SQL
    Map<String, Integer> dsMonBanChay = new LinkedHashMap<>();
    Connection conn = null; // Khai báo ngoài try để đóng trong finally
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = ConnectDataBase.getConnection();
        String sql = "SELECT TOP 10 sp.tenSanPham, SUM(cthd.soLuong) AS soLuongBan " +
                "FROM ChiTietHoaDon cthd " +
                "INNER JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon " +
                "INNER JOIN SanPham sp ON cthd.maSanPham = sp.maSanPham " +
                "WHERE hd.ngayLap BETWEEN ? AND ? " +
                "GROUP BY sp.tenSanPham " +
                "ORDER BY soLuongBan DESC";

        pstmt = conn.prepareStatement(sql);
        pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(ngayBatDau));
        pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(ngayKetThuc));

        rs = pstmt.executeQuery();

        while (rs.next()) {
            String tenSanPham = rs.getString("tenSanPham");
            int soLuongBan = rs.getInt("soLuongBan");
            dsMonBanChay.put(tenSanPham, soLuongBan);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {

        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return dsMonBanChay;
}

    /**
     * Phương thức này insert hóa đơn lên cơ sở dữ liệu
     * @param maHoaDon mã hóa đơn
     * @param maNhanVien mã nhân viên
     * @param ngayLap ngày lâoj hóa đơn
     * @param tongTien tổng tiền sản phẩm
     * @return
     */
    public static boolean insertHoaDon(String maHoaDon, String maNhanVien, LocalDateTime ngayLap, double tongTien) {
        String sql = "INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayLap, tongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maHoaDon);
            pstmt.setString(2, maNhanVien);
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(ngayLap));
            pstmt.setDouble(4, tongTien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting HoaDon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức này kiểm tra xem mã hóa đơn đã tồn tại trong cơ sở dữ liệu hay chưa
     * @param maHoaDon
     * @return true nếu chưa tồn tại, false nếu đã tồn tại
     */
    public boolean isMaHoaDonExists(String maHoaDon) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maHoaDon);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count > 0
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking maHoaDon existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức này sẽ insert chi tiết hóa đơn lên cơ sở dữ liệu
     * @param maChiTietHoaDon
     * @param maHoaDon
     * @param maSanPham
     * @param soLuong
     * @param giaBan
     * @param thanhTien
     * @return
     */
    public boolean insertChiTietHoaDon(String maChiTietHoaDon, String maHoaDon, String maSanPham, int soLuong, double giaBan, double thanhTien) {
        String sql = "INSERT INTO ChiTietHoaDon ( maChiTietHoaDon, maHoaDon, maSanPham, soLuong, giaBan, thanhTien) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maChiTietHoaDon);
            pstmt.setString(2, maHoaDon);
            pstmt.setString(3, maSanPham);
            pstmt.setInt(4, soLuong);
            pstmt.setDouble(5, giaBan);
            pstmt.setDouble(6, thanhTien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting ChiTietHoaDon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức này sẽ lấy danh sách hóa đơn của nhân viên dựa trên tên đăng nhập
     * @param tenDangNhap
     * @return
     */
    public List<HoaDon> getAllDsachHoaDon(String tenDangNhap) {
        Map<String, HoaDon> hoaDonMap = new HashMap<>();

        try {
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT * FROM HoaDon"
                    + " INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien"
                    + " INNER JOIN TaiKhoan ON NhanVien.tenDangNhap = TaiKhoan.tenDangNhap"
                    + " INNER JOIN ChiTietHoaDon ON HoaDon.maHoaDon = ChiTietHoaDon.maHoaDon"
                    + " INNER JOIN SanPham ON ChiTietHoaDon.maSanPham = SanPham.maSanPham"
                    + " INNER JOIN LoaiSanPham ON SanPham.maLoaiSanPham = LoaiSanPham.maLoaiSanPham"
                    + " WHERE TaiKhoan.tenDangNhap = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tenDangNhap);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("maHoaDon");
                String maNV = rs.getString("maNhanVien");
                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();

                String maSP = rs.getString("maSanPham");
                String tenSP = rs.getString("tenSanPham");
                double giaBan = rs.getDouble("giaBan");
                int soLuong = rs.getInt("soLuong");
                double thanhTien = rs.getDouble("thanhTien");
                String maLoaiSP = rs.getString("maLoaiSanPham");
                String tenLoaiSP = rs.getString("tenLoaiSanPham");
                String hinhAnh = rs.getString("hinhAnh");

                SanPham sp = new SanPham(maSP, tenSP, giaBan, soLuong, new LoaiSanPham(maLoaiSP, tenLoaiSP), hinhAnh);

                if (!hoaDonMap.containsKey(maHD)) {
                    List<SanPham> dsSP = new ArrayList<>();
                    dsSP.add(sp);
                    HoaDon hd = new HoaDon(maHD, maNV, ngayLap, dsSP, soLuong, giaBan, thanhTien);
                    hoaDonMap.put(maHD, hd);
                } else {
                    hoaDonMap.get(maHD).getDsachSanPham().add(sp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(hoaDonMap.values());
    }


    public boolean isMaChiTietHoaDonExists(String maChiTietHoaDon) {
        String sql = "SELECT COUNT(*) FROM ChiTietHoaDon WHERE maChiTietHoaDon = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maChiTietHoaDon);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count > 0
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking maChiTietHoaDon existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Map<LocalDate, Double> getDoanhThuTheoThoiGian(LocalDateTime startTime, LocalDateTime endTime) {
        Map<LocalDate, Double> doanhThu = new LinkedHashMap<>();
        try {
            Connection conn = ConnectDataBase.getConnection();
            String sql = "SELECT CAST(ngayLap AS DATE) AS ngay, SUM(tongTien) AS doanhThu " +
                    "FROM HoaDon " +
                    "WHERE ngayLap BETWEEN ? AND ? " +
                    "GROUP BY CAST(ngayLap AS DATE)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(startTime));
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(endTime));
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                LocalDate date = rs.getDate("ngay").toLocalDate();
                double total = rs.getDouble("doanhThu");
                doanhThu.put(date, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doanhThu;
    }
}


