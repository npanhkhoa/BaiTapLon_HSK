package entity;

public class Ban {
    private String maBan;
    private String tenBan;
    private int sucChua;
    private String viTri;
    private String trangThai;
    private String ghiChu;

    public Ban(String maBan, String tenBan, int sucChua, String viTri, String trangThai, String ghiChu) throws Exception {
        this.setMaBan(maBan);
        this.setTenBan(tenBan);
        this.setSucChua(sucChua);
        this.setViTri(viTri);
        this.setTrangThai(trangThai);
        this.setGhiChu(ghiChu);
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) throws Exception {
        if (maBan != null && maBan.matches("B\\d{2,3}")) {
            this.maBan = maBan;
        } else {
            throw new Exception("Mã bàn phải có dạng B## hoặc B### (ví dụ: B01, B102)");
        }
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) throws Exception {
        if (tenBan == null || tenBan.trim().isEmpty()) {
            throw new Exception("Tên bàn không được để trống");
        }
        this.tenBan = tenBan.trim();
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) throws Exception {
        if (sucChua <= 0) {
            throw new Exception("Sức chứa phải lớn hơn 0");
        }
        this.sucChua = sucChua;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = (viTri != null) ? viTri.trim() : "";
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) throws Exception {
        if (trangThai == null || trangThai.trim().isEmpty()) {
            this.trangThai = "Trống"; // theo mặc định trong SQL
            return;
        }

        if (!trangThai.equalsIgnoreCase("Trống") && !trangThai.equalsIgnoreCase("Đang sử dụng")) {
            throw new Exception("Trạng thái chỉ được là 'Trống' hoặc 'Đang sử dụng'");
        }

        this.trangThai = trangThai.trim();
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = (ghiChu != null) ? ghiChu.trim() : "";
    }

    @Override
    public String toString() {
        return maBan + " | " + tenBan + " | " + sucChua + " | " 
                + viTri + " | " + trangThai + " | " + ghiChu + " | ";
    }
}
