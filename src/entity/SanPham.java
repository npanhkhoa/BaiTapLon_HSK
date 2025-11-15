package entity;

public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private double giaBan;
    private int soLuong;
    private LoaiSanPham loaiSanPham;
    private String hinhAnh;
    private NguyenLieu nguyenLieu;

    public SanPham(String maSanPham, String tenSanPham, double giaBan, int soLuong, LoaiSanPham loaiSanPham, String hinhAnh) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.loaiSanPham = loaiSanPham;
        this.hinhAnh = hinhAnh;
    }

    public SanPham(String maSanPham, String tenSanPham, double giaBan, int soLuong, LoaiSanPham loaiSanPham, String hinhAnh, NguyenLieu nguyenLieu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.loaiSanPham = loaiSanPham;
        this.hinhAnh = hinhAnh;
        this.nguyenLieu = nguyenLieu;
    }

    public SanPham() {

    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        if (maSanPham == null || maSanPham.isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được để trống");
        }
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        if (tenSanPham == null || tenSanPham.isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống");
        }
        this.tenSanPham = tenSanPham;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        if (giaBan < 0) {
            throw new IllegalArgumentException("Giá bán không hợp lệ. Giá bán phải lớn hơn hoặc bằng 0");
        }
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ. Số lượng phải lớn hơn hoặc bằng 0");
        }
        this.soLuong = soLuong;
    }

    public LoaiSanPham getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
        if (loaiSanPham == null) {
            throw new IllegalArgumentException("Loại sản phẩm không được để trống");
        }
        this.loaiSanPham = loaiSanPham;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }


    public void addQuantity(int quantity) {
        this.soLuong += quantity;
    }
    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;

    }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", giaBan=" + giaBan +
                ", soLuong=" + soLuong +
                ", loaiSanPham=" + loaiSanPham +
                ", hinhAnh='" + hinhAnh + '\'' +
                '}';
    }
}
