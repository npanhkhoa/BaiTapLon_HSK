package view.Employee;

import controller.UserController;
import entity.CaLamViec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class CaLamViecPanel extends JPanel implements ActionListener {
    private JTextField soTienField;
    private ShiftListener shiftListener;
    JButton openShiftButton;

    /**
     * Constructor của panel ca làm việc
     */
    public CaLamViecPanel() {
        setLayout(new BorderLayout());

        // Tiêu đề phía trên
        add(createTitlePanel(), BorderLayout.NORTH);

        // Form ở giữa màn hình
        add(createFormCenterPanel(), BorderLayout.CENTER);
    }

   /**
     * Phương thức này tạo một panel tiêu đề với chữ "Vui lòng mở ca làm việc".
     * @return JPanel chứa tiêu đề.
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Vui lòng mở ca làm việc", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(new Color(10, 82, 116));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

   /**
     * Phương thức này tạo một panel chứa các trường nhập liệu và nút mở ca làm việc.
     * @return JPanel chứa các trường nhập liệu và nút.
     */
    private JPanel createFormCenterPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout()); // để căn giữa toàn bộ panel form
        JPanel formPanel = new JPanel(new GridBagLayout());  // panel chứa các trường nhập
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // Padding giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Số tiền mở ca:"), gbc);

        gbc.gridx = 1;
        soTienField = new JTextField();
        soTienField.setPreferredSize(new Dimension(250, 30));
        soTienField.setText("455000");
        formPanel.add(soTienField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        openShiftButton = new JButton("Mở ca làm việc");
        openShiftButton.setPreferredSize(new Dimension(150, 30));
        openShiftButton.setBackground(new Color(10, 82, 116));
        openShiftButton.setForeground(Color.WHITE);

        openShiftButton.addActionListener(this);
        formPanel.add(openShiftButton, gbc);

        // Đưa form vào giữa bằng GridBagLayout
        outerPanel.add(formPanel);
        return outerPanel;
    }


    /**
     * Phương thức này trả về thời gian bắt đầu ca làm việc.
     * @param startTime Thời gian bắt đầu ca làm việc.
     * @return Thời gian hiện tại.
     */
    public LocalDateTime getStartTime(LocalDateTime startTime) {
        return LocalDateTime.now();
    }

public interface ShiftListener {
    void onShiftOpened(double tienMoCa); // Truyền số tiền mở ca
}

    /**
     * Phương thức này thiết lập listener cho sự kiện mở ca làm việc.
     * @param shiftListener Listener cho sự kiện mở ca làm việc.
     */
    public void setShiftListener(ShiftListener shiftListener) {
        this.shiftListener = shiftListener;
    }

    /**
     * Phương thức này xử lý sự kiện khi nhấn nút mở ca làm việc.
     * @param e Sự kiện nhấn nút.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(openShiftButton)) {
            String soTien = soTienField.getText();
            if (soTien.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền mở ca!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                double soTienMoCa = Double.parseDouble(soTien);
                if(soTienMoCa < 455000) {
                    JOptionPane.showMessageDialog(this, "Số tiền mở ca tối thiểu là 455000", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                CaLamViec caLamViec = new CaLamViec();
                caLamViec.setThoiGianBatDau(LocalDateTime.now());
                caLamViec.setTienMoCa(soTienMoCa);

                if (shiftListener != null) {
                    shiftListener.onShiftOpened(soTienMoCa); // Truyền số tiền mở ca
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tiền mở ca không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



}
