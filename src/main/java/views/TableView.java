package views;

import component.Chart;
import model.ApproximationMethod;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TableView {
    private JFrame window;
    private JPanel jPanel;

    public TableView(Double[][] data){
        setElement(data);

        window = new JFrame();
        setSetting();
        window.add(jPanel);
    }

    private void setSetting(){
        window.setVisible(true);
        window.setBounds(100,0,400,1500);
    }

    private void setElement(Double[][] data){
        jPanel = new JPanel();
        jPanel.add(initTable(data));
    }

    private JPanel initTable(Double[][] data) {
        Vector columnNames =  new Vector<String>(3);
        columnNames.add("№");
        columnNames.add("X");
        columnNames.add("Y");

        Vector<Vector<String>> dataTable = new Vector<>(data.length+1);

        Vector header = new Vector<String>(3);
        header.addElement("№");
        header.addElement("x");
        header.addElement("y");
        dataTable.addElement(header);

        for (int i = 0; i< data.length-1; i++){
            Vector preData = new Vector<String>(3);
            preData.addElement(i);
            preData.addElement(data[i][0]);
            preData.addElement(data[i][1]);
            dataTable.addElement(preData);
        }

        JTable table = new JTable(dataTable, columnNames);
        table.setDefaultEditor(Object.class, null);
        jPanel = new JPanel();
        jPanel.add(table);
        return jPanel;
    }
}
