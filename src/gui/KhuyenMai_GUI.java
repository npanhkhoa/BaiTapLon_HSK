package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.KhuyenMai_DAO;
import entity.KhuyenMai;

public class KhuyenMai_GUI extends JFrame {
    private JTextField txtMaKM, txtTenKM, txtMoTa, txtPhanTramGiam;
    private JButton btnThem, btnXoa, btnLamMoi;
    private JTable tableKhuyenMai;
    private DefaultTableModel tableModel;
    private KhuyenMai_DAO khuyenMaiDAO;

    private static final Color COLOR_PRIMARY = new Color(30, 136, 229);
    private static final Color COLOR_SECONDARY = new Color(46, 204, 113);
    private static final Color COLOR_ACCENT = new Color(231, 76, 60);
    private static final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private static final Color COLOR_PANEL = Color.WHITE;

    public KhuyenMai_GUI() {
        khuyenMaiDAO = new KhuyenMai_DAO();

        initializeComponents();
        setupLayout();
        applyCustomStyles();
        setupEvents();
        loadDataToTable();

        setTitle("Quản Lý Khuyến Mãi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initializeComponents() {
        txtMaKM = new JTextField(15);
        txtMaKM.setEditable(false);
        txtMaKM.setBackground(COLOR_BACKGROUND);
        txtTenKM = new JTextField(15);
        txtMoTa = new JTextField(15);
        txtPhanTramGiam = new JTextField(15);

        btnThem = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        String[] columnNames = {"Mã KM", "Tên Khuyến Mãi", "Mô Tả", "Phần Trăm Giảm (%)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableKhuyenMai = new JTable(tableModel);
        tableKhuyenMai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableKhuyenMai.setRowHeight(25);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_BACKGROUND);

        // --- Bên trái: Form nhập liệu ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(COLOR_PANEL);
        panelForm.setBorder(new CompoundBorder(
                new EmptyBorder(15, 15, 15, 15),
                new TitledBorder(new LineBorder(COLOR_PRIMARY, 2, true),
                        "THÔNG TIN KHUYẾN MÃI",
                        TitledBorder.CENTER, TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 16), COLOR_PRIMARY)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Mã KM
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Mã KM:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtMaKM, gbc);

        // Tên KM
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Tên KM:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtTenKM, gbc);

        // Mô tả
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtMoTa, gbc);

        // Phần trăm giảm
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Phần trăm (%):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtPhanTramGiam, gbc);

        // Buttons Lưu/Xóa/Làm mới
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButtons.setBackground(COLOR_PANEL);
        panelButtons.add(btnThem);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelForm.add(panelButtons, gbc);

        // --- Bên phải: Bảng danh sách ---
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(COLOR_PANEL);
        panelTable.setBorder(new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new TitledBorder(new LineBorder(COLOR_SECONDARY, 2, true),
                        "DANH SÁCH KHUYẾN MÃI",
                        TitledBorder.CENTER, TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 16), COLOR_SECONDARY)
        ));
        panelTable.add(new JScrollPane(tableKhuyenMai), BorderLayout.CENTER);

        // --- JSplitPane chia 2 bên ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, panelTable);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.3);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
    }

    private void applyCustomStyles() {
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);
        txtMaKM.setFont(fieldFont);
        txtTenKM.setFont(fieldFont);
        txtMoTa.setFont(fieldFont);
        txtPhanTramGiam.setFont(fieldFont);

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);
        Dimension btnSize = new Dimension(100, 35);
        for (JButton btn : new JButton[]{btnThem, btnXoa, btnLamMoi}) {
            btn.setFont(buttonFont);
            btn.setPreferredSize(btnSize);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Màu nút
            if (btn == btnThem) btn.setBackground(COLOR_SECONDARY);
            else if (btn == btnXoa) btn.setBackground(COLOR_ACCENT);
            else btn.setBackground(new Color(149, 165, 166));
            btn.setForeground(Color.WHITE);

            // Hover effect
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { btn.setBackground(btn.getBackground().darker()); }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (btn == btnThem) btn.setBackground(COLOR_SECONDARY);
                    else if (btn == btnXoa) btn.setBackground(COLOR_ACCENT);
                    else btn.setBackground(new Color(149, 165, 166));
                }
            });
        }

        tableKhuyenMai.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableKhuyenMai.getTableHeader().setBackground(COLOR_PRIMARY);
        tableKhuyenMai.getTableHeader().setForeground(Color.WHITE);
        tableKhuyenMai.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableKhuyenMai.setGridColor(new Color(220, 220, 220));
        tableKhuyenMai.setShowGrid(true);
    }

    private void setupEvents() {
        btnThem.addActionListener(e -> luuKhuyenMai());
        btnXoa.addActionListener(e -> xoaKhuyenMai());
        btnLamMoi.addActionListener(e -> lamMoi());

        tableKhuyenMai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiThongTinTuTable();
            }
        });
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<KhuyenMai> ds = khuyenMaiDAO.layTatCa();
        for (KhuyenMai km : ds) {
            Object[] row = {
                    km.getMaKM(),
                    km.getTenKM(),
                    km.getMoTa(),
                    String.format("%.2f", km.getPhanTramGiam())
            };
            tableModel.addRow(row);
        }
    }

    private void luuKhuyenMai() {
        try {
            String maKM = txtMaKM.getText().trim();
            String tenKM = txtTenKM.getText().trim();
            String moTa = txtMoTa.getText().trim();
            String phanTramStr = txtPhanTramGiam.getText().trim();

            if (tenKM.isEmpty() || phanTramStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double phanTram = Double.parseDouble(phanTramStr);
            if (phanTram <= 0 || phanTram > 100) {
                JOptionPane.showMessageDialog(this,
                        "Phần trăm giảm phải từ 0 đến 100!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhuyenMai km = new KhuyenMai(maKM.isEmpty() ? "" : maKM, tenKM, moTa, phanTram);

            boolean success;
            if (maKM.isEmpty()) { // Thêm mới
                success = khuyenMaiDAO.themKhuyenMai(km);
                if (success) JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
            } else { // Cập nhật
                success = khuyenMaiDAO.capNhatKhuyenMai(km);
                if (success) JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
            }

            if (success) {
                loadDataToTable();
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, "Thao tác thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void xoaKhuyenMai() {
        int row = tableKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maKM = String.valueOf(tableModel.getValueAt(row, 0));
            if (khuyenMaiDAO.xoaKhuyenMai(maKM)) {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
                loadDataToTable();
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa khuyến mãi thất bại! Có thể đang được sử dụng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lamMoi() {
        txtMaKM.setText("");
        txtTenKM.setText("");
        txtMoTa.setText("");
        txtPhanTramGiam.setText("");
        tableKhuyenMai.clearSelection();
        txtTenKM.requestFocus();
    }

    private void hienThiThongTinTuTable() {
        int row = tableKhuyenMai.getSelectedRow();
        if (row >= 0) {
            txtMaKM.setText(String.valueOf(tableModel.getValueAt(row, 0)));
            txtTenKM.setText(String.valueOf(tableModel.getValueAt(row, 1)));
            txtMoTa.setText(String.valueOf(tableModel.getValueAt(row, 2)));
            txtPhanTramGiam.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ConnectDB.getInstance().connect();
                new KhuyenMai_GUI().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Lỗi kết nối database: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
