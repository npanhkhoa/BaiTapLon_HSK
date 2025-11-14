package gui;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

import connectDB.ConnectDB;
import dao.HoaDon_DAO;
import entity.HoaDon;
import javax.swing.table.DefaultTableModel;

public class ThongKe_GUI extends JFrame {
    private JSpinner spinnerTuNgay, spinnerDenNgay;
    private JButton btnThongKe;
    private JLabel lblTongDoanhThu, lblTongHoaDon;
    private JTable tableChiTietHoaDon;
    private DefaultTableModel tableModel;
    private ChartPanel panelBieuDo;
    private JComboBox<String> comboBoxLoaiThongKe;

    
    private HoaDon_DAO hoaDonDAO;
    private List<HoaDon> danhSachHoaDon; // Lưu danh sách hóa đơn hiện tại

    // Style 
    private static final Color COLOR_PRIMARY = new Color(30, 136, 229); // xanh biển
    private static final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private static final Color COLOR_PANEL = Color.WHITE;

    public ThongKe_GUI() {
        hoaDonDAO = new HoaDon_DAO();
        danhSachHoaDon = new ArrayList<>();

        initComponents();
        buildLayout();
        registerEvents();

        setTitle("Thống Kê Doanh Thu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // Khởi tạo
    
    private void initComponents() {
        comboBoxLoaiThongKe = new JComboBox<>(new String[] {"Theo ngày", "Theo tháng", "Theo năm"});

        // mặc định: 7 ngày gần nhất
        SpinnerDateModel modelTu = new SpinnerDateModel();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6);
        modelTu.setValue(cal.getTime());
        spinnerTuNgay = new JSpinner(modelTu);
        spinnerTuNgay.setEditor(new JSpinner.DateEditor(spinnerTuNgay, "dd/MM/yyyy"));

        SpinnerDateModel modelDen = new SpinnerDateModel();
        modelDen.setValue(new Date());
        spinnerDenNgay = new JSpinner(modelDen);
        spinnerDenNgay.setEditor(new JSpinner.DateEditor(spinnerDenNgay, "dd/MM/yyyy"));

        btnThongKe = new JButton("Thống Kê");
        btnThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VNĐ");
        lblTongDoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongDoanhThu.setForeground(COLOR_PRIMARY);

        lblTongHoaDon = new JLabel("Tổng số hóa đơn: 0");
        lblTongHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongHoaDon.setForeground(COLOR_PRIMARY);

        // Bảng cho chi tiết hóa đơn
        String[] columnNames = {"Mã HĐ", "Ngày lập", "Tổng tiền (VNĐ)", "Nhân viên", "Khách hàng", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableChiTietHoaDon = new JTable(tableModel);
        tableChiTietHoaDon.setRowHeight(25);
        tableChiTietHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableChiTietHoaDon.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableChiTietHoaDon.getTableHeader().setBackground(COLOR_PRIMARY);
        tableChiTietHoaDon.getTableHeader().setForeground(Color.WHITE);
        tableChiTietHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableChiTietHoaDon.setGridColor(new Color(220, 220, 220));
        tableChiTietHoaDon.setShowGrid(true);

        panelBieuDo = new ChartPanel(); // panel vẽ biểu đồ
    }

    // Layout
    
    private void buildLayout() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_BACKGROUND);

        // Top controls
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        top.setBackground(COLOR_PANEL);
        top.setBorder(new CompoundBorder(
                new EmptyBorder(12, 12, 12, 12),
                new LineBorder(COLOR_PRIMARY, 2, true)
        ));

        JLabel lblLoai = new JLabel("Loại thống kê:");
        lblLoai.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLoai.setForeground(COLOR_PRIMARY);

        JLabel lblTu = new JLabel("Từ:");
        lblTu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTu.setForeground(COLOR_PRIMARY);

        JLabel lblDen = new JLabel("Đến:");
        lblDen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDen.setForeground(COLOR_PRIMARY);

        top.add(lblLoai);
        top.add(comboBoxLoaiThongKe);
        top.add(Box.createHorizontalStrut(10));
        top.add(lblTu);
        top.add(spinnerTuNgay);
        top.add(Box.createHorizontalStrut(10));
        top.add(lblDen);
        top.add(spinnerDenNgay);
        top.add(Box.createHorizontalStrut(10));
        styleButtonPrimary(btnThongKe);
        top.add(btnThongKe);

        // Center: chart (left) + details (right)
        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        center.setBackground(COLOR_BACKGROUND);
        center.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Chart panel
        JPanel pChart = new JPanel(new BorderLayout());
        pChart.setBackground(COLOR_PANEL);
        pChart.setBorder(new TitledBorder(new LineBorder(COLOR_PRIMARY, 2, true), "Biểu đồ doanh thu",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16), COLOR_PRIMARY));
        pChart.add(panelBieuDo, BorderLayout.CENTER);

        // Details panel
        JPanel pDetail = new JPanel(new BorderLayout());
        pDetail.setBackground(COLOR_PANEL);
        pDetail.setBorder(new TitledBorder(new LineBorder(COLOR_PRIMARY, 2, true), "Chi tiết hóa đơn",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16), COLOR_PRIMARY));
        JScrollPane scroll = new JScrollPane(tableChiTietHoaDon);
        scroll.setBorder(null);
        pDetail.add(scroll, BorderLayout.CENTER);

        center.add(pChart);
        center.add(pDetail);

        // Bottom summary
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        bottom.setBackground(COLOR_PANEL);
        bottom.setBorder(new CompoundBorder(
                new EmptyBorder(12, 12, 12, 12),
                new LineBorder(COLOR_PRIMARY, 2, true)
        ));
        bottom.add(lblTongDoanhThu);
        bottom.add(lblTongHoaDon);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

  
    // Style button
    
    private void styleButtonPrimary(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(110, 34));
       
        }
   
    // Sự kiện
    private void registerEvents() {
        btnThongKe.addActionListener(e -> thucHienThongKe());
        comboBoxLoaiThongKe.addActionListener(e -> capNhatKhoangThoiGianMacDinh());
    }

    // Cập nhật thời gian theo loại thống kê mặc định
    private void capNhatKhoangThoiGianMacDinh() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        String loai = (String) comboBoxLoaiThongKe.getSelectedItem();
        switch (loai) {
            case "Theo ngày": cal.add(Calendar.DAY_OF_MONTH, -7); break;
            case "Theo tháng": cal.add(Calendar.MONTH, -6); break;
            case "Theo năm": cal.add(Calendar.YEAR, -2); break;
        }
        spinnerTuNgay.setValue(cal.getTime());
        spinnerDenNgay.setValue(today);
    }

    // Thực hiện thống kê: lấy dữ liệu, tính toán
 
    private void thucHienThongKe() {
        try {
            String loai = (String) comboBoxLoaiThongKe.getSelectedItem();

            Date tuNgayDate = (Date) spinnerTuNgay.getValue();
            Date denNgayDate = (Date) spinnerDenNgay.getValue();
            if (tuNgayDate == null || denNgayDate == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate tuNgay = tuNgayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate denNgay = denNgayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (tuNgay.isAfter(denNgay)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String tuStr = sdf.format(tuNgayDate);
            String denStr = sdf.format(denNgayDate);

            // Lấy danh sách hóa đơn từ DAO
            List<HoaDon> ds = hoaDonDAO.layHoaDonTheoNgay(tuNgay, denNgay);
            danhSachHoaDon = ds; 

            if (ds == null || ds.isEmpty()) {
                tableModel.setRowCount(0);
                panelBieuDo.updateData(new LinkedHashMap<>());
                lblTongDoanhThu.setText("Tổng doanh thu: 0 VNĐ");
                lblTongHoaDon.setText("Tổng số hóa đơn: 0");
                return;
            }
            
            // Load dữ liệu vào bảng
            loadDataToTable(ds);

            Map<String, Double> mapDoanhThu;
            switch (loai) {
                case "Theo tháng":
                    mapDoanhThu = tinhDoanhThuTheoThang(ds);
                    break;
                case "Theo năm":
                    mapDoanhThu = tinhDoanhThuTheoNam(ds);
                    break;
                default: // "Theo ngày"
                    mapDoanhThu = tinhDoanhThuTheoNgay(ds);
            }

            double tong = 0;
            for (double v : mapDoanhThu.values()) tong += v;
            long soHD = ds.size();

            panelBieuDo.updateData(mapDoanhThu);
            lblTongDoanhThu.setText(String.format("Tổng doanh thu: %,d VNĐ", (long) tong));
            lblTongHoaDon.setText(String.format("Tổng số hóa đơn: %d", soHD));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thực hiện thống kê: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------------------
    // Hàm tính doanh thu (ngày / tháng / năm)
    // ---------------------------
    private Map<String, Double> tinhDoanhThuTheoNgay(List<HoaDon> ds) {
        Map<String, Double> m = new TreeMap<>();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM");
        for (HoaDon hd : ds) {
            String key = hd.getNgayLap().format(f);
            m.put(key, m.getOrDefault(key, 0.0) + hd.getTongTien());
        }
        return m;
    }

    private Map<String, Double> tinhDoanhThuTheoThang(List<HoaDon> ds) {
        Map<String, Double> m = new TreeMap<>();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/yyyy");
        for (HoaDon hd : ds) {
            String key = hd.getNgayLap().format(f);
            m.put(key, m.getOrDefault(key, 0.0) + hd.getTongTien());
        }
        return m;
    }

    private Map<String, Double> tinhDoanhThuTheoNam(List<HoaDon> ds) {
        Map<String, Double> m = new TreeMap<>();
        for (HoaDon hd : ds) {
            String key = String.valueOf(hd.getNgayLap().getYear());
            m.put(key, m.getOrDefault(key, 0.0) + hd.getTongTien());
        }
        return m;
    }

    // ---------------------------
    // Load dữ liệu hóa đơn vào bảng
    // ---------------------------
    private void loadDataToTable(List<HoaDon> ds) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (HoaDon hd : ds) {
            String ngayLap = hd.getNgayLap().format(formatter);
            String tongTien = String.format("%,.0f", hd.getTongTien());
            String nhanVien = hd.getMaNhanVien() != null ? hd.getMaNhanVien() : "";
            String khachHang = hd.getMaKhachHang() != null ? hd.getMaKhachHang() : "";
            String trangThai = hd.getTrangThaiThanhToan() == 1 ? "Đã thanh toán" : "Chưa thanh toán";
            
            Object[] row = {
                hd.getMaHoaDon(),
                ngayLap,
                tongTien,
                nhanVien,
                khachHang,
                trangThai
            };
            tableModel.addRow(row);
        }
    }
    

    //  Biểu đồ 
    
    class ChartPanel extends JPanel {
        private Map<String, Double> data = new LinkedHashMap<>();
        private static final int PADDING = 60;
        private static final int BAR_SPACING = 18;
        private static final int MAX_GRID_LINES = 5;

        ChartPanel() {
            setBackground(COLOR_PANEL);
            setPreferredSize(new Dimension(600, 420));
        }

        void updateData(Map<String, Double> newData) {
            this.data = newData == null ? new LinkedHashMap<>() : newData;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            if (data == null || data.isEmpty()) {
                drawNoData(g2, w, h);
                return;
            }

            // compute chart area
            int chartX = PADDING;
            int chartY = PADDING;
            int chartW = w - 2 * PADDING;
            int chartH = h - 2 * PADDING;

            // max value
            double max = data.values().stream().mapToDouble(Double::doubleValue).max().orElse(1.0);

            // draw grid lines
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(new Color(230, 230, 230));
            for (int i = 0; i <= MAX_GRID_LINES; i++) {
                int y = chartY + (chartH * i / MAX_GRID_LINES);
                g2.drawLine(chartX, y, chartX + chartW, y);
            }

            // axes
            g2.setColor(COLOR_PRIMARY);
            g2.setStroke(new BasicStroke(2f));
            g2.drawLine(chartX, chartY, chartX, chartY + chartH);
            g2.drawLine(chartX, chartY + chartH, chartX + chartW, chartY + chartH);

            // bars
            int count = data.size();
            int totalSpacing = (count + 1) * BAR_SPACING;
            int barWidth = Math.max(10, (chartW - totalSpacing) / count);

            int x = chartX + BAR_SPACING;
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            for (Map.Entry<String, Double> e : data.entrySet()) {
                String label = e.getKey();
                double value = e.getValue();
                int barH = (int) ((value / max) * (chartH - 20)); // leave space top

                // draw bar (single color)
                int barX = x;
                int barY = chartY + chartH - barH;
                g2.setColor(COLOR_PRIMARY);
                g2.fillRect(barX, barY, barWidth, barH);

                // border darker
                g2.setColor(COLOR_PRIMARY.darker());
                g2.drawRect(barX, barY, barWidth, barH);

                // value label above bar
                String valStr = String.format("%,.0f", value);
                FontMetrics fm = g2.getFontMetrics();
                int txtW = fm.stringWidth(valStr);
                int txtX = barX + (barWidth - txtW) / 2;
                int txtY = Math.max(chartY + 12, barY - 6);
                g2.setColor(Color.BLACK);
                g2.drawString(valStr, txtX, txtY);

                // label below bar (truncate if too long)
                String lab = label.length() > 10 ? label.substring(0, 9) + ".." : label;
                int labW = fm.stringWidth(lab);
                int labX = barX + (barWidth - labW) / 2;
                int labY = chartY + chartH + 18;
                g2.drawString(lab, labX, labY);

                x += barWidth + BAR_SPACING;
            }

            // Y-axis labels (simple)
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(COLOR_PRIMARY);
            FontMetrics fm = g2.getFontMetrics();
            for (int i = 0; i <= MAX_GRID_LINES; i++) {
                double v = max - (max * i / MAX_GRID_LINES);
                String s = String.format("%,.0f", v);
                int textW = fm.stringWidth(s);
                int y = chartY + (chartH * i / MAX_GRID_LINES) + 4;
                g2.drawString(s, chartX - textW - 8, y);
            }
        }

        private void drawNoData(Graphics2D g2, int w, int h) {
            String msg = "Chưa có dữ liệu để hiển thị";
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            FontMetrics fm = g2.getFontMetrics();
            int x = (w - fm.stringWidth(msg)) / 2;
            int y = h / 2;
            g2.drawString(msg, x, y);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ConnectDB.getInstance().connect();
                ThongKe_GUI gui = new ThongKe_GUI();
                gui.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối database: " + ex.getMessage());
            }
        });
    }
}
