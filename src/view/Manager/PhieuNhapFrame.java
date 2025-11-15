package view.Manager;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.toedter.calendar.JDateChooser;
import dao.PhieuNhap_Dao;
import entity.KhoNguyenLieu;
import entity.NguyenLieu;
import entity.NhaCungCap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PhieuNhapFrame extends JPanel implements ActionListener, MouseListener {
    private JTextField txtTim;
    private JTable tbl;
    private DefaultTableModel tblModel;
    private JDateChooser dateNhapTim, dateHetHanTim;
    private JButton btnThem, btnXoa, btnSua, btnTim, btnXuatPDF;
    private PhieuNhap_Dao phieuNhapDao = new PhieuNhap_Dao();

    public PhieuNhapFrame() {
        setLayout(new BorderLayout());
        add(createTextFieldPanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);

        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnTim.addActionListener(this);
        btnXuatPDF.addActionListener(this);
        tbl.addMouseListener(this);

        loadDataToTable();
    }

    private JPanel createTextFieldPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnThem = createRoundButton("Thêm", "image/addEmp.png");
        btnXoa = createRoundButton("Xóa", "image/removeEmp.png");
        btnSua = createRoundButton("Sửa", "image/repairEmp.png");
        btnXuatPDF = createRoundButton("Xuất PDF", "image/PDF.png");

        Dimension buttonSize = new Dimension(120, 40);
        btnThem.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXuatPDF.setPreferredSize(buttonSize);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXuatPDF);

        mainPanel.add(buttonPanel);
        mainPanel.add(createSearchPanel());

        return mainPanel;
    }

    private JScrollPane createTableScrollPane() {
        String[] cols = {"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá nhập", "Ngày nhập", "Ngày hết hạn", "Mã kho", "Tên Kho", "Địa chỉ", "Số lượng"};
        tblModel = new DefaultTableModel(cols, 0);
        tbl = new JTable(tblModel);
        return new JScrollPane(tbl);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        searchPanel.add(new JLabel("Ngày nhập:"));
        dateNhapTim = new JDateChooser();
        dateNhapTim.setDateFormatString("dd/MM/yyyy");
        searchPanel.add(dateNhapTim);

        searchPanel.add(new JLabel("Ngày hết hạn:"));
        dateHetHanTim = new JDateChooser();
        dateHetHanTim.setDateFormatString("dd/MM/yyyy");
        searchPanel.add(dateHetHanTim);

        searchPanel.add(new JLabel("Tên nguyên liệu:"));
        txtTim = new JTextField();
        txtTim.setPreferredSize(new Dimension(200, 25));

        btnTim = createRoundButton("Tìm", "image/search.png");
        btnTim.setPreferredSize(new Dimension(100, 40));

        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(txtTim);
        fieldPanel.add(btnTim);

        searchPanel.add(fieldPanel);

        return searchPanel;
    }

    private JButton createRoundButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setBackground(new Color(10, 82, 116));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);

        List<NguyenLieu> dsNguyenLieu = new PhieuNhap_Dao().printAllNguyenLieu();

        for (NguyenLieu nl : dsNguyenLieu) {
            // Xử lý khi kho là null
            String maKho = (nl.getKhoNguyenLieu() != null) ? nl.getKhoNguyenLieu().getMaKho() : "Không có kho";
            String tenKho = (nl.getKhoNguyenLieu() != null) ? nl.getKhoNguyenLieu().getTenKho() : "";

            model.addRow(new Object[]{
                    nl.getMaNguyenLieu(),
                    nl.getTenNguyenLieu(),
                    nl.getDonViTinh(),
                    nl.getGiaNhap(),
                    nl.getNgayNhap(),
                    nl.getNgayHetHan(),
                    maKho,
                    tenKho,
                    nl.getKhoNguyenLieu() != null ? nl.getKhoNguyenLieu().getDiaChi() : "Không có địa chỉ",
                    nl.getSoLuong(),
            });
        }
    }


    private void timNguyenLieu() {
        java.util.Date ngayNhap = dateNhapTim.getDate();
        java.util.Date ngayHetHan = dateHetHanTim.getDate();
        String tenNguyenLieu = txtTim.getText().trim();

        if (ngayNhap == null && ngayHetHan == null && tenNguyenLieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một tiêu chí tìm kiếm!");
            return;
        }

        java.sql.Date sqlNgayNhap = (ngayNhap != null) ? new java.sql.Date(ngayNhap.getTime()) : null;
        java.sql.Date sqlNgayHetHan = (ngayHetHan != null) ? new java.sql.Date(ngayHetHan.getTime()) : null;

        List<NguyenLieu> result = phieuNhapDao.timKiem(sqlNgayNhap, sqlNgayHetHan, tenNguyenLieu);

        tblModel.setRowCount(0);
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nguyên liệu nào!");
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (NguyenLieu nl : result) {
            KhoNguyenLieu kho = nl.getKhoNguyenLieu();

            String ngayNhapStr = (nl.getNgayNhap() != null) ? nl.getNgayNhap().format(dtf) : "";
            String ngayHetHanStr = (nl.getNgayHetHan() != null) ? nl.getNgayHetHan().format(dtf) : "";

            tblModel.addRow(new Object[]{
                    nl.getMaNguyenLieu(), nl.getTenNguyenLieu(), nl.getDonViTinh(), nl.getGiaNhap(),
                    ngayNhapStr, ngayHetHanStr,
                    kho != null ? kho.getMaKho() : "",
                    kho != null ? kho.getTenKho() : "",
                    kho != null ? kho.getDiaChi() : "",
                    nl.getSoLuong()
            });
        }
    }



    private void xuatPDF() {
        //cho nhỏ lại một chúc xíu
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        try {
            String filePath = "PhieuNhapPDF/PhieuNhap.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 20, Font.BOLD);
            Font cellFont = new Font(baseFont, 10);

            Paragraph title = new Paragraph("Phiếu Nhập Nguyên Liệu", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nNgày lập: " + new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()), cellFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setWidths(new float[]{7, 5, 4, 4, 5, 5, 4, 4, 5, 4});
            Font headerFont = new Font(baseFont, 10, Font.BOLD);

            String[] headers = {
                    "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá nhập", "Ngày nhập", "Ngày hết hạn",
                    "Mã kho", "Tên kho", "Địa chỉ kho", "Số lượng"
            };

            for (String header : headers) {
                table.addCell(new PdfPCell(new Phrase(header, headerFont)));
            }

            for (int i = 0; i < tblModel.getRowCount(); i++) {
                for (int j = 0; j < tblModel.getColumnCount(); j++) {
                    Object cellValue = tblModel.getValueAt(i, j);
                    String cellText = (cellValue != null) ? cellValue.toString() : ""; // Handle null values
                    table.addCell(new PdfPCell(new Phrase(cellText, cellFont)));
                }
            }

            document.add(table);
            document.close();


            //Mở tệp PDF sau khi suất
            File file = new File(filePath);
            if(Desktop.isDesktopSupported()){
                Desktop.getDesktop().open(file);
            }
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công! Đã lưu tại: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void themNguyenLieuDialog() {
        new ThemNguyenLieuDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm nguyên liệu", this).setVisible(true);
    }

    private void suaNguyenLieuDialog() {
        int row = tbl.getSelectedRow();
        if(row != -1){
            String maNguyenLieu = tblModel.getValueAt(row, 0).toString();
            String maKho = tblModel.getValueAt(row, 6).toString();

            NguyenLieu nl = phieuNhapDao.timByManguyenLieuVaMaKho(maNguyenLieu, maKho);
            if(nl == null){
                JOptionPane.showMessageDialog(this, "Nguyên liệu không tồn tại trong kho này");
                return;
            }
            SuaNguyenLieuDialog suaDialog = new SuaNguyenLieuDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Sửa Nguyên Liệu", this, maNguyenLieu, maKho);
            suaDialog.setData(nl);
            suaDialog.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu để sửa.");
        }

    }

    public void themNguyenLieu(NguyenLieu nl) {
        if (nl == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin nguyên liệu");
            return;
        }
        if (phieuNhapDao.addNguyenLieu1(nl)) {
            JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm nguyên liệu thất bại!");
        }
    }

    public void suaNguyenLieu(NguyenLieu nl) {
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu để sửa.");
            return;
        }
        if (phieuNhapDao.suaNguyenLieu(nl)) {
            JOptionPane.showMessageDialog(this, "Cập nhật nguyên liệu thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật nguyên liệu thất bại!");
        }
    }

    private void xoaNguyenLieu() {
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nguyên liệu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maNguyenLieu = tblModel.getValueAt(row, 0).toString();
            if (phieuNhapDao.deleteNguyenLieu(maNguyenLieu)) {
                JOptionPane.showMessageDialog(this, "Xóa nguyên liệu thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nguyên liệu thất bại!");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnThem) themNguyenLieuDialog();
        else if (src == btnXoa) xoaNguyenLieu();
        else if (src == btnSua) suaNguyenLieuDialog();
        else if (src == btnTim) timNguyenLieu();
        else if (src == btnXuatPDF) xuatPDF();
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}