package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;

public class HoaDon_DAO {
	private Connection con;
	private ChiTietHoaDon_DAO ctHoaDon_DAO; // Một DAO gọi DAO khác

	public HoaDon_DAO() {
		con = ConnectDB.getInstance().getConnection();
		ctHoaDon_DAO = new ChiTietHoaDon_DAO(); // Khởi tạo DAO phụ
	}

	/**
	 * Lấy tất cả hóa đơn
	 */
	public List<HoaDon> layTatCa() {
		List<HoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM HoaDon";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"),rs.getDate("ngayLap").toLocalDate(),
									   rs.getDouble("tongTien") , rs.getString("maPTTT") , rs.getString("maKhachHang") , 
									   rs.getString("maKM") , rs.getInt("trangThaiThanhToan") , null);

				ds.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	/**
	 * Thêm một hóa đơn mới vào CSDL (bao gồm cả chi tiết) Đây là một giao dịch
	 */
	public boolean themHoaDon(HoaDon hd) {
		String sqlHD = "INSERT INTO HoaDon (maHoaDon, maNhanVien , ngayLap , tongTien , maPTTT , maKhachHang , maKM , trangThaiThanhToan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con.setAutoCommit(false);

			// 1. Thêm Hóa Đơn
			try (PreparedStatement pstmHD = con.prepareStatement(sqlHD)) {
				pstmHD.setString(1, hd.getMaHoaDon());
				pstmHD.setString(2, hd.getMaNhanVien());
				pstmHD.setDate(3, java.sql.Date.valueOf(hd.getNgayLap()));
				pstmHD.setDouble(4, hd.getTongTien());
				pstmHD.setString(5, hd.getMaPTTT());
				pstmHD.setString(6, hd.getMaKhachHang());
				pstmHD.setString(7, hd.getMaKM());
				pstmHD.setInt(8, hd.getTrangThaiThanhToan());
				pstmHD.executeUpdate();
			}

			// 2. Thêm tất cả Chi Tiết Hóa Đơn
			for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
				if (!ctHoaDon_DAO.themChiTiet(ct, con)) {
					con.rollback();
					return false;
				}
			}

			con.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public HoaDon timHoaDonTheoMa(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			return null;
		String sql = "select * from HoaDon where maHoaDon = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maHoaDon.trim());
			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"),rs.getDate("ngayLap").toLocalDate(),
							     rs.getDouble("tongTien") , rs.getString("maPTTT") , rs.getString("maKhachHang"),
							     rs.getString("maKM") , rs.getInt("trangThaiThanhToan") , null);
					return hd;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

//	Lấy hóa đơn theo ngày để thống kê doanh thu
	public ArrayList<HoaDon> layHoaDonTheoNgay(LocalDate tuNgay, LocalDate denNgay) {
		String sql = "SELECT * FROM HoaDon WHERE ngayLap BETWEEN ? AND ?";
		List<HoaDon> dsHD = new ArrayList<HoaDon>();
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setDate(1, java.sql.Date.valueOf(tuNgay));
			pstm.setDate(2, java.sql.Date.valueOf(denNgay));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"),rs.getDate("ngayLap").toLocalDate(),
						   	 rs.getDouble("tongTien") , rs.getString("maPTTT") , rs.getString("maKhachHang"),
						   	 rs.getString("maKM") , rs.getInt("trangThaiThanhToan") , null);
				dsHD.add(hd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return (ArrayList<HoaDon>) dsHD;
	}

	public List<HoaDon> layTheoTrangThai(int trangThaiThanhToan) {
		List<HoaDon> ds = new ArrayList<>();
		String sql = "SELECT * FROM HoaDon WHERE trangThaiThanhToan = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setInt(1, trangThaiThanhToan);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				HoaDon hd =	new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"),rs.getDate("ngayLap").toLocalDate(),
						     rs.getDouble("tongTien") , rs.getString("maPTTT") , rs.getString("maKhachHang"),
						     rs.getString("maKM") , rs.getInt("trangThaiThanhToan") , null);
				ds.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

// Lấy lịch xử mua hàng của KhachHang
	public ArrayList<HoaDon> layHoaDonTheoMaKhachHang(String maKhachHang) {
		String sql = "select * from HoaDon where maKhachHang = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maKhachHang);
			ResultSet rs = pstm.executeQuery();
			List<HoaDon> dsHD = new ArrayList<HoaDon>();
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"),rs.getDate("ngayLap").toLocalDate(),
						     rs.getDouble("tongTien") , rs.getString("maPTTT") , rs.getString("maKhachHang"),
						     rs.getString("maKM") , rs.getInt("trangThaiThanhToan") , null);
				dsHD.add(hd);
			}
			return (ArrayList<HoaDon>) dsHD;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}