package view.Manager;

import controller.HoaDonController;
import view.Employee.ThongTinPanel;
import view.login.fLogin;

import javax.swing.*;
import java.awt.*;

public class Manager_GUI {
    private JFrame frame;
    private JPanel mainPanel;      // Panel chứa các màn hình
    private CardLayout cardLayout; // Bộ quản lý layout chuyển panel
    private HoaDonController hoaDonController;

    public Manager_GUI() {
        initUI(hoaDonController);
    }

    private void initUI(HoaDonController hoaDonController) {
        frame = new JFrame("Coffee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // === Sidebar trái ===
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // === Main panel với CardLayout ===
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Thêm các panel tương ứng vào mainPanel
        mainPanel.add(new SanPhamPanel(), "PRODUCT");
        mainPanel.add(new ThongKeFrame(hoaDonController), "THONG_KE");
        mainPanel.add(new PhieuNhapFrame(), "PHIEU_NHAP");
        mainPanel.add(new KhoPanel(), "KHO");
        mainPanel.add(new TaiKhoanFrame(), "ACCOUNT");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(10, 82, 116));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel hiLabel = new JLabel("Xin chào, Admin!");
        hiLabel.setForeground(Color.WHITE);
        hiLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hiLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        hiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(hiLabel);

        ImageIcon logoIcon = new ImageIcon("image/Logo.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(200, 170, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        sidebar.add(logoLabel);


        // Thêm các nút vào sidebar
        addSidebarButton(sidebar, "SẢN PHẨM", "image/products.png", "PRODUCT");
        addSidebarButton(sidebar, "PHIẾU NHẬP", "image/import_export.png", "PHIEU_NHAP");
//        addSidebarButton(sidebar, "PHIẾU XUẤT", "image/import_export.png", "PHIEU_XUAT");
        addSidebarButton(sidebar, "QUẢN LÝ KHO", "image/inventory.png", "KHO");
        addSidebarButton(sidebar, "TÀI KHOẢN", "image/profile_2.png", "ACCOUNT");
        addSidebarButton(sidebar, "BÁO CÁO", "image/thongke.png", "THONG_KE");

        sidebar.add(Box.createVerticalGlue());


        addSidebarButton(sidebar, "ĐĂNG XUẤT", "image/logout.png", "DANG_XUAT");

        return sidebar;
    }

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
            } else {
                btn.addActionListener(e -> openFrame(frameToOpen));
            }
        }

        sidebar.add(btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void openFrame(String frameName) {
        // Chuyển đổi giữa các panel bằng CardLayout
        cardLayout.show(mainPanel, frameName);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose(); // Đóng cửa sổ hiện tại
            new fLogin();
        }
    }

}