package dao;

import connectDB.ConnectDataBase;
import controller.UserController;
import entity.CaLamViec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CaLamViec_DAO {

    /**
     * Phương thức insert ca làm việc
     * @return Danh sách ca làm việc.
     */
    public boolean insertCaLamViec(CaLamViec caLamViec) {
        try {
            String sql = "INSERT INTO CaLamViec (maCaLamViec, tenDangNhap, thoiGianBatDau, thoiGianKetThuc, tienMoCa, tienDongCa) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            Connection conn = ConnectDataBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, caLamViec.getMaCaLamViec());
            stmt.setString(2, caLamViec.getNhanVien().getTaiKhoan().getTenDangNhap());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(caLamViec.getThoiGianBatDau()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(caLamViec.getThoiGianKetThuc()));
            stmt.setDouble(5, caLamViec.getTienMoCa());
            stmt.setDouble(6, caLamViec.getTienDongCa()); // Đảm bảo giá trị này không null

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Phương thức cập nhật ca làm việc
     * @param caLamViec Ca làm việc cần cập nhật.
     * @return true nếu cập nhật thành công, false nếu không.
     */
    public boolean updateCaLamViec(CaLamViec caLamViec) {
        String sql = "UPDATE CaLamViec SET thoiGianKetThuc = ?, tienDongCa = ? WHERE maCaLamViec = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(caLamViec.getThoiGianKetThuc())); // Thời gian kết thúc
            stmt.setDouble(2, caLamViec.getTienDongCa()); // Số tiền đóng ca
            stmt.setString(3, caLamViec.getMaCaLamViec()); // Mã ca làm việc
    
            return stmt.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức kiểm tra mã ca làm việc đã tồn tại hay chưa
     * @param maCaLamViec Mã ca làm việc cần kiểm tra.
     * @return true nếu mã ca làm việc đã tồn tại, false nếu không.
     */
    public boolean isShiftIdExists(String maCaLamViec) {
    String sql = "SELECT COUNT(*) FROM CaLamViec WHERE maCaLamViec = ?";
    try (Connection conn = ConnectDataBase.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, maCaLamViec);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Trả về true nếu mã ca làm việc đã tồn tại
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
