package view.Employee;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.HoaDonController;
import controller.UserController;
import entity.LoaiSanPham;
import entity.NhanVien;
import entity.SanPham;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.SanPhamController;

public class BanHangPanel extends JPanel implements MouseListener, ActionListener {
    private final JPanel mainPanel;
    private CardLayout cardLayout;

    private JButton btnThanhToan, btnThemGioHang;
    private SanPhamController sanPhamController;

    private JLabel lblSoLuongValue, lblTienValue;

    private HoaDonController hoaDonController;
    private UserController userController;


    private JPanel cardPanel;

    private Map<SanPham, Integer> cartItems;
    
    private JComboBox<String> cboPhuongThucThanhToan;


    /**
     * Constructor của panel bán hàng
     * @param userController
     * @param mainPanel
     * @param cardLayout
     * @param sanPhamController
     */
    public BanHangPanel(UserController userController, JPanel mainPanel, CardLayout cardLayout, SanPhamController sanPhamController) {
        this.userController = userController;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.sanPhamController = sanPhamController;

        setLayout(new BorderLayout());

        cartItems =new HashMap<>();

        lblSoLuongValue = new JLabel("0");
        lblTienValue = new JLabel("0 VND");

        cardPanel = InForBuyPanel();

        add(cardPanel, BorderLayout.CENTER);
        add(createLoaiSanPhamPanel(sanPhamController.getDsachSanPham()), BorderLayout.WEST);

        btnThanhToan.addActionListener(this);

        this.hoaDonController = new HoaDonController();
    }

    public JPanel InForBuyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin bán hàng"));
        panel.setPreferredSize(new Dimension(300, 1000));

        JPanel nothPanel = new JPanel(new GridLayout(2,1));
        JLabel lblTongSoLuong = new JLabel("Tổng số lượng: ");
        JLabel lblTongTien = new JLabel("Tổng tiền: ");

        nothPanel.add(lblTongSoLuong);
        nothPanel.add(lblSoLuongValue);
        nothPanel.add(lblTongTien);
        nothPanel.add(lblTienValue);

        panel.add(nothPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setPreferredSize(new Dimension(300, 600));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setPreferredSize(new Dimension(300, 600));

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        cboPhuongThucThanhToan = new JComboBox<>();
        cboPhuongThucThanhToan.addItem("Tiền mặt");
        cboPhuongThucThanhToan.addItem("Chuyển khoản");
        cboPhuongThucThanhToan.setPreferredSize(new Dimension(100, 25));
        westPanel.add(cboPhuongThucThanhToan);

        btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setPreferredSize(new Dimension(200, 35));
        btnThanhToan.setBackground(new Color(10, 82, 116));
        btnThanhToan.setForeground(Color.WHITE);
        southPanel.add(btnThanhToan);

        panel.add(southPanel, BorderLayout.SOUTH);
        panel.add(westPanel, BorderLayout.WEST);
        return panel;
    }

    private void addItemToCart(SanPham sp) {
        if (cartItems.containsKey(sp)) {
            cartItems.put(sp, cartItems.get(sp) + 1);
        } else {
            cartItems.put(sp, 1);
        }

        updateCartPanel();
        updateTotalInfo();
    }

    private void updateTotalInfo() {
        int tongSoLuong = cartItems.values().stream().mapToInt(Integer::intValue).sum();
        double tongTien = cartItems.entrySet().stream().mapToDouble(entry -> entry.getKey().getGiaBan() * entry.getValue()).sum();

        lblSoLuongValue.setText(String.valueOf(tongSoLuong));
        lblTienValue.setText(tongTien + " VND");
    }

    private void updateCartPanel(){
        JScrollPane scrollPane = (JScrollPane) cardPanel.getComponent(1);
        if(scrollPane !=null){
            JPanel centerPanel = (JPanel) scrollPane.getViewport().getView();
            centerPanel.removeAll();

            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

            for (Map.Entry<SanPham, Integer> entry : cartItems.entrySet()) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS)); 
                itemPanel.setMaximumSize(new Dimension(450, 50)); 
                
                JLabel lblTenMon = new JLabel("" + entry.getKey().getTenSanPham());
                lblTenMon.setFont(new Font("Arial", Font.PLAIN, 13));
                lblTenMon.setPreferredSize(new Dimension(150, 25));

                JLabel lblSoLuong = new JLabel("" + entry.getValue());
                lblSoLuong.setFont(new Font("Arial", Font.PLAIN, 13));
                lblSoLuong.setPreferredSize(new Dimension(40, 25));

                JLabel lblGia = new JLabel("" + entry.getKey().getGiaBan() + " VND");
                lblGia.setFont(new Font("Arial", Font.PLAIN, 13));
                lblGia.setPreferredSize(new Dimension(90, 25));
                
                JButton btnGiam = new JButton("-");
                btnGiam.setPreferredSize(new Dimension(40,25));
                btnGiam.addActionListener(ev -> {
                	int current = cartItems.get(entry.getKey());
                	if (current > 1) {
                		cartItems.put(entry.getKey(), current - 1);
                	}else {
                		cartItems.remove(entry.getKey());
                	}
                		updateCartPanel();
                		updateTotalInfo();
                });

                itemPanel.add(lblTenMon);
                itemPanel.add(lblSoLuong);
                itemPanel.add(lblGia);
                itemPanel.add(btnGiam);

                centerPanel.add(itemPanel);
        }
            centerPanel.revalidate();
            centerPanel.repaint();
        }
        else{
            System.out.println("Loi khong co scrollPane");

        }
    }

    private JPanel createLoaiSanPhamPanel(List<SanPham> listSanPham){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(950, 750));

        Map<String, List<SanPham>> loaiSanPham = new HashMap<>();

        for(SanPham sp : listSanPham){
            LoaiSanPham loaiSP = sanPhamController.getLoaiSanPham(sp.getMaSanPham());

            if(loaiSP != null){
                String tenLoaiSanPham = loaiSP.getTenLoaiSanPham();

                loaiSanPham.putIfAbsent(tenLoaiSanPham, new ArrayList<>());
                loaiSanPham.get(tenLoaiSanPham).add(sp);
            }
        }

        JPanel loaiPanelContainer = new JPanel();
        loaiPanelContainer.setLayout(new BoxLayout(loaiPanelContainer, BoxLayout.Y_AXIS));

        for(Map.Entry<String, List<SanPham>> entry : loaiSanPham.entrySet()){
            String loaiSP = entry.getKey();
            List<SanPham> sanPhams = entry.getValue();

            JPanel loaiPanel = new JPanel();
            loaiPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            loaiPanel.setPreferredSize(new Dimension(950,750));
            loaiPanel.setBorder(BorderFactory.createTitledBorder(loaiSP));

            for(SanPham sp : sanPhams){
                JPanel productCard = createProductCard(sp);
                loaiPanel.add(productCard);
            }

            loaiPanelContainer.add(loaiPanel);

        }

        JScrollPane scrollPane = new JScrollPane(loaiPanelContainer);
        scrollPane.setPreferredSize(new Dimension(950, 600));
        panel.add(scrollPane,BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProductCard(SanPham sp) {
        JPanel cardWrapper = new JPanel();
        cardWrapper.setPreferredSize(new Dimension(170, 220));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(170, 200));
        card.setBorder(BorderFactory.createLineBorder(Color.green));
        card.setBackground(Color.white);

        ImageIcon icon;
        String path = "imgSanPham/" + sp.getHinhAnh();
        File file = new File(path);
        if (file.exists()) {
            icon = new ImageIcon(path);
        } else {
            icon = new ImageIcon("image/logo_Wind'sCoffee.png");
        }

        // Ảnh sản phẩm
        Image scaledImage = icon.getImage().getScaledInstance(150, 140, Image.SCALE_SMOOTH);
        JLabel imageLbl = new JLabel(new ImageIcon(scaledImage));
        imageLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel namelbl = new JLabel(sp.getTenSanPham());
        namelbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel priceLbl = new JLabel(sp.getGiaBan() + " VNĐ");
        priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(imageLbl);
        card.add(Box.createVerticalStrut(10));
        card.add(namelbl);
        card.add(priceLbl);

        cardWrapper.add(card, BorderLayout.CENTER);
        cardWrapper.putClientProperty("sanPham", sp);

        cardWrapper.addMouseListener(this);

        return cardWrapper;
    }

    private void xuatHoaDonPDF(String maHoaDon, LocalDateTime ngayLap, String tenNhanVien, double tongTien) {
        try {
            String directory = "HoaDonPDF";
            java.nio.file.Path dirPath = java.nio.file.Paths.get(directory);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }

            String filename = directory + "/HoaDon_" + maHoaDon + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            String fontPath = "fonts/arial.ttf";
            BaseFont unicodeFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font fontTieuDe = new com.itextpdf.text.Font(unicodeFont, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(unicodeFont, 12);
            com.itextpdf.text.Font fontTableHeader = new com.itextpdf.text.Font(unicodeFont, 12, com.itextpdf.text.Font.BOLD); // Font cho header bảng

            Paragraph tieuDe = new Paragraph("Hóa Đơn Bán Hàng", fontTieuDe);
            tieuDe.setAlignment(Element.ALIGN_CENTER);
            document.add(tieuDe);

            document.add(new Paragraph("Mã Hóa Đơn: " + maHoaDon, fontNormal));
            document.add(new Paragraph("Ngày Lập:  " + ngayLap, fontNormal));
            document.add(new Paragraph("Nhân viên: " + tenNhanVien, fontNormal));
            document.add(new Paragraph("------------------------------------------------------"));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.addCell(new com.itextpdf.text.Phrase("Tên Sản Phẩm", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Số Lượng", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Đơn Giá", fontTableHeader));
            table.addCell(new com.itextpdf.text.Phrase("Thành Tiền", fontTableHeader));

            // Use cartItems to populate the table
            for (Map.Entry<SanPham, Integer> entry : cartItems.entrySet()) {
                SanPham sp = entry.getKey();
                int soLuong = entry.getValue();
                double thanhTien = sp.getGiaBan() * soLuong;

                table.addCell(new com.itextpdf.text.Phrase(sp.getTenSanPham(), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(soLuong), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(sp.getGiaBan() + " VNĐ"), fontNormal));
                table.addCell(new com.itextpdf.text.Phrase(String.valueOf(thanhTien), fontNormal));
            }

            document.add(table);

            document.add(new Paragraph("------------------------------------------------------"));
            document.add(new Paragraph("Tổng Tiền: " + tongTien, fontNormal));

            document.close();

            java.awt.Desktop.getDesktop().open(new java.io.File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn PDF");
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(btnThanhToan)) {
            try {
                double tongTien = 0;
                List<SanPham> danhSachSanPham = new ArrayList<>();

                for (Map.Entry<SanPham, Integer> entry : cartItems.entrySet()) {
                    SanPham sp = entry.getKey();
                    int soLuong = entry.getValue();
                    double thanhTien = sp.getGiaBan() * soLuong;
                    tongTien += thanhTien;

                    sp.setSoLuong(soLuong);
                    danhSachSanPham.add(sp);
                }

                String maHoaDon = hoaDonController.generateMaHoaDon();
                NhanVien nhanVien = userController.getNhanVienByTenDangNhap(userController.getCurrentUsername());
                if (nhanVien == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDateTime ngayLap = LocalDateTime.now();
                boolean hoaDonInsert = hoaDonController.insertHoaDon(maHoaDon, nhanVien.getMaNhanVien(), ngayLap, tongTien);
                if (!hoaDonInsert) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean chiTietHoaDonInsert = true;
                for (SanPham sp : danhSachSanPham) {
                    String maChiTietHoaDon = hoaDonController.generateMaChiTietHoaDon();
                    String maSanPham = sp.getMaSanPham();
                    int soLuong = sp.getSoLuong();
                    double donGia = sp.getGiaBan();
                    double thanhTienSP = soLuong * donGia;
                    if (!hoaDonController.insertChiTietHoaDon(maChiTietHoaDon, maHoaDon, maSanPham, soLuong, donGia, thanhTienSP)) {
                        chiTietHoaDonInsert = false;
                        break;
                    }
                }

                if (chiTietHoaDonInsert) {
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    int comfirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất hóa đơn PDF không?", "Xuất hóa đơn", JOptionPane.YES_NO_OPTION);
                    if (comfirm == JOptionPane.YES_OPTION) {
                        xuatHoaDonPDF(maHoaDon, ngayLap, nhanVien.getTenNhanVien(), tongTien);
                    }

                    cartItems.clear();
                    updateCartPanel();
                    updateTotalInfo();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu chi tiết hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source instanceof JPanel) {
            JPanel clickedPanel = (JPanel) source;
            SanPham selectedSanPham = (SanPham) clickedPanel.getClientProperty("sanPham");
            if (selectedSanPham != null) {
                addItemToCart(selectedSanPham);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}


