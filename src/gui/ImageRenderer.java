package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * der ImageRenderer teilt dem Table mit, dass er ein Bild rendern muss.
 *
 * @author Aladin Boudouda
 *
 */

class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        lbl.setIcon((ImageIcon)value);
        return lbl;
    }
}
