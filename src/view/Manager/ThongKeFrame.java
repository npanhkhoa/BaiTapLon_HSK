package view.Manager;

import javax.swing.*;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.*;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.ui.RectangleInsets;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleEdge;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.toedter.calendar.JDateChooser;
import controller.HoaDonController;
import dao.HoaDon_Dao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

public class ThongKeFrame extends JPanel {
    private JDateChooser dateChooser, dateChooser2;
    private JComboBox<String> loaiThongKeComboBox;
    private ChartPanel chartPanel;
    private JPanel mainChartPanel;
    private HoaDonController hoaDonController;


    private final String[] loaiThongKeOptions = {"Doanh Thu", "Sản phẩm bán chạy"};


    public ThongKeFrame(HoaDonController hoaDonController) {
        setLayout(new BorderLayout());
        add(createTextFieldPanel(), BorderLayout.NORTH);

        mainChartPanel = new JPanel(new BorderLayout());
        mainChartPanel.setBackground(Color.WHITE);

        chartPanel = createBarChart("Thống kê sản phẩm", new DefaultCategoryDataset());
        mainChartPanel.add(chartPanel, BorderLayout.CENTER);
        add(mainChartPanel, BorderLayout.CENTER);
        this.hoaDonController = new HoaDonController();
    }

    private JPanel createTextFieldPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 20, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    panel.setBorder(BorderFactory.createTitledBorder("Thống kê"));

    // Row 0: Title
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.anchor = GridBagConstraints.CENTER;
    JLabel titleLabel = new JLabel("THỐNG KÊ");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setForeground(new Color(10, 82, 116));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(titleLabel, gbc);
    gbc.gridwidth = 1;

    // Row 1: "Thống kê theo"
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel thongKeLabel = new JLabel("Thống kê theo: ");
    panel.add(thongKeLabel, gbc);

    loaiThongKeComboBox = new JComboBox<>(loaiThongKeOptions);
    customizeComboBox(loaiThongKeComboBox);
    gbc.gridx = 1;
    panel.add(loaiThongKeComboBox, gbc);

    // Row 2: "Từ ngày"
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel tuNgayLabel = new JLabel("Từ ngày: ");
    panel.add(tuNgayLabel, gbc);
    dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("dd/MM/yyyy");
    customizeDateChoice(dateChooser);
    gbc.gridx = 1;
    panel.add(dateChooser, gbc);

    // Row 3: "Đến ngày"
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel denNgayLabel = new JLabel("Đến ngày: ");
    panel.add(denNgayLabel, gbc);
    dateChooser2 = new JDateChooser();
    dateChooser2.setDateFormatString("dd/MM/yyyy");
    customizeDateChoice(dateChooser2);
    gbc.gridx = 1;
    panel.add(dateChooser2, gbc);


    // Row 4: Button
    gbc.gridx = 0;
    gbc.gridx = 4;
    JButton thongKeButton = new JButton("Thống kê");
    thongKeButton.setPreferredSize(new Dimension(120, 30));
    thongKeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    thongKeButton.setBackground(new Color(10, 82, 116));
    thongKeButton.setForeground(Color.WHITE);
    thongKeButton.setFocusPainted(false);
    panel.add(thongKeButton, gbc);

    // Xử lý sự kiện thống kê
    thongKeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            java.util.Date utilStartDate = dateChooser.getDate();
            java.util.Date utilEndDate = dateChooser2.getDate();

            // --- KIỂM TRA NGÀY HỢP LỆ ---
            if (utilStartDate == null || utilEndDate == null) {
                JOptionPane.showMessageDialog(ThongKeFrame.this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utilStartDate.after(utilEndDate)) {
                JOptionPane.showMessageDialog(ThongKeFrame.this, "Ngày bắt đầu không được sau ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- CHUYỂN ĐỔI DATE VÀ ĐIỀU CHỈNH ENDDATE ---
            LocalDateTime startDate = utilStartDate.toInstant()
                                                .atZone(java.time.ZoneId.systemDefault())
                                                .toLocalDate() // Lấy LocalDate (chỉ ngày)
                                                .atStartOfDay(); // Đặt giờ về 00:00:00

            LocalDateTime endDate = utilEndDate.toInstant()
                                            .atZone(java.time.ZoneId.systemDefault())
                                            .toLocalDate() // Lấy LocalDate (chỉ ngày)
                                            .atTime(23, 59, 59, 999_999_999); // Đặt giờ về cuối ngày 23:59:59.999...

            System.out.println("Thống kê từ: " + startDate + " đến: " + endDate); // In ra để kiểm tra

            // --- LẤY DỮ LIỆU VÀ CẬP NHẬT BIỂU ĐỒ ---
            if (loaiThongKeComboBox.getSelectedItem().equals("Doanh Thu")) {
                DefaultCategoryDataset dataset = getDatasetDoanhThu(startDate, endDate);
                updateLineChart(dataset);
            } else if (loaiThongKeComboBox.getSelectedItem().equals("Sản phẩm bán chạy")) {
                DefaultCategoryDataset dataset = getDatasetProducts(startDate, endDate);
                updateBarChart(dataset);
            }

        }
    });

    return panel;
}

private void customizeDateChoice(JDateChooser dateChooser) {
    dateChooser.setPreferredSize(new Dimension(150, 25));
    dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    dateChooser.setBackground(Color.WHITE);
    dateChooser.setForeground(new Color(10, 82, 116));
    dateChooser.setBorder(BorderFactory.createLineBorder(new Color(10, 82, 116), 1));
    dateChooser.setFocusable(false);
    dateChooser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    ((JTextFieldDateEditor) dateChooser.getDateEditor()).setFont(new Font("Segoe UI", Font.PLAIN, 14));
    ((JTextFieldDateEditor) dateChooser.getDateEditor()).setBackground(Color.WHITE);
    ((JTextFieldDateEditor) dateChooser.getDateEditor()).setForeground(new Color(10, 82, 116));
    }


    private void customizeComboBox(JComboBox<String> comboBox) {
        comboBox.setPreferredSize(new Dimension(150, 25));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(10, 82, 116));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(10, 82, 116), 1));
        comboBox.setFocusable(false);
        comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Phương thức này tạo biểu đồ cột với dữ liệu được cung cấp
     * @param title Tiêu đề biểu đồ
     * @param dataset Dữ liệu cho biểu đồ
     * @return ChartPanel chứa biểu đồ
     */
    private ChartPanel createBarChart(String title, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Sản phẩm",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        customChartPanel(chart); // Tùy chỉnh biểu đồ

        // Thêm chú thích phụ đẹp hơn
        chart.setPadding(new RectangleInsets(50, 100, 50, 100));
        TextTitle subtitle = new TextTitle("10 sản phẩm bán chạy nhất từ ngày " + dateChooser.getDate() + " đến " + dateChooser2.getDate());
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setPaint(new Color(10, 82, 116));
        subtitle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        subtitle.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(subtitle);


        // Tùy chỉnh thanh màu
        BarRenderer renderer =
                (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setMaximumBarWidth(0.025);
        Color blue = new Color(10, 82, 116);
        for (int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesPaint(i, blue);
        }

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setMouseWheelEnabled(true);
        panel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180))); // Viền đẹp

        return panel;
    }

    /**
     * Phương thức này tùy chỉnh biểu đồ
     * @param chart JFreeChart biểu đồ cần tùy chỉnh
     */
    public void customChartPanel(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlineVisible(false);
        chart.getCategoryPlot().setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.getCategoryPlot().setDomainGridlinePaint(Color.LIGHT_GRAY);
        chart.getCategoryPlot().setDomainGridlinesVisible(true);
        chart.getCategoryPlot().setRangeGridlinesVisible(true);

        chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
        chart.getCategoryPlot().getDomainAxis().setLabelPaint(new Color(10, 82, 116));
        chart.getCategoryPlot().getRangeAxis().setLabelPaint(new Color(10, 82, 116));
    }


    /**
     * Phương thức này cập nhật biểu đồ với dữ liệu mới
     * @param dataset DefaultCategoryDataset chứa dữ liệu mới
     */
    private void updateBarChart(DefaultCategoryDataset dataset) {
        mainChartPanel.remove(chartPanel);
        chartPanel = createBarChart("Thống kê sản phẩm bán chạy nhất", dataset);
        mainChartPanel.add(chartPanel, BorderLayout.CENTER);
        mainChartPanel.revalidate();
        mainChartPanel.repaint();
    }

    private void updateLineChart(DefaultCategoryDataset dataset) {
        mainChartPanel.remove(chartPanel);
        JFreeChart chart = createLineChart("Thống kê doanh thu", dataset);

        // Thêm renderer để hiển thị số liệu trên các điểm dữ liệu
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        renderer.setBaseItemLabelsVisible(true); // Bật nhãn
        renderer.setBaseItemLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        renderer.setBaseItemLabelPaint(new Color(10, 82, 116)); // Màu chữ
        renderer.setBaseShapesVisible(true); // Hiển thị chấm tròn tại mỗi điểm
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        plot.setRenderer(renderer); // Áp dụng renderer vào plot

        chartPanel = new ChartPanel(chart);
        mainChartPanel.add(chartPanel, BorderLayout.CENTER);
        mainChartPanel.revalidate();
        mainChartPanel.repaint();
    }


    /**
     * Phương thức này lấy dữ liệu sản phẩm bán chạy từ controller
     * @param startDate Thời gian bắt đầu
     * @param endDate Thời gian kết thúc
     * @return DefaultCategoryDataset chứa dữ liệu sản phẩm bán chạy
     */
    private DefaultCategoryDataset getDatasetProducts(LocalDateTime startDate, LocalDateTime endDate) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        Map<String, Integer> sanPhamMap = hoaDonController.getTop8SanPhamBanChay(startDate, endDate);
        System.out.println("Kết quả thống kê: " + sanPhamMap);
        for (Map.Entry<String, Integer> entry : sanPhamMap.entrySet()) {
            String tenSanPham = entry.getKey();
            int soLuong = entry.getValue();
            dataset.addValue(soLuong, "Số lượng", tenSanPham);
        }
        return dataset;
    }

    /**
     * Phương thức này lấy dữ liệu doanh thu theo thời gian từ controller
     * @param startDate Thời gian bắt đầu
     * @param endDate Thời gian kết thúc
     * @return DefaultCategoryDataset chứa dữ liệu doanh thu
     */
    private DefaultCategoryDataset getDatasetDoanhThu(LocalDateTime startDate, LocalDateTime endDate) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<LocalDate, Double> doanhThuMap = hoaDonController.getDoanhThuTheoThoiGian(startDate, endDate);
        System.out.println("Kết quả thống kê: " + doanhThuMap);
        for (Map.Entry<LocalDate, Double> entry : doanhThuMap.entrySet()) {
            String ngay = entry.getKey().toString();
            double doanhThu = entry.getValue();
            dataset.addValue(doanhThu, "Doanh thu", ngay);
        }
        return dataset;
    }

    private JFreeChart createLineChart(String title, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                title,
                "Thời gian",
                "Doanh thu",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        customChartPanel(chart); // Tùy chỉnh biểu đồ

        // Thêm chú thích phụ đẹp hơn
        chart.setPadding(new RectangleInsets(50, 100, 50, 100));
        TextTitle subtitle = new TextTitle("Doanh thu từ ngày " + dateChooser.getDate() + " đến " + dateChooser2.getDate());
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setPaint(new Color(10, 82, 116));
        subtitle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        subtitle.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(subtitle);

        return chart;
    }
}
