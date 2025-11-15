
package view.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controller.SanPhamController;
import controller.UserController;
import entity.HoaDon;
import controller.HoaDonController;
import entity.NhanVien;
import entity.SanPham;


public class HoaDonPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton reFreshBtn;

    private HoaDonController hoaDonController;
    private UserController userController;
    private SanPhamController sanPhamController;

    /**
     * Constructor của panel hóa đơn
     * @param userController
     * @param sanPhamController
     */
    public HoaDonPanel(UserController userController, SanPhamController sanPhamController) {
        if (userController == null) {
            throw new IllegalArgumentException("UserController cannot be null");
        }
        this.userController = userController;
        this.sanPhamController = sanPhamController;

        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(10, 82, 116));
        add(titleLabel, BorderLayout.NORTH);
        add(createTableScrollPane());
        setData();
        add(createPanel(), BorderLayout.SOUTH);
    }

    /**
     * Phương thức này tạo một JScrollPane chứa bảng hóa đơn.
     * @return JScrollPane chứa bảng hóa đơn.
     */
    public JScrollPane createTableScrollPane() {
        String[] columnNames = {"Mã hóa đơn", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Thời gian", "Nhân Viên"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(10, 82, 116));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(null);
        return scrollPane;
    }

    public JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        reFreshBtn = new JButton("Làm mới");
        reFreshBtn.setPreferredSize(new Dimension(100, 30));
        reFreshBtn.setBackground(new Color(10, 82, 116));
        reFreshBtn.setForeground(Color.WHITE);
        reFreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        reFreshBtn.addActionListener(e -> {
            setData();
        });
        panel.add(reFreshBtn);
        return panel;
    }

    /**
     * Phương thức này lấy dữ liệu hóa đơn từ cơ sở dữ liệu và hiển thị lên bảng.
     */
    public void setData() {
        // Lấy thông tin nhân viên
        NhanVien nhanVien = userController.getNhanVienByTenDangNhap(userController.getCurrentUsername());
        if (nhanVien == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        hoaDonController = new HoaDonController(); //Khởi tạo để lấy dữ liệu từ hoaDonController
        List<HoaDon> list = hoaDonController.getAllHoaDon(nhanVien.getTaiKhoan().getTenDangNhap());

        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (HoaDon hd : list) {
            // Gộp tên sản phẩm thành chuỗi
            StringBuilder tenSanPhamBuilder = new StringBuilder();
            for (SanPham sp : hd.getDsachSanPham()) {
                tenSanPhamBuilder.append(sp.getTenSanPham()).append(", ");
            }

            // Xóa dấu ", " cuối nếu có
            String tenSanPham = tenSanPhamBuilder.length() > 0
                    ? tenSanPhamBuilder.substring(0, tenSanPhamBuilder.length() - 2)
                    : "";
            String soLuong = hd.getDsachSanPham().stream()
                    .map(sp -> String.valueOf(sp.getSoLuong()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("");


           double thanhTien = hd.getDsachSanPham().stream()
                   .mapToDouble(sp -> sp.getGiaBan() * sp.getSoLuong())
                   .sum();

           //Hiển thị đơn giá từng sản phẩm cách nhau bởi dấu ohaayr
            String donGia = hd.getDsachSanPham().stream()
                    .map(sp -> String.valueOf(sp.getGiaBan()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("");
            // Thêm dữ liệu vào bảng
            Object[] rowData = {
                    hd.getMaHoaDon(),
                    tenSanPham,
                    soLuong,
                    donGia,
                    thanhTien,
                    hd.getNgayLap(),
                    nhanVien.getTenNhanVien()
            };
            tableModel.addRow(rowData);
        }
    }




}
