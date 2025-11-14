package gui;

import javax.swing.*;
import java.awt.*;

public class KhuyenMai_GUI extends JPanel {
    public KhuyenMai_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Khuyến Mãi", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
