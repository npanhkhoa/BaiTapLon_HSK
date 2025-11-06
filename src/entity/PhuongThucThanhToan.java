package entity;

import java.util.Objects;

public class PhuongThucThanhToan {
	private String maPTTT;
	private String tenPTTT;
	private String moTa;
	private int trangThaiThanhToan;
	
	public PhuongThucThanhToan() {
		super();
	}

	public PhuongThucThanhToan(String maPTTT, String tenPTTT, String moTa, int trangThaiThanhToan) {
		super();
		this.maPTTT = maPTTT;
		this.tenPTTT = tenPTTT;
		this.moTa = moTa;
		this.trangThaiThanhToan = trangThaiThanhToan;
	}

	public String getMaPTTT() {
		return maPTTT;
	}

	public void setMaPTTT(String maPTTT) {
		 if (maPTTT == null || maPTTT.trim().isEmpty())
	            throw new IllegalArgumentException("Mã phương thức thanh toán không được để trống!");
	        this.maPTTT = maPTTT.trim();
	}

	public String getTenPTTT() {
		return tenPTTT;
	}

	public void setTenPTTT(String tenPTTT) {
		 if (tenPTTT == null || tenPTTT.trim().isEmpty())
	            throw new IllegalArgumentException("Tên phương thức thanh toán không được để trống!");
	        this.tenPTTT = tenPTTT.trim();
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		  if (moTa == null) moTa = "";
	        this.moTa = moTa.trim();
	}

	public int getTrangThaiThanhToan() {
		return trangThaiThanhToan;
	}

	public void setTrangThaiThanhToan(int trangThaiThanhToan) {
		if (trangThaiThanhToan < 0 || trangThaiThanhToan > 1)
            throw new IllegalArgumentException("Trạng thái chỉ có thể là 0 (không hoạt động) hoặc 1 (đang hoạt động)!");
        this.trangThaiThanhToan = trangThaiThanhToan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPTTT);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhuongThucThanhToan other = (PhuongThucThanhToan) obj;
		return Objects.equals(maPTTT, other.maPTTT);
	}
	
	
	
	
	

}
	
	
