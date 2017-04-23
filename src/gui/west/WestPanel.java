package gui.west;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import logic.Logic;

/**
 *
 * @author Mats
 */
public class WestPanel extends JPanel {

    public WestPanel() {
        EntryTable t = new EntryTable();
        Logic.getInstance().bind(t);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        add(new NorthPanel(), c);
        
        c.weighty = 1;
        c.gridy = 1;
        
        JScrollPane jsp = new JScrollPane(t);
        add(jsp, c);
    }
} 