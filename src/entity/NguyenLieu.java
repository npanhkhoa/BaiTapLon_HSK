package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NguyenLieu {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donViTinh;
    private double giaNhap;
    private LocalDate ngayNhap;
    private LocalDate ngayHetHan;
    private KhoNguyenLieu khoNguyenLieu;
    private int soLuong; // Thêm trường soLuong
    private NhaCungCap nhaCungCap; // Thêm trường nhaCungCap

    // Constructor
    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String donViTinh, double giaNhap, LocalDate ngayNhap, LocalDate ngayHetHan, KhoNguyenLieu khoNguyenLieu, int soLuong, NhaCungCap nhaCungCap) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donViTinh = donViTinh;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.ngayHetHan = ngayHetHan;
        this.khoNguyenLieu = khoNguyenLieu;
        this.soLuong = soLuong;
        this.nhaCungCap = nhaCungCap;
    }
    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String donViTinh, double giaNhap, int soLuong, KhoNguyenLieu khoNguyenLieu, NhaCungCap nhaCungCap) {
        this.maNguyenLieu = "Chua xac dinh";
        this.tenNguyenLieu = "Chua xac dinh";
        this.donViTinh = "";
        this.giaNhap = 0;
        this.ngayNhap = LocalDate.now();
        this.ngayHetHan = LocalDate.now();
        this.khoNguyenLieu = null;
        this.soLuong = 0;
        this.nhaCungCap = null;
    }


    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if(soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không thể âm.");
        }
        this.soLuong = soLuong;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        if (giaNhap < 0) {
            throw new IllegalArgumentException("Giá nhập không thể âm.");
        }
        this.giaNhap = giaNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        if(ngayHetHan.isBefore(ngayNhap)) {
            throw new IllegalArgumentException("Ngày hết hạn không được trước ngày nhập.");
        }
        this.ngayHetHan = ngayHetHan;
    }

    public KhoNguyenLieu getKhoNguyenLieu() {
        return khoNguyenLieu;
    }

    public void setKhoNguyenLieu(KhoNguyenLieu khoNguyenLieu) {
        this.khoNguyenLieu = khoNguyenLieu;
    }
    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }
    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NguyenLieu that = (NguyenLieu) o;
        return maNguyenLieu.equals(that.maNguyenLieu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNguyenLieu);
    }

    @Override
    public String toString() {
        return "NguyenLieu{" +
                "maNguyenLieu='" + maNguyenLieu + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }
}