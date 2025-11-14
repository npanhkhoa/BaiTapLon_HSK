package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhaCungCap;

public class NhaCungCap_DAO {
    private Connection con;

    public NhaCungCap_DAO() {
    	try {
            con = ConnectDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu muốn dừng chương trình ngay khi kết nối lỗi:
            // throw new RuntimeException(e);
        }
    }

    /**
     * Lấy toàn bộ danh sách nhà cung cấp
     */
    public List<NhaCungCap> layTatCa() {
        List<NhaCungCap> ds = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                    rs.getString("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diaChi"),
                    rs.getString("soDienThoai")
                );
                ds.add(ncc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    /**
     * Tìm nhà cung cấp theo mã
     */
    public NhaCungCap timTheoMa(String maNCC) {
        String sql = "SELECT * FROM NhaCungCap WHERE maNCC = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maNCC);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new NhaCungCap(
                        rs.getString("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("diaChi"),
                        rs.getString("soDienThoai")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Thêm nhà cung cấp mới
     */
    public boolean them(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, soDienThoai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ncc.getMaNCC());
            pstm.setString(2, ncc.getTenNCC());
            pstm.setString(3, ncc.getDiaChi());
            pstm.setString(4, ncc.getSoDienThoai());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật thông tin nhà cung cấp
     */
    public boolean capNhat(NhaCungCap ncc) {
        String sql = "UPDATE NhaCungCap SET tenNCC = ?, diaChi = ?, soDienThoai = ? WHERE maNCC = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ncc.getTenNCC());
            pstm.setString(2, ncc.getDiaChi());
            pstm.setString(3, ncc.getSoDienThoai());
            pstm.setString(4, ncc.getMaNCC());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa nhà cung cấp theo mã
     */
    public boolean xoa(String maNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE maNCC = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maNCC);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

