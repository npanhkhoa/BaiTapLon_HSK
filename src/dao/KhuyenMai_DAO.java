package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhuyenMai;

public class KhuyenMai_DAO {
	private Connection con;

	public KhuyenMai_DAO() {
		con = ConnectDB.getInstance().getConnection();
	}

	public List<KhuyenMai> layTatCa() {
		List<KhuyenMai> ds = new ArrayList<>();
		String sql = "SELECT * FROM KhuyenMai";
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				// maKM là INT IDENTITY, convert sang String
				KhuyenMai km = new KhuyenMai(String.valueOf(rs.getInt("maKM")), rs.getString("tenKM"), 
											 rs.getString("moTa"),rs.getDouble("phanTramGiam"));
				ds.add(km);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themKhuyenMai(KhuyenMai km) {
		// Nếu maKM là IDENTITY, không cần insert maKM
		String sql = "INSERT INTO KhuyenMai (tenKM, moTa, phanTramGiam) VALUES (?, ?, ?)";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, km.getTenKM());
			pstm.setString(2, km.getMoTa());
			pstm.setDouble(3, km.getPhanTramGiam());
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Cập nhật toàn bộ thông tin của một khuyến mãi dựa trên maKM.
	 * 
	 * @param khuyenMaiMoi Đối tượng KhuyenMai chứa thông tin mới.
	 * @return true nếu cập nhật thành công, false nếu thất bại.
	 */
	public boolean capNhatKhuyenMai(KhuyenMai khuyenMaiMoi) {
		String sql = "UPDATE KhuyenMai SET tenKM = ?, moTa = ?, phanTramGiam = ? WHERE maKM = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, khuyenMaiMoi.getTenKM());
			pstm.setString(2, khuyenMaiMoi.getMoTa());
			pstm.setDouble(3, khuyenMaiMoi.getPhanTramGiam());
			pstm.setInt(4, Integer.parseInt(khuyenMaiMoi.getMaKM()));

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public KhuyenMai timTheoMaKhuyenMai(String maKM) {
		String sql = "SELECT * FROM KhuyenMai WHERE maKM = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setInt(1, Integer.parseInt(maKM));

			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					// maKM là INT IDENTITY, convert sang String
					KhuyenMai km = new KhuyenMai(String.valueOf(rs.getInt("maKM")), rs.getString("tenKM"), 
							 	                 rs.getString("moTa"),rs.getDouble("phanTramGiam"));
					return km;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean capNhatPhanTramGiam(String maKM, double phanTramGiamMoi) {
		String sql = "UPDATE KhuyenMai SET phanTramGiam = ? WHERE maKM = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setDouble(1, phanTramGiamMoi);
			pstm.setInt(2, Integer.parseInt(maKM));

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean xoaKhuyenMai(String maKM) {
		String sql = "DELETE FROM KhuyenMai WHERE maKM = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setInt(1, Integer.parseInt(maKM));
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
