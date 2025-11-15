package view.Manager;

import com.toedter.calendar.JDateChooser;
import dao.Kho_DAO;
import entity.KhoViewTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class SuaKhoDialog extends JDialog implements ActionListener {
    private JTextField maKhoField, tenKhoField, diaChiKhoField;
    private JTextField maNLField, tenNLField, donViTinhField, soLuongField, giaNhapField;
    private JTextField maNCCField, tenNCCField, diaChiNCCField, sdtNCCField;
    private JButton btnCapNhat, btnHuy;
    private KhoPanel khoPanel;
    private KhoViewTable kho;
    private JDateChooser ngayNhapField, ngayHetHanField;
    private Kho_DAO kho_dao;

    public SuaKhoDialog(Frame parent, String title, KhoPanel khoPanel, String maKho, String maNguyenLieu) {
        super(parent, title, true);
        this.khoPanel = khoPanel;

        // Khởi tạo đối tượng DAO để lấy danh sách kho
        kho_dao = new Kho_DAO();
        kho = kho_dao.getKhoByMaKhoViewTable(maKho,maNguyenLieu);

        if (kho == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kho với mã " + maKho, "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(7, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(176, 226, 255));

        // Khởi tạo các trường nhập liệu và gán giá trị từ đối tượng kho
        maKhoField = new JTextField(kho.getMaKho());
        tenKhoField = new JTextField(kho.getTenKho());
        diaChiKhoField = new JTextField(kho.getDiaChiKho());
        maNLField = new JTextField(kho.getMaNguyenLieu());
        tenNLField = new JTextField(kho.getTenNguyenLieu());
        donViTinhField = new JTextField(kho.getDonViTinh());
        soLuongField = new JTextField(String.valueOf(kho.getSoLuong()));
        giaNhapField = new JTextField(String.valueOf(kho.getGiaNhap()));

        ngayNhapField = new JDateChooser();
        ngayNhapField.setDateFormatString("yyyy-MM-dd");
        ngayNhapField.setDate(kho.getNgayNhap());

        ngayHetHanField = new JDateChooser();
        ngayHetHanField.setDateFormatString("yyyy-MM-dd");
        ngayHetHanField.setDate(kho.getNgayHetHan());

        maNCCField = new JTextField(kho.getMaNhaCungCap());
        tenNCCField = new JTextField(kho.getTenNhaCungCap());
        diaChiNCCField = new JTextField(kho.getDiaChiNhaCungCap());
        sdtNCCField = new JTextField(kho.getSoDienThoaiNhaCungCap());

        // Thêm các trường vào panel nhập liệu
        inputPanel.add(new JLabel("Mã kho:"));
        inputPanel.add(maKhoField);
        inputPanel.add(new JLabel("Tên kho:"));
        inputPanel.add(tenKhoField);
        inputPanel.add(new JLabel("Địa chỉ kho:"));
        inputPanel.add(diaChiKhoField);
        inputPanel.add(new JLabel("Mã nguyên liệu:"));
        inputPanel.add(maNLField);
        inputPanel.add(new JLabel("Tên nguyên liệu:"));
        inputPanel.add(tenNLField);
        inputPanel.add(new JLabel("Đơn vị tính:"));
        inputPanel.add(donViTinhField);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(soLuongField);
        inputPanel.add(new JLabel("Giá nhập:"));
        inputPanel.add(giaNhapField);
        inputPanel.add(new JLabel("Ngày nhập (yyyy-mm-dd):"));
        inputPanel.add(ngayNhapField);
        inputPanel.add(new JLabel("Ngày hết hạn (yyyy-mm-dd):"));
        inputPanel.add(ngayHetHanField);
        inputPanel.add(new JLabel("Mã nhà cung cấp:"));
        inputPanel.add(maNCCField);
        inputPanel.add(new JLabel("Tên nhà cung cấp:"));
        inputPanel.add(tenNCCField);
        inputPanel.add(new JLabel("Địa chỉ NCC:"));
        inputPanel.add(diaChiNCCField);
        inputPanel.add(new JLabel("SĐT NCC:"));
        inputPanel.add(sdtNCCField);

        add(inputPanel, BorderLayout.CENTER);

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(176, 226, 255));

        btnCapNhat = new JButton("Xác nhận");
        btnCapNhat.setBackground(new Color(144, 238, 144));
        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(144, 238, 144));
        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnHuy);

        add(buttonPanel, BorderLayout.SOUTH);

        btnCapNhat.addActionListener(this::capNhatKho);
        btnHuy.addActionListener(e -> dispose());

        // Không cho sửa mã kho
        maKhoField.setEditable(false);
    }

    private void capNhatKho(ActionEvent e) {
        try {
            kho.setMaKho(maKhoField.getText().trim());
            kho.setTenKho(tenKhoField.getText().trim());
            kho.setDiaChiKho(diaChiKhoField.getText().trim());

            kho.setMaNguyenLieu(maNLField.getText().trim());
            kho.setTenNguyenLieu(tenNLField.getText().trim());
            kho.setDonViTinh(donViTinhField.getText().trim());
            kho.setSoLuong(Integer.parseInt(soLuongField.getText().trim()));
            kho.setGiaNhap(Double.parseDouble(giaNhapField.getText().trim()));
            kho.setNgayNhap(new Date(ngayNhapField.getDate().getTime()));
            kho.setNgayHetHan(new Date(ngayHetHanField.getDate().getTime()));

            kho.setMaNhaCungCap(maNCCField.getText().trim());
            kho.setTenNhaCungCap(tenNCCField.getText().trim());
            kho.setDiaChiNhaCungCap(diaChiNCCField.getText().trim());
            kho.setSoDienThoaiNhaCungCap(sdtNCCField.getText().trim());

            // Kiểm tra dữ liệu hợp lệ
            if (!khoPanel.validateInputKho(
                    kho.getMaKho(), kho.getTenKho(), kho.getDiaChiKho(),
                    kho.getMaNguyenLieu(), kho.getTenNguyenLieu(), kho.getDonViTinh(),
                    kho.getGiaNhap(), kho.getSoLuong(), kho.getNgayNhap(), kho.getNgayHetHan(),
                    kho.getMaNhaCungCap(), kho.getTenNhaCungCap(), kho.getDiaChiNhaCungCap(),
                    kho.getSoDienThoaiNhaCungCap())) {
                return;
            }

            // Cập nhật kho trong danh sách
            for (KhoViewTable khoItem : kho_dao.getAllKhoNguyenLieu()) {
                if (khoItem.getMaKho().equals(kho.getMaKho())) {
                    khoItem.setTenKho(kho.getTenKho());
                    khoItem.setDiaChiKho(kho.getDiaChiKho());
                    khoItem.setMaNguyenLieu(kho.getMaNguyenLieu());
                    khoItem.setTenNguyenLieu(kho.getTenNguyenLieu());
                    khoItem.setDonViTinh(kho.getDonViTinh());
                    khoItem.setSoLuong(kho.getSoLuong());
                    khoItem.setGiaNhap(kho.getGiaNhap());
                    khoItem.setNgayNhap(kho.getNgayNhap());
                    khoItem.setNgayHetHan(kho.getNgayHetHan());
                    khoItem.setMaNhaCungCap(kho.getMaNhaCungCap());
                    khoItem.setTenNhaCungCap(kho.getTenNhaCungCap());
                    khoItem.setDiaChiNhaCungCap(kho.getDiaChiNhaCungCap());
                    khoItem.setSoDienThoaiNhaCungCap(kho.getSoDienThoaiNhaCungCap());
                    break;
                }
            }

            khoPanel.suaKho(kho);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

