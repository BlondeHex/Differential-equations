package model;

public class Function {

    private String function;
    private Type type;
    private double [] [] data;

    Function(String function, Type type, double[][] data) {
        this.function = function;
        this.type = type;
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
}
