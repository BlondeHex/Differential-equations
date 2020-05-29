package controllers;

import model.NumericalMethods;
import views.Chart;
import views.MainView;
import model.Equation;

import javax.swing.*;
import java.awt.*;

public class MainController {
    private MainView view;
    private Chart chart;
    public MainController(){
        view = new MainView(1200,700);
        addActionListener(view);
    }
    private void addActionListener(MainView view) {

        view.getButton().addActionListener(li -> {
            Equation equation = dataProcessing();

            view.getView().remove(view.getPanelTable());
            JScrollPane wrapperTable = view.initTable(equation.getValueTable());
            GridBagConstraints baseConstraints = new GridBagConstraints();
            view.setPanelTable(wrapperTable);

            baseConstraints.gridx = 0;
            baseConstraints.gridy = 1;
            view.getView().add(wrapperTable, baseConstraints);

            if (chart != null) {
                view.getPanelChart().remove(chart.getJPanel());
            }

            chart = new Chart(equation.getValueTable());
            view.getPanelChart().add(chart.getJPanel());

            view.getView().revalidate();
            view.getView().repaint();
        });
    }

    private Equation dataProcessing(){
        double x0 = Double.parseDouble(view.getBeginXField().getText());
        double y0 = Double.parseDouble(view.getBeginYField().getText());
        double xn = Double.parseDouble(view.getEndXField().getText());
        double h = Double.parseDouble(view.getAccuracyField().getText());
        String function = view.getFunctionField().getText();
        Equation equation = new Equation(x0,y0,xn,h,function);
        NumericalMethods numericalMethods = new NumericalMethods();
        numericalMethods.run(equation);
        return equation;
    }
}
