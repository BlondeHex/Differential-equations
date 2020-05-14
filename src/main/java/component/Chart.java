package component;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Chart extends JPanel {
    private int arrowSize;
    private int plotWidth;
    private int plotHeight;
    private double coefficientX;
    private double coefficientY;
    private int centerX;
    private int centerY;
    private int stepX=1;
    private int offsetX=20;
    private int offsetY=20;
    private Expression formula1;
    private Double[][] points;

    private Double minY = Double.MAX_VALUE;
    private Double minX = Double.MAX_VALUE;
    private Double maxY = Double.MIN_VALUE;
    private Double maxX = Double.MIN_VALUE;

    public Chart(int width, int height, String formula1, Double[][] points) {
        this.formula1 = new ExpressionBuilder(formula1).variable("x").build();
        this.plotWidth = width;
        this.plotHeight = height;
        this.centerX = width / 2 - offsetX;
        this.centerY = height / 2 - offsetY;
        this.points = points;
        this.arrowSize = 5;
        calculateBoundsAndCoefficients();
    }

    private void calculateBoundsAndCoefficients() {
        for (int i=0; i < points.length-1; i++) {
            if (points[i][0] > maxX) {
                maxX = points[i][0];
            }
            if (points[i][0] < minX) {
                minX = points[i][0];
            }
            if (points[i][1] > maxY) {
                maxY = points[i][1];
            }
            if (points[i][1] < minY) {
                minY = points[i][1];
            }
        }

        coefficientX = ((double)plotWidth/2 - offsetX) / max(abs(maxX), abs(minX));
        coefficientY = ((double)plotHeight/2 - offsetY) / max(abs(maxY), abs(minY));
    }

    protected void paintComponent(@Nullable Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, this.plotWidth, this.plotHeight);
        graphics.setColor(Color.GRAY);
        graphics.drawLine(0, this.centerY, this.plotWidth, this.centerY);
        graphics.drawLine(this.centerX, 0, this.centerX, this.plotHeight);
        graphics.drawLine(this.plotWidth, this.centerY, this.plotWidth - this.arrowSize, this.centerY - this.arrowSize);
        graphics.drawLine(this.plotWidth, this.centerY, this.plotWidth - this.arrowSize, this.centerY + this.arrowSize);
        graphics.drawString("X", this.plotWidth - 10, this.centerY - 10);
        graphics.drawString("Y", this.centerX + 10, 10);
        graphics.drawLine(this.centerX, 0, this.centerX + this.arrowSize, this.arrowSize);
        graphics.drawLine(this.centerX, 0, this.centerX - this.arrowSize, this.arrowSize);
        int distanceXFromCenter  = this.centerX + (int) this.coefficientX /2;
        int distanceYFromCenter = this.centerY - (int) this.coefficientY /2;
        double xCounter = 0.5;
        double yCounter = 0.5;
        boolean isNumberShouldShowLeft = true;

        while (distanceXFromCenter<this.plotWidth) {
            if (isNumberShouldShowLeft) {
                graphics.drawString(xCounter + "", distanceXFromCenter, this.centerY - 5);
                isNumberShouldShowLeft = false;
            } else {
                graphics.drawString(xCounter + "", distanceXFromCenter, this.centerY + 15);
                isNumberShouldShowLeft = true;

            }
            xCounter+=0.5;
            distanceXFromCenter+=(int) (this.coefficientX /2);
        }

        while (distanceYFromCenter>0) {
            if (isNumberShouldShowLeft) {
                isNumberShouldShowLeft = false;
                graphics.drawString(yCounter + "", this.centerX - 24, distanceYFromCenter);
            } else {
                graphics.drawString(yCounter + "", this.centerX + 4, distanceYFromCenter);
                isNumberShouldShowLeft = true;

            }
            yCounter+=0.5;
            distanceYFromCenter-=(int) (this.coefficientY /2);
        }

        graphics.translate(this.centerX, this.centerY);
        int i = 0;


        for (int var3 = this.points.length-1; i < var3; ++i) {
            int x = (int) (this.points[i][0] * this.coefficientX);
            int y = -((int) (this.points[i][1] * this.coefficientY));

            graphics.fillArc(x - 3, y - 3, 6, 6, 0, 360);

        }

        double prevX = (double) (-this.centerX) / this.coefficientX;
        double prevY1 = -this.formula1.setVariable("x", prevX).evaluate();
        int fromX = -this.centerX;
        int toX = Math.abs(this.centerX) + this.plotWidth;

        for(int x = fromX; x<toX; x+=stepX)  {
            double tx = (double) x / this.coefficientX;
            Double ty1 = null;
            Double ty2 = null;

            try {
                ty1 = -this.formula1.setVariable("x", tx).evaluate();
            } catch (Exception var18) {
                ty1 = -this.formula1.setVariable("x", tx + 1.0E-5D).evaluate();
            }

            graphics.setColor(Color.GREEN);
            graphics.drawLine((int) (prevX * this.coefficientX), (int) (prevY1 * this.coefficientY), (int) (tx * this.coefficientX), (int) (ty1 * this.coefficientY));
            graphics.setColor(Color.BLUE);
            prevX = tx;
            prevY1 = ty1;
        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(this.plotWidth, this.plotHeight);
    }

    public double getCoefficientX() {
        return this.coefficientX;
    }

    public void setCoefficientX(double var1) {
        this.coefficientX = var1;
    }

    public double getCoefficientY() {
        return this.coefficientY;
    }

    public void setCoefficientY(double var1) {
        this.coefficientY = var1;
    }

    public int getCenterX() {
        return this.centerX;
    }

    public void setCenterX(int var1) {
        this.centerX = var1;
    }

    public void setCenterY(int var1) {
        this.centerY = var1;
    }

}
