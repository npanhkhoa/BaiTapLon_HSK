package entity;

public class PhuongThucThanhToan {
	private String maPTTT;
	private String tenPTTT;
	private String moTa;
	
	public PhuongThucThanhToan(String maPTTT) {
		super();
		this.maPTTT = maPTTT;
	}

	public PhuongThucThanhToan(String maPTTT, String tenPTTT, String moTa) {
		super();
		this.maPTTT = maPTTT;
		this.tenPTTT = tenPTTT;
		this.moTa = moTa;
	}

	public String getMaPTTT() {
		return maPTTT;
	}

	public void setMaPTTT(String maPTTT) {
		this.maPTTT = maPTTT;
	}

	public String getTenPTTT() {
		return tenPTTT;
	}

	public void setTenPTTT(String tenPTTT) {
		this.tenPTTT = tenPTTT;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

}
	
	
