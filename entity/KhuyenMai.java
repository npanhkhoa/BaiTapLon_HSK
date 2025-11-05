package entity;

public class KhuyenMai {
	private String maKM;
	private String tenKM;
	private String moTa;
	private double phanTramGiam;
	
	public KhuyenMai() {
		super();
	}

	public KhuyenMai(String maKM, String tenKM, String moTa, double phanTramGiam) {
		super();
		this.maKM = maKM;
		this.tenKM = tenKM;
		this.moTa = moTa;
		this.phanTramGiam = phanTramGiam;
	}

	public String getMaKM() {
		return maKM;
	}

	public void setMaKM(String maKM) {
		if (maKM == null || maKM.isEmpty())
			throw new IllegalArgumentException("Mã khuyến mãi không được bỏ trống");
		if (!maKM.matches("^MKM\\d{3}$")) {
			throw new IllegalArgumentException("Mã khuyến mãi phải theo dạng MKMxxx");
		}
		this.maKM = maKM;
	}

	public String getTenKM() {
		return tenKM;
	}

	public void setTenKM(String tenKM) {
		this.tenKM = tenKM;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public double getPhanTramGiam() {
		return phanTramGiam;
	}

	public void setPhanTramGiam(double phanTramGiam) {
		if (phanTramGiam <= 0) {
			throw new IllegalArgumentException("Giá trị khuyến mãi phải lớn hơn 0");
		}
		this.phanTramGiam = phanTramGiam;
	}

	@Override
	public String toString() {
		return "KhuyenMai [maKM=" + maKM + ", tenKM=" + tenKM + ", moTa=" + moTa + ", phanTramGiam=" + phanTramGiam
				+ "]";
	}

	
	
	
	

	
	
}
