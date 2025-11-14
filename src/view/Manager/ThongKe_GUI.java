package view.Manager;

import javax.swing.*;
import java.awt.*;

public class ThongKe_GUI extends JPanel {
    public ThongKe_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Thống Kê", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
