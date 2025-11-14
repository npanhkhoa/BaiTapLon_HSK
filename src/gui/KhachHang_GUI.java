package gui;

import javax.swing.*;
import java.awt.*;

public class KhachHang_GUI extends JPanel {
    public KhachHang_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Khách Hàng", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
