package controller;/*
 * @ (#) KhoController.java   1.0     30/04/2025
package controller;


/**
 * @description :
 * @author : Vy, Pham Kha Vy
 * @version 1.0
 * @created : 30/04/2025
 */

import dao.Kho_DAO;
import entity.KhoViewTable;
import view.Manager.KhoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class KhoController implements ActionListener {
    private KhoPanel view;
    private Kho_DAO kho_dao = new Kho_DAO();
    private List<KhoViewTable> khoViewTables = new ArrayList<>();

    public KhoController(KhoPanel view) {
        this.view = view;
    }

    public KhoController() {
        this.khoViewTables = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "Thêm":
                view.dialogThemKho();
                break;
            case "Xóa":
                view.xoaKho();
                break;
            case "Sửa":
                // Kiểm tra xem có dòng nào được chọn trong bảng không
                int selectedRow = view.getTable().getSelectedRow();  // giả sử bạn có phương thức getTable() trả về JTable
                if (selectedRow != -1) {
                    KhoViewTable selectedKho = view.getSelectedKho();
                    if (selectedKho != null) {
                        view.dialogSuaKho(selectedKho);
                    }
                } else {
                    // Thông báo nếu chưa chọn dòng
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn kho để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "Xuất PDF":
                view.xuatPDF();
                break;
        }
    }

    public String generateMaKho() {
        String maKho;
        do {
            maKho = "K" + (int) (Math.random() * 1000);
        } while (kho_dao.isMaKhoExists(maKho));
        return maKho;
    }

    public String generateMaNguyenLieu() {
        String maNguyenLieu;
        do {
            maNguyenLieu = "NL" + (int) (Math.random() * 1000);
        } while (kho_dao.isMaNguyenLieuExists(maNguyenLieu));
        return maNguyenLieu;
    }

    public String generateMaNCC() {
        String maNCC;
        do {
            maNCC = "NCC" + (int) (Math.random() * 1000);
        } while (kho_dao.isMaNhaCungCapExists(maNCC));
        return maNCC;
    }

}
