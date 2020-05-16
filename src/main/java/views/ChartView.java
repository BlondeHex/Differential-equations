package views;

import component.Chart;
import model.ApproximationMethod;

import javax.swing.*;
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
        jPanel.add(new Chart(640,640, function, data));
    }

}
