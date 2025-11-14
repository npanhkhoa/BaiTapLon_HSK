package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.PhuongThucThanhToan;

public class PhuongThucThanhToan_DAO {
	private Connection con;

	public PhuongThucThanhToan_DAO() {
		try {
	        con = ConnectDB.getInstance().getConnection();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Nếu muốn dừng chương trình ngay khi kết nối lỗi:
	        // throw new RuntimeException(e);
	    }
	}

	public List<PhuongThucThanhToan> layTatCa() {
		List<PhuongThucThanhToan> ds = new ArrayList<>();
		String sql = "SELECT * FROM PhuongThucThanhToan WHERE trangThai = 1"; // Chỉ lấy PTTT đang hoạt động
		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				PhuongThucThanhToan pttt = new PhuongThucThanhToan(rs.getString("maPTTT"), rs.getString("tenPTTT"),
						 										   rs.getString("moTa") ,rs.getInt("trangThaiThanhToan"));
				ds.add(pttt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

}
