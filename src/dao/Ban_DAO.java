package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ban;

public class Ban_DAO {

    public Ban_DAO() {
    }

    // Lấy toàn bộ danh sách bàn
    public List<Ban> getAllBan() throws Exception {
        Connection conn = ConnectDB.getConnection();
        List<Ban> dsBan = new ArrayList<>();

        String sql = "SELECT * FROM Ban";
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String maBan = rs.getString("maBan");
            String tenBan = rs.getString("tenBan");
            int sucChua = rs.getInt("sucChua");
            String viTri = rs.getString("viTri");
            String trangThai = rs.getString("trangThai");
            String ghiChu = rs.getString("ghiChu");

            Ban ban = new Ban(maBan, tenBan, sucChua, viTri, trangThai, ghiChu);
            dsBan.add(ban);
        }

        // đóng resources nếu cần (tùy ConnectDB quản lý connection)
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stm != null) stm.close(); } catch (Exception e) {}

        return dsBan;
    }

    // Tìm bàn theo mã
    public Ban getBanByID(String maBan) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM Ban WHERE maBan = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maBan);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String tenBan = rs.getString("tenBan");
            int sucChua = rs.getInt("sucChua");
            String viTri = rs.getString("viTri");
            String trangThai = rs.getString("trangThai");
            String ghiChu = rs.getString("ghiChu");

            Ban ban = new Ban(maBan, tenBan, sucChua, viTri, trangThai, ghiChu);

            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}

            return ban;
        }

        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stm != null) stm.close(); } catch (Exception e) {}

        return null;
    }

    // Kiểm tra bàn tồn tại theo mã
    public boolean checkBanByID(String maBan) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT 1 FROM Ban WHERE maBan = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maBan);
        ResultSet rs = stm.executeQuery();

        boolean exists = rs.next();

        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stm != null) stm.close(); } catch (Exception e) {}

        return exists;
    }

    // Thêm bàn mới (ghi đầy đủ 6 cột)
    public void addBan(Ban ban) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "INSERT INTO Ban (maBan, tenBan, sucChua, viTri, trangThai, ghiChu) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, ban.getMaBan());
        stm.setString(2, ban.getTenBan());
        stm.setInt(3, ban.getSucChua());
        stm.setString(4, ban.getViTri());
        stm.setString(5, ban.getTrangThai());
        stm.setString(6, ban.getGhiChu());
        stm.executeUpdate();

        try { if (stm != null) stm.close(); } catch (Exception e) {}
    }

    // Cập nhật thông tin bàn
    public void updateBan(Ban ban) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "UPDATE Ban SET tenBan = ?, sucChua = ?, viTri = ?, trangThai = ?, ghiChu = ? WHERE maBan = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, ban.getTenBan());
        stm.setInt(2, ban.getSucChua());
        stm.setString(3, ban.getViTri());
        stm.setString(4, ban.getTrangThai());
        stm.setString(5, ban.getGhiChu());
        stm.setString(6, ban.getMaBan());
        stm.executeUpdate();

        try { if (stm != null) stm.close(); } catch (Exception e) {}
    }

    // Xóa bàn theo mã
    public void deleteBan(String maBan) throws Exception {
        Connection conn = ConnectDB.getConnection();
        String sql = "DELETE FROM Ban WHERE maBan = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, maBan);
        stm.executeUpdate();

        try { if (stm != null) stm.close(); } catch (Exception e) {}
    }
}
