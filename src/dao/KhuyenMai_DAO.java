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
				KhuyenMai km = new KhuyenMai(rs.getString("maKM"), rs.getString("tenKM"), 
											 rs.getString("moTa"),rs.getDouble("phanTramGiam"));
				ds.add(km);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean themKhuyenMai(KhuyenMai km) {
		String sql = "INSERT INTO KhuyenMai (maKM, tenKM, moTa ,phanTramGiam) VALUES (?, ?, ?, ?,)";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, km.getMaKM());
			pstm.setString(2, km.getTenKM());
			pstm.setString(3, km.getMoTa());
			pstm.setDouble(4, km.getPhanTramGiam());
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
		String sql = "UPDATE KhuyenMai SET tenKM = ?, moTA = ? ,phanTramGiam = ? WHERE maKM = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, khuyenMaiMoi.getTenKM());
			pstm.setString(2, khuyenMaiMoi.getMoTa());
			pstm.setDouble(3, khuyenMaiMoi.getPhanTramGiam());
			pstm.setString(4, khuyenMaiMoi.getMaKM());

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public KhuyenMai timTheoMaKhuyenMai(String maKM) {
		String sql = "SELECT * FROM KhuyenMai WHERE maKM = ?";
		try (PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, maKM);

			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					KhuyenMai km = new KhuyenMai(rs.getString("maKM"), rs.getString("tenKM"), 
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
			pstm.setString(2, maKM);

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
