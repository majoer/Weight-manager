package gui.west.calendar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.Calendar;

/**
 *
 * @author Mats
 */
public class DatePicker extends JPanel implements ActionListener, ICalendar {

    private enum LEVEL {

        DAY, MONTH, YEAR
    }

    private LEVEL level;
    private Calendar calendar;

    private final long fadeTime = 500;
    private final short frameRate = 60;
    private ISpinner iface;
    private int buttons;

    public DatePicker() {
        setLayout(new GridLayout(1, 1));
        calendar = Calendar.getInstance();
        level = LEVEL.DAY;
        drawLayout();
    }

    public void bind(ISpinner iface) {
        this.iface = iface;
        iface.updateDate(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH)
                + " " + calendar.get(Calendar.YEAR));
    }

    private void drawLayout() {
        JPanel p = new JPanel();
        int cols;
        switch (level) {
            case DAY:
                buttons = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                cols = 7;
                break;

            case MONTH:
                buttons = calendar.getMaximum(Calendar.MONTH) + 1;
                cols = 4;
                break;
            case YEAR:
                buttons = calendar.getMaximum(Calendar.MONTH) + 1;
                cols = 4;
                break;
            default:
                buttons = 0;
                cols = 0;
        }
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;

        for (int i = 1; i < buttons + 1; i++) {
            p.add(createButton(i, buttons), c);

            if (i % cols == 0) {
                c.gridy++;
                c.gridx = 0;
            } else {
                c.gridx++;
            }
        }
        add(p);
    }

    private JButton createButton(int i, int buttons) {
        String actionCommand, label;
        switch (level) {
            case DAY:
                actionCommand = String.valueOf(i);
                label = actionCommand;
                break;
            case MONTH:
                actionCommand = String.valueOf(i - 1);
                label = new DateFormatSymbols(Locale.ENGLISH).getMonths()[i - 1];
                break;
            case YEAR:
                int year = calendar.get(Calendar.YEAR);
                actionCommand = String.valueOf(year - (int) (buttons / 2) + i);
                label = actionCommand;
                break;
            default:
                actionCommand = "";
                label = "";
        }

        JButton b = new JButton(label);
        b.setActionCommand(actionCommand);
        b.addActionListener(this);
        return b;
    }

    private void fadeOutLayout() {
        long startTime = System.currentTimeMillis();
        int frames = (int) (frameRate * fadeTime / 1000);
        while (System.currentTimeMillis() < startTime + fadeTime && frames > 0) {
            long start = System.currentTimeMillis();

            Dimension currentSize = getSize();
            Dimension reduction = new Dimension(currentSize.width / frames, currentSize.height / frames);
            setSize(getSize().width - reduction.width, getSize().height - reduction.height);
            setLocation(getLocation().x + (int) reduction.width / 2, getLocation().y + (int) reduction.height / 2);

            frames--;
            limit(start);
        }
        removeAll();
    }

    private void limit(long start) {
        long wait = 1000 / frameRate;
        long difference = System.currentTimeMillis() - start;
        try {
            Thread.sleep(wait - difference);
        } catch (InterruptedException ex) {
            Logger.getLogger(DatePicker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fadeInLaoyout() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (level) {
            case DAY:

                break;
            case MONTH:
                removeAll();
                level = LEVEL.DAY;
                calendar.set(Calendar.MONTH, Integer.parseInt(e.getActionCommand()));
                drawLayout();
                iface.updateDate(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH)
                        + " " + calendar.get(Calendar.YEAR));
                break;
            case YEAR:
                removeAll();
                level = LEVEL.MONTH;
                calendar.set(Calendar.YEAR, Integer.parseInt(e.getActionCommand()));
                drawLayout();
                iface.updateDate(String.valueOf(calendar.get(Calendar.YEAR)));
                break;
        }
        updateUI();
    }

    @Override
    public void next() {
        switch (level) {
            case DAY:
                removeAll();
                int month = calendar.get(Calendar.MONTH) + 1;
                if (month > calendar.getActualMaximum(Calendar.MONTH)) {
                    month = 0;
                    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
                }
                calendar.set(Calendar.MONTH, month);
                drawLayout();
                iface.updateDate(getMonth());
                break;
            case MONTH:
                removeAll();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
                iface.updateDate(String.valueOf(calendar.get(Calendar.YEAR)));
                drawLayout();
                break;
            case YEAR:
                removeAll();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + buttons);
                iface.updateDate((calendar.get(Calendar.YEAR) + 1 - buttons / 2) + "-" + (calendar.get(Calendar.YEAR) + buttons / 2));
                drawLayout();
                break;
        }
    }

    @Override
    public void prev() {
        switch (level) {
            case DAY:
                removeAll();
                int month = calendar.get(Calendar.MONTH) - 1;
                if (month < 0) {
                    month = calendar.getActualMaximum(Calendar.MONTH);
                    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
                }
                calendar.set(Calendar.MONTH, month);
                drawLayout();
                iface.updateDate(getMonth());
                break;
            case MONTH:
                removeAll();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
                iface.updateDate(getYear());
                drawLayout();
                break;
            case YEAR:
                removeAll();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - buttons);
                iface.updateDate((calendar.get(Calendar.YEAR) + 1 - buttons / 2) + "-" + (calendar.get(Calendar.YEAR) + buttons / 2));
                drawLayout();
                break;
        }
    }

    @Override
    public void up() {
        switch (level) {
            case DAY:
                removeAll();
                level = LEVEL.MONTH;
                drawLayout();
                iface.updateDate(String.valueOf(calendar.get(Calendar.YEAR)));
                break;
            case MONTH:
                removeAll();
                level = LEVEL.YEAR;
                iface.updateDate((calendar.get(Calendar.YEAR) + 1 - buttons / 2) + "-" + (calendar.get(Calendar.YEAR) + buttons / 2));
                drawLayout();
                break;
            case YEAR:

                break;
        }
        updateUI();
    }

    private String getYear() {
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
    
    private String getMonth() {
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH)
                        + " " + calendar.get(Calendar.YEAR);
    }
}
