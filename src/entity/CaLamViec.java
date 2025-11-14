package entity;

import java.time.LocalTime;

public class CaLamViec {
    private String maCa;
    private String tenCa;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private double tienMoCa;
    private double tienDongCa;
    private NhanVien nhanVien;

    public CaLamViec() {
        super();
    }

    public CaLamViec(String maCa, String tenCa, LocalTime gioBatDau, LocalTime gioKetThuc , double tienMoCa , double tienDongCa , NhanVien nhanVien) {
        super();
        setMaCa(maCa);
        setTenCa(tenCa);
        setGioBatDau(gioBatDau);
        setGioKetThuc(gioKetThuc);
        setTienMoCa(tienMoCa);
        setTienDongCa(tienDongCa);
        setNhanVien(nhanVien);
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        if (maCa == null || maCa.trim().isEmpty())
            throw new IllegalArgumentException("Mã ca không được để trống!");
        this.maCa = maCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        if (tenCa == null || tenCa.trim().isEmpty())
            throw new IllegalArgumentException("Tên ca không được để trống!");
        this.tenCa = tenCa;
    }

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalTime gioBatDau) {
        if (gioBatDau == null)
            throw new IllegalArgumentException("Giờ bắt đầu không được để trống!");
        this.gioBatDau = gioBatDau;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalTime gioKetThuc) {
        if (gioKetThuc == null)
            throw new IllegalArgumentException("Giờ kết thúc không được để trống!");
        if (gioBatDau != null && !gioKetThuc.isAfter(gioBatDau))
            throw new IllegalArgumentException("Giờ kết thúc phải sau giờ bắt đầu!");
        this.gioKetThuc = gioKetThuc;
    }
    

    public double getTienMoCa() {
		return tienMoCa;
	}

	public void setTienMoCa(double tienMoCa) {
		this.tienMoCa = tienMoCa;
	}

	public double getTienDongCa() {
		return tienDongCa;
	}

	public void setTienDongCa(double tienDongCa) {
		this.tienDongCa = tienDongCa;
	}
	

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public String toString() {
		return "CaLamViec [maCa=" + maCa + ", tenCa=" + tenCa + ", gioBatDau=" + gioBatDau + ", gioKetThuc="
				+ gioKetThuc + ", tienMoCa=" + tienMoCa + ", tienDongCa=" + tienDongCa + ", nhanVien=" + nhanVien + "]";
	}
	
	

	

	
}
