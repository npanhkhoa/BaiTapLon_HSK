package view.Manager;/*
 * @ (#) KhoPanel.java   1.0     29/04/2025
package view.Manager;


/**
 * @description :
 * @author : Vy, Pham Kha Vy
 * @version 1.0
 * @created : 29/04/2025
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.KhoController;
import dao.Kho_DAO;
import entity.KhoViewTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class KhoPanel extends JPanel implements ActionListener, MouseListener {

    private JComboBox<String> khoComboBox, searchComboBoxNL, searchComboBoxNCC;
    private JTextField searchTextFieldNL, searchTextFieldNCC;
    private JButton searchButtonNL, searchButtonNCC;
    private JTable khoTable;
    private DefaultTableModel tableModel;

    private JLabel lblMaKho, lblTenKho;
    private JLabel lblMaNguyenLieu, lblTenNguyenLieu, lblDonViTinh, lblGiaNhap, lblNgayNhap, lblNgayHetHan, lblSoLuong;
    private JLabel lblMaNCC, lblTenNCC, lblSoDienThoai;

    private JTextArea lblDiaChiKho, lblDiaChiNCC;

    private List<KhoViewTable> danhSach;
    private Kho_DAO kho_dao;

    private KhoViewTable selectedKho = null;
    private KhoController khoController;

    public KhoPanel(){
        setLayout(new BorderLayout());

        kho_dao = new Kho_DAO();
        danhSach = new ArrayList<>();
        selectedKho = new KhoViewTable();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(createComboboxPanel(), BorderLayout.NORTH);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.add(createToolbarPanel(), BorderLayout.NORTH);
        panelTable.add(createKhoTable(), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(panelTable, BorderLayout.WEST);
        centerPanel.add(createTableDetails(), BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createComboboxPanel(){
        JPanel toolbar = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20 ,10));

        JLabel label = new JLabel("Thông Tin Chi Tiết Các Kho Nguyên Liệu:");
        label.setFont(new Font("Segoe UI", Font.BOLD,20));
        label.setForeground(new Color(10, 82, 116));

        khoComboBox = new JComboBox<>();
        khoComboBox.setPreferredSize(new Dimension(200, 30));

        loadKhoData();

        khoComboBox.addActionListener(this);

        panel.add(label);
        panel.add(khoComboBox);
        toolbar.add(panel, BorderLayout.WEST);
        toolbar.add(createButtonToolbar(), BorderLayout.CENTER);

        return toolbar;
    }

    private JPanel createToolbarPanel(){
        JPanel toolbar = new JPanel(new GridLayout(1, 2));
        toolbar.add(createSearchToolBarNL());
        toolbar.add(createSearchToolbarNCC());

        return toolbar;
    }

    private JPanel createButtonToolbar(){
        JPanel toolbar1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        String[] btnNames = {"Thêm", "Xóa", "Sửa", "Xuất PDF"};
        String[] inconPaths = {"image/add.png","image/removeEmp.png","image/repairEmp.png","image/import_export.png"};

        List<String> btnNamesList = Arrays.asList(btnNames);


        for (String name : btnNames) {
            int index = btnNamesList.indexOf(name);
            JButton btn = createStyledButton(name);
            btn.setActionCommand(name);

            ImageIcon icon = new ImageIcon(new ImageIcon(inconPaths[index]).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btn.setIcon(icon);

            btn.addActionListener(new KhoController(this));
            toolbar1.add(btn);
        }
        toolbar1.setPreferredSize(new Dimension(700, 60));
        toolbar1.setBorder(BorderFactory.createEmptyBorder(2,0,0,50));
        return toolbar1;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(110, 30));
        button.setFocusPainted(false);
        button.setBorder(null);
        button.setBackground(new Color(10, 82, 116));
        button.setForeground(Color.WHITE);
        return button;
    }

    private JPanel createSearchToolBarNL(){
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        searchComboBoxNL = new JComboBox<>(new String[]{"Tìm Kiếm Theo Tên", "Tìm Kiếm Theo Mã"});
        searchTextFieldNL = new JTextField(10);
        searchButtonNL = createStyledButton("Tìm Kiếm");

        ImageIcon icon = new ImageIcon(new ImageIcon("image/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        searchButtonNL.setIcon(icon);

        toolbar.add(searchComboBoxNL);
        toolbar.add(searchTextFieldNL);
        toolbar.add(searchButtonNL);

        searchButtonNL.addActionListener(this);
        searchComboBoxNL.addActionListener(this);

        toolbar.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Thông Tin Nguyên liệu:"));
        toolbar.setPreferredSize(new Dimension(100,80));
        return toolbar;
    }

    public JPanel createSearchToolbarNCC(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        searchComboBoxNCC = new JComboBox<>(new String[]{"Tìm Kiếm Theo Tên", "Tìm Kiếm Theo Mã"});
        searchTextFieldNCC = new JTextField(10);
        searchButtonNCC = createStyledButton("Tìm Kiếm");

        ImageIcon icon = new ImageIcon(new ImageIcon("image/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        searchButtonNCC.setIcon(icon);

        panel.add(searchComboBoxNCC);
        panel.add(searchTextFieldNCC);
        panel.add(searchButtonNCC);

        searchButtonNCC.addActionListener(this);
        searchComboBoxNCC.addActionListener(this);

        panel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm Thông Tin Nhà Cung Cấp:"));
        panel.setPreferredSize(new Dimension(100,80));
        return panel;
    }

    private JPanel createKhoTable(){
        JPanel tablePanel = new JPanel();

        String[] columnNames = {"Mã Kho", "Mã Nguyên Liệu", "Tên Nguyên Liệu","Đơn Vị Tính", "Giá Nhập","Số Lượng", "Ngày Nhập", "Ngày Hết Hạn","Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp" };
        tableModel = new DefaultTableModel(columnNames,0);

        Kho_DAO kho_dao = new Kho_DAO();
        List<KhoViewTable> list = kho_dao.getAllKhoNguyenLieu();

        for (KhoViewTable k : list){
            tableModel.addRow(new Object[]{k.getMaKho(), k.getMaNguyenLieu(), k.getTenNguyenLieu(), k.getDonViTinh(), k.getGiaNhap() + " VNĐ",k.getSoLuong(),k.getNgayNhap(), k.getNgayHetHan(), k.getMaNhaCungCap(), k.getTenNhaCungCap()});
        }

        khoTable = new JTable(tableModel);
        khoTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(khoTable);
        khoTable.setPreferredScrollableViewportSize(new Dimension(850, 700));
        khoTable.setRowHeight(25);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        khoTable.addMouseListener(this);

        return tablePanel;
    }

    private JPanel createTableDetails(){
        JPanel detailPhanel = new JPanel();
        detailPhanel.setLayout(new BoxLayout(detailPhanel, BoxLayout.Y_AXIS));
        detailPhanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        detailPhanel.add(createInfoSection());
        detailPhanel.add(createNguyenLieuSection());
        detailPhanel.add(createNhaCungCapSection());

        return detailPhanel;
    }

    private JPanel createInfoSection(){
        JPanel panel = new JPanel(new GridLayout(3,2,1,5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Chi Tiết Kho:"));

        panel.add(new JLabel("Mã Kho:"));
        lblMaKho = new JLabel();
        panel.add(lblMaKho);

        panel.add(new JLabel("Tên Kho:"));
        lblTenKho = new JLabel();
        panel.add(lblTenKho);

        panel.add(new JLabel("Địa Chỉ Kho:"));
        lblDiaChiKho =  new JTextArea();
        lblDiaChiKho.setLineWrap(true);
        lblDiaChiKho.setWrapStyleWord(true);
        lblDiaChiKho.setEditable(false);
        lblDiaChiKho.setOpaque(false);
        lblDiaChiKho.setBorder(null);

        panel.add(lblDiaChiKho);


        return panel;
    }

    private JPanel createNguyenLieuSection(){
        JPanel panel = new JPanel(new GridLayout(7,2,1,5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Chi Tiết Nguyên Liệu:"));

        panel.add(new JLabel("Mã Nguyên Liệu:"));
        lblMaNguyenLieu = new JLabel();
        panel.add(lblMaNguyenLieu);

        panel.add(new JLabel("Tên Nguyên Liệu:"));
        lblTenNguyenLieu = new JLabel();
        panel.add(lblTenNguyenLieu);

        panel.add(new JLabel("Đơn Vị Tính:"));
        lblDonViTinh = new JLabel();
        panel.add(lblDonViTinh);

        panel.add(new JLabel("Giá Nhập:"));
        lblGiaNhap = new JLabel();
        panel.add(lblGiaNhap);

        panel.add(new JLabel("Số Lượng:"));
        lblSoLuong = new JLabel();
        panel.add(lblSoLuong);

        panel.add(new JLabel("Ngày Nhập:"));
        lblNgayNhap = new JLabel();
        panel.add(lblNgayNhap);

        panel.add(new JLabel("Ngày Hết Hạn:"));
        lblNgayHetHan = new JLabel();
        panel.add(lblNgayHetHan);

        return panel;
    }

    private JPanel createNhaCungCapSection(){
        JPanel panel = new JPanel(new GridLayout(4,2,1,5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Chi Tiết Nhà Cung Cấp:"));

        panel.add(new JLabel("Mã Nhà Cung Cấp:"));
        lblMaNCC = new JLabel();
        panel.add(lblMaNCC);

        panel.add(new JLabel("Tên Nhà Cung Cấp:"));
        lblTenNCC = new JLabel();
        panel.add(lblTenNCC);

        panel.add(new JLabel("Địa Chỉ Nhà Cung Cấp:"));
        lblDiaChiNCC = new JTextArea();
        lblDiaChiNCC.setLineWrap(true);
        lblDiaChiNCC.setWrapStyleWord(true);
        lblDiaChiNCC.setEditable(false);
        lblDiaChiNCC.setOpaque(false);
        lblDiaChiNCC.setBorder(null);

        panel.add(lblDiaChiNCC);

        panel.add(new JLabel("Số Điện Thoại:"));
        lblSoDienThoai = new JLabel();
        panel.add(lblSoDienThoai);

        return panel;
    }

    private void updateDetailPanels(int row){
        String maKho = tableModel.getValueAt(row, 0).toString();
        String maNguyenLieu = tableModel.getValueAt(row, 1).toString();
        String tenNguyenLieu = tableModel.getValueAt(row, 2).toString();
        String donViTinh = tableModel.getValueAt(row, 3).toString();
        String giaNhap = tableModel.getValueAt(row, 4).toString();
        String soLuong =  tableModel.getValueAt(row, 5).toString();
        String ngayNhap = tableModel.getValueAt(row, 6).toString();
        String ngayHetHan = tableModel.getValueAt(row, 7).toString();
        String maNhaCungCap = tableModel.getValueAt(row, 8).toString();
        String tenNhaCungCap =  tableModel.getValueAt(row, 9).toString();

        lblMaKho.setText(maKho);
        lblMaNguyenLieu.setText(maNguyenLieu);
        lblTenNguyenLieu.setText(tenNguyenLieu);
        lblDonViTinh.setText(donViTinh);
        lblGiaNhap.setText(giaNhap);
        lblSoLuong.setText(soLuong);
        lblNgayNhap.setText(ngayNhap);
        lblNgayHetHan.setText(ngayHetHan);
        lblMaNCC.setText(maNhaCungCap);
        lblTenNCC.setText(tenNhaCungCap);

        setLabelPlainFont(lblMaKho, lblMaNguyenLieu, lblTenNguyenLieu, lblDonViTinh, lblGiaNhap, lblSoLuong, lblNgayNhap, lblNgayHetHan, lblMaNCC, lblTenNCC, lblSoDienThoai, lblTenKho);

        Kho_DAO kho_dao = new Kho_DAO();
        List<KhoViewTable> listkho = kho_dao.getKhoChiTietByMaKho(maKho, maNguyenLieu);

        if(listkho != null && !listkho.isEmpty()){
            KhoViewTable kho = listkho.get(0);

            lblTenKho.setText(kho.getTenKho());
            lblDiaChiKho.setText(kho.getDiaChiKho());
            lblDiaChiNCC.setText(kho.getDiaChiNhaCungCap());
            lblSoDienThoai.setText(kho.getSoDienThoaiNhaCungCap());
        }
    }

    private void setLabelPlainFont(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(new Font(label.getFont().getName(), Font.PLAIN, label.getFont().getSize()));
        }
    }

    private void loadKhoData(){
        khoComboBox.removeAllItems();
        khoComboBox.addItem("Tổng Kho");

        Kho_DAO kho_dao = new Kho_DAO();

        List<String> khoList = kho_dao.getAllKho();

        for (String tenKho : khoList){
            khoComboBox.addItem(tenKho);
        }

        khoComboBox.setSelectedIndex(0);
    }

    private void showKhoTheoTenKho(String kho){
        kho_dao = new Kho_DAO();
        tableModel.setRowCount(0);

        if(kho.equals("Tổng Kho")){
            danhSach = kho_dao.getAllKhoNguyenLieu();
        }
        else{
            danhSach = kho_dao.getKhoTheoTenKho(kho);
        }

        for(KhoViewTable k : danhSach){
            tableModel.addRow(new Object[]{k.getMaKho(), k.getMaNguyenLieu(), k.getTenNguyenLieu(), k.getDonViTinh(), k.getGiaNhap() + " VNĐ",k.getSoLuong(),k.getNgayNhap(), k.getNgayHetHan(), k.getMaNhaCungCap(), k.getTenNhaCungCap()});
        }
    }

    public boolean validateInputKho(String maKho, String tenKho, String diaChiKho,
                                    String maNguyenLieu, String tenNguyenLieu, String donViTinh,
                                    double giaNhap, int soLuong, Date ngayNhap,
                                    Date ngayHetHan, String maNhaCungCap, String tenNhaCungCap,
                                    String diaChiNhaCungCap, String soDienThoaiNhaCungCap) {
        if (maKho.isEmpty() || tenKho.isEmpty() || diaChiKho.isEmpty() ||
                maNguyenLieu.isEmpty() || tenNguyenLieu.isEmpty() || donViTinh.isEmpty() ||
                maNhaCungCap.isEmpty() || tenNhaCungCap.isEmpty() || diaChiNhaCungCap.isEmpty() ||
                soDienThoaiNhaCungCap.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Mã kho: KHO + số
        if (!maKho.matches("^K\\d{3}$")) {
            JOptionPane.showMessageDialog(this, "Mã kho phải bắt đầu bằng 'K' và có 3 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Mã nguyên liệu: NL + số
        if (!maNguyenLieu.matches("^NL\\d{3}$")) {
            JOptionPane.showMessageDialog(this, "Mã nguyên liệu phải bắt đầu bằng 'NL' và có 3 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Mã nhà cung cấp: NCC + số
        if (!maNhaCungCap.matches("^NCC\\d{3}$")) {
            JOptionPane.showMessageDialog(this, "Mã nhà cung cấp phải bắt đầu bằng 'NCC' và có 3 chữ số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Giá nhập: phải dương
        if (giaNhap <= 0) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Số lượng: phải dương
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Ngày nhập và ngày hết hạn
        if (ngayNhap == null || ngayHetHan == null) {
            JOptionPane.showMessageDialog(this, "Ngày nhập và ngày hết hạn không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày nhập không được sau ngày hiện tại
        Date currentDate = new Date(System.currentTimeMillis());
        if (ngayNhap.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày nhập không được sau ngày hiện tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày nhập phải trước ngày hết hạn
        if (ngayNhap.after(ngayHetHan)) {
            JOptionPane.showMessageDialog(this, "Ngày nhập phải trước ngày hết hạn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày hết hạn không được trước ngày hiện tại
        if (ngayHetHan.before(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày hết hạn không được trước ngày hiện tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Số điện thoại: đúng định dạng
        if (!soDienThoaiNhaCungCap.matches("^(0)[0-9]{9,10}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhà cung cấp không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    public void showAllKho(){
        tableModel.setRowCount(0);
        danhSach = kho_dao.getAllKhoNguyenLieu();
        for(KhoViewTable kho : danhSach){
            tableModel.addRow(new Object[]{kho.getMaKho(), kho.getMaNguyenLieu(), kho.getTenNguyenLieu(), kho.getDonViTinh(), kho.getGiaNhap() + " VNĐ", kho.getSoLuong(), kho.getNgayNhap(), kho.getNgayHetHan(), kho.getMaNhaCungCap(), kho.getTenNhaCungCap()});
        }
    }

    public void themKho(KhoViewTable kho){
        if(kho == null){
            JOptionPane.showMessageDialog(this,"Vui lòng nhập đẩy đủ thông tin về kho, sản phẩm và nhà cung cấp!","Thông báo",JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(kho_dao.themKho(kho)){
            JOptionPane.showMessageDialog(this,"Thêm kho thành công!","Thông báo",JOptionPane.WARNING_MESSAGE);
            showAllKho();
        }
        else{
            JOptionPane.showMessageDialog(this,"Thêm kho thất bại!","Thông báo",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void xoaKho(){
        if(selectedKho != null){
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nguyên liệu ở kho này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                if(kho_dao.xoaKho(selectedKho.getMaKho())){
                    JOptionPane.showMessageDialog(this,"Xóa kho thành công!","Thông báo",JOptionPane.WARNING_MESSAGE);
                    showAllKho();
                }
                else{
                    JOptionPane.showMessageDialog(this,"Xóa kho thất bại!","Thông báo",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Vui lòng chọn kho để xóa!","Thông báo",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void suaKho(KhoViewTable kho){
        if(kho == null){
            JOptionPane.showMessageDialog(this,"Vui lòng nhập đẩy đủ thông tin về kho, sản phẩm và nhà cung cấp!","Thông báo",JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(kho_dao.suaKho(kho)){
            JOptionPane.showMessageDialog(this,"Sửa kho thành công!","Thông báo",JOptionPane.WARNING_MESSAGE);
            showAllKho();
        }
        else{
            JOptionPane.showMessageDialog(this,"Sửa kho thất bại!","Thông báo",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void dialogThemKho(){
        ThemKhoDialog dialog = new ThemKhoDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),"Thêm Kho",this);
        dialog.setVisible(true);
    }

    public void dialogSuaKho(KhoViewTable kho){
        int selectedRow = khoTable.getSelectedRow();
        if(selectedRow != -1) {
            String maKho = khoTable.getValueAt(selectedRow,0).toString();
            String maNguyenLieu = khoTable.getValueAt(selectedRow,1).toString();
            SuaKhoDialog dialog = new SuaKhoDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this), "Sửa Kho", this, maKho, maNguyenLieu);
            dialog.setVisible(true);
        }
    }

    public KhoViewTable getSelectedKho(){
        return selectedKho;
    }

    public void xuatPDF() {
        danhSach = kho_dao.getDsachKhoNguyenLieu();
        if (danhSach != null && !danhSach.isEmpty()) {
            com.itextpdf.text.Document document = new Document();
            String fileName = "DanhSachKhoNguyenLieuPDF/DanhSachKho.pdf";

            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                document.open();

                BaseFont font = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(font, 24, com.itextpdf.text.Font.BOLD);
                com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(font, 14, com.itextpdf.text.Font.BOLD);
                com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(font, 12);
                com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(font, 12);

                // Tiêu đề
                Paragraph title = new Paragraph("DANH SÁCH KHO NGUYÊN LIỆU\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Ngày xuất
                String ngayXuat = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
                Paragraph date = new Paragraph("Ngày xuất: " + ngayXuat + "\n\n", normalFont);
                date.setAlignment(Element.ALIGN_CENTER);
                document.add(date);

                // **Bảng Kho Nguyên Liệu**
                document.add(new Paragraph("BẢNG KHO NGUYÊN LIỆU", headerFont));
                PdfPTable tableKho = new PdfPTable(3);  // 3 cột cho bảng Kho
                tableKho.setWidthPercentage(100);
                tableKho.setSpacingBefore(10f);
                tableKho.setSpacingAfter(10f);
                String[] khoHeaders = {"Mã kho", "Tên kho", "Địa chỉ kho"};
                for (String header : khoHeaders) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableKho.addCell(headerCell);
                }

                // Sử dụng Set để lọc kho
                Set<String> printedKho = new HashSet<>();
                for (KhoViewTable kho : danhSach) {
                    if (!printedKho.contains(kho.getMaKho())) {
                        tableKho.addCell(new Phrase(kho.getMaKho(), cellFont));
                        tableKho.addCell(new Phrase(kho.getTenKho(), cellFont));
                        tableKho.addCell(new Phrase(kho.getDiaChiKho(), cellFont));
                        printedKho.add(kho.getMaKho());
                    }
                }
                document.add(tableKho);

                // **Bảng Nguyên Liệu**
                document.add(new Paragraph("BẢNG NGUYÊN LIỆU", headerFont));
                PdfPTable tableNguyenLieu = new PdfPTable(4);  // 4 cột cho bảng Nguyên Liệu
                tableNguyenLieu.setWidthPercentage(100);
                tableNguyenLieu.setSpacingBefore(10f);
                tableNguyenLieu.setSpacingAfter(10f);
                String[] nguyenLieuHeaders = {"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá nhập"};
                for (String header : nguyenLieuHeaders) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableNguyenLieu.addCell(headerCell);
                }

                // Sử dụng Set để lọc nguyên liệu
                Set<String> printedNguyenLieu = new HashSet<>();
                for (KhoViewTable kho : danhSach) {
                    if (!printedNguyenLieu.contains(kho.getMaNguyenLieu())) {
                        tableNguyenLieu.addCell(new Phrase(kho.getMaNguyenLieu(), cellFont));
                        tableNguyenLieu.addCell(new Phrase(kho.getTenNguyenLieu(), cellFont));
                        tableNguyenLieu.addCell(new Phrase(kho.getDonViTinh(), cellFont));
                        tableNguyenLieu.addCell(new Phrase(String.valueOf(kho.getGiaNhap() + " VNĐ"), cellFont));
                        printedNguyenLieu.add(kho.getMaNguyenLieu());
                    }
                }
                document.add(tableNguyenLieu);

                // **Bảng Nhà Cung Cấp**
                document.add(new Paragraph("BẢNG NHÀ CUNG CẤP", headerFont));
                PdfPTable tableNhaCungCap = new PdfPTable(4);  // 4 cột cho bảng Nhà Cung Cấp
                tableNhaCungCap.setWidthPercentage(100);
                tableNhaCungCap.setSpacingBefore(10f);
                tableNhaCungCap.setSpacingAfter(10f);
                String[] nhaCungCapHeaders = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ nhà cung cấp", "SĐT nhà cung cấp"};
                for (String header : nhaCungCapHeaders) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableNhaCungCap.addCell(headerCell);
                }

                // Sử dụng Set để lọc nhà cung cấp
                Set<String> printedNhaCungCap = new HashSet<>();
                for (KhoViewTable kho : danhSach) {
                    if (!printedNhaCungCap.contains(kho.getMaNhaCungCap())) {
                        tableNhaCungCap.addCell(new Phrase(kho.getMaNhaCungCap(), cellFont));
                        tableNhaCungCap.addCell(new Phrase(kho.getTenNhaCungCap(), cellFont));
                        tableNhaCungCap.addCell(new Phrase(kho.getDiaChiNhaCungCap(), cellFont));
                        tableNhaCungCap.addCell(new Phrase(kho.getSoDienThoaiNhaCungCap(), cellFont));
                        printedNhaCungCap.add(kho.getMaNhaCungCap());
                    }
                }
                document.add(tableNhaCungCap);

                JOptionPane.showMessageDialog(this, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xuất PDF thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                document.close();
                if (Desktop.isDesktopSupported()) {
                    try {
                        File pdfFile = new File(fileName);
                        Desktop.getDesktop().open(pdfFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Không thể mở file PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu kho để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public JTable getTable(){
        return this.khoTable;
    }

    private void timKiemNguyenLieu() {
        danhSach = kho_dao.getDsachKhoNguyenLieu();

        String luaChon = (String) searchComboBoxNL.getSelectedItem();  // Lựa chọn tìm kiếm theo mã hoặc tên nguyên liệu
        String tuKhoa = searchTextFieldNL.getText().trim();  // Từ khóa tìm kiếm

        if (tuKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<KhoViewTable> ketQuaTimKiem = new ArrayList<>();

        // Kiểm tra xem danh sách có chứa nguyên liệu không
        if (danhSach == null) {
            JOptionPane.showMessageDialog(this, "Danh sách nguyên liệu trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (KhoViewTable nl : danhSach) {  // danhSachNguyenLieu là danh sách nguyên liệu
            if (luaChon.equals("Tìm Kiếm Theo Mã")) {
                if (nl.getMaNguyenLieu().contains(tuKhoa)) {
                    ketQuaTimKiem.add(nl);
                }
            } else if (luaChon.equals("Tìm Kiếm Theo Tên")) {
                if (nl.getTenNguyenLieu().contains(tuKhoa)) {
                    ketQuaTimKiem.add(nl);
                }
            }
        }

        if (ketQuaTimKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nguyên liệu nào với từ khóa: " + tuKhoa, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

        tableModel.setRowCount(0);
        for (KhoViewTable nl : ketQuaTimKiem) {
            tableModel.addRow(new Object[] {
                    nl.getMaKho(),
                    nl.getMaNguyenLieu(), nl.getTenNguyenLieu(), nl.getDonViTinh(), nl.getGiaNhap() + " VNĐ",
                    nl.getSoLuong(), nl.getNgayNhap(), nl.getNgayHetHan(), nl.getMaNhaCungCap(), nl.getTenNhaCungCap()
            });
        }

        searchTextFieldNL.setText("");
    }



    private void timKiemNhaCungCap() {
        danhSach = kho_dao.getAllKhoNguyenLieu();
        String luaChon = (String) searchComboBoxNCC.getSelectedItem();
        String tuKhoa = searchTextFieldNCC.getText().trim();

        if (tuKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<KhoViewTable> ketQuaTimKiem = new ArrayList<>();

        if (danhSach == null || danhSach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách nhà cung cấp trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (KhoViewTable ncc : danhSach) {  // danhSachNhaCungCap là danh sách nhà cung cấp
            if (luaChon.equals("Tìm Kiếm Theo Mã")) {
                if (ncc.getMaNhaCungCap().contains(tuKhoa)) {
                    ketQuaTimKiem.add(ncc);
                }
            } else if (luaChon.equals("Tìm Kiếm Theo Tên")) {
                if (ncc.getTenNhaCungCap().contains(tuKhoa)) {
                    ketQuaTimKiem.add(ncc);
                }
            }
        }

        if (ketQuaTimKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp nào với từ khóa: " + tuKhoa, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

        tableModel.setRowCount(0);
        for (KhoViewTable ncc : ketQuaTimKiem) {
            tableModel.addRow(new Object[] {
                    ncc.getMaKho(), ncc.getMaNguyenLieu(), ncc.getTenNguyenLieu(), ncc.getDonViTinh(), ncc.getGiaNhap() + " VNĐ", ncc.getSoLuong(),ncc.getNgayNhap(),ncc.getNgayHetHan(), ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap()
            });
        }

        searchTextFieldNCC.setText("");
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

       if(o == khoComboBox){
           String selectedKho = khoComboBox.getSelectedItem().toString();
            showKhoTheoTenKho(selectedKho);
           return;
       }

       String command = e.getActionCommand();
       switch (command){
           case "Thêm":
               dialogThemKho();
               break;
           case "Xuất PDF":
               xuatPDF();
               break;
           default:
               break;
       }

       if(command.equals("Tìm Kiếm")){
           if(o == searchButtonNL){
               timKiemNguyenLieu();
           }
           else if(o == searchButtonNCC){
               timKiemNhaCungCap();
           }
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = khoTable.getSelectedRow();
        if(selectedRow >= 0){
            String maKho = khoTable.getValueAt(selectedRow,0).toString();
            String maNguyenLieu = khoTable.getValueAt(selectedRow,1).toString();
            String tenNguyenLieu = khoTable.getValueAt(selectedRow,2).toString();
            String donViTinh = khoTable.getValueAt(selectedRow,3).toString();
            double giaNhap = Double.parseDouble(khoTable.getValueAt(selectedRow,4).toString().replace(" VNĐ",""));
            int soLuong = Integer.parseInt(khoTable.getValueAt(selectedRow,5).toString());
            Date ngayNhap = Date.valueOf(khoTable.getValueAt(selectedRow,6).toString());
            Date ngayHetHan = Date.valueOf(khoTable.getValueAt(selectedRow,7).toString());
            String maNhaCungCap = khoTable.getValueAt(selectedRow,8).toString();
            String tenNhaCungCap = khoTable.getValueAt(selectedRow,9).toString();

            selectedKho = new KhoViewTable(maKho, maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, soLuong, ngayNhap, ngayHetHan, maNhaCungCap, tenNhaCungCap);
            updateDetailPanels(selectedRow);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
