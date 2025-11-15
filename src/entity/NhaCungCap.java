package entity;

import java.util.ArrayList;
import java.util.List;

public class NhaCungCap {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private String soDienThoai;
    private List<NguyenLieu> danhSachNguyenLieu; // Nguyên liệu mà nhà cung cấp cung cấp

    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String diaChi, String soDienThoai) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.danhSachNguyenLieu = new ArrayList<>();
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        if (diaChi == null || diaChi.isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        this.soDienThoai = soDienThoai;
    }

    public void themNguyenLieu(NguyenLieu nguyenLieu) {
        if (nguyenLieu == null) {
            throw new IllegalArgumentException("Nguyên liệu không được để trống");
        }
        danhSachNguyenLieu.add(nguyenLieu);
    }

    @Override
    public String toString() {
        return "NhaCungCap{" +
                "maNhaCungCap='" + maNhaCungCap + '\'' +
                ", tenNhaCungCap='" + tenNhaCungCap + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", danhSachNguyenLieu=" + danhSachNguyenLieu +
                '}';
    }

}
