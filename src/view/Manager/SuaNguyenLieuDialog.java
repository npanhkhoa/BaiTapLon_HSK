package view.Manager;

import com.toedter.calendar.JDateChooser;
import entity.KhoNguyenLieu;
import entity.NguyenLieu;
import entity.NhaCungCap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

public class SuaNguyenLieuDialog extends JDialog implements ActionListener {
    private JTextField txtMaNguyenLieu, txtTenNguyenLieu, txtDonViTinh, txtGiaNhap, txtMaKho, txtTenKho, txtDiaChiKho, txtSoLuong;
    private JTextField txtMaNCC, txtTenNCC, txtDCNCC, txtSDTNCC;
    private JDateChooser dateNgayNhap, dateNgayHetHan;
    private JButton btnOK, btnCancel;
    private final PhieuNhapFrame phieuNhapFrame;

    public SuaNguyenLieuDialog(JFrame parent, String title, PhieuNhapFrame phieuNhapFrame, String maNguyenLieu, String maKho) {
        super(parent, title, true);
        this.phieuNhapFrame = phieuNhapFrame;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        mainPanel.setBackground(new Color(10, 82, 116));

        // Initialize fields
        txtMaNguyenLieu = new JTextField(15);
        txtTenNguyenLieu = new JTextField(15);
        txtDonViTinh = new JTextField(15);
        txtGiaNhap = new JTextField(15);
        txtSoLuong = new JTextField(15);
        txtMaNCC = new JTextField(15);
        txtTenNCC = new JTextField(15);
        txtDCNCC = new JTextField(15);
        txtSDTNCC = new JTextField(15);
        txtMaKho = new JTextField(15);
        txtTenKho = new JTextField(15);
        txtDiaChiKho = new JTextField(15);
        dateNgayNhap = new JDateChooser();
        dateNgayNhap.setDateFormatString("dd/MM/yyyy");
        dateNgayHetHan = new JDateChooser();
        dateNgayHetHan.setDateFormatString("dd/MM/yyyy");

        // Add components to the main panel
        addLabelAndField(mainPanel, "Mã nguyên liệu:", txtMaNguyenLieu);
        addLabelAndField(mainPanel, "Tên nguyên liệu:", txtTenNguyenLieu);
        addLabelAndField(mainPanel, "Đơn vị tính:", txtDonViTinh);
        addLabelAndField(mainPanel, "Giá nhập:", txtGiaNhap);
        addLabelAndField(mainPanel, "Số lượng:", txtSoLuong);
        addLabelAndField(mainPanel, "Mã NCC:", txtMaNCC);
        addLabelAndField(mainPanel, "Tên NCC:", txtTenNCC);
        addLabelAndField(mainPanel, "Địa chỉ NCC:", txtDCNCC);
        addLabelAndField(mainPanel, "SĐT NCC:", txtSDTNCC);
        addLabelAndField(mainPanel, "Mã kho:", txtMaKho);
        addLabelAndField(mainPanel, "Tên kho:", txtTenKho);
        addLabelAndField(mainPanel, "Ngày nhập:", dateNgayNhap);
        addLabelAndField(mainPanel, "Ngày hết hạn:", dateNgayHetHan);
        addLabelAndField(mainPanel, "Địa chỉ kho:", txtDiaChiKho);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(10, 82, 116));

        btnOK = new JButton("OK");
        btnCancel = new JButton("Hủy");

        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void addLabelAndField(JPanel panel, String labelText, Component field) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.white);
        panel.add(label);
        panel.add(field);
    }

    private boolean validateInput() {
        String maNguyenLieu = txtMaNguyenLieu.getText().trim();
        String tenNguyenLieu = txtTenNguyenLieu.getText().trim();
        String donViTinh = txtDonViTinh.getText().trim();
        String giaNhapStr = txtGiaNhap.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();
        String maKho = txtMaKho.getText().trim();
        String tenKho = txtTenKho.getText().trim();
        String diaChiKho = txtDiaChiKho.getText().trim();
        Date ngayNhap = dateNgayNhap.getDate();
        Date ngayHetHan = dateNgayHetHan.getDate();
        String maNCC = txtMaNCC.getText().trim();
        String tenNCC = txtTenNCC.getText().trim();
        String diaChiNCC = txtDCNCC.getText().trim();
        String sdtNCC = txtSDTNCC.getText().trim();

        if (!maNguyenLieu.matches("^[a-zA-Z0-9]+$")) {
            showError("Mã nguyên liệu không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và số, không dấu.");
            return false;
        }
        if (tenNguyenLieu.isEmpty() || !tenNguyenLieu.matches("^[\\p{L}\\s]+$")) {
            showError("Tên nguyên liệu không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và khoảng trắng.");
            return false;
        }
        if (donViTinh.isEmpty() || !donViTinh.matches("^[\\p{L}\\s]+$")) {
            showError("Đơn vị tính không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và khoảng trắng.");
            return false;
        }
        if (!giaNhapStr.matches("^\\d+(\\.\\d{1,2})?$")) {
            showError("Giá nhập không hợp lệ.\nĐịnh dạng: số dương, có thể có 1-2 chữ số thập phân.");
            return false;
        }
        if (!soLuongStr.matches("^\\d+$")) {
            showError("Số lượng không hợp lệ.\nĐịnh dạng: số nguyên dương.");
            return false;
        }
        if (ngayNhap == null || ngayHetHan == null) {
            showError("Ngày nhập và ngày hết hạn không được để trống.");
            return false;
        }
        if (ngayHetHan.before(ngayNhap)) {
            showError("Ngày hết hạn phải sau hoặc bằng ngày nhập.");
            return false;
        }
        if (maKho.isEmpty() || !maKho.matches("^[a-zA-Z0-9]+$")) {
            showError("Mã kho không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và số.");
            return false;
        }
        if (tenKho.isEmpty() || !tenKho.matches("^[\\p{L}\\s]+$")) {
            showError("Tên kho không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và khoảng trắng.");
            return false;
        }
        if (diaChiKho.isEmpty() || !diaChiKho.matches("^[\\p{L}\\d\\s,./-]+$")) {
            showError("Địa chỉ kho không hợp lệ.\nĐịnh dạng: chữ, số và các ký tự , . / -.");
            return false;
        }
        if (maNCC.isEmpty() || !maNCC.matches("^[a-zA-Z0-9]+$")) {
            showError("Mã nhà cung cấp không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và số.");
            return false;
        }
        if (tenNCC.isEmpty() || !tenNCC.matches("^[\\p{L}\\s]+$")) {
            showError("Tên nhà cung cấp không hợp lệ.\nĐịnh dạng: chỉ gồm chữ và khoảng trắng.");
            return false;
        }
        if (diaChiNCC.isEmpty() || !diaChiNCC.matches("^[\\p{L}\\d\\s,./-]+$")) {
            showError("Địa chỉ nhà cung cấp không hợp lệ.\nĐịnh dạng: chữ, số và các ký tự , . / -.");
            return false;
        }
        if (!sdtNCC.matches("^\\d{10,12}$")) {
            showError("Số điện thoại nhà cung cấp không hợp lệ.\nĐịnh dạng: từ 10 đến 12 chữ số.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
    }

    private void suaNguyenLieu() {
        if (validateInput()) {
            String maNguyenLieu = txtMaNguyenLieu.getText().trim();
            String tenNguyenLieu = txtTenNguyenLieu.getText().trim();
            String donViTinh = txtDonViTinh.getText().trim();
            double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            LocalDate ngayNhap = new java.sql.Date(dateNgayNhap.getDate().getTime()).toLocalDate();
            LocalDate ngayHetHan = new java.sql.Date(dateNgayHetHan.getDate().getTime()).toLocalDate();
            String maKho = txtMaKho.getText().trim();
            String tenKho = txtTenKho.getText().trim();
            String diaChiKho = txtDiaChiKho.getText().trim();
            String maNCC = txtMaNCC.getText().trim();
            String tenNCC = txtTenNCC.getText().trim();
            String diaChiNCC = txtDCNCC.getText().trim();
            String sdtNCC = txtSDTNCC.getText().trim();

            NhaCungCap nhaCungCap = new NhaCungCap(maNCC, tenNCC, diaChiNCC, sdtNCC);
            KhoNguyenLieu khoNguyenLieu = new KhoNguyenLieu(maKho, tenKho, diaChiKho);
            NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan,khoNguyenLieu, soLuong, nhaCungCap);

            phieuNhapFrame.suaNguyenLieu(nguyenLieu);
            dispose();
        }
    }

    public void setData(NguyenLieu nl) {
        txtMaNguyenLieu.setText(nl.getMaNguyenLieu());
        txtTenNguyenLieu.setText(nl.getTenNguyenLieu());
        txtDonViTinh.setText(nl.getDonViTinh());
        txtGiaNhap.setText(String.valueOf(nl.getGiaNhap()));
        txtSoLuong.setText(String.valueOf(nl.getSoLuong()));
        dateNgayNhap.setDate(java.sql.Date.valueOf(nl.getNgayNhap()));
        dateNgayHetHan.setDate(java.sql.Date.valueOf(nl.getNgayHetHan()));

        KhoNguyenLieu kho = nl.getKhoNguyenLieu();
        txtMaKho.setText(kho.getMaKho());
        txtTenKho.setText(kho.getTenKho());
        txtDiaChiKho.setText(kho.getDiaChi());

        NhaCungCap ncc = nl.getNhaCungCap();
        txtMaNCC.setText(ncc.getMaNhaCungCap());
        txtTenNCC.setText(ncc.getTenNhaCungCap());
        txtDCNCC.setText(ncc.getDiaChi());
        txtSDTNCC.setText(ncc.getSoDienThoai());

        txtMaNguyenLieu.setEnabled(false);
        txtMaKho.setEnabled(false);
        txtMaNCC.setEnabled(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOK) {
            try {
                int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật nguyên liệu?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    suaNguyenLieu();
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }

}