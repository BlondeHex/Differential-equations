package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.util.Precision;

public class Equation {
    private double xn;
    private double h;
    private String function;
    private double[][] valueTable;
    private int iterations;

    public Equation(double x0, double y0, double xn, double h, String function){

        this.h=Math.pow(h, 0.25);
        iterations = (int)(Math.ceil((xn-x0)/this.h));
        valueTable = new double[iterations+1][2];
        valueTable[0][0]= x0;
        valueTable[0][1]= y0;
        this.xn=xn;
        this.function=function;
        for (int i=1; i<iterations; i++){
            valueTable[i][0] = Precision.round(valueTable[i-1][0]+this.h,6);
        }
        valueTable[iterations][0]=xn;
    }

    public double getValueFunction(double x, double y) {
        try {
            Expression e = new ExpressionBuilder(function)
                    .variables("x", "y")
                    .build()
                    .setVariable("x", x)
                    .setVariable("y", y);
            return (e.evaluate());
        } catch (java.lang.ArithmeticException e){
            return 0;
        }
    }

    public int getIterations() {
        return iterations;
    }

    public double[][] getValueTable() {
        return valueTable;
    }

    public void showTable(){
        for (double[] doubles : valueTable) System.out.println(doubles[0] + " " + doubles[1]);
    }

    public double getXn() {
        return xn;
    }

    public void setXn(double xn) {
        this.xn = xn;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setValueTable(double[][] valueTable) {
        this.valueTable = valueTable;
    }
}
