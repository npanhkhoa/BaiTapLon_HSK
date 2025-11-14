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

public class GUI_KHUYENMAI extends JFrame {
    private JTextField txtMaKM, txtPhanTramGiam;
    private JButton btnLuu;
    private JTable tableKhuyenMai;
    private DefaultTableModel tableModel;
    
    private KhuyenMai_DAO khuyenMaiDAO;
    
    // Màu sắc
    private static final Color COLOR_PRIMARY = new Color(30, 136, 229);
    private static final Color COLOR_SECONDARY = new Color(46, 204, 113);
    private static final Color COLOR_ACCENT = new Color(231, 76, 60);
    private static final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private static final Color COLOR_PANEL = Color.WHITE;
    
    public GUI_KHUYENMAI() {
        khuyenMaiDAO = new KhuyenMai_DAO();
        
        initializeComponents();
        setupLayout();
        setupEvents();
        applyCustomStyles();
        loadDataToTable();
        
        setTitle("Quản Lý Khuyến Mãi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void initializeComponents() {
        // Text fields - chỉ có Mã KM và Phần trăm giảm theo wireframe
        txtMaKM = new JTextField(20);
        txtMaKM.setEditable(false);
        txtMaKM.setBackground(COLOR_BACKGROUND);
        txtPhanTramGiam = new JTextField(20);
        
        // Button
        btnLuu = new JButton("LƯU");
        
        // Table
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
        
        // Title
        JLabel lblTitle = new JLabel("KHUYẾN MÃI", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Center - 2 panels side by side
        JPanel panelCenter = new JPanel(new GridLayout(1, 2, 15, 15));
        panelCenter.setBackground(COLOR_BACKGROUND);
        panelCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Left panel - Thông tin khuyến mãi
        JPanel panelThongTin = new JPanel(new GridBagLayout());
        panelThongTin.setBackground(COLOR_PANEL);
        panelThongTin.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new TitledBorder(new LineBorder(COLOR_PRIMARY, 2, true), 
                           "THÔNG TIN KHUYẾN MÃI", 
                           TitledBorder.CENTER, TitledBorder.TOP,
                           new Font("Segoe UI", Font.BOLD, 16), COLOR_PRIMARY)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Mã khuyến mãi
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblMaKM = new JLabel("MÃ KHUYẾN MÃI:");
        lblMaKM.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelThongTin.add(lblMaKM, gbc);
        
        gbc.gridx = 1;
        panelThongTin.add(txtMaKM, gbc);
        
        // Phần trăm giảm
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPhanTram = new JLabel("PHẦN TRĂM GIẢM:");
        lblPhanTram.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelThongTin.add(lblPhanTram, gbc);
        
        gbc.gridx = 1;
        panelThongTin.add(txtPhanTramGiam, gbc);
        
        // Right panel - Danh sách khuyến mãi
        JPanel panelDanhSach = new JPanel(new BorderLayout());
        panelDanhSach.setBackground(COLOR_PANEL);
        panelDanhSach.setBorder(new CompoundBorder(
            new EmptyBorder(10, 10, 10, 10),
            new TitledBorder(new LineBorder(COLOR_SECONDARY, 2, true), 
                           "DANH SÁCH KHUYẾN MÃI", 
                           TitledBorder.CENTER, TitledBorder.TOP,
                           new Font("Segoe UI", Font.BOLD, 16), COLOR_SECONDARY)
        ));
        
        JScrollPane scrollPane = new JScrollPane(tableKhuyenMai);
        scrollPane.setBorder(null);
        panelDanhSach.add(scrollPane, BorderLayout.CENTER);
        
        panelCenter.add(panelThongTin);
        panelCenter.add(panelDanhSach);
        
        // Bottom - Button LƯU
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBottom.setBackground(COLOR_PANEL);
        panelBottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelBottom.add(btnLuu);
        
        add(lblTitle, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }
    
    private void applyCustomStyles() {
        // Text fields
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        txtMaKM.setFont(fieldFont);
        txtPhanTramGiam.setFont(fieldFont);
        
        // Button LƯU
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setBackground(COLOR_SECONDARY);
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);
        btnLuu.setBorderPainted(false);
        btnLuu.setOpaque(true);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLuu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLuu.setBackground(COLOR_SECONDARY.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnLuu.setBackground(COLOR_SECONDARY);
            }
        });
        
        // Table styles
        tableKhuyenMai.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableKhuyenMai.getTableHeader().setBackground(COLOR_PRIMARY);
        tableKhuyenMai.getTableHeader().setForeground(Color.WHITE);
        tableKhuyenMai.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableKhuyenMai.setGridColor(new Color(220, 220, 220));
        tableKhuyenMai.setShowGrid(true);
    }
    
    private void setupEvents() {
        btnLuu.addActionListener(e -> luuKhuyenMai());
        
        tableKhuyenMai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    hienThiThongTinTuTable();
                } else if (e.getClickCount() == 2) {
                    xoaKhuyenMai();
                }
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
            String phanTramStr = txtPhanTramGiam.getText().trim();
            
            if (phanTramStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập phần trăm giảm!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double phanTramGiam = Double.parseDouble(phanTramStr);
            if (phanTramGiam <= 0 || phanTramGiam > 100) {
                JOptionPane.showMessageDialog(this, 
                    "Phần trăm giảm phải từ 0 đến 100!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (maKM.isEmpty()) {
                // Thêm mới - chỉ cần phần trăm giảm
                KhuyenMai km = new KhuyenMai("", "Khuyến mãi " + phanTramGiam + "%", 
                    "Giảm giá " + phanTramGiam + "%", phanTramGiam);
                
                if (khuyenMaiDAO.themKhuyenMai(km)) {
                    JOptionPane.showMessageDialog(this, 
                        "Thêm khuyến mãi thành công!", 
                        "Thông báo", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    lamMoi();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Thêm khuyến mãi thất bại!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Sửa - cập nhật phần trăm giảm
                KhuyenMai kmHienTai = khuyenMaiDAO.timTheoMaKhuyenMai(maKM);
                if (kmHienTai != null) {
                    KhuyenMai km = new KhuyenMai(maKM, kmHienTai.getTenKM(), 
                        kmHienTai.getMoTa(), phanTramGiam);
                    
                    if (khuyenMaiDAO.capNhatKhuyenMai(km)) {
                        JOptionPane.showMessageDialog(this, 
                            "Cập nhật khuyến mãi thành công!", 
                            "Thông báo", 
                            JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable();
                        lamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Cập nhật khuyến mãi thất bại!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Phần trăm giảm phải là số!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void xoaKhuyenMai() {
        int row = tableKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khuyến mãi cần xóa!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa khuyến mãi này?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String maKM = (String) tableModel.getValueAt(row, 0);
            if (khuyenMaiDAO.xoaKhuyenMai(maKM)) {
                JOptionPane.showMessageDialog(this, 
                    "Xóa khuyến mãi thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa khuyến mãi thất bại! Có thể khuyến mãi đang được sử dụng.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void lamMoi() {
        txtMaKM.setText("");
        txtPhanTramGiam.setText("");
        tableKhuyenMai.clearSelection();
    }
    
    private void hienThiThongTinTuTable() {
        int row = tableKhuyenMai.getSelectedRow();
        if (row >= 0) {
            txtMaKM.setText((String) tableModel.getValueAt(row, 0));
            txtPhanTramGiam.setText((String) tableModel.getValueAt(row, 3));
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ConnectDB.getInstance().connect();
                new GUI_KHUYENMAI().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Lỗi kết nối database: " + e.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
