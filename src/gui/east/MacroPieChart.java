package gui.east;

import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Mats
 */
public class MacroPieChart extends JPanel {

    private final String title = "Macro split";

    public MacroPieChart() {
        setLayout(new GridLayout(1, 1));
        add(new ChartPanel(initializeChart()));

    }

    private JFreeChart initializeChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Carbs", 50);
        dataset.setValue("Proteins", 25);
        dataset.setValue("Fats", 25);
        
        return ChartFactory.createPieChart(title, dataset, true, true, true);
    }
}
