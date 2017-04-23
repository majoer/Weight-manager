package gui.west;

import entity.WeightEntry;
import gui.listeners.OnHoverMouseListener;
import gui.utils.Utils;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import logic.Logic;
import string.Urls;

/**
 *
 * @author Mats
 */
public class NorthPanel extends JPanel implements ActionListener {

    private final String plus = "Add", minus = "Remove", undo = "Undo", redo = "Redo";

    public NorthPanel() {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;

        add(createButton(undo, Urls.undo, Urls.undo_big), c);
        c.weightx = 0;
        c.gridx++;
        
        add(createButton(redo, Urls.redo, Urls.redo_big), c);
        
        c.gridx++;
        
        add(createButton(minus, Urls.minus, Urls.minus_big), c);

        c.gridx++;
        add(createButton(plus, Urls.plus, Urls.plus_big), c);

    }

    private JButton createButton(String actionCommand, String smallURL, String bigURL) {
        //Declare
        JButton button = new JButton();
        ImageIcon small = Utils.getIcon(smallURL);
        ImageIcon big = Utils.getIcon(bigURL);
        
        //Logistics
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        //Onhover
        button.addMouseListener(new OnHoverMouseListener(button, small, big));
        button.setIcon(small);
        
        //Visual
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        //Size
        Dimension d = new Dimension(big.getIconWidth(), big.getIconHeight());
        button.setSize(d);
        button.setPreferredSize(d);
        button.setMinimumSize(d);
        
        //Misc
        if(actionCommand.length() > 0) {
            button.setToolTipText(actionCommand);
        }
        
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case plus:
                plus();
                break;
            case minus:
                minus();
                break;
            case undo:
                undo();
                break;
            case redo:
                redo();
                break;
        }
    }

    private void plus() {
        Date date = Calendar.getInstance().getTime();
        WeightEntry we = new WeightEntry(date, 0, "");
        Logic.getInstance().newWeightEntry(we, false, true, true);
    }

    private void minus() {
        Logic.getInstance().removeSelected();
    }

    private void undo() {
        Logic.getInstance().undo();
    }
    
    private void redo() {
        Logic.getInstance().redo();
    }

}
