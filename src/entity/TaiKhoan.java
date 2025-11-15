package entity;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private boolean quyenHan = false;

    public TaiKhoan(String tenDangNhap, String matKhau, boolean quyenHan) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.quyenHan = quyenHan;

    }

    public TaiKhoan(String tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        if (tenDangNhap == null || tenDangNhap.isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống");
        }
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        if (matKhau == null || matKhau.isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        this.matKhau = matKhau;
    }

    public boolean isQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(boolean quyenHan) {
        this.quyenHan = quyenHan;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
