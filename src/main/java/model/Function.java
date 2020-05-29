package model;

public class Function {

    private String function;
    private Type type;
    private double minSquare;
    private double [] [] data;

    public Function(String function, Type type, double minSquare, double[][] data) {
        this.function = function;
        this.type = type;
        this.minSquare = minSquare;
        this.data = data;
    }

    public String getFunction() {
        return function;
    }

    public Type getType() {
        return type;
    }

    public double[][] getData() {
        return data;
    }

    public double getMinSquare() {
        return minSquare;
    }
}
