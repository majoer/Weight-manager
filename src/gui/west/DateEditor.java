package gui.west;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Locale;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Mats
 */
public class DateEditor extends AbstractCellEditor implements TableCellEditor, CaretListener, ActionListener {

    private Object cellEditorValue, originalValue;
    private JTextField f;
    private int lastRow = -1, lastColumn = -1;

    @Override
    public Object getCellEditorValue() {
        Calendar orig = Calendar.getInstance();
        Calendar edit = Calendar.getInstance();

        orig.setTime((Date) originalValue);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            edit.setTime(dateFormat.parse(f.getText()));
        } catch(ParseException e) {
            return orig.getTime();
        }
        
        orig.set(Calendar.YEAR, edit.get(Calendar.YEAR));
        orig.set(Calendar.MONTH, edit.get(Calendar.MONTH));
        orig.set(Calendar.DAY_OF_MONTH, edit.get(Calendar.DAY_OF_MONTH));
        
        
        return orig.getTime();

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        DefaultCellEditor dce = new DefaultCellEditor(f = new JTextField());
        f = (JTextField) dce.getTableCellEditorComponent(table, value, isSelected, row, column);
        f.addActionListener(this);
        
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss", new Locale("EN"));
            f.setText(sdf.format(value));
            f.addCaretListener(this);
            this.originalValue = value;
        }
        lastRow = row;
        lastColumn = column;
        return f;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
        try {
            cellEditorValue = dateFormat.parse(f.getText());

        } catch (ParseException ex) {

        }
    }

    @Override
    public void cancelCellEditing() {
        super.cancelCellEditing();
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if(e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= 2;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopCellEditing();
    }
}
