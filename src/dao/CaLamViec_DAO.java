package dao;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.CaLamViec;

public class CaLamViec_DAO {
    private Connection con;

    public CaLamViec_DAO() {
        con = ConnectDB.getInstance().getConnection();
    }

    // ---------------- LẤY TOÀN BỘ CA LÀM VIỆC ----------------
    public List<CaLamViec> layTatCa() {
        List<CaLamViec> ds = new ArrayList<>();
        String sql = "SELECT * FROM CaLamViec";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                CaLamViec ca = new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenCa"),
                    rs.getTime("gioBatDau").toLocalTime(),
                    rs.getTime("gioKetThuc").toLocalTime()
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
        String sql = "INSERT INTO CaLamViec (maCa, tenCa, gioBatDau, gioKetThuc) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ca.getMaCa());
            pstm.setString(2, ca.getTenCa());
            pstm.setTime(3, Time.valueOf(ca.getGioBatDau()));
            pstm.setTime(4, Time.valueOf(ca.getGioKetThuc()));

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- CẬP NHẬT CA LÀM VIỆC ----------------
    public boolean capNhatCaLamViec(CaLamViec ca) {
        String sql = "UPDATE CaLamViec SET tenCa = ?, gioBatDau = ?, gioKetThuc = ? WHERE maCa = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, ca.getTenCa());
            pstm.setTime(2, Time.valueOf(ca.getGioBatDau()));
            pstm.setTime(3, Time.valueOf(ca.getGioKetThuc()));
            pstm.setString(4, ca.getMaCa());

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
                return new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenCa"),
                    rs.getTime("gioBatDau").toLocalTime(),
                    rs.getTime("gioKetThuc").toLocalTime()
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
                CaLamViec ca = new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenCa"),
                    rs.getTime("gioBatDau").toLocalTime(),
                    rs.getTime("gioKetThuc").toLocalTime()
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
}