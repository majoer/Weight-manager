package gui.west.calendar;

import gui.west.calendar.Spinner;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Mats
 */
public class Calendar extends JPanel {
    public DatePicker picker;
    public Calendar() {
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        
        Spinner s;
        this.picker = new DatePicker();
        add(s = new Spinner(picker), c);
        picker.bind(s);
        
        c.gridy = 1;
        c.weighty = 1;
        add(picker, c);
    }
    
    
}
