package gui.east;

import java.awt.GridLayout;
import javax.swing.JPanel;
import logic.Logic;

/**
 *
 * @author Mats
 */
public class EastPanel extends JPanel {

    public EastPanel() {
        setLayout(new GridLayout(1, 1));
        
        WeightChart wc = new WeightChart();
        Logic.getInstance().bind(wc);
        
        add(wc);
//        add(new MacroPieChart());
    }

}
