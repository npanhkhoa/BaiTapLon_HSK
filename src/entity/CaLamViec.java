package entity;

import java.time.LocalDateTime;

public class CaLamViec {
    private String maCaLamViec;
    private NhanVien nhanVien;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private double tienMoCa;
    private double tienDongCa;

    public CaLamViec(String maCaLamViec, NhanVien nhanVien, LocalDateTime thoiGianBatDau, LocalDateTime thoiGianKetThuc, double tienMoCa, double tienDongCa) {
        this.maCaLamViec = maCaLamViec;
        this.nhanVien = nhanVien;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.tienMoCa = tienMoCa;
        this.tienDongCa = tienDongCa;
    }

    public CaLamViec() {
    }

    public String getMaCaLamViec() {
        return maCaLamViec;
    }

    public void setMaCaLamViec(String maCaLamViec) {
        if (maCaLamViec == null || maCaLamViec.isEmpty()) {
            throw new IllegalArgumentException("Mã ca làm việc không được để trống");
        }
        this.maCaLamViec = maCaLamViec;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không được để trống");
        }
        this.nhanVien = nhanVien;
    }

    public LocalDateTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) {
        if (thoiGianBatDau == null) {
            throw new IllegalArgumentException("Thời gian bắt đầu không được để trống");
        }
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public LocalDateTime getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) {
        if (thoiGianKetThuc == null) {
            throw new IllegalArgumentException("Thời gian kết thúc không được để trống");
        }
        this.thoiGianKetThuc = thoiGianKetThuc;
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

    @Override
    public String toString() {
        return "CaLamViec{" +
                "maCaLamViec='" + maCaLamViec + '\'' +
                ", nhanVien=" + nhanVien +
                ", thoiGianBatDau=" + thoiGianBatDau +
                ", thoiGianKetThuc=" + thoiGianKetThuc +
                ", tienMoCa=" + tienMoCa +
                ", tienDongCa=" + tienDongCa +
                '}';
    }
}
