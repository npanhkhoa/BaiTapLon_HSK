package view.Manager;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import controller.SanPhamController;
import dao.SanPham_Dao;
import entity.LoaiSanPham;
import entity.SanPham;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.Phaser;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SanPhamPanel extends JPanel implements ActionListener, MouseListener {
    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private JPanel productCardPanel;
    private JPanel lastSelectedCard =  null;
    private SanPham selectedSanPham = null;

    private SanPhamController sanPhamController;

    private JComboBox<String> loaiSPComboBox;
    private JComboBox<String> searchComboBox;
    private JTextField searchField;
    private JButton searchButton;

    private JTextField maSPField, tenSPField, soLuongField, donGiaField, hinhAnhField;

    private List<SanPham> danhSach;

    public SanPhamPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(createTextFieldPanel(), BorderLayout.CENTER);
        topPanel.add(createToolbarPanel(), BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

        productCardPanel = new JPanel();
        productCardPanel.setLayout(new GridLayout(0,6,20,20));
        JScrollPane scrollPane = new JScrollPane(productCardPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        showAllSanPham();
    }

    // Hàm tạo các combobox
    /**
     * Phương thức này tạo một panel chứa combobox để nhập thông tin sản phẩm.
     * @return JPanel chứa các thành phần nhập liệu.
     */
    private JPanel createTextFieldPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        TitledBorder titledBorder = BorderFactory.createTitledBorder( "Lọc các sản phẩm theo từng loại:");

        panel.setBorder(titledBorder);

        JLabel loaiSPLabel = new JLabel("Loại sản phẩm:");
        loaiSPComboBox = new JComboBox<>(new String[]{"Tất cả", "Coffee", "Nước Ngọt", "Sinh tố", "Trà", "Bánh Ngọt", "Đồ Ăn Vặt", "Thuốc Lá"});
        loaiSPComboBox.setPreferredSize(new Dimension(250, 30));

        loaiSPComboBox.addActionListener(this);

        panel.add(loaiSPLabel);
        panel.add(loaiSPComboBox);

        return panel;
    }

    // Hàm tạo thanh công cụ chức năng và tìm kiếm
    /**
     * Phương thức này tạo một thanh công cụ với các nút chức năng và tìm kiếm.
     * @return JPanel chứa thanh công cụ.
     */
    private JPanel createToolbarPanel() {
        JPanel toolbar = new JPanel(new GridLayout(1, 2));
        toolbar.add(createFunctionToolbar());
        toolbar.add(createSearchToolbar());
        return toolbar;
    }

    // Hàm tạo phần các nút chức năng: Thêm, Xóa, Sửa, Xem chi tiết, Xuất PDF
    /**
     * Phương thức này tạo một thanh công cụ với các nút chức năng như Thêm, Xóa, Sửa, Xem chi tiết và Xuất PDF.
     * @return JPanel chứa các nút chức năng.
     */
    private JPanel createFunctionToolbar() {
        JPanel toolbar1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        String[] btnNames = {"Thêm", "Xóa", "Sửa", "Xem chi tiết", "Xuất PDF"};
        String[] inconPaths = {"image/add.png","image/removeEmp.png","image/repairEmp.png","image/list.png","image/import_export.png"};

        List<String> btnNamesList = Arrays.asList(btnNames);

        for (String name : btnNames) {
            int index = btnNamesList.indexOf(name);
            JButton btn = createStyledButton(name);
            btn.setActionCommand(name);

            ImageIcon icon = new ImageIcon(new ImageIcon(inconPaths[index]).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btn.setIcon(icon);

            btn.addActionListener(new SanPhamController(this));
            toolbar1.add(btn);
        }

        toolbar1.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        toolbar1.setPreferredSize(new Dimension(700, 60));
        return toolbar1;
    }

    // Hàm tạo phần tìm kiếm: ComboBox, TextField và Button
    /**
     * Phương thức này tạo một thanh công cụ tìm kiếm với các thành phần như JComboBox, JTextField và JButton.
     * @return JPanel chứa các thành phần tìm kiếm.
     */
    private JPanel createSearchToolbar() {
        JPanel toolbar2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        searchComboBox = new JComboBox<>(new String[]{"Tìm kiếm theo mã", "Tìm kiếm theo tên"});
        searchField = new JTextField(20);
        searchButton = createStyledButton("Tìm kiếm");
        searchButton.setPreferredSize(new Dimension(100, 30));

        ImageIcon icon = new ImageIcon(new ImageIcon("image/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        searchButton.setIcon(icon);

        ImageIcon logo = new ImageIcon(new ImageIcon("image/Logo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(logo);

        toolbar2.add(label);
        toolbar2.add(searchComboBox);
        toolbar2.add(searchField);
        toolbar2.add(searchButton);
        searchButton.addActionListener(this);
        searchComboBox.addActionListener(this);


        toolbar2.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        toolbar2.setPreferredSize(new Dimension(500, 60));
        return toolbar2;
    }

    /**
     * Phương thức này tạo một JPanel chứa các khung có hình ảnh và tên, giá của sản phẩm.
     * @return JPanel chứa các thẻ.
     */
    private JPanel createProductCard(SanPham sp){
        JPanel cardWrapper = new JPanel();
        cardWrapper.setPreferredSize(new Dimension(170,220));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(170, 200));
        card.setBorder(BorderFactory.createLineBorder(Color.green));
        card.setBackground(Color.white);

        ImageIcon icon;
        String path = "imgSanPham/" + sp.getHinhAnh();
        File file = new File(path);
        if(file.exists()){
            icon = new ImageIcon(path);
        }else{
            icon = new ImageIcon("image/logo_Wind'sCoffee.png");
        }

        //Ảnh các sản phẩm
        Image scaledImage = icon.getImage().getScaledInstance(150,140,Image.SCALE_SMOOTH);
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

    private void showAllSanPham(){
        productCardPanel.removeAll();;
        danhSach = sanPhamDao.getDsachSanPham();
        for(SanPham sp : danhSach){
            productCardPanel.add(createProductCard(sp));
        }
        productCardPanel.revalidate();
        productCardPanel.repaint();
    }

    private void showSanPhamTheoLoai(String loai){
        productCardPanel.removeAll();

        if(loai.equals("Tất cả")){
            danhSach = sanPhamDao.getDsachSanPham();
        }
        else{
            danhSach = sanPhamDao.getSanPhamTheoLoaiSanPham(loai);
        }

        for(SanPham sp : danhSach){
            productCardPanel.add(createProductCard(sp));
        }

        productCardPanel.revalidate();
        productCardPanel.repaint();
    }



    // Hàm tạo nút với kiểu dáng chuẩn
    /**
     * Phương thức này tạo một nút JButton với kiểu dáng chuẩn.
     * @param text Văn bản hiển thị trên nút.
     * @return Nút JButton đã được định dạng.
     */
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

    /**
     * Phương thức này kiểm tra tính hợp lệ của các thông tin sản phẩm trước khi thêm vào bảng.
     * Nếu thông tin hợp lệ, nó sẽ thêm sản phẩm vào bảng và làm mới bảng.
     * @param maSP
     * @param tenSP
     * @param maloaiSP
     * @param soLuong
     * @param donGia
     * @return true nếu thông tin hợp lệ và sản phẩm được thêm thành công, false nếu không.
     */
    public boolean validateInput(String maSP, String tenSP, String donGia, String soLuong,LoaiSanPham maloaiSP, String hinhAnh) {
        if (maSP.isEmpty() || tenSP.isEmpty() || maloaiSP.getMaLoaiSanPham().isEmpty() || soLuong.isEmpty() || donGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(!soLuong.matches("\\d+") ){
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số nguyên dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!donGia.matches("\\d+(\\.\\d+)?")){
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số thực dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if(!maloaiSP.getMaLoaiSanPham().matches("^LSP\\d{2,}")) {
            JOptionPane.showMessageDialog(this, "Mã loại sản phẩm phải bắt đầu bằng 'LSP' và có ít nhất 2 ký tự số sau đó.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(!hinhAnh.matches("^[a-zA-Z0-9_ ]+\\.(jpg)$")) {
            JOptionPane.showMessageDialog(this, "Tên hình ảnh không hợp lệ. Vui lòng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void themSanPham(SanPham sp){
        if(sp == null){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(sanPhamDao.themSanPham(sp)){
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            showAllSanPham();
        }
        else{
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void xoaSanPham(){
        if(selectedSanPham != null){
            int confirm = JOptionPane.showConfirmDialog(this,"bạn có chắc chắn muốn xóa sản phẩm này?","Xác nhận",JOptionPane.YES_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                if(sanPhamDao.xoaSanPham(selectedSanPham.getMaSanPham())){
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    showAllSanPham();
                    selectedSanPham = null;
                }
                else{
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void suaSanPham(SanPham sp) {
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (sanPhamDao.suaSanPham(sp)) {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            showAllSanPham();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void xemChiTiet(){
        if(selectedSanPham != null){
            JPanel detailPanel = new JPanel(new BorderLayout(10,10));

            String path = "imgSanPham/" + selectedSanPham.getHinhAnh();
            File file = new File(path);
            ImageIcon icon;
            if(file.exists()) {
                icon = new ImageIcon(path);
            }
            else{
                icon = new ImageIcon("image/logo_Wind'sCoffee.png");
            }

            Image scaledImage = icon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
            JLabel imageLbl = new JLabel(new ImageIcon(scaledImage));

            JTextPane info = new JTextPane();
            info.setEditable(false);
            info.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            info.setBackground(new Color(240, 240, 240));

            StyledDocument doc = info.getStyledDocument();

            SimpleAttributeSet labelAttr = new SimpleAttributeSet();
            StyleConstants.setForeground(labelAttr, new Color(26, 102, 227));
            StyleConstants.setBold(labelAttr, true);

            SimpleAttributeSet dataAttr = new SimpleAttributeSet();
            StyleConstants.setForeground(dataAttr, Color.BLACK);
            StyleConstants.setBold(dataAttr, false);

            try {
                doc.insertString(doc.getLength(), "Mã sản phẩm: ", labelAttr);
                doc.insertString(doc.getLength(), selectedSanPham.getMaSanPham() + "\n", dataAttr);

                doc.insertString(doc.getLength(), "Tên sản phẩm: ", labelAttr);
                doc.insertString(doc.getLength(), selectedSanPham.getTenSanPham() + "\n", dataAttr);

                doc.insertString(doc.getLength(), "Số lượng: ", labelAttr);
                doc.insertString(doc.getLength(), selectedSanPham.getSoLuong() + "\n", dataAttr);

                doc.insertString(doc.getLength(), "Đơn giá: ", labelAttr);
                doc.insertString(doc.getLength(), selectedSanPham.getGiaBan() + " NVĐ\n", dataAttr);

                doc.insertString(doc.getLength(), "Loại sản phẩm: ", labelAttr);
                doc.insertString(doc.getLength(), selectedSanPham.getLoaiSanPham().getTenLoaiSanPham() + "\n", dataAttr);

                doc.insertString(doc.getLength(), "Hình ảnh: ", labelAttr);
                doc.insertString(doc.getLength(), selectedSanPham.getHinhAnh(), dataAttr);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.add(info, BorderLayout.CENTER);

            detailPanel.add(imageLbl, BorderLayout.WEST);
            detailPanel.add(infoPanel, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this, detailPanel, "Thông tin sản phẩm", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void xuatPDF() {
        if(danhSach != null && !danhSach.isEmpty()) {
            Document document = new Document();
            String fileName = "DanhSachSanPhamPDF/DanhSachSanPham.pdf";
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                document.open();

                BaseFont font = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(font, 24, com.itextpdf.text.Font.BOLD);
                com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(font, 14, com.itextpdf.text.Font.BOLD);
                com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(font, 12);
                com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(font, 12);

                Paragraph title = new Paragraph("DANH SÁCH SẢN PHẨM\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                String ngayXuat = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                Paragraph date = new Paragraph("Ngày xuất: " + ngayXuat + "\n\n", normalFont);
                date.setAlignment(Element.ALIGN_CENTER);
                document.add(date);

                Map<String, List<SanPham>> sanPhamTheoLoai = new TreeMap<>();
                for (SanPham sp : danhSach) {
                    String loai = sp.getLoaiSanPham().getTenLoaiSanPham();
                    if (!sanPhamTheoLoai.containsKey(loai)) {
                        sanPhamTheoLoai.put(loai, new ArrayList<>());
                    }
                    sanPhamTheoLoai.get(loai).add(sp);
                }

                int tongSoLuong = 0;

                for (Map.Entry<String, List<SanPham>> entry : sanPhamTheoLoai.entrySet()) {
                    String tenLoai = entry.getKey();
                    List<SanPham> sanPhamList = entry.getValue();

                    Paragraph loaiParagraph = new Paragraph(tenLoai + "\n\n", headerFont);
                    loaiParagraph.setAlignment(Element.ALIGN_LEFT);
                    document.add(loaiParagraph);

                    PdfPTable table = new PdfPTable(6);
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10f);
                    table.setSpacingAfter(10f);

                    String[] headers = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Loại Sản Phẩm", "Hình Ảnh"};
                    for (String header : headers) {
                        PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(headerCell);
                    }

                    for (SanPham sp : sanPhamList) {
                        table.addCell(new Phrase(sp.getMaSanPham(), cellFont));
                        table.addCell(new Phrase(sp.getTenSanPham(), cellFont));
                        table.addCell(new Phrase(String.valueOf(sp.getSoLuong()), cellFont));
                        table.addCell(new Phrase(String.valueOf(sp.getGiaBan()) + " NVĐ", cellFont));
                        table.addCell(new Phrase(sp.getLoaiSanPham().getTenLoaiSanPham(), cellFont));
                        table.addCell(new Phrase(sp.getHinhAnh(), cellFont));

                        tongSoLuong += sp.getSoLuong();
                    }
                    document.add(table);
                }
                Paragraph tongSoLuongPara = new Paragraph("\nTổng số lượng sản phẩm trong kho:" + tongSoLuong, normalFont);
                tongSoLuongPara.setAlignment(Element.ALIGN_LEFT);
                document.add(tongSoLuongPara);

                JOptionPane.showMessageDialog(this, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xuất PDF thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            finally {
                document.close();

                if(Desktop.isDesktopSupported()){
                    try{
                        File pdfFile = new File(fileName);
                        Desktop.getDesktop().open(pdfFile);
                    }catch (IOException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Không thể mở file PDF!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void highlightSelectedCard(JPanel card){
        if(lastSelectedCard != null){
            lastSelectedCard.setBorder(BorderFactory.createLineBorder(Color.green));
        }
        card.setBorder(BorderFactory.createLineBorder(Color.cyan, 2));
        lastSelectedCard = card;
    }

    public void dialogThemSanPham(){
        ThemSanPhamDialog dialogThem = new ThemSanPhamDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),"Thêm Sản Phẩm", this);
        dialogThem.setVisible(true);
    }

    public void dialogSuaSanPham(SanPham sanPham) {
        SuaSanPhamDialog dialogSua = new SuaSanPhamDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), "Sửa Sản Phẩm", this);
        dialogSua.setVisible(true);
    }

    private void timKiemSanPham(){
        String luaChon = (String) searchComboBox.getSelectedItem();
        String tuKhoa = searchField.getText().trim().toLowerCase();

        if(tuKhoa.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<SanPham> ketQuaTimKiem = new ArrayList<>();

        for(SanPham sp : danhSach){
            if(luaChon.equals("Tìm kiếm theo mã")){
                if(sp.getMaSanPham().toLowerCase().contains(tuKhoa)){
                    ketQuaTimKiem.add(sp);
                }
            }else if(luaChon.equals("Tìm kiếm theo tên")){
                if(sp.getTenSanPham().toLowerCase().contains(tuKhoa)){
                    ketQuaTimKiem.add(sp);
                }
            }
        }

        if(ketQuaTimKiem.isEmpty()){
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào với từ khóa: " + tuKhoa, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

        productCardPanel.removeAll();
        for(SanPham sp : ketQuaTimKiem){
            productCardPanel.add(createProductCard(sp));
        }
        productCardPanel.revalidate();
        productCardPanel.repaint();

        searchField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if(o == loaiSPComboBox){
            String seletedLoai = (String) loaiSPComboBox.getSelectedItem();
            showSanPhamTheoLoai(seletedLoai);
            return;
        }
        String command = e.getActionCommand();
        switch (command){
            case "Thêm":
                dialogThemSanPham();
                break;
            case "Xem chi tiết":
                xemChiTiet();
                break;
            case "Xuất PDF":
                xuatPDF();
                break;
            default:
                break;
        }

        if(command.equals("Tìm kiếm")){
            timKiemSanPham();
        }
    }

    public SanPham getSelectedSanPham() {
        return selectedSanPham;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        JPanel cardWrapper = (JPanel) e.getSource();
        SanPham sp = (SanPham) cardWrapper.getClientProperty("sanPham");

        if (sp != null){
            selectedSanPham = sp;
            highlightSelectedCard(cardWrapper);
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
