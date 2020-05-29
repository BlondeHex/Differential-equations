package views;

import model.ApproximationMethod;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Chart {
    private JPanel jPanel;
    private XYDataset first;
    private DefaultXYDataset points;
    private double[][] data;
    private String function;


    public Chart(double[][] data){
        this.data = data;
        ApproximationMethod approximationMethod = new ApproximationMethod();
        function = approximationMethod.run(data);
        setDataset();
        jPanel = setElement();

    }


    private JPanel setElement(){
        JPanel jPanel = new JPanel();
        jPanel.add(createPanel());
        return jPanel;
    }


    private JPanel createPanel() {
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }
        });
        chartPanel.setVerticalAxisTrace(true);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setMouseZoomable(true);

        return chartPanel;
    }

    private void setDataset() {

        Expression formula1 = new ExpressionBuilder(function).variable("x").build();
        double[][] borders = getLeftAndRightBorders();
        double leftBorder, rightBorder;

            leftBorder = borders[0][1] - 15;
            rightBorder = borders[0][0] + 15;
            first = DatasetUtilities.sampleFunction2D(
                new Function(formula1),
                leftBorder,
                rightBorder,
                300,
                function
        );

            points = new DefaultXYDataset();
        double[] x = new double[data.length];
        double[] y = new double[data.length];
        for (int i = 0; i<data.length; i++) {
                y[i] = data[i][0];
                x[i] = data[i][1];
        }

        double[][] p = {y, x};

        points.addSeries("Точки", p);
    }


    private JFreeChart createChart() {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики",
                "X",
                "Y",
                points,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setRenderer(0, new XYLineAndShapeRenderer());
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer(0);
        r.setSeriesLinesVisible(0, false);

        plot.setDataset(1, first);
        plot.setRenderer(1, new StandardXYItemRenderer());



        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);
        return chart;
    }

    private double[][] getLeftAndRightBorders() {
        double max = -Integer.MAX_VALUE + 0.0;
        double min = Integer.MAX_VALUE + 0.0;
        for (double[] i : data) {
            if (i[0]>max) max = i[0];
            if (i[0]<min) min = i[0];
        }
        return new double[][]{{max,min}};
    }


    static class Function implements Function2D {
        Expression ex;

        Function(Expression ex) {
            this.ex = ex;
        }

        public double getValue(double x) {
            return ex.setVariable("x", x).evaluate();
        }
    }

    public JPanel getJPanel() {
        return jPanel;
    }
}
