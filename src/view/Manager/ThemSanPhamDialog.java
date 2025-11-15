package view.Manager;/*
 * @ (#) ThemSanPhamDialog.java   1.0     27/04/2025
package view.Manager;


/**
 * @description :
 * @author : Vy, Pham Kha Vy
 * @version 1.0
 * @created : 27/04/2025
 */

import controller.SanPhamController;
import dao.SanPham_Dao;
import entity.LoaiSanPham;
import entity.SanPham;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ThemSanPhamDialog extends JDialog implements ActionListener {
    private JTextField maSPField, tenSPField, soLuongField, donGiaField;
    private JComboBox<String> hinhAnhField;
    private JComboBox<String> loaiSPComboBox;
    private JButton btnXacNhan, btnHuy;
    private boolean isConfirmed = false;
    private SanPhamPanel sanPhamPanel;
    private SanPhamController sanPhamController = new SanPhamController();

    public ThemSanPhamDialog(Frame parent, String tittle, SanPhamPanel sanPhamPanel){
        super(parent, tittle, true);
        this.sanPhamPanel = sanPhamPanel;

        setSize(500, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridLayout(3, 6, 20, 20));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(176, 226, 255));

        JLabel maSPLabel = new JLabel("Mã sản phẩm:");
        maSPLabel.setForeground(new Color(26, 102, 227));
        inputPanel.add(maSPLabel);
        maSPField = new JTextField();
        maSPField.setEnabled(false);
        inputPanel.add(maSPField);

        JLabel tenSPLabel = new JLabel("Tên sản phẩm:");
        tenSPLabel.setForeground(new Color(26, 102, 227));
        inputPanel.add(tenSPLabel);
        tenSPField = new JTextField();
        inputPanel.add(tenSPField);

        JLabel donGiaLabel = new JLabel("Đơn giá:");
        donGiaLabel.setForeground(new Color(26, 102, 227));
        inputPanel.add(donGiaLabel);
        donGiaField = new JTextField();
        inputPanel.add(donGiaField);

        JLabel soLuongLabel = new JLabel("Số lượng:");
        soLuongLabel.setForeground(new Color(26, 102, 227));
        inputPanel.add(soLuongLabel);
        soLuongField = new JTextField();
        inputPanel.add(soLuongField);

        JLabel loaiSPLabel = new JLabel("Loại sản phẩm:");
        loaiSPLabel.setForeground(new Color(26, 102, 227));
        inputPanel.add(loaiSPLabel);
        loaiSPComboBox = new JComboBox<>(new String[]{"Coffee", "Nước Ngọt", "Sinh tố", "Trà", "Bánh Ngọt", "Đồ Ăn Vặt", "Thuốc Lá"});
        inputPanel.add(loaiSPComboBox);

        JLabel hinhAnhLabel = new JLabel("Hình ảnh:");
        hinhAnhLabel.setForeground(new Color(26, 102, 227));
        inputPanel.add(hinhAnhLabel);
        hinhAnhField = new JComboBox<>();
        loadAnhTuSrc();
        inputPanel.add(hinhAnhField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(176, 226, 255));
        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBackground(new Color(144, 238, 144));
        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(144, 238,144));

        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);

        add(buttonPanel, BorderLayout.SOUTH);

        hinhAnhField.addActionListener(this);
        btnXacNhan.addActionListener(e -> themSanPham());
        btnHuy.addActionListener(e -> dispose());
    }

    private void themSanPham(){
        try{
            String maSp = sanPhamController.generateMaSanPham();
            String tenSP = tenSPField.getText().trim();
            String soLuong = soLuongField.getText().trim();
            String donGia = donGiaField.getText().trim();
            String loaiSPText = (String) loaiSPComboBox.getSelectedItem();
            String hinhAnh = (String) hinhAnhField.getSelectedItem();

            int soLuongInt = Integer.parseInt(soLuong);
            int donGiaInt = Integer.parseInt(donGia);

            LoaiSanPham maloaiSP = new LoaiSanPham(generateMaLoai(loaiSPText), loaiSPText);

            SanPham sp = new SanPham(maSp, tenSP, donGiaInt, soLuongInt, maloaiSP, hinhAnh);

            if(sanPhamPanel.validateInput(maSp,tenSP,donGia,soLuong,maloaiSP,hinhAnh)){
                sanPhamPanel.themSanPham(sp);
                isConfirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thông tin sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho đơn giá và số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAnhTuSrc(){
        String folderPath = "imgSanPham";

        File hinhAnh = new File(folderPath);
        if(hinhAnh.exists() && hinhAnh.isDirectory()){
            for(File file : hinhAnh.listFiles()){
                if(file.isFile()){
                    hinhAnhField.addItem(file.getName());
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Thư mục không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateMaLoai(String tenLoai) {
        switch (tenLoai) {
            case "Coffee":
                return "LSP01";
            case "Nước Ngọt":
                return "LSP02";
            case "Sinh tố":
                return "LSP03";
            case "Trà":
                return "LSP04";
            case "Bánh Ngọt":
                return "LSP06";
            case "Đồ Ăn Vặt":
                return "LSP07";
            case "Thuốc Lá":
                return "LSP05";
            default:
                return "LSP00";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }



}
