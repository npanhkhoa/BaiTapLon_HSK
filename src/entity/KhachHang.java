package entity;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;

    public KhachHang(String maKhachHang, String tenKhachHang, String diaChi, String soDienThoai) throws Exception {
        setMaKhachHang(maKhachHang);
        setTenKhachHang(tenKhachHang);
        setDiaChi(diaChi);
        setSoDienThoai(soDienThoai);
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) throws Exception {
        if (maKhachHang != null && maKhachHang.matches("^KH\\d{2,}$")) {
            this.maKhachHang = maKhachHang;
        } else {
            throw new Exception("Mã khách hàng không hợp lệ (ví dụ: KH01, KH02...)");
        }
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) throws Exception {
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            throw new Exception("Tên khách hàng không được để trống");
        }
        if (!tenKhachHang.matches("^[\\p{Lu}][\\p{L}]+([\\s][\\p{Lu}][\\p{L}]+)*$")) {
            throw new Exception("Tên khách hàng phải viết hoa chữ cái đầu mỗi từ (ví dụ: Nguyễn Văn A)");
        }
        this.tenKhachHang = tenKhachHang.trim();
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) throws Exception {
        if (diaChi == null || diaChi.trim().isEmpty()) {
            throw new Exception("Địa chỉ không được để trống");
        }
        this.diaChi = diaChi.trim();
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) throws Exception {
        if (soDienThoai == null || !soDienThoai.matches("^0\\d{9}$")) {
            throw new Exception("Số điện thoại phải có 10 chữ số và bắt đầu bằng 0");
        }
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return maKhachHang + " | " + tenKhachHang + " | " + diaChi + " | " + soDienThoai;
    }
}
