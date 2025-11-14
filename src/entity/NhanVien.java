package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private int tuoi;
    private String diaChi;
    private String soDienThoai;
    private ChucVu chucVu;
    private float luongNV;
    private LocalDate ngayVaoLam;
    private String gioiTinh;
    private CaLamViec caLamViec;
    private TaiKhoan taiKhoan;

    public NhanVien() {
        super();
    }

    public NhanVien(String maNhanVien, String tenNhanVien, int tuoi, String diaChi,
                    String soDienThoai, ChucVu chucVu, float luongNV,
                    LocalDate ngayVaoLam, String gioiTinh, CaLamViec caLamViec, TaiKhoan taiKhoan) {
        setMaNhanVien(maNhanVien);
        setTenNhanVien(tenNhanVien);
        setTuoi(tuoi);
        setDiaChi(diaChi);
        setSoDienThoai(soDienThoai);
        setChucVu(chucVu);
        setLuongNV(luongNV);
        setNgayVaoLam(ngayVaoLam);
        setGioiTinh(gioiTinh);
        setCaLamViec(caLamViec);
        setTaiKhoan(taiKhoan);
    }

    // Getter & Setter
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.trim().isEmpty())
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        if (tenNhanVien == null || tenNhanVien.trim().isEmpty())
            throw new IllegalArgumentException("Tên nhân viên không được để trống!");
        this.tenNhanVien = tenNhanVien;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        if (tuoi < 18 || tuoi > 60)
            throw new IllegalArgumentException("Tuổi nhân viên phải từ 18 đến 60!");
        this.tuoi = tuoi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        if (diaChi == null || diaChi.trim().isEmpty())
            throw new IllegalArgumentException("Địa chỉ không được để trống!");
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (!soDienThoai.matches("^0\\d{9,10}$"))
            throw new IllegalArgumentException("Số điện thoại không hợp lệ!");
        this.soDienThoai = soDienThoai;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        if (chucVu == null)
            throw new IllegalArgumentException("Chức vụ không được null!");
        this.chucVu = chucVu;
    }

    public float getLuongNV() {
        return luongNV;
    }

    public void setLuongNV(float luongNV) {
        if (luongNV < 0)
            throw new IllegalArgumentException("Lương không được âm!");
        this.luongNV = luongNV;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        if (ngayVaoLam == null)
            throw new IllegalArgumentException("Ngày vào làm không được để trống!");
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        if (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nữ"))
            throw new IllegalArgumentException("Giới tính phải là Nam hoặc Nữ!");
        this.gioiTinh = gioiTinh;
    }

    public CaLamViec getCaLamViec() {
        return caLamViec;
    }

    public void setCaLamViec(CaLamViec caLamViec) {
        if (caLamViec == null)
            throw new IllegalArgumentException("Ca làm việc không được null!");
        this.caLamViec = caLamViec;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        if (taiKhoan == null)
            throw new IllegalArgumentException("Tài khoản không được null!");
        this.taiKhoan = taiKhoan;
    }

    // toString
    @Override
    public String toString() {
        return "NhanVien [" +
                "maNhanVien=" + maNhanVien +
                ", tenNhanVien=" + tenNhanVien +
                ", tuoi=" + tuoi +
                ", diaChi=" + diaChi +
                ", soDienThoai=" + soDienThoai +
                ", chucVu=" + (chucVu != null ? chucVu.getTenCV() : "null") +
                ", luongNV=" + luongNV +
                ", ngayVaoLam=" + ngayVaoLam +
                ", gioiTinh=" + gioiTinh +
                ", caLamViec=" + (caLamViec != null ? caLamViec.getTenCa() : "null") +
                ", taiKhoan=" + (taiKhoan != null ? taiKhoan.getUsername() : "null") +
                "]";
    }

    // equals & hashCode dựa trên maNhanVien
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NhanVien)) return false;
        NhanVien nv = (NhanVien) o;
        return Objects.equals(maNhanVien, nv.maNhanVien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien);
    }
}
