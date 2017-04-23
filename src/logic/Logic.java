package logic;

import entity.Action;
import entity.Event;
import entity.WeightEntry;
import gui.Mainframe;
import gui.east.WeightChart;
import gui.west.EntryTable;
import interfaces.ILogic;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mats
 */
public class Logic {

    private static Logic instance = new Logic();
    private ArrayList<WeightEntry> entries;
    private LinkedBlockingQueue<WeightEntry> tmp;
    private HistoryManager history;

    private EntryTable entryTable;
    private DateSorter sorter;
    private WeightChart chart;

    private Logic() {
        this.tmp = new LinkedBlockingQueue<>();
        this.entries = new ArrayList<>();
        this.sorter = new DateSorter(entries);
        this.history = new HistoryManager();
    }

    public static Logic getInstance() {
        return instance;
    }

    public void bind(WeightChart chart) {
        this.chart = chart;
        emptyTmp();
    }

    public void bind(EntryTable entryTable) {
        this.entryTable = entryTable;
        emptyTmp();
    }

    public void removeSelected() {
        if (!initialized()) {
            return;
        }
        WeightEntry we = entries.get(entryTable.getSelectedRow());
        remove(we, true);
    }

    private void remove(WeightEntry we, boolean addToHistory) {
        entries.remove(we);
        chart.removeEntry(we);
        if (addToHistory) {
            history.push(new Action(Action.ActionType.REMOVE, we, null));
        }
        entryTable.updateUI();
    }

    public int updateWeightEntry(WeightEntry oldEntry, WeightEntry newEntry, boolean addToHistory) {
        if (!initialized()) {
            return -1;
        }
        int pos = entries.indexOf(oldEntry);

        entries.set(pos, newEntry);
        chart.removeEntry(oldEntry);
        chart.newEntry(newEntry);
        if (addToHistory) {
            history.push(new Action(Action.ActionType.UPDATE, newEntry, oldEntry));
        }
        return sorter.sort(pos, oldEntry, newEntry);
    }

    public void undo() {
        Action a = history.undo();
        if (a == null) {
            return;
        }
        switch (a.getType()) {
            case ADD:
                remove(a.getTarget(), false);
                break;

            case REMOVE:
                newWeightEntry(a.getTarget(), true, false, false);
                break;

            case UPDATE:
                int newPos = updateWeightEntry(a.getTarget(), a.getOldEntry(), false);
                entryTable.autoScroll(newPos);
                break;
        }
    }

    public void redo() {
        Action a = history.redo();
        if (a == null) {
            return;
        }
        switch (a.getType()) {
            case ADD:
                newWeightEntry(a.getTarget(), true, false, false);
                break;

            case REMOVE:
                remove(a.getTarget(), false);
                break;

            case UPDATE:
                int newPos = updateWeightEntry(a.getTarget(), a.getOldEntry(), false);
                entryTable.autoScroll(newPos);
                break;
        }
    }

    public synchronized void newWeightEntry(WeightEntry w, boolean sort, boolean push, boolean addToHistory) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int row = 0;
                if (!initialized()) {
                    tmp.add(w);
                    return;
                }
                if (!entries.contains(w)) {
                    if (push) {
                        entries.add(0, w);
                    } else {
                        entries.add(w);
                    }
                    if (sort) {
                        row = sorter.sort(w);
                    }
                    chart.newEntry(w);
                    if (addToHistory) {
                        history.push(new Action(Action.ActionType.ADD, w, null));
                    }
                }
                entryTable.updateUI();
                entryTable.autoScroll(row);
            }
        });
    }

    public ArrayList<WeightEntry> getEntries() {
        return entries;
    }

    private boolean initialized() {
        return entryTable != null && chart != null;
    }

    private void emptyTmp() {
        if (initialized()) {
            while (!tmp.isEmpty()) {
                try {
                    newWeightEntry(tmp.take(), false, false, false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
