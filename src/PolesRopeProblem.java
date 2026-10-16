import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class PolesRopeProblem {
    private final int h;
    private final double ab;
    public PolesRopeProblem(int h, double ab) {
        this.h = h;
        this.ab = ab;
    }
    public double parabolicRope(double x, double l) {
        return ((4*(h - ab))/(l*l))*Math.pow(x - l/2, 2) + ab;
    }
    public double triangularRopeLength(double l) {
        return 2*Math.sqrt(Math.pow(h-ab, 2) + Math.pow(l/2, 2));
    }
    //l is pole distance
    public double approxRopeLength(double l, int n) {
        double sum = 0;
        for (int k = 0; k < n; k++) {
            sum += Math.sqrt(Math.pow(parabolicRope(k*(l/n), l) - parabolicRope((k+1)*(l/n), l), 2) + Math.pow((l/n), 2));
        }
        return sum;
    }
    private static final int N_DEFAULT = 50;
    private static final int H_DEFAULT = 50;

    private static final double AB_DEFAULT = 10;
    private static final double MAX_X = 800;
    public static void main(String[] args) {
        PolesRopeProblem problem = new PolesRopeProblem(H_DEFAULT, AB_DEFAULT);
        System.out.println(problem.approxRopeLength(0.00001, N_DEFAULT));

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Length of parabolic rope, between poles spaced out by x");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Create dataset
            XYSeries series = new XYSeries("y = approxRopeLength(x, 50)");
            XYSeries series2 = new XYSeries("y = Lange des Dreiecks");
            for (double l = 0; l < MAX_X; l += 0.1) {
                series.add(l, problem.approxRopeLength(l, N_DEFAULT));
                series2.add(l, problem.triangularRopeLength(l));
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);
            dataset.addSeries(series2);

            // Create chart
            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Function Graph", "X", "Y", dataset,
                    PlotOrientation.VERTICAL, true, true, false);

            XYPlot plot = chart.getXYPlot();
            NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
            NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

            // Enable auto range
            xAxis.setAutoRange(true);
            yAxis.setAutoRange(true);

            // Override chart panel to maintain 1:1 scaling
            ChartPanel chartPanel = new ChartPanel(chart) {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    Rectangle bounds = getBounds();
                    double width = bounds.getWidth();
                    double height = bounds.getHeight();
                    double minSize = Math.min(width, height); // Make it square

                    // Ensure that X and Y units have the same length
                    Rectangle2D newBounds = new Rectangle2D.Double(
                            (width - minSize) / 2, (height - minSize) / 2, minSize, minSize);
                    chart.draw((Graphics2D) g, newBounds);
                }
            };

            frame.add(chartPanel);
            frame.setVisible(true);
        });
    }
}
