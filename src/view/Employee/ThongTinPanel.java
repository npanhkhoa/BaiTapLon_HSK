package view.Employee;

import controller.UserController;
import entity.NhanVien;

import javax.swing.*;
import java.awt.*;

public class ThongTinPanel extends JPanel {
    private final UserController userController;

    public ThongTinPanel(UserController userController) {
        this.userController = userController;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Thông tin cá nhân", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel thongTinPanel = showThongTin();
        thongTinPanel.setBackground(Color.WHITE);
        add(thongTinPanel, BorderLayout.CENTER);
    }

    public JPanel showThongTin() {
        NhanVien nhanVien = getThongTinNhanVien();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Avatar Panel
        JLabel avatar = new JLabel(new ImageIcon("image/profile_thongtin.png"));
        avatar.setHorizontalAlignment(JLabel.CENTER);
        JPanel avatarPanel = new JPanel(new BorderLayout());
        avatarPanel.setBackground(Color.WHITE);
        avatarPanel.add(avatar, BorderLayout.CENTER);
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(avatarPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết"));
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        int y = 0;
        addRow(formPanel, gbc, y++, "Mã Nhân Viên:", nhanVien.getMaNhanVien(), labelFont);
        addRow(formPanel, gbc, y++, "Họ và Tên:", nhanVien.getTenNhanVien(), labelFont);
        addRow(formPanel, gbc, y++, "Tuổi:", String.valueOf(nhanVien.getTuoi()), labelFont);
        addRow(formPanel, gbc, y++, "Địa chỉ:", nhanVien.getDiaChi(), labelFont);
        addRow(formPanel, gbc, y++, "Số điện thoại:", nhanVien.getSoDienThoai(), labelFont);
        addRow(formPanel, gbc, y++, "Tên đăng nhập:", nhanVien.getTaiKhoan().getTenDangNhap(), labelFont);

        // Password field with show/hide
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(labelFont);
        passwordField.setText(nhanVien.getTaiKhoan().getMatKhau());
        char defaultEcho = passwordField.getEchoChar();
        formPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        JCheckBox showPassword = new JCheckBox("Hiện mật khẩu");
        showPassword.setBackground(new Color(245, 245, 245));
        showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(showPassword, gbc);

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar(defaultEcho);
            }
        });

        // Role
        gbc.gridx = 0;
        gbc.gridy = ++y;
        formPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 1;
        JLabel roleLabel = new JLabel(nhanVien.getTaiKhoan().isQuyenHan() ? "Quản lý" : "Nhân viên");
        roleLabel.setFont(labelFont);
        formPanel.add(roleLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        return mainPanel;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String labelText, String valueText, Font font) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbc);

        gbc.gridx = 1;
        JLabel valueLabel = new JLabel(valueText);
        valueLabel.setFont(font);
        panel.add(valueLabel, gbc);
    }

    public NhanVien getThongTinNhanVien() {
        String maNV = userController.getCurrentUsername();
        return userController.getNhanVienByTenDangNhap(maNV);
    }
}
