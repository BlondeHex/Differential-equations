package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.util.Precision;

public class Equation {
    private double h;
    private String function;
    private double[][] valueTable;

    public Equation(double x0, double y0, double xn, double h, String function){

        this.h=Math.pow(h, 0.25);
        int iterations = (int) (Math.ceil((xn - x0) / this.h));
        valueTable = new double[iterations +1][2];
        valueTable[0][0]= x0;
        valueTable[0][1]= y0;
        this.function=function;
        for (int i = 1; i< iterations; i++){
            valueTable[i][0] = Precision.round(valueTable[i-1][0]+this.h,6);
        }
        valueTable[iterations][0]=xn;
    }

    double getValueFunction(double x, double y) {
        try {
            Expression e = new ExpressionBuilder(function)
                    .variables("x", "y")
                    .build()
                    .setVariable("x", x)
                    .setVariable("y", y);
            return (e.evaluate());
        } catch (ArithmeticException e){
            return 0;
        }
    }

    public double[][] getValueTable() {
        return valueTable;
    }

    double getH() {
        return h;
    }

    void setValueTable(double[][] valueTable) {
        this.valueTable = valueTable;
    }
}
