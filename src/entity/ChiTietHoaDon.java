package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ChiTietHoaDon {
	private HoaDon hoaDon;
	private SanPham sanPham;
	private int soLuong;
	private BigDecimal giaBan;
	private BigDecimal thanhTien;
	
	public ChiTietHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuong, BigDecimal giaBan, BigDecimal thanhTien) {
		super();
		setHoaDon(hoaDon);
		setSanPham(sanPham);
		setSoLuong(soLuong);
		setGiaBan(giaBan);
		setThanhTien(thanhTien);
	}

	public ChiTietHoaDon() {
		this.hoaDon = null;
		this.sanPham = null;
		this.soLuong = 1;
		this.giaBan = BigDecimal.ZERO;
		this.thanhTien = BigDecimal.ZERO;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		if (hoaDon == null) {
			throw new IllegalArgumentException("Hóa đơn không được null");
		}
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		if (sanPham == null) {
			throw new IllegalArgumentException("Sản phẩm không được null");
		}
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		if (soLuong <= 0)
			throw new IllegalArgumentException("Số lượng phải > 0");
		this.soLuong = soLuong;
	}

	public BigDecimal getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(BigDecimal giaBan) {
		if (giaBan == null || giaBan.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Đơn giá phải > 0");
		this.giaBan = giaBan;
	}

	public BigDecimal getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(BigDecimal thanhTien) {
		this.thanhTien = thanhTien;
	}
	
	public void capNhatSoLuong(int soLuong) {
		if (soLuong <= 0) {
			throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
		}
		this.soLuong = soLuong;
	}

	@Override
	public String toString() {
		return "ChiTietHoaDon [hoaDon=" + hoaDon + ", sanPham=" + sanPham + ", soLuong=" + soLuong + ", giaBan="
				+ giaBan + ", thanhTien=" + thanhTien + "]";
	}
	
	

	

	
	
	
	
	
	
}
