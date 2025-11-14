package gui;

import javax.swing.*;
import java.awt.*;

public class SanPham_GUI extends JPanel {
    public SanPham_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Sản Phẩm", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
