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
import utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ChartView {
    private JFrame window;
    private JPanel jPanel;

    public ChartView(Double[][] data){
        setElement(data);

        window = new JFrame();
        setSetting();
        window.add(jPanel);
    }

    private void setSetting(){
        window.setVisible(true);
        window.setBounds(400,200,900,700);
    }

    private void setElement(Double[][] data){
        jPanel = new JPanel();
        ApproximationMethod approximationMethod = new ApproximationMethod();
        String function = approximationMethod.run(data);
        jPanel.add(createPanel(function, data));
    }


    public JPanel createPanel(String function1, Double [][] data) {
        JFreeChart chart = createChart(createDataset(function1, data));
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

    public Pair<XYDataset, DefaultXYDataset> createDataset(String function1,  Double [][] data) {

        Expression formula1 = new ExpressionBuilder(function1).variable("x").build();
        Pair<Double, Double> borders = getLeftAndRightBorders(data);
        Double leftBorder, rightBorder;

            leftBorder = borders.snd - 15;
            rightBorder = borders.fst + 15;

        XYDataset first = DatasetUtilities.sampleFunction2D(
                new Function(formula1),
                leftBorder,
                rightBorder,
                300,
                function1
        );


        DefaultXYDataset points = new DefaultXYDataset();
        double x[] = new double[data.length];
        double y[] = new double[data.length];
        int inc = 0;
        for (int i = 0; i<data.length; i++) {
                y[i] = data[i][0];
                x[i] = data[i][1];
        }

        double p[][] = { y , x };

        points.addSeries("Точки", p);
        return Pair.of(first, points);
    }


    private JFreeChart createChart(Pair<XYDataset, DefaultXYDataset> dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики",
                "X",
                "Y",
                dataset.snd,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setRenderer(0, new XYLineAndShapeRenderer());
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer(0);
        r.setSeriesLinesVisible(0, false);

        plot.setDataset(1, dataset.fst);
        plot.setRenderer(1, new StandardXYItemRenderer());



        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);
        return chart;
    }

    private Pair<Double,Double> getLeftAndRightBorders(Double data[] []) {
        Double max = -Integer.MAX_VALUE + 0.0;
        Double min = Integer.MAX_VALUE + 0.0;
        for (Double[] i : data) {
            if (i[0]>max) max = i[0];
            if (i[0]<min) min = i[0];
        }
        return Pair.of(max,min);
    }


    static class Function implements Function2D {
        Expression ex;

        public Function(Expression ex) {
            this.ex = ex;
        }

        public double getValue(double x) {
            return ex.setVariable("x", x).evaluate();
        }
    }


}
