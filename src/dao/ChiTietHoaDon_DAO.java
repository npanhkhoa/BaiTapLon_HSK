package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.SanPham;

public class ChiTietHoaDon_DAO {

	private Connection con;
	private SanPham_DAO sanPham_DAO;

	public ChiTietHoaDon_DAO() {
		con = ConnectDB.getInstance().getConnection();
		sanPham_DAO = new SanPham_DAO();
	}

	/**
	 * Lấy tất cả chi tiết của 1 hóa đơn
	 */
	public List<ChiTietHoaDon> layChiTietTheoMaHD(String maHD) {
		List<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
		String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHD);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				HoaDon hd = new HoaDon();
				hd.setMaHoaDon(maHD);
				SanPham sp = sanPham_DAO.timTheoMa(rs.getString("maSanPham"));
				sp.setMaSanPham(rs.getString("maSanPham"));

				ChiTietHoaDon ct = new ChiTietHoaDon(hd, sp, rs.getInt("soLuong"), rs.getBigDecimal("giaBan") , rs.getBigDecimal("thanhTien"));
				dsChiTietHoaDon.add(ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsChiTietHoaDon;
	}

	/**
	 * Thêm một chi tiết hóa đơn (dùng cho Transaction của HoaDon_DAO)
	 */
	public boolean themChiTiet(ChiTietHoaDon ct, Connection transCon) {
		String sql = "INSERT INTO ChiTietHoaDon (maHoaDon, maSanPham, soLuong, giaBan , thanhTien) VALUES (?, ?, ?, ?,?)";
		try (PreparedStatement pstm = transCon.prepareStatement(sql)) {
			pstm.setString(1, ct.getHoaDon().getMaHoaDon());
			pstm.setString(2, ct.getSanPham().getMaSanPham());
			pstm.setInt(3, ct.getSoLuong());
			pstm.setBigDecimal(4, ct.getGiaBan());
			pstm.setBigDecimal(5, ct.getThanhTien());
			
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean xoaChiTietHoaDon(String maHoaDon, String maSanPham) {
	    String sql = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ? AND maSanPham = ?";
	    try (PreparedStatement pstm = con.prepareStatement(sql)) {
	        pstm.setString(1, maHoaDon);
	        pstm.setString(2, maSanPham);
	        return pstm.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
