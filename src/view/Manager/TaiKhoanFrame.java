package view.Manager;

import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaiKhoanFrame extends JPanel implements ActionListener, MouseListener {


    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtDC;
    private JTextField txtSDT;
    private JTextField txtTuoi;
    private JTextField txtTDN;
    private JTextField txtPw;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnTim;
    private JTable tbl;
    private DefaultTableModel tblModel;
    TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_DAO();
    private JComboBox<String> cmbQuyen;
    private JTextField txtTim;


    public TaiKhoanFrame() {
        setLayout(new BorderLayout());
        add(createTextFieldPanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);


        loadDataToTable();
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        tbl.addMouseListener(this);
        cmbQuyen.addActionListener(this);
        btnTim.addActionListener(this);


    }

    private JPanel createTextFieldPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        // Cột 1 (Trái)
        mainPanel.add(createLeftPanel());

        // Cột 2 (Giữa)
        mainPanel.add(createCenterPanel());

        // Cột 3 (Phải)
        mainPanel.add(createRightPanel());

        return mainPanel;
    }

    private JScrollPane createTableScrollPane() {
        String[] cols = {"Mã nhân viên", "Tên nhân viên", "Tuổi", "Địa chỉ", "Số điện thoại", "Tên đăng nhập", "Password"};
        tblModel = new DefaultTableModel(cols, 0);
        tbl = new JTable(tblModel);
        JScrollPane scroll = new JScrollPane(tbl);

        return new JScrollPane(tbl);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.weightx = 0.2;
        gbc.weighty = 1.0;


        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mã nhân viên:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtMa = new JTextField();
        txtMa.setPreferredSize(new Dimension(200, 25));
        panel.add(txtMa, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Tên nhân viên:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtTen = new JTextField();
        txtTen.setPreferredSize(new Dimension(200, 25));
        panel.add(txtTen, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Địa chỉ:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtDC = new JTextField();
        txtDC.setPreferredSize(new Dimension(200, 25));
        panel.add(txtDC, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Quyền:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        String[] quyenOptions = {"Quản lý", "Nhân viên"};
        cmbQuyen = new JComboBox<>(quyenOptions);
        panel.add(cmbQuyen, gbc);

        return panel;
    }


    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Số điện thoại:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtSDT = new JTextField();
        txtSDT.setPreferredSize(new Dimension(200, 25));
        panel.add(txtSDT, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Tuổi:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtTuoi = new JTextField();
        txtTuoi.setPreferredSize(new Dimension(200, 25));
        panel.add(txtTuoi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtTDN = new JTextField();
        txtTDN.setPreferredSize(new Dimension(200, 25));
        panel.add(txtTDN, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtPw = new JPasswordField();
        txtPw.setPreferredSize(new Dimension(200, 25));
        panel.add(txtPw, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm bằng tên đăng nhập: "));

        txtTim = new JTextField();
        txtTim.setPreferredSize(new Dimension(200, 25));

        Dimension buttonSize = new Dimension(100, 40);
        btnTim = createRoundButton("Tìm", "image/search.png");
        btnTim.setPreferredSize(buttonSize);

        ImageIcon originalIcon = new ImageIcon("image/search.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnTim.setIcon(new ImageIcon(scaledImage));

        searchPanel.add(txtTim);
        searchPanel.add(btnTim);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        btnThem = createRoundButton("Thêm", "image/addEmp.png");
        btnXoa = createRoundButton("Xóa", "image/removeEmp.png");
        btnSua = createRoundButton("Sửa", "image/repairEmp.png");

        btnThem.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);

        mainPanel.add(searchPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private JButton createRoundButton (String text, String iconPath){
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setBackground(new Color(10, 82, 116));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            themTaiKhoan();
        } else if (o.equals(btnXoa)) {
            xoaTaiKhoan();
        } else if (o.equals(btnSua)) {
            suaTaiKhoan();
        } else if (o.equals(btnTim)) {
            timTaiKhoan();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tbl.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tbl.getValueAt(row, 0).toString());
            txtTen.setText(tbl.getValueAt(row, 1).toString());
            txtTuoi.setText(tbl.getValueAt(row, 2).toString());
            txtDC.setText(tbl.getValueAt(row, 3).toString());
            txtSDT.setText(tbl.getValueAt(row, 4).toString());
            txtTDN.setText(tbl.getValueAt(row, 5).toString());
            txtPw.setText(tbl.getValueAt(row, 6).toString());
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


    private void clearTextFields() {
        txtMa.setText("");
        txtTen.setText("");
        txtDC.setText("");
        txtSDT.setText("");
        txtTuoi.setText("");
        txtTDN.setText("");
        txtPw.setText("");
    }

    private void loadDataToTable(){
        tblModel.setRowCount(0);
        for (NhanVien nv : taiKhoan_DAO.getAllThongTinTaiKhoan()) {
            String[] rowData = {
                    nv.getMaNhanVien(),
                    nv.getTenNhanVien(),
                    String.valueOf(nv.getTuoi()),
                    nv.getDiaChi(),
                    nv.getSoDienThoai(),
                    nv.getTaiKhoan().getTenDangNhap(),
                    nv.getTaiKhoan().getMatKhau()
            };
            tblModel.addRow(rowData);
        }
    }

    private void themTaiKhoan(){
        try{
            NhanVien nv = layThongTinTuFiels();

            boolean quyen = cmbQuyen.getSelectedItem().equals("Quản lý");
            nv.getTaiKhoan().setQuyenHan(quyen);
            if(taiKhoan_DAO.addTaiKhoan(nv)){
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công");
                loadDataToTable();
                clearTextFields();
            }else{
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại");
            e.printStackTrace();
        }
    }

    private NhanVien layThongTinTuFiels(){
        try {
            // Lấy dữ liệu từ form
            String maNV = txtMa.getText().trim();
            String tenNV = txtTen.getText().trim();
            String tuoi = txtTuoi.getText().trim();
            String sdt = txtSDT.getText().trim();
            String username = txtTDN.getText().trim();
            String password = txtPw.getText().trim();


            // Validate dữ liệu
            if(!(maNV.matches("NV\\d{1,5}"))) {
                throw new IllegalArgumentException("Mã NV phải có dạng NV + là kí tự số (VD: NV1)");
            }

            if (!(tenNV.matches("^[a-zA-Z\\s]{2,50}$"))) {
                throw new IllegalArgumentException("Tên NV chỉ chứa chữ cái và dấu cách (2-50 ký tự)");
            }

            if (!(tuoi.matches("^(1[8-9]|[2-5][0-9]|6[0-5])$"))) {
                throw new IllegalArgumentException("Tuổi phải từ 18-65");
            }

            if (!(sdt.matches("^[0]\\d{9}$"))) {
                throw new IllegalArgumentException("Số điện thoại phải có 10 số, bắt đầu bằng 0");
            }

            if (!(username.matches("^[a-zA-Z0-9_]{6,20}$"))) {
                throw new IllegalArgumentException("Username 6-20 ký tự (chữ, số, _)");
            }

            if (!(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$"))) {
                throw new IllegalArgumentException("Password 8-20 ký tự, có ít nhất 1 chữ hoa, 1 chữ thường, 1 số");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return new NhanVien(
                txtMa.getText().trim(),
                txtTen.getText().trim(),
                Integer.parseInt(txtTuoi.getText().trim()),
                txtDC.getText().trim(),
                txtSDT.getText().trim(),
                new TaiKhoan(txtTDN.getText().trim(), txtPw.getText().trim())
        );
    }

    private void xoaTaiKhoan() {
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa");
            return;
        }
        String tenDN = tbl.getValueAt(row, 5).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (taiKhoan_DAO.deleteTaiKhoan(tenDN)) {
                tblModel.removeRow(row);
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công");
                loadDataToTable();
                clearTextFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại");
            }
        }
    }

    private void suaTaiKhoan(){
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để sửa");
            return;
        }
        String maNV = tbl.getValueAt(row, 0).toString();
        int cofirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        NhanVien nv = layThongTinTuFiels();
        nv.setMaNhanVien(maNV);
        if(cofirm == JOptionPane.YES_OPTION) {
            if (taiKhoan_DAO.updateTaiKhoan(nv)) {
                updateTable();
                loadDataToTable();
                clearTextFields();
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thất bại");
            }
        }
    }

    //cap nhat du lieu moi sua vao bang
    private void updateTable() {
        int row = tbl.getSelectedRow();
        if (row != -1) {
            tblModel.setValueAt(txtMa.getText(), row, 0);
            tblModel.setValueAt(txtTen.getText(), row, 1);
            tblModel.setValueAt(txtTuoi.getText(), row, 2);
            tblModel.setValueAt(txtDC.getText(), row, 3);
            tblModel.setValueAt(txtSDT.getText(), row, 4);
            tblModel.setValueAt(txtTDN.getText(), row, 5);
            tblModel.setValueAt(txtPw.getText(), row, 6);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để cập nhật");
        }
    }

    //tim nhan vien bang ten dang nhap
    private void timTaiKhoan() {
        String tenDangNhap = txtTim.getText().trim();
        NhanVien nv = taiKhoan_DAO.searchTaiKhoan(tenDangNhap);

        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);

        if (nv != null) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(),
                    nv.getTenNhanVien(),
                    nv.getTuoi(),
                    nv.getDiaChi(),
                    nv.getSoDienThoai(),
                    nv.getTaiKhoan().getTenDangNhap()
            });
            JOptionPane.showMessageDialog(this, "Tìm thấy tài khoản với tên đăng nhập: " + tenDangNhap);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản với tên đăng nhập: " + tenDangNhap);
        }
    }





}

