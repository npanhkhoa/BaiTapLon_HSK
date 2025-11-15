package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private String maNhanVien;
    private LocalDateTime ngayLap;
    private List<SanPham> dsachSanPham;
    private int soLuong;
    private double giaBan;
    private double thanhTien;



    public HoaDon(String maHoaDon, String maNhanVien, LocalDateTime ngayLap, List<SanPham> dsachSanPham, int soLuong, double giaBan, double thanhTien) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.dsachSanPham = dsachSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }

    public HoaDon() {

    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        if (maHoaDon == null || maHoaDon.isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        this.maNhanVien = maNhanVien;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        if (ngayLap == null) {
            throw new IllegalArgumentException("Ngày lập không được để trống");
        }
        this.ngayLap = ngayLap;
    }

    public List<SanPham> getDsachSanPham() {
        return dsachSanPham;
    }

    public void setDsachSanPham(List<SanPham> dsachSanPham) {
        if (dsachSanPham == null || dsachSanPham.isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được để trống");
        }
        this.dsachSanPham = dsachSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", ngayLap=" + ngayLap +
                ", dsachSanPham=" + dsachSanPham +
                ", soLuong=" + soLuong +
                ", giaBan=" + giaBan +
                ", thanhTien=" + thanhTien +
                '}';
    }

}
