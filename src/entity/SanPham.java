package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class SanPham {
	private String maSanPham;
	private String tenSanPham;
	private BigDecimal giaBan;
	private int soLuong;
	private String maNCC;
	private String donViTinh;
	
	public SanPham() {
		super();
	}

	public SanPham(String maSanPham, String tenSanPham, BigDecimal giaBan, 
			       int soLuong, String maNCC,String donViTinh) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.maNCC = maNCC;
		this.donViTinh = donViTinh;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		if (maSanPham == null || maSanPham.isEmpty())
			throw new IllegalArgumentException("Mã sản phẩm không được bỏ trống");
		if (!maSanPham.matches("^SP\\d{3}$")) {
			throw new IllegalArgumentException("Mã sản phẩm phải theo dạng SPxxx");
		}
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		if (tenSanPham == null || tenSanPham.isEmpty())
			throw new IllegalArgumentException("Tên sản phẩm không được bỏ trống");

		this.tenSanPham = tenSanPham;
	}

	public BigDecimal getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(BigDecimal giaBan) {
		if (giaBan.intValue() < 0)
			throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn bằng 0");

		this.giaBan = giaBan;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(maSanPham);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SanPham other = (SanPham) obj;
		return Objects.equals(maSanPham, other.maSanPham);
	}

	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", giaBan=" + giaBan + ", soLuong="
				+ soLuong + ", maNCC=" + maNCC + ", donViTinh=" + donViTinh + "]";
	}
	
	
	
	
	
	
	
	
}
