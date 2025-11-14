package gui;

import javax.swing.*;
import java.awt.*;

public class ChonBan_GUI extends JPanel {
    public ChonBan_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Chọn Bàn", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}