package gui;

import javax.swing.*;
import java.awt.*;

public class DatBan_GUI extends JPanel { // ✅ kế thừa JPanel
    public DatBan_GUI(MainFrame frame) {
        setLayout(new BorderLayout());
        add(new JLabel("Đây là trang Đặt Bàn", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
