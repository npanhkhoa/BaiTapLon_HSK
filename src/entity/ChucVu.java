package entity;

public class ChucVu {
    private String maCV;
    private String tenCV;
    private double phuCap;

    public ChucVu() {
        super();
    }

    public ChucVu(String maCV, String tenCV, double phuCap) {
        super();
        setMaCV(maCV);
        setTenCV(tenCV);
        setPhuCap(phuCap);
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        if (maCV == null || maCV.trim().isEmpty())
            throw new IllegalArgumentException("Mã chức vụ không được để trống!");
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        if (tenCV == null || tenCV.trim().isEmpty())
            throw new IllegalArgumentException("Tên chức vụ không được để trống!");
        this.tenCV = tenCV;
    }

    public double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(double phuCap) {
        if (phuCap < 0)
            throw new IllegalArgumentException("Phụ cấp không được âm!");
        this.phuCap = phuCap;
    }

    @Override
    public String toString() {
        return "ChucVu [maCV=" + maCV + ", tenCV=" + tenCV + ", phuCap=" + phuCap + "]";
    }
}
