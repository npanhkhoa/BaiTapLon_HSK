package entity;/*
 * @ (#) KhoViewTable.java   1.0     30/04/2025
package entity;


/**
 * @description :
 * @author : Vy, Pham Kha Vy
 * @version 1.0
 * @created : 30/04/2025
 */


import java.sql.Date;

public class KhoViewTable {
    private String maKho, maNguyenLieu, tenNguyenLieu,donViTinh, maNhaCungCap, tenNhaCungCap;
    private double giaNhap;
    private java.sql.Date ngayNhap, ngayHetHan;
    private int soLuong;
    private String tenKho, diaChiKho, diaChiNhaCungCap, soDienThoaiNhaCungCap;

    public KhoViewTable(String maKho, String maNguyenLieu, String tenNguyenLieu, String donViTinh, double giaNhap, int soLuong, Date ngayNhap, Date ngayHetHan, String maNhaCungCap, String tenNhaCungCap) {
        this.maKho = maKho;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donViTinh = donViTinh;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.ngayHetHan = ngayHetHan;
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public KhoViewTable(String maKho,String tenKho, String diaChiKho, String maNguyenLieu, String tenNguyenLieu, String donViTinh,double giaNhap, int soLuong, Date ngayNhap, Date ngayHetHan, String maNhaCungCap, String tenNhaCungCap, String diaChiNhaCungCap, String soDienThoaiNhaCungCap) {
        this.maKho = maKho;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donViTinh = donViTinh;
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.ngayHetHan = ngayHetHan;
        this.soLuong = soLuong;
        this.tenKho = tenKho;
        this.diaChiKho = diaChiKho;
        this.diaChiNhaCungCap = diaChiNhaCungCap;
        this.soDienThoaiNhaCungCap = soDienThoaiNhaCungCap;
    }

    public KhoViewTable(){

    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        if (maKho == null || maKho.isEmpty()) {
            throw new IllegalArgumentException("Mã kho không được để trống");
        }
        this.maKho = maKho;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaChiKho() {
        return diaChiKho;
    }

    public void setDiaChiKho(String diaChiKho) {
        this.diaChiKho = diaChiKho;
    }

    public String getDiaChiNhaCungCap() {
        return diaChiNhaCungCap;
    }

    public void setDiaChiNhaCungCap(String diaChiNhaCungCap) {
        this.diaChiNhaCungCap = diaChiNhaCungCap;
    }

    public String getSoDienThoaiNhaCungCap() {
        return soDienThoaiNhaCungCap;
    }

    public void setSoDienThoaiNhaCungCap(String soDienThoaiNhaCungCap) {
        this.soDienThoaiNhaCungCap = soDienThoaiNhaCungCap;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        if (maNguyenLieu == null || maNguyenLieu.isEmpty()) {
            throw new IllegalArgumentException("Mã nguyên liệu không được để trống");
        }
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        if (tenNguyenLieu == null || tenNguyenLieu.isEmpty()) {
            throw new IllegalArgumentException("Tên nguyên liệu không được để trống");
        }
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ");
        }
        this.soLuong = soLuong;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        if (maNhaCungCap == null || maNhaCungCap.isEmpty()) {
            throw new IllegalArgumentException("Mã nhà cung cấp không được để trống");
        }
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        if (tenNhaCungCap == null || tenNhaCungCap.isEmpty()) {
            throw new IllegalArgumentException("Tên nhà cung cấp không được để trống");
        }
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        if (giaNhap < 0) {
            throw new IllegalArgumentException("Giá nhập không hợp lệ");
        }
        this.giaNhap = giaNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayNhap(Date ngayNhap) {
        Date currentDate = new Date(System.currentTimeMillis());
        if (ngayNhap.after(currentDate)) {
            throw new IllegalArgumentException("Ngày nhập không được sau ngày hiện tại.");
        }
        this.ngayNhap = ngayNhap;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        if (ngayHetHan.before(ngayNhap)) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau ngày nhập.");
        }
        Date currentDate = new Date(System.currentTimeMillis());
        if (ngayHetHan.before(currentDate)) {
            throw new IllegalArgumentException("Ngày hết hạn không được trước ngày hiện tại.");
        }
        this.ngayHetHan = ngayHetHan;
    }


    @Override
    public String toString() {
        return "KhoViewTable{" +
                "maKho='" + maKho + '\'' +
                ", maNguyenLieu='" + maNguyenLieu + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", maNhaCungCap='" + maNhaCungCap + '\'' +
                ", tenNhaCungCap='" + tenNhaCungCap + '\'' +
                ", giaNhap=" + giaNhap +
                ", ngayNhap=" + ngayNhap +
                ", ngayHetHan=" + ngayHetHan +
                ", soLuong=" + soLuong +
                '}';
    }
}
