package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.PhanCong;
import entity.NhanVien;
import entity.CaLamViec;

public class PhanCong_DAO {
    private Connection con;

    public PhanCong_DAO() {
    	try {
            con = ConnectDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu muốn dừng chương trình ngay khi kết nối lỗi:
            // throw new RuntimeException(e);
        }
    }

    // ---------------- LẤY TOÀN BỘ PHÂN CÔNG ----------------
    public List<PhanCong> layTatCa() {
        List<PhanCong> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhanCong";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("maNhanVien"), null, 0, null, null, null, 0, null, null, null, null);
                CaLamViec ca = new CaLamViec(rs.getString("maCa"), null, null, null, 0, 0, null);
                
                PhanCong pc = new PhanCong(
                    rs.getString("maPhanCong"),
                    rs.getDate("ngayPhanCong").toLocalDate(),
                    nv,
                    ca
                );
                ds.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ---------------- THÊM PHÂN CÔNG ----------------
    public boolean themPhanCong(PhanCong pc) {
        String sql = "INSERT INTO PhanCong (maPhanCong, ngayPhanCong, maNhanVien, maCa) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, pc.getMaPhanCong());
            pstm.setDate(2, Date.valueOf(pc.getNgayPhanCong()));
            pstm.setString(3, pc.getNhanVien().getMaNhanVien());
            pstm.setString(4, pc.getCaLamViec().getMaCa());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- CẬP NHẬT PHÂN CÔNG ----------------
    public boolean capNhatPhanCong(PhanCong pc) {
        String sql = "UPDATE PhanCong SET ngayPhanCong = ?, maNhanVien = ?, maCa = ? WHERE maPhanCong = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setDate(1, Date.valueOf(pc.getNgayPhanCong()));
            pstm.setString(2, pc.getNhanVien().getMaNhanVien());
            pstm.setString(3, pc.getCaLamViec().getMaCa());
            pstm.setString(4, pc.getMaPhanCong());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- TÌM THEO MÃ ----------------
    public PhanCong timTheoMa(String maPC) {
        String sql = "SELECT * FROM PhanCong WHERE maPhanCong = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maPC);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("maNhanVien"), null, 0, null, null, null, 0, null, null, null, null);
                CaLamViec ca = new CaLamViec(rs.getString("maCa"), null, null, null, 0, 0, nv);
                
                return new PhanCong(
                    rs.getString("maPhanCong"),
                    rs.getDate("ngayPhanCong").toLocalDate(),
                    nv,
                    ca
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- TÌM THEO NHÂN VIÊN ----------------
    public List<PhanCong> timTheoNhanVien(String maNV) {
        List<PhanCong> dsPC = new ArrayList<>();
        String sql = "SELECT * FROM PhanCong WHERE maNhanVien = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maNV);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("maNhanVien"), null, 0, null, null, null, 0, null, null, null, null);
                CaLamViec ca = new CaLamViec(rs.getString("maCa"), null, null, null, 0, 0, nv);
                
                PhanCong pc = new PhanCong(
                    rs.getString("maPhanCong"),
                    rs.getDate("ngayPhanCong").toLocalDate(),
                    nv,
                    ca
                );
                dsPC.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPC;
    }

    // ---------------- XOÁ PHÂN CÔNG ----------------
    public boolean xoaPhanCong(String maPhanCong) {
        String sql = "DELETE FROM PhanCong WHERE maPhanCong = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maPhanCong);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}