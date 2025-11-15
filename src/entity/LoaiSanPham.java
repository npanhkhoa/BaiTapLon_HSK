package entity;

public class LoaiSanPham {
    private String maLoaiSanPham;
    private String tenLoaiSanPham;

    public LoaiSanPham(String maLoaiSanPham, String tenLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(String maLoaiSanPham) {
        if (maLoaiSanPham == null || maLoaiSanPham.isEmpty()) {
            throw new IllegalArgumentException("Mã loại sản phẩm không được để trống");
        }
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        if (tenLoaiSanPham == null || tenLoaiSanPham.isEmpty()) {
            throw new IllegalArgumentException("Tên loại sản phẩm không được để trống");
        }
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    @Override
    public String toString() {
        return "LoaiSanPham{" +
                "maLoaiSanPham='" + maLoaiSanPham + '\'' +
                ", tenLoaiSanPham='" + tenLoaiSanPham + '\'' +
                '}';
    }
}
