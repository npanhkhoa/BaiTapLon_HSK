package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class HoaDon {
	private String maHoaDon;
	private String maNhanVien;
	private LocalDate ngayLap;
	private double tongTien;
	private String maPTTT;
	private String maKhachHang;
	private String maKM;
	private int trangThaiThanhToan;
	private String ghiChu;
	
	private ArrayList<ChiTietHoaDon> dsChiTiet;
	
	public HoaDon() {
		super();
	}

// Constructor cho việc lấy hóa đơn từ CSDL
	public HoaDon(String maHoaDon, String maNhanVien, LocalDate ngayLap, 
				  double tongTien, String maPTTT,String maKhachHang, 
			      String maKM, int trangThaiThanhToan , String ghiChu) {
		
		this.maHoaDon = maHoaDon;
		this.maNhanVien = maNhanVien;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
		this.maPTTT = maPTTT;
		this.maKhachHang = maKhachHang;
		this.maKM = maKM;
		this.trangThaiThanhToan = trangThaiThanhToan;
		this.ghiChu = ghiChu;
		this.dsChiTiet = new ArrayList<>();
	}
	
//Constructor cho việc tạo hóa đơn mới (chưa có mã)
	public HoaDon(String maNhanVien, LocalDate ngayLap, double tongTien, 
		          String maPTTT, String maKhachHang,
		          String maKM, int trangThaiThanhToan ,String ghiChu) {
	    this.maHoaDon = null; //chấp nhận null khi tạo 1 hoá đơn mới
	    this.maNhanVien = maNhanVien;
	    this.ngayLap = ngayLap;
	    this.tongTien = tongTien;
	    this.maPTTT = maPTTT;
	    this.maKhachHang = maKhachHang;
	    this.maKM = maKM;
	    this.trangThaiThanhToan = trangThaiThanhToan;
	    this.ghiChu = ghiChu;
	    this.dsChiTiet = new ArrayList<>();
}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			throw new IllegalArgumentException("Mã hóa đơn không được rỗng");

		if (!maHoaDon.matches("^HD\\d{3}$"))
			throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx");

		this.maHoaDon = maHoaDon;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public LocalDate getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(LocalDate ngayLap) {
		this.ngayLap = ngayLap;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public String getMaPTTT() {
		return maPTTT;
	}

	public void setMaPTTT(String maPTTT) {
		this.maPTTT = maPTTT;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getMaKM() {
		return maKM;
	}

	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}

	public int getTrangThaiThanhToan() {
		return trangThaiThanhToan;
	}

	public void setTrangThaiThanhToan(int trangThaiThanhToan) {
		this.trangThaiThanhToan = trangThaiThanhToan;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public ArrayList<ChiTietHoaDon> getDsChiTiet() {
		return dsChiTiet;
	}

	public void setDsChiTiet(ArrayList<ChiTietHoaDon> dsChiTiet) {
		this.dsChiTiet = dsChiTiet;
	}
	
	
	



	
	
	
	
	
	

	
	
	
	
	
}
