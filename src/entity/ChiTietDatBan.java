package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChiTietDatBan {
	private String maCTDB;
	private String maKhachHang;
	private String maBan;
	private String maNhanVien;
	private LocalDate ngayDat;
	private LocalTime gioDat;
	private int soNguoi;
	private String trangThai; // DEFAULT 'Chưa đến'
	private String ghiChu;

	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

	public ChiTietDatBan(String maCTDB, String maKhachHang, String maBan, String maNhanVien, LocalDate ngayDat,
			LocalTime gioDat, int soNguoi, String trangThai, String ghiChu) throws Exception {
		this.setMaCTDB(maCTDB);
		this.setMaKhachHang(maKhachHang);
		this.setMaBan(maBan);
		this.setMaNhanVien(maNhanVien);
		this.setNgayDat(ngayDat);
		this.setGioDat(gioDat);
		this.setSoNguoi(soNguoi);
		this.setTrangThai(trangThai);
		this.setGhiChu(ghiChu);
	}

	public String getMaCTDB() {
		return maCTDB;
	}

	public void setMaCTDB(String maCTDB) throws Exception {
		if (maCTDB != null && maCTDB.matches("^DB\\d{2,3}$")) {
			this.maCTDB = maCTDB;
		} else {
			throw new Exception("Mã chi tiết đặt bàn không hợp lệ (ví dụ: DB01, DB001).");
		}
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = (maKhachHang != null && !maKhachHang.trim().isEmpty()) ? maKhachHang.trim() : null;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) throws Exception {
		if (maBan == null || maBan.trim().isEmpty()) {
			throw new Exception("Mã bàn không được để trống (bắt buộc).");
		}
		this.maBan = maBan.trim();
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = (maNhanVien != null && !maNhanVien.trim().isEmpty()) ? maNhanVien.trim() : null;
	}

	public LocalDate getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(LocalDate ngayDat) throws Exception {
		if (ngayDat == null) {
			throw new Exception("Ngày đặt không được để trống.");
		}
		this.ngayDat = ngayDat;
	}

	public LocalTime getGioDat() {
		return gioDat;
	}

	public void setGioDat(LocalTime gioDat) throws Exception {
		if (gioDat == null) {
			throw new Exception("Giờ đặt không được để trống.");
		}
		this.gioDat = gioDat;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) throws Exception {
		if (soNguoi <= 0) {
			throw new Exception("Số người phải là số dương và khác 0.");
		}
		this.soNguoi = soNguoi;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		if (trangThai == null || trangThai.trim().isEmpty()) {
			this.trangThai = "Chưa đến"; // theo DEFAULT của script
		} else {
			this.trangThai = trangThai.trim();
		}
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = (ghiChu != null) ? ghiChu.trim() : null;
	}

	@Override
	public String toString() {
		String dateStr = (ngayDat != null) ? ngayDat.format(DATE_FMT) : "";
		String timeStr = (gioDat != null) ? gioDat.format(TIME_FMT) : "";
		return maCTDB + " | " + (maKhachHang != null ? maKhachHang : "NULL") + " | " + maBan + " | "
				+ (maNhanVien != null ? maNhanVien : "NULL") + " | " + dateStr + " | " + timeStr + " | " + soNguoi
				+ " | " + (trangThai != null ? trangThai : "") + " | " + (ghiChu != null ? ghiChu : "");
	}
}
