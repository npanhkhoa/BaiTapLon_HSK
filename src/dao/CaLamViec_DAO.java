package dao;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.CaLamViec;
import entity.NhanVien;

public class CaLamViec_DAO {
    private Connection con;

    public CaLamViec_DAO() {
    	 try {
             con = ConnectDB.getInstance().getConnection();
         } catch (SQLException e) {
             e.printStackTrace();
             // Nếu muốn dừng chương trình ngay khi kết nối lỗi:
             // throw new RuntimeException(e);
         }
    }

    // ---------------- LẤY TOÀN BỘ CA LÀM VIỆC ----------------
    public List<CaLamViec> layTatCa() {
        List<CaLamViec> ds = new ArrayList<>();
        String sql = "SELECT * FROM CaLamViec";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
            	NhanVien nv = null;
            	String maNV = rs.getString("maNhanVien");
            	if(maNV !=null) {
            		nv = new NhanVien();
            		nv.setMaNhanVien(maNV);
            	}
                CaLamViec ca = new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenCa"),
                    rs.getTime("gioBatDau").toLocalTime(),
                    rs.getTime("gioKetThuc").toLocalTime(),
                    rs.getDouble("tienMoCa"),
                    rs.getDouble("tienDongCa"),
                    nv
                );
                ds.add(ca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // ---------------- THÊM CA LÀM VIỆC ----------------
    public boolean themCaLamViec(CaLamViec ca) {
        String sql = "INSERT INTO CaLamViec (maCa, tenCa, gioBatDau, gioKetThuc , tienMoCa , tienDongCa , maNhanVien) VALUES (?, ?, ?, ?, ? , ? , ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ca.getMaCa());
            pstm.setString(2, ca.getTenCa());
            pstm.setTime(3, Time.valueOf(ca.getGioBatDau()));
            pstm.setTime(4, Time.valueOf(ca.getGioKetThuc()));
            pstm.setDouble(5,ca.getTienMoCa());
            pstm.setDouble(6, ca.getTienDongCa());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- CẬP NHẬT CA LÀM VIỆC ----------------
    public boolean capNhatCaLamViec(CaLamViec ca) {
        String sql = "UPDATE CaLamViec SET tenCa = ?, gioBatDau = ?, gioKetThuc = ?, tienMoCa = ?, tienDongCa = ?  , maNhanVien = ? WHERE maCa = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ca.getTenCa());
            pstm.setTime(2, Time.valueOf(ca.getGioBatDau()));
            pstm.setTime(3, Time.valueOf(ca.getGioKetThuc()));
            pstm.setDouble(4, ca.getTienMoCa());
            pstm.setDouble(5,ca.getTienDongCa());
            pstm.setString(6, ca.getNhanVien().getMaNhanVien());
            pstm.setString(7, ca.getMaCa());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- TÌM THEO MÃ ----------------
    public CaLamViec timTheoMa(String maCa) {
        String sql = "SELECT * FROM CaLamViec WHERE maCa = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maCa);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
            	NhanVien nv = null;
            	String maNV = rs.getString("maNhanVien");
            	if (maNV != null) {
            		nv = new NhanVien();
            		nv.setMaNhanVien(maNV);
            	}
                return new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenCa"),
                    rs.getTime("gioBatDau").toLocalTime(),
                    rs.getTime("gioKetThuc").toLocalTime(),
                    rs.getDouble("tienMoCa"),
                    rs.getDouble("tienDongCa"),
                    nv
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- TÌM THEO TÊN ----------------
    public List<CaLamViec> timTheoTen(String tenCa) {
        List<CaLamViec> dsCa = new ArrayList<>();
        String sql = "SELECT * FROM CaLamViec WHERE tenCa LIKE ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, "%" + tenCa + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
            	NhanVien nv = null;
                CaLamViec ca = new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenCa"),
                    rs.getTime("gioBatDau").toLocalTime(),
                    rs.getTime("gioKetThuc").toLocalTime(),
                    rs.getDouble("tienMoCa"),
                    rs.getDouble("tienDongCa"),
                    nv
                );
                dsCa.add(ca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCa;
    }

    // ---------------- XOÁ CA LÀM VIỆC ----------------
    public boolean xoaCaLamViec(String maCa) {
        String sql = "DELETE FROM CaLamViec WHERE maCa = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, maCa);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public boolean isShiftIdExists(String maCaLamViec) {
		// TODO Auto-generated method stub
		return false;
	}
}