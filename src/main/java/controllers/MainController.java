package controllers;

import model.NumericalMethods;
import views.ChartView;
import views.MainView;
import model.Equation;

public class MainController {
    private MainView view;
    public MainController(){
        view = new MainView(600,250);
        addActionListener(view);
    }
    private void addActionListener(MainView view) {

        view.getButton().addActionListener(li -> {
            Double[][] data = dataProcessing();
            new ChartView(data);
        });
    }

    private Double[][] dataProcessing(){
        double x0 = Double.parseDouble(view.getBeginXField().getText());
        double y0 = Double.parseDouble(view.getBeginYField().getText());
        double xn = Double.parseDouble(view.getEndXField().getText());
        double h = Double.parseDouble(view.getAccuracyField().getText());
        String function = view.getFunctionField().getText();
        Equation equation = new Equation(x0,y0,xn,h,function);
        NumericalMethods numericalMethods = new NumericalMethods();
        numericalMethods.run(equation);
        return equation.getValueTable();
    }
}
