package controller;

import connectDB.ConnectDataBase;
import dao.CaLamViec_DAO;
import dao.NhanVien_Dao;
import dao.TaiKhoan_DAO;
import entity.CaLamViec;
import entity.NhanVien;
import entity.TaiKhoan;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class UserController {
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();
    private CaLamViec_DAO caLamViecDao = new CaLamViec_DAO();

    private String currentUsername;

    /**
     * Phương thức này kiểm tra xem tài khoản có hợp lệ hay không.
     *
     * @param tenDangNhap Tên đăng nhập của tài khoản.
     * @param matKhau Mật khẩu của tài khoản.
     * @return true nếu tài khoản hợp lệ, false nếu không.
     */
    public boolean checkLogin(String tenDangNhap, String matKhau) {
        String sql = "SELECT COUNT(*) FROM [dbo].[TaiKhoan] WHERE tenDangNhap = ? AND matKhau = ?";

        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Mặc định trả về false nếu có lỗi hoặc không tìm thấy
    }

    /**
     * Phương thức này kiểm tra xem tài khoản có quyền admin hay không.
     *
     * @param username Tên đăng nhập của tài khoản.
     * @param password Mật khẩu của tài khoản.
     * @return true nếu tài khoản có quyền admin, false nếu không.
     */
    public boolean checkAdmin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM [dbo].[TaiKhoan] WHERE tenDangNhap = ? AND matKhau = ? AND quyen = 1";

        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức này tạo mã ca làm việc ngẫu nhiên.
     *
     * @return Mã ca làm việc ngẫu nhiên.
     */
    public String generateShiftId() {
        String maCaLamViec;
        do {
            int randomNumber = new Random().nextInt(10000); // Tạo số ngẫu nhiên từ 0 đến 9999
            maCaLamViec = "CLV" + String.format("%04d", randomNumber); // Định dạng mã ca làm việc
        } while (caLamViecDao.isShiftIdExists(maCaLamViec)); // Kiểm tra tính duy nhất
        return maCaLamViec;
    }

    /**
     * Phương thức này lưu tên đăng nhập hiện tại.
     *
     * @return Danh sách nhân viên.
     */
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    /**
     * Phương thức này lấy tên đăng nhập hiện tại.
     *
     * @return Tên đăng nhập hiện tại.
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Phương thức này tạo ca làm việc mới.
     *
     * @param maCaLamViec
     * @param tienMoCa
     * @return
     */
    public CaLamViec createShift(String maCaLamViec, double tienMoCa) {
        if (currentUsername == null || currentUsername.isEmpty()) {
            throw new IllegalStateException("Tên đăng nhập hiện tại không hợp lệ. Vui lòng đăng nhập lại.");
        }

        // Lấy thông tin nhân viên từ tên đăng nhập
        NhanVien nhanVien = getNhanVienByTenDangNhap(currentUsername);
        if (nhanVien == null) {
            throw new IllegalStateException("Không tìm thấy thông tin nhân viên cho tên đăng nhập: " + currentUsername);
        }

        // Tạo ca làm việc
        CaLamViec caLamViec = new CaLamViec();
        caLamViec.setMaCaLamViec(maCaLamViec);
        caLamViec.setNhanVien(nhanVien);
        caLamViec.setThoiGianBatDau(LocalDateTime.now()); // Thời gian bắt đầu là thời điểm hiện tại
        caLamViec.setThoiGianKetThuc(LocalDateTime.now()); // Thời gian kết thúc là thời điểm hiện tại
        caLamViec.setTienMoCa(tienMoCa);
        caLamViec.setTienDongCa(0.0); // Số tiền đóng ca mặc định là 0
        return caLamViec;
    }

    /**
     * Phương thức này lưu ca làm việc vào cơ sở dữ liệu.
     *
     * @param caLamViec Ca làm việc cần lưu.
     * @return true nếu lưu thành công, false nếu không.
     */
    public boolean saveShift(CaLamViec caLamViec) {
        return caLamViecDao.insertCaLamViec(caLamViec);
    }

    /**
     * Phương thức này đóng ca làm việc.
     * @param caLamViec Ca làm việc cần đóng.
     * @param tienDongCa Số tiền đóng ca.
     * @return true nếu đóng ca thành công, false nếu không.
     */
    public boolean closeShift(CaLamViec caLamViec, double tienDongCa) {
        if (caLamViec == null || caLamViec.getMaCaLamViec() == null || caLamViec.getMaCaLamViec().isEmpty()) {
            throw new IllegalArgumentException("Ca làm việc không hợp lệ hoặc thiếu mã ca làm việc.");
        }

        caLamViec.setThoiGianKetThuc(LocalDateTime.now()); // Thời gian kết thúc là thời gian hiện tại
        caLamViec.setTienDongCa(tienDongCa); // Số tiền đóng ca
        return caLamViecDao.updateCaLamViec(caLamViec); // Gọi DAO để cập nhật thông tin ca làm việc
    }

    /**
     * Phương thức này lấy thông tin nhân viên theo tên đăng nhập.
     *
     * @param tenDangNhap Tên đăng nhập của nhân viên.
     * @return Thông tin nhân viên.
     */
    public NhanVien getNhanVienByTenDangNhap(String tenDangNhap) {
        return nhanVienDao.getNhanVienByTenDangNhap(tenDangNhap);
    }


}
