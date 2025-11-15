package view.custom;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

// Custom renderer để hiện hình trong JTable
public class ImageRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // Reset component before setting new values
        setIcon(null);
        setText("");
        setHorizontalAlignment(JLabel.CENTER); // Center the image/text

        if (value instanceof ImageIcon) {
            setIcon((ImageIcon) value);
        } else if (value != null) {
            // Fallback if the value is not an ImageIcon but not null
            setText(value.toString());
        }

        // Handle selection background
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }


}


