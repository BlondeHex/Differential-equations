package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ApproximationMethod {

    public String run(double[][] data){
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
            function = powerFunction;
            minSquare = powerFunctionSquare;
        }

        String squareFunction = square(data);
        double squareFunctionSquare = findSquareDeviations(data, squareFunction);
        if (minSquare>squareFunctionSquare){
            function = squareFunction;
            minSquare = squareFunctionSquare;
        }

        String logFunction = log(data);
        double logFunctionSquare = findSquareDeviations(data, logFunction);
        if (minSquare>logFunctionSquare){
            function = logFunction;
            minSquare = logFunctionSquare;
        }

        String expFunction = exp(data);
        double expFunctionSquare = findSquareDeviations(data, expFunction);
        if (minSquare>expFunctionSquare){
            function = expFunction;
            minSquare = expFunctionSquare;
        }

        String hyperbolaFunction = hyperbola(data);
        double hyperbolaFunctionSquare = findSquareDeviations(data, hyperbolaFunction);
        if (minSquare>hyperbolaFunctionSquare)
            function = hyperbolaFunction;
        return function;

    }

    private double findSquareDeviations(double[][] data, String function){
        double sum = 0;
        for (double[] datum : data) sum += Math.pow(datum[1] - getValueFunction(function, datum[0]), 2);
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

    private String linear(double[][] data){
        double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (double[] datum : data) {
            sum[0] += datum[0]; //x
            sum[1] += datum[1]; //y
            sum[2] += datum[0] * datum[1]; //xy
            sum[3] += Math.pow(datum[0], 2.0); //x2
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

    private String square(double[][] data) {
        double[] sum = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        for (double[] datum : data) {
            double x = datum[0];
            double y = datum[1];
            sum[0] += x; //x
            sum[1] += y; //y
            sum[2] += Math.pow(x, 2.0); //x2
            sum[3] += Math.pow(x, 3.0); //x3
            sum[4] += Math.pow(x, 4.0);//x4
            sum[5] += x * y; //xy
            sum[6] += Math.pow(x, 2.0) * y; //x2y
        }
        double[][] matrix = new double[3][3];
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

    private String power(double[][] data) {
        double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (double[] datum : data) {
            double x = datum[0];
            double y = datum[1];
            sum[0] += Math.log(x); //x
            sum[1] += Math.log(y); //y
            sum[2] += Math.pow(Math.log(x), 2); //x2
            sum[3] += Math.log(x) * Math.log(y); //xy
        }

        double b = (data.length * sum[3] - sum[0] * sum[1]) / (data.length * sum[2] - Math.pow(sum[0],2));
        double a = Math.exp(1.0 / data.length * sum[1] - b / data.length * sum[0]);
        return a+ "* x ^ "+b;
    }

    private String hyperbola(double[][] data) {
        double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (double[] datum : data) {
            double x = datum[0];
            double y = datum[1];
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


    private String log(double[][] data) {
        double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (double[] datum : data) {
            double x = datum[0];
            double y = datum[1];
            sum[0] += y * Math.log(x); //ylnx
            sum[1] += Math.log(x); //lnx
            sum[2] += y; //y
            sum[3] += Math.pow(Math.log(x),2); //ln2x
        }

        double b =  (data.length * sum[0] - sum[1] * sum[2]) / (data.length * sum[3] - Math.pow(sum[1],2));
        double a =  1.0/data.length * sum[2] - b/data.length * sum[1];
        String result =a+"";
        if (b >= 0) {
            result+="+"+b +"*log(x)";
        } else if (b!=0) {
            result+=b+"*log(x)";
        }
        return result;
    }

    private String exp(double[][] data) {
        double[] sum = {0.0, 0.0, 0.0, 0.0};

        for (double[] datum : data) {
            double x = datum[0];
            double y = datum[1];
            sum[0] += x * Math.log(y); //xlny
            sum[1] += x; //x
            sum[2] += Math.log(y); //lny
            sum[3] +=Math.pow(x,2); //x2
        }

        double b =  (data.length * sum[0] - sum[1] * sum[2]) / (data.length * sum[3] - Math.pow(sum[1],2));
        double a =  1.0/data.length * sum[2] - b/data.length * sum[1];
        String result ="e^("+a;
        if (b >= 0) {
            result+="+"+b+"* x)";
        } else if (b!=0){
            result+=b +"* x)";
        }
        return result;
    }


    private double determinant(double[][] a) {
        double sum = a[0][0]*(a[1][1]*a[2][2]-a[1][2]*a[2][1]);
        sum-= a[1][0]*(a[0][1]*a[2][2]-a[0][2]*a[2][1]);
        sum+= a[2][0]*(a[0][1]*a[1][2]-a[0][2]*a[1][1]);
        return sum;
    }
}
