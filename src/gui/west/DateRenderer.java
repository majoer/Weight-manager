package gui.west;

import entity.WeightEntry;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Mats
 */
public class DateRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer c = (DefaultTableCellRenderer) dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(1, 1));
//        panel.setBackground(Color.white);

        if (value instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", new Locale("EN"));
            JLabel l = new JLabel(dateFormat.format(value));
//            l.setFont(l.getFont().deriveFont(12));
//            c.add(l);
            c.setText(dateFormat.format(value));
        }
//        if(isSelected) {
//            panel.setBackground(c.getBackground());
//        }
//        if(hasFocus) {
//            panel.setBorder(BorderFactory.createLineBorder(c.getBackground()));
//        }

        return c;
    }

}
