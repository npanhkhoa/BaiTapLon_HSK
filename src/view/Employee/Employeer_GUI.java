package view.Employee;

import controller.HoaDonController;
import controller.SanPhamController;
import controller.UserController;
import view.login.fLogin;
import entity.CaLamViec;

import javax.swing.*;
import java.awt.*;

public class Employeer_GUI {
    private UserController userController;
    private HoaDonController hoaDonController;
    private SanPhamController sanPhamController;

    private boolean isShiftOpen = false;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private CaLamViec currentShift;

//    private ThanhToanPanel thanhToanPanel;

    /**
     * Constructor của JFrame chính
     * @param userController
     * @param sanPhamController
     * @param hoaDonController
     */
    public Employeer_GUI(UserController userController, SanPhamController sanPhamController, HoaDonController hoaDonController) {
        this.userController = userController;
        this.sanPhamController = sanPhamController;
        this.hoaDonController = hoaDonController;
        initUI();
    }

    /**
     * Khởi tạo giao diện chính
     */
    private void initUI() {
        frame = new JFrame("Coffee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Tạo panel ca làm việc và thêm listener cho nó để mở ca làm việc
        CaLamViecPanel caLamViecPanel = new CaLamViecPanel();
        caLamViecPanel.setShiftListener(tienMoCa -> {
            try {
                String maCaLamViec = userController.generateShiftId();
                CaLamViec caLamViec = userController.createShift(maCaLamViec, tienMoCa);

                if (userController.saveShift(caLamViec)) {
                    currentShift = caLamViec;
                    isShiftOpen = true;
                    cardLayout.show(mainPanel, "BAN_HANG");
                } else {
                    JOptionPane.showMessageDialog(frame, "Lỗi khi lưu ca làm việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });


        mainPanel.add(caLamViecPanel, "CA_LAM_VIEC");

//        ThanhToanPanel thanhToanPanel = new ThanhToanPanel(sanPhamController, userController, hoaDonController);
        BanHangPanel banHangPanel = new BanHangPanel(userController, mainPanel, cardLayout, sanPhamController);
        HoaDonPanel hoaDonPanel = new HoaDonPanel(userController, sanPhamController);


        mainPanel.add(banHangPanel, "BAN_HANG");
        mainPanel.add(hoaDonPanel, "HOA_DON");
        mainPanel.add(new ThongTinPanel(userController), "UPDATE");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Tạo sidebar bên trái
     * @return JPanel sidebar
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(10, 82, 116));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel hiLabel = new JLabel("Wind's Coffee Shop");
        hiLabel.setForeground(Color.WHITE);
        hiLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hiLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        hiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(hiLabel);

        ImageIcon logoIcon = new ImageIcon("image/Logo.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(200, 170, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        sidebar.add(logoLabel);

        addSidebarButton(sidebar, "BÁN HÀNG", "image/products.png", "BAN_HANG");
        addSidebarButton(sidebar, "HÓA ĐƠN", "image/list.png", "HOA_DON");

        sidebar.add(Box.createVerticalGlue());

        addSidebarButton(sidebar, "ĐÓNG CA", "image/dongca.png", "DONG_CA");
        addSidebarButton(sidebar, "ĐỔI THÔNG TIN", "image/update.png", "UPDATE");
        addSidebarButton(sidebar, "ĐĂNG XUẤT", "image/logout.png", "DANG_XUAT");

        return sidebar;
    }

    /**
     * Thêm nút vào sidebar
     * @param sidebar JPanel sidebar
     * @param text String text hiển thị
     * @param iconPath String đường dẫn icon
     * @param frameToOpen String tên frame để mở
     */
    private void addSidebarButton(JPanel sidebar, String text, String iconPath, String frameToOpen) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(10, 82, 116));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(scaledImg));

        if (frameToOpen != null) {
            if (frameToOpen.equals("DANG_XUAT")) {
                btn.addActionListener(e -> handleLogout());
            } else if (frameToOpen.equals("DONG_CA")) {
                btn.addActionListener(e -> handleShiftClose());
            } else {
                btn.addActionListener(e -> openFrame(frameToOpen));
            }
        }

        sidebar.add(btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
    }


    /**
     * Mở frame tương ứng với tên frame
     * @param frameName String tên frame
     */
    private void openFrame(String frameName) {
        if (frameName.equals("BAN_HANG") && !isShiftOpen || frameName.equals("HOA_DON") && !isShiftOpen) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Vui lòng mở ca làm việc trước khi vào Bán hàng!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE

            );
            return;
        }
        cardLayout.show(mainPanel, frameName);
    }

    /**
     * Xử lý sự kiện đăng xuất
     */
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION && !isShiftOpen) {
            frame.dispose();
            new fLogin();
        }
        if (confirm == JOptionPane.YES_OPTION && isShiftOpen) {
            handleShiftClose();
        }
    }

    /**
     * Xử lý sự kiện đóng ca làm việc
     */
    private void handleShiftClose() {
        if (!isShiftOpen || currentShift == null) {
            JOptionPane.showMessageDialog(frame, "Không có ca làm việc nào đang mở!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Bạn có chắc chắn muốn đóng ca làm việc?",
                "Xác nhận đóng ca",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String input = JOptionPane.showInputDialog(frame, "Nhập số tiền đóng ca:");
                if (input == null || input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Số tiền đóng ca không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double soTienDongCa = Double.parseDouble(input.trim());
                if (soTienDongCa < 455000) {
                    JOptionPane.showMessageDialog(frame, "Số tiền đóng ca không được nhỏ hơn số tiền mở ca!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userController.closeShift(currentShift, soTienDongCa)) {
                    JOptionPane.showMessageDialog(frame,
                            "Ca làm việc đã được đóng thành công!\n" +
                                    "Thời gian kết thúc: " + currentShift.getThoiGianKetThuc() + "\n" +
                                    "Số tiền đóng ca: " + currentShift.getTienDongCa(),
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    isShiftOpen = false;
                    cardLayout.show(mainPanel, "CA_LAM_VIEC");
                } else {
                    JOptionPane.showMessageDialog(frame, "Lỗi khi đóng ca làm việc! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Số tiền đóng ca không hợp lệ! Vui lòng nhập một số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}