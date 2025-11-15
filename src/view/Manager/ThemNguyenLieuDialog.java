package view.Manager;

import com.toedter.calendar.JDateChooser;
import controller.KhoController;
import entity.NguyenLieu;
import entity.KhoNguyenLieu;
import entity.NhaCungCap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThemNguyenLieuDialog extends JDialog implements ActionListener {
    // Existing fields
    private JTextField txtMaNguyenLieu;
    private JTextField txtTenNguyenLieu;
    private JTextField txtDonViTinh;
    private JTextField txtGiaNhap;
    private JTextField txtMaKho;
    private JTextField txtTenKho;
    private JDateChooser dateNgayNhap;
    private JDateChooser dateNgayHetHan;
    private JTextField txtDiaChiKho;
    private JTextField txtSoLuong;
    private JTextField txtMaNCC;
    private JTextField txtTenNCC;
    private JTextField txtDCNCC;
    private JTextField txtSDTNCC;

    private JButton btnOK;
    private JButton btnCancel;
    private PhieuNhapFrame phieuNhapFrame;
    private KhoController khoController = new KhoController();

    public ThemNguyenLieuDialog(JFrame parent, String title, PhieuNhapFrame phieuNhapFrame) {
        super(parent, title, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.phieuNhapFrame = phieuNhapFrame;

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        mainPanel.setBackground(new Color(10, 82, 116));

        // Initialize fields
        txtMaNguyenLieu = new JTextField(15);
        txtMaNguyenLieu.setEnabled(false);
        txtTenNguyenLieu = new JTextField(15);
        txtDonViTinh = new JTextField(15);
        txtGiaNhap = new JTextField(15);
        txtSoLuong = new JTextField(15);
        txtMaNCC = new JTextField(15);
        txtMaNCC.setEnabled(false);
        txtTenNCC = new JTextField(15);
        txtDCNCC = new JTextField(15);
        txtSDTNCC = new JTextField(15);
        txtMaKho = new JTextField(15);
        txtMaKho.setEnabled(false);
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(10, 82, 116));

        btnOK = new JButton("OK");
        btnCancel = new JButton("Hủy");

        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        add(mainPanel, BorderLayout.CENTER);
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

    private boolean isValidDate() {
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

        // Validate existing fields
        if (tenNguyenLieu.isEmpty() || !tenNguyenLieu.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Tên nguyên liệu không hợp lệ.");
        }
        if (donViTinh.isEmpty() || !donViTinh.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Đơn vị tính không hợp lệ.");
        }
        if (!giaNhapStr.matches("^\\d+(\\.\\d{1,2})?$")) {
            throw new IllegalArgumentException("Giá nhập không hợp lệ.");
        }
        if (!soLuongStr.matches("^\\d+$")) {
            throw new IllegalArgumentException("Số lượng không hợp lệ.");
        }
        if (ngayNhap == null || ngayHetHan == null) {
            throw new IllegalArgumentException("Ngày nhập và ngày hết hạn không được để trống.");
        }
        if (ngayHetHan.before(ngayNhap)) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau hoặc bằng ngày nhập.");
        }

        if (tenKho.isEmpty() || !tenKho.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Tên kho không hợp lệ.");
        }
        if (diaChiKho.isEmpty() || !diaChiKho.matches("^[\\p{L}\\d\\s,./-]+$")) {
            throw new IllegalArgumentException("Địa chỉ kho không hợp lệ.");
        }

        if (tenNCC.isEmpty() || !tenNCC.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Tên NCC không hợp lệ.");
        }
        if (diaChiNCC.isEmpty() || !diaChiNCC.matches("^[\\p{L}\\d\\s,./-]+$")) {
            throw new IllegalArgumentException("Địa chỉ NCC không hợp lệ.");
        }
        if (!sdtNCC.matches("^\\d{10,12}$")) {
            throw new IllegalArgumentException("SĐT NCC không hợp lệ.");
        }

        return true;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOK) {
            try {
                themNguyenLieu();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }

    private void themNguyenLieu() {
        try {
            if (!isValidDate()) {
                JOptionPane.showMessageDialog(this, "Thông tin không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maNguyenLieu = khoController.generateMaNguyenLieu();
            String tenNguyenLieu = txtTenNguyenLieu.getText().trim();
            String donViTinh = txtDonViTinh.getText().trim();
            double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            String maKho = khoController.generateMaKho();
            String tenKho = txtTenKho.getText().trim();
            String diaChiKho = txtDiaChiKho.getText().trim();
            Date ngayNhapDate = dateNgayNhap.getDate();
            LocalDate ngayNhap = ngayNhapDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date ngayHetHanDate = dateNgayHetHan.getDate();
            LocalDate ngayHetHan = ngayHetHanDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String maNCC = khoController.generateMaNCC();
            String tenNCC = txtTenNCC.getText().trim();
            String diaChiNCC = txtDCNCC.getText().trim();
            String sdtNCC = txtSDTNCC.getText().trim();


            NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChiNCC, sdtNCC);
            KhoNguyenLieu kho = new KhoNguyenLieu(maKho, tenKho, diaChiKho);
            NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan, kho, soLuong, ncc);
            phieuNhapFrame.themNguyenLieu(nguyenLieu);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}