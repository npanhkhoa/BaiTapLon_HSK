package entity;

import java.time.LocalDate;

public class PhanCong {
    private String maPhanCong;
    private LocalDate ngayPhanCong;
    private NhanVien nhanVien;
    private CaLamViec caLamViec;

    public PhanCong() {
        super();
    }

    public PhanCong(String maPhanCong, LocalDate ngayPhanCong, NhanVien nhanVien, CaLamViec caLamViec) {
        super();
        setMaPhanCong(maPhanCong);
        setNgayPhanCong(ngayPhanCong);
        setNhanVien(nhanVien);
        setCaLamViec(caLamViec);
    }

    public String getMaPhanCong() {
        return maPhanCong;
    }

    public void setMaPhanCong(String maPhanCong) {
        if (maPhanCong == null || maPhanCong.trim().isEmpty())
            throw new IllegalArgumentException("Mã phân công không được để trống!");
        this.maPhanCong = maPhanCong;
    }

    public LocalDate getNgayPhanCong() {
        return ngayPhanCong;
    }

    public void setNgayPhanCong(LocalDate ngayPhanCong) {
        if (ngayPhanCong == null)
            throw new IllegalArgumentException("Ngày phân công không được để trống!");
        this.ngayPhanCong = ngayPhanCong;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        if (nhanVien == null)
            throw new IllegalArgumentException("Nhân viên không được để trống!");
        this.nhanVien = nhanVien;
    }

    public CaLamViec getCaLamViec() {
        return caLamViec;
    }

    public void setCaLamViec(CaLamViec caLamViec) {
        if (caLamViec == null)
            throw new IllegalArgumentException("Ca làm việc không được để trống!");
        this.caLamViec = caLamViec;
    }

    @Override
    public String toString() {
        return "PhanCong [maPhanCong=" + maPhanCong
                + ", ngayPhanCong=" + ngayPhanCong
                + ", nhanVien=" + (nhanVien != null ? nhanVien.getTenNhanVien() : "null")
                + ", caLamViec=" + (caLamViec != null ? caLamViec.getTenCa() : "null") + "]";
    }
}
