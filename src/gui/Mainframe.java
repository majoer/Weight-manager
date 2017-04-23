package gui;

import entity.Event;
import gui.east.EastPanel;
import gui.east.WeightChart;
import gui.west.WestPanel;
import interfaces.IGui;
import interfaces.ILogic;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Mats
 */
public class Mainframe extends JFrame implements IGui {

    public Mainframe() {
        initializeFrame();
        initializeLayout();
        initializeMenuBar();
    }

    private void initializeFrame() {
        setSize(1024, 720);
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.3;
        c.weighty = 1;
        add(new WestPanel(), c);

        c.gridx = 1;
        c.weightx = 0.7;
        add(new EastPanel(), c);

    }

    private void initializeMenuBar() {
    }
}
