package controller;

import dao.SanPham_Dao;
import entity.LoaiSanPham;
import entity.SanPham;
import view.Manager.KhoPanel;
import view.Manager.SanPhamPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SanPhamController implements ActionListener {
    private SanPhamPanel view;
    private KhoPanel khoPanel;

    public SanPhamController(SanPhamPanel view) {
        this.view = view;
    }

    /**
     * Phương thức này sẽ lấy thông tin sản phẩm từ đối tượng SanPham
     * @param sanPham
     * @return
     */
    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private List<SanPham> sharedProducts = new ArrayList<>();

    public SanPhamController() {
        this.sharedProducts = new ArrayList<>();
    }

    public SanPham getSanPham(SanPham sanPham) {
        return sanPham;
    }

    /**
     * Phương thức này sẽ lấy danh sách sản phẩm từ cơ sở dữ liệu
     * @return dsachSanPham
     */
    public List<SanPham> getDsachSanPham() {
        return new SanPham_Dao().getDsachSanPham();
    }

    /**
     * Phương thức này lấy thông tin sản phẩm từ cơ sở dữ liệu
     * @param maSanPham
     * @return sanPham
     */
    public SanPham getThongTinSanPham(String maSanPham) {
        return sanPhamDao.getThongTinSanPham(maSanPham);
    }

    /**
     * Phương thức này sẽ lấy danh sách loại sản phẩm từ cơ sở dữ liệu
     * @return loaiSanPham
     */
    public LoaiSanPham getLoaiSanPham(String maSanPham) {
        return sanPhamDao.getLoaiSanPham(maSanPham);
    }

    public List<SanPham> getSharedProducts() {
        return sharedProducts;
    }

    public void setSharedProducts(List<SanPham> sanPham) {
        this.sharedProducts = sanPham;
    }

    public String generateMaSanPham() {
        String maSanPham;
        do {
            maSanPham = "SP" + (int) (Math.random() * 10000);
        } while (sanPhamDao.isMaSanPhamExists(maSanPham));
        return maSanPham;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd){
            case "Thêm":
                view.dialogThemSanPham();
                break;
            case "Xóa":
                view.xoaSanPham();
                break;
            case "Sửa":
                SanPham selectedSanPham = view.getSelectedSanPham();
                if (selectedSanPham != null) {
                    view.dialogSuaSanPham(selectedSanPham);
                } else {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "Xem chi tiết":
                view.xemChiTiet();
                break;
            case "Xuất PDF":
                view.xuatPDF();
                break;
        }
    }
}


