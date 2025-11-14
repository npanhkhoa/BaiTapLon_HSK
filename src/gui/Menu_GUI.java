package gui;

import javax.swing.*;
import java.awt.*;

public class Menu_GUI extends JPanel {
    public Menu_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Menu / Bán Hàng", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
