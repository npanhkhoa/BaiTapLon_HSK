package controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;

import dao.SanPham_DAO;
import entity.LoaiSanPham;
import entity.SanPham;

import view.Manager.SanPhamPanel;

public class SanPhamController implements ActionListener {
    private SanPhamPanel view;
    private SanPham_DAO sanPhamDao = new SanPham_DAO();
    private List<SanPham> sharedProducts = new ArrayList<>();

    public SanPhamController(SanPhamPanel view) {
        this.view = view;
    }

    public List<SanPham> getDsachSanPham() {
        return sanPhamDao.getDsachSanPham();
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
        switch (cmd) {
            case "Xóa":
                view.xoaSanPham();
                break;
            case "Sửa":
                SanPham selectedSanPham = view.getSelectedSanPham();
                if (selectedSanPham != null) {
                   
                } else {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "Xem chi tiết":
                view.xemChiTiet();
                break;
            
        }
    }

	public LoaiSanPham getLoaiSanPham(String maSanPham) {
		// TODO Auto-generated method stub
		return null;
	}
}
