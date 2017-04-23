package gui.west.calendar;

import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mats
 */
public class Layout extends JPanel {
    public Calendar c;
    public Layout() {
        setLayout(new GridLayout(1, 2));
        add(c = new Calendar());
        add(new Clock());
    }
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.add(new Layout());
        f.setSize(700, 300);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
}
