package view.Manager;

import com.toedter.calendar.JDateChooser;
import controller.KhoController;
import entity.KhoViewTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class ThemKhoDialog extends JDialog {
    private JTextField maKhoField, tenKhoField, diaChiKhoField;
    private JTextField maNLField, tenNLField, donViTinhField, soLuongField, giaNhapField;
    private JTextField maNCCField, tenNCCField, diaChiNCCField, sdtNCCField;
    private JButton btnXacNhan, btnHuy;
    private KhoPanel khoPanel;
    private JDateChooser ngayNhapField, ngayHetHanField;
    private KhoController khoController = new KhoController();

    public ThemKhoDialog(Frame parent, String title, KhoPanel khoPanel) {
        super(parent, title, true);
        this.khoPanel = khoPanel;

        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridLayout(7, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(176, 226, 255));

        inputPanel.add(new JLabel("Mã kho:"));
        maKhoField = new JTextField();
        maKhoField.setEnabled(false);
        inputPanel.add(maKhoField);

        inputPanel.add(new JLabel("Tên kho:"));
        tenKhoField = new JTextField();
        inputPanel.add(tenKhoField);

        inputPanel.add(new JLabel("Địa chỉ kho:"));
        diaChiKhoField = new JTextField();
        inputPanel.add(diaChiKhoField);

        inputPanel.add(new JLabel("Mã nguyên liệu:"));
        maNLField = new JTextField();
        maNLField.setEnabled(false);
        inputPanel.add(maNLField);

        inputPanel.add(new JLabel("Tên nguyên liệu:"));
        tenNLField = new JTextField();
        inputPanel.add(tenNLField);

        inputPanel.add(new JLabel("Đơn vị:"));
        donViTinhField = new JTextField();
        inputPanel.add(donViTinhField);

        inputPanel.add(new JLabel("Số lượng:"));
        soLuongField = new JTextField();
        inputPanel.add(soLuongField);

        inputPanel.add(new JLabel("Giá nhập:"));
        giaNhapField = new JTextField();
        inputPanel.add(giaNhapField);

        inputPanel.add(new JLabel("Ngày nhập (yyyy-mm-dd):"));
        ngayNhapField = new JDateChooser();
        ngayNhapField.setDateFormatString("yyyy-MM-dd");
        inputPanel.add(ngayNhapField);

        inputPanel.add(new JLabel("Ngày hết hạn (yyyy-mm-dd):"));
        ngayHetHanField = new JDateChooser();
        ngayHetHanField.setDateFormatString("yyyy-MM-dd");
        inputPanel.add(ngayHetHanField);

        inputPanel.add(new JLabel("Mã nhà cung cấp:"));
        maNCCField = new JTextField();
        maNCCField.setEnabled(false);
        inputPanel.add(maNCCField);

        inputPanel.add(new JLabel("Tên nhà cung cấp:"));
        tenNCCField = new JTextField();
        inputPanel.add(tenNCCField);

        inputPanel.add(new JLabel("Địa chỉ NCC:"));
        diaChiNCCField = new JTextField();
        inputPanel.add(diaChiNCCField);

        inputPanel.add(new JLabel("SĐT NCC:"));
        sdtNCCField = new JTextField();
        inputPanel.add(sdtNCCField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(176, 226, 255));

        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBackground(new Color(144, 238, 144));
        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(144, 238,144));
        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);

        add(buttonPanel, BorderLayout.SOUTH);

        btnXacNhan.addActionListener(this::xacNhanThem);
        btnHuy.addActionListener(e -> dispose());
    }

    private void xacNhanThem(ActionEvent e) {
        try {
            KhoViewTable kho = new KhoViewTable();
            kho.setMaKho(khoController.generateMaKho());
            kho.setTenKho(tenKhoField.getText().trim());
            kho.setDiaChiKho(diaChiKhoField.getText().trim());

            kho.setMaNguyenLieu(khoController.generateMaNguyenLieu());
            kho.setTenNguyenLieu(tenNLField.getText().trim());
            kho.setDonViTinh(donViTinhField.getText().trim());
            kho.setSoLuong(Integer.parseInt(soLuongField.getText().trim()));
            kho.setGiaNhap(Double.parseDouble(giaNhapField.getText().trim()));
            kho.setNgayNhap(new Date(ngayNhapField.getDate().getTime()));
            kho.setNgayHetHan(new Date(ngayHetHanField.getDate().getTime()));

            kho.setMaNhaCungCap(khoController.generateMaNCC());
            kho.setTenNhaCungCap(tenNCCField.getText().trim());
            kho.setDiaChiNhaCungCap(diaChiNCCField.getText().trim());
            kho.setSoDienThoaiNhaCungCap(sdtNCCField.getText().trim());

            if (!khoPanel.validateInputKho(
                    kho.getMaKho(), kho.getTenKho(), kho.getDiaChiKho(),
                    kho.getMaNguyenLieu(), kho.getTenNguyenLieu(), kho.getDonViTinh(),
                    kho.getGiaNhap(), kho.getSoLuong(), kho.getNgayNhap(), kho.getNgayHetHan(),
                    kho.getMaNhaCungCap(), kho.getTenNhaCungCap(), kho.getDiaChiNhaCungCap(),
                    kho.getSoDienThoaiNhaCungCap())) {
                return;
            }

            khoPanel.themKho(kho);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu đầu vào: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
