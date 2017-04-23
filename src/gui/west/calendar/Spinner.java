package gui.west.calendar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mats
 */
public class Spinner extends JPanel implements ActionListener, ISpinner {
    
    private final String p = "prev", m = "middle", n = "next";
    private ICalendar iface;
    private JButton middle;
    
    
    public Spinner(ICalendar iface) {
        this.iface = iface;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        
        JButton prev = new JButton();
        prev.setActionCommand(p);
        prev.addActionListener(this);
        add(prev, c);
        
        c.gridx = 1;
        c.weightx = 1;
        middle = new JButton();
        middle.setActionCommand(m);
        middle.addActionListener(this);
        add(middle, c);
        
        c.gridx = 2;
        c.weightx = 0;
        JButton next = new JButton();
        next.setActionCommand(n);
        next.addActionListener(this);
        add(next, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case p:
                iface.prev();
                break;
            case m:
                iface.up();
                break;
            case n:
                iface.next();
                break;
        }
    }

    @Override
    public void updateDate(String s) {
        middle.setText(s);
    }
}
