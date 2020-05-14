package views;

import component.Chart;
import model.ApproximationMethod;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

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
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        window.setBounds(300,300,600,600);
    }

    private void setElement(Double[][] data){
        jPanel = new JPanel();
        jPanel.add(initTable(data));
        ApproximationMethod approximationMethod = new ApproximationMethod();
        String function = approximationMethod.run(data);
        jPanel.add(new Chart(640,640, function, data));
    }

    private JTable initTable(Double[][] data) {
        Vector columnNames =  new Vector<String>(3);
        columnNames.add("№");
        columnNames.add("X");
        columnNames.add("Y");

        Vector<Vector<String>> dataTable = new Vector<>(data.length);
        for (int i = 0; i< data.length; i++){
            Vector preData = new Vector<String>(3);
            preData.addElement(i);
            preData.addElement(data[i][0]);
            preData.addElement(data[i][1]);
            dataTable.addElement(preData);
        }

        JTable table = new JTable(dataTable, columnNames);
        table.setDefaultEditor(Object.class, null);
        return table;
    }
}
