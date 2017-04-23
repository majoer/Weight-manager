package gui.west;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import entity.WeightEntry;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;
import logic.Logic;

/**
 *
 * @author Mats
 */
public class EntryTable extends JTable{

    private final String enterEvent = "ENTER";

    private DefaultTableModel dtm;

    public EntryTable() {
        super(new DefaultTableModel());
        dtm = (DefaultTableModel) getModel();

        initializeConstraints();
        addColumns();
        addKeyStroke();
        initMouseListener();
    }

    @Override
    public Object getValueAt(int row, int column) {
        WeightEntry we = Logic.getInstance().getEntries().get(row);
        switch (column) {
            case 0:
                return we.getDate();
            case 1:
                return we.getWeight();
            case 2:
                return we.getComment();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        WeightEntry oldEntry = Logic.getInstance().getEntries().get(row);
        WeightEntry newEntry = new WeightEntry(oldEntry);
        if (oldEntry == null) {
            return;
        }

        switch (column) {
            case 0:
                newEntry.setDate((Date) aValue);
                break;
            case 1:
                try {
                    double d = Double.parseDouble((String) aValue);
                    newEntry.setWeight(d);
                } catch (NumberFormatException e) {
                    //Do nothing
                }
                break;
            case 2:
                newEntry.setComment((String) aValue);
                break;
        }
        if (!newEntry.equals(oldEntry)) {
            int newPos = Logic.getInstance().updateWeightEntry(oldEntry, newEntry, true);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    updateUI();
                    autoScroll(newPos);
                }
            });
        }
    }

    @Override
    public int getRowCount() {
        return Logic.getInstance().getEntries().size();
    }

    private void addColumns() {
        dtm.addColumn("Date");
        dtm.addColumn("Weight");
        dtm.addColumn("Comment");

        getColumn("Date").setCellEditor(new DateEditor());
        getColumn("Date").setCellRenderer(new DateRenderer());
    }

    private void initializeConstraints() {
        getTableHeader().setReorderingAllowed(false);
    } 

    public void autoScroll(int row) {
        if(row < 0 ^ row >= getRowCount()) {
            return;
        }
        if (!(this.getParent() instanceof JViewport)) {
            return;
        }
        Rectangle rect = getCellRect(row, 0, true);
        scrollRectToVisible(rect);
        getSelectionModel().setSelectionInterval(row, row);
        
    }

    private void addKeyStroke() {

        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, enterEvent);
        getActionMap().put(enterEvent, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("enter");

                getCellEditor().stopCellEditing();
            }
        });
    }

    private void initMouseListener() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event.getID() == MouseEvent.MOUSE_CLICKED) {
                    if (!isAncestorOf((Component) event.getSource())) {
                        TableCellEditor ce = getCellEditor();
                        if (ce != null) {
                            getCellEditor().stopCellEditing();
                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

}
