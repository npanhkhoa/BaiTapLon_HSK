package view.custom;

import javax.swing.*;
import java.awt.*;

public class customPanel {
    private static customPanel instance;

    public static customPanel getInstance() {
        if (instance == null)
            instance = new customPanel();
        return instance;
    }

    public void setCustomBtn(JButton btn) {
        btn.setBackground(Color.decode("#d0e1fd"));
        btn.setForeground(Color.decode("#1a66e3"));
    }

    public void setCustomBtnHover(JButton btn) {
        btn.setBackground(Color.decode("#73cdf5"));
        btn.setForeground(Color.WHITE);
    }

    public void setCustomLbTitle(JLabel lb) {
        lb.setFont(new Font("Dialog", Font.BOLD,
                24));
        lb.setForeground(Color.decode("#1a66e3"));
    }
}
