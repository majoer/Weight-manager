package gui.east;

import entity.WeightEntry;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.Regression;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Mats
 */
public class WeightChart extends JPanel implements MouseWheelListener {

    private final String title = "Weight Chart";
    private final int n = 10;
    private TimeSeries s, regressionLine;

    private double maxY, minY;

    private XYDataset dataset;
    private ValueAxis rangeAxis;
    private DateAxis domainAxis;
    private double[] coefficients;

    public WeightChart() {
        setLayout(new GridLayout(1, 1));
        this.minY = Double.MAX_VALUE;
        this.maxY = 0;

        JFreeChart chart = createChart(dataset = createDataset());
        ChartPanel cp = new ChartPanel(chart);
        cp.addMouseWheelListener(this);
        cp.setFillZoomRectangle(true);
        cp.setMouseWheelEnabled(true);

        add(cp);
    }

    public void removeEntry(WeightEntry we) {
        s.delete(new Second(we.getDate()));
        updateRegressionLine();
        updateRangeAxis(we, false);
    }

    public void removeEntry(int index) {
        s.delete(s.getDataItem(index).getPeriod());
        updateRegressionLine();
        updateRangeAxis();
    }

    public void newEntry(WeightEntry we) {
        s.add(new Second(we.getDate()), we.getWeight());
        updateRegressionLine();
        updateRangeAxis(we, true);
    }

    private void updateRegressionLine() {
        if (s.getItemCount() > 1) {
            if (s.getItemCount() > 20) {
                coefficients = Regression.getPolynomialRegression(dataset, 0, n);
            } else {
                coefficients = Regression.getPolynomialRegression(dataset, 0, 1);
            }
            redrawRegressionLine(coefficients);
        }
    }

    private void redrawRegressionLine(double[] reg) {
        regressionLine.clear();
        for (int i = 0; i < s.getItemCount(); i++) {
            TimeSeriesDataItem item = new TimeSeriesDataItem(s.getTimePeriod(i), y(dataset.getXValue(0, i), reg));

            regressionLine.add(item);
        }
    }

    private double y(double x, double[] reg) {
        double y = 0;
        for (int i = 0; i < reg.length - 1; i++) {
            y += reg[i] * Math.pow(x, i);
        }
        return y;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date", "Weight", dataset, false, true, false);
        chart.setBackgroundPaint(Color.lightGray);

        configurePlot(chart);
        configureRenderer(chart);
        configureDomainAxis(chart);
        configureRangeAxis(chart);
        return chart;
    }

    private void configurePlot(JFreeChart chart) {
        XYPlot plot = (XYPlot) chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
    }

    private void updateRangeAxis(WeightEntry we, boolean add) {
        if (add) {
            if (we.getWeight() > maxY) {
                maxY = we.getWeight();
            }
            if (we.getWeight() < minY) {
                minY = we.getWeight();
            }
        } else {
            maxY = 0;
            minY = Double.MAX_VALUE;
            for (int i = 0; i < dataset.getItemCount(0); i++) {
                double value = dataset.getYValue(0, i);
                if (value > maxY) {
                    maxY = value;
                }
                if (value < minY) {
                    minY = value;
                }
            }
        }
        rangeAxis.setRange(minY - 0.5, maxY + 0.5);
    }

    private void updateRangeAxis() {
        minY = Double.MAX_VALUE;
        maxY = 0;

        for (int i = 0; i < s.getItemCount(); i++) {
            double we = (double) s.getDataItem(i).getValue();
            if (we > maxY) {
                maxY = we;
            }
            if (we < minY) {
                minY = we;
            }
        }
        rangeAxis.setRange(minY - 0.5, maxY + 0.5);

    }

    private void configureDomainAxis(JFreeChart chart) {
        domainAxis = (DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis();
        domainAxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

//        domainAxis.setVerticalTickLabels(true);
//        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1));
//        domainAxis.setDateFormatOverride(new SimpleDateFormat("hh:mm"));
//        domainAxis.setLowerMargin(0.01);
//        domainAxis.setUpperMargin(0.01);
    }

    private void configureRangeAxis(JFreeChart chart) {
        rangeAxis = ((XYPlot) chart.getPlot()).getRangeAxis();
    }

    private XYDataset createDataset() {
        s = new TimeSeries("");
        regressionLine = new TimeSeries("");

        TimeSeriesCollection d = new TimeSeriesCollection();
        d.addSeries(s);
        d.addSeries(regressionLine);
        return d;
    }

    private void configureRenderer(JFreeChart chart) {
        XYItemRenderer r = ((XYPlot) chart.getPlot()).getRenderer();

        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setSeriesShapesVisible(1, false);

        }

//        renderer.setShapesVisible(true);
//        renderer.setDrawOutlines(true);
//        renderer.setUseFillPaint(true);
//        renderer.setFillPaint(Color.white);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //Get minimum visible X
//        Date min = domainAxis.getMinimumDate();
//
//        //Get maximum visible X
//        Date max = domainAxis.getMaximumDate();
//
//        //Add/remove missing values
//        for (int i = 0; i < domainAxis.get; i++) {
//            
//            TimeSeriesDataItem n = new TimeSeriesDataItem(min, y(, coefficients));
//            regressionLine.add(n);
//        }
    }
}
