package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KhoNguyenLieu {
    private String maKho;
    private String tenKho;
    private String diaChi;
    private Map<NguyenLieu, Integer> danhSachNguyenLieu; // Nguyên liệu và số lượng

    public KhoNguyenLieu(String maKho, String tenKho, String diaChi) {
        if(maKho == null || maKho.isEmpty()) {
            throw new IllegalArgumentException("Mã kho không được để trống");
        }

        if(tenKho == null || tenKho.isEmpty()) {
            throw new IllegalArgumentException("Tên kho không được để trống");
        }

        if(diaChi == null || diaChi.isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaChi = diaChi;
        this.danhSachNguyenLieu = new HashMap<>();
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

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        if (tenKho == null || tenKho.isEmpty()) {
            throw new IllegalArgumentException("Tên kho không được để trống");
        }
        this.tenKho = tenKho;
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

    public Map<NguyenLieu, Integer> getDanhSachNguyenLieu() {
        return new HashMap<>(danhSachNguyenLieu);
    }

    public void themNguyenLieu(NguyenLieu nguyenLieu, int soLuong) {
        if (nguyenLieu == null) {
            throw new IllegalArgumentException("Nguyên liệu không được null");
        }
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        int tongSoLuong = danhSachNguyenLieu.getOrDefault(nguyenLieu, 0) + soLuong;
        if (tongSoLuong < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }
        danhSachNguyenLieu.put(nguyenLieu, tongSoLuong);
    }

    public void xoaNguyenLieu(NguyenLieu nguyenLieu) {
        if (nguyenLieu == null) {
            throw new IllegalArgumentException("Nguyên liệu không được null");
        }
        danhSachNguyenLieu.remove(nguyenLieu);
    }
    public void capNhatSoLuong(NguyenLieu nguyenLieu, int soLuongMoi) {
        if (nguyenLieu == null) {
            throw new IllegalArgumentException("Nguyên liệu không được null");
        }
        if (soLuongMoi < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }
        danhSachNguyenLieu.put(nguyenLieu, soLuongMoi);
    }
    public int getSoLuong(NguyenLieu nguyenLieu) {
        return danhSachNguyenLieu.getOrDefault(nguyenLieu, 0);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KhoNguyenLieu that = (KhoNguyenLieu) o;
        return maKho.equals(that.maKho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKho);
    }

    @Override
    public String toString() {
        return new StringBuilder("KhoNguyenLieu{")
                .append("maKho='").append(maKho).append('\'')
                .append(", tenKho='").append(tenKho).append('\'')
                .append(", diaChi='").append(diaChi).append('\'')
                .append(", danhSachNguyenLieu=").append(danhSachNguyenLieu)
                .append('}')
                .toString();
    }

}