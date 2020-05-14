package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ApproximationMethod {

    public String run(Double[][] data){
        double minSquare= 1000;
        String function = "";

        String linearFunction = linear(data);
        double linearFunctionSquare = findSquareDeviations(data, linearFunction);
        if (minSquare>linearFunctionSquare){
            function = linearFunction;
            minSquare = linearFunctionSquare;
        }

        String powerFunction = power(data);
        double powerFunctionSquare = findSquareDeviations(data, powerFunction);
        if (minSquare>powerFunctionSquare){
            function = linearFunction;
            minSquare = linearFunctionSquare;
        }

        String squareFunction = square(data);
        double squareFunctionSquare = findSquareDeviations(data, squareFunction);
        if (minSquare>squareFunctionSquare){
            function = squareFunction;
            minSquare = squareFunctionSquare;
        }

        String hyperbolaFunction = hyperbola(data);
        double hyperbolaFunctionSquare = findSquareDeviations(data, hyperbolaFunction);
        if (minSquare>hyperbolaFunctionSquare)
            function = hyperbolaFunction;
        return function;

    }

    private double findSquareDeviations(Double[][] data, String function){
        double sum = 0;
        for (int i=0; i<data.length; i++)
            sum += Math.pow(data[i][1]-getValueFunction(function,data[i][0]),2);
        return sum;
    }

    private double getValueFunction(String function, double x){
        if (!function.contains("NaN")) {
            try {
                Expression e = new ExpressionBuilder(function)
                        .variables("x")
                        .build()
                        .setVariable("x", x);
                return (e.evaluate());
            } catch (java.lang.ArithmeticException e) {
                return 100;
            }
        } else {
            return 100;
        }
    }

    private String linear(Double[][] data){
        Double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            sum[0] += data[i][0]; //x
            sum[1] += data[i][1]; //y
            sum[2] += sum[0] * sum[1]; //xy
            sum[3] += Math.pow(sum[0], 2.0); //x2
        }
        double del = Math.pow(sum[0], 2.0) - data.length * sum[3];
        double a = (sum[0] * sum[1] - data.length * sum[2]) / del;
        double b = (sum[0] * sum[2] - sum[3] * sum[1]) / del;
        if (b >= 0) {
            return a+"*x+"+b;
        } else {
            return a+"*x"+b;
        }
    }

    private String square(Double[][] data) {
        Double[] sum = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sum[0] += x; //x
            sum[1] += y; //y
            sum[2] += Math.pow(x, 2.0); //x2
            sum[3] += Math.pow(x, 3.0); //x3
            sum[4] += Math.pow(x, 4.0);//x4
            sum[5] += x * y; //xy
            sum[6] += Math.pow(x, 2.0) * y; //x2y
        }
        Double[][] matrix = new Double[3][3];
        matrix[0][0] = sum[2];
        matrix[0][1] = sum[0];
        matrix[0][2] = data.length * 1.0;
        matrix[1][0] = sum[3];
        matrix[1][1] = sum[2];
        matrix[1][2] = sum[0];
        matrix[2][0] = sum[4];
        matrix[2][1] = sum[3];
        matrix[2][2] = sum[2];
        Double d = determinant(matrix);

        matrix[0][0] = sum[1];
        matrix[1][0] = sum[5];
        matrix[2][0] = sum[6];
        Double da = determinant(matrix);

        matrix[0][0] = sum[2];
        matrix[1][0] = sum[3];
        matrix[2][0] = sum[4];
        matrix[0][1] = sum[1];
        matrix[1][1] = sum[5];
        matrix[2][1] = sum[6];
        Double db = determinant(matrix);

        matrix[0][1] = sum[0];
        matrix[1][1] = sum[2];
        matrix[2][1] = sum[3];
        matrix[0][2] = sum[1];
        matrix[1][2] = sum[5];
        matrix[2][2] = sum[6];
        Double dc = determinant(matrix);

        double a = da / d;
        double b = db / d;
        double c = dc / d;
        String result = a+"*x^2";
        if (b >= 0) {
            result += "+"+b+"*x";
        } else {
            result += b+"*x";
        }
        if (c >= 0) {
            result += "+"+c;
        } else {
            result += c;
        }
        return result;
    }

    private String power(Double[][] data) {
        Double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sum[0] += Math.log(x); //x
            sum[1] += Math.log(y); //y
            sum[2] += Math.pow(Math.log(x), 2); //x2
            sum[3] += Math.log(x) * Math.log(y); //xy
        }

        double b = (data.length * sum[3] - sum[0] * sum[1]) / (data.length * sum[2] - Math.pow(sum[0],2));
        double a = Math.exp(1.0 / data.length * sum[1] - b / data.length * sum[0]);
        return a+ "* x ^ "+b;
    }

    private String hyperbola(Double[][] data) {
        Double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sum[0] += 1 / x; //x
            sum[1] += 1 / Math.pow(x, 2.0); //x2
            sum[2] += y / x; //yx
            sum[3] += y; //y
        }

        double b = (data.length * sum[2] - sum[0] * sum[3]) / (data.length * sum[1] - Math.pow(sum[0], 2));
        double a = 1.0 / data.length * sum[3] - b / data.length * sum[0];
        if (b > 0) {
            return a + "+" + b + "/x";
        } else {
            return a + "" + b + "/x";
        }
    }


    private double determinant(Double[][] a) {
        double sum = a[0][0]*(a[1][1]*a[2][2]-a[1][2]*a[2][1]);
        sum-= a[1][0]*(a[0][1]*a[2][2]-a[0][2]*a[2][1]);
        sum+= a[2][0]*(a[0][1]*a[1][2]-a[0][2]*a[1][1]);
        return sum;
    }
}
