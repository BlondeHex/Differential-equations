package views;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainView {
    private JLabel errorLabel;
    private JPanel view;
    private JScrollPane panelTable;
    private JPanel panelChart;
    private JTextField functionField;
    private JTextField beginXField;
    private JTextField beginYField;
    private JTextField endXField;
    private JTextField accuracyField;
    private JButton button;


    public MainView(int width, int height){

        setElement();

        JFrame window = new JFrame("Educational work №4");
        window.getContentPane().add(view);
        setSetting(width,height,window);

    }
    private void setSetting(int width, int height, JFrame window){
        window.setVisible(true);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        window.setBounds(dimension.width/2-width/2,dimension.height/2-height/2,width,height);
    }

    private void setElement(){
        view = new JPanel();
        view.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();
        JPanel inputPanel = setInputPanel();

        panelTable = initTable(new double[][]{});

        panelChart = new JPanel();
        panelChart.setLayout(new GridBagLayout());

        baseConstraints.gridwidth=2;
        view.add(inputPanel, baseConstraints);
        baseConstraints.gridy = 1;
        baseConstraints.gridx = 0;
        view.add(panelTable, baseConstraints);
        baseConstraints.gridx = 1;
        view.add(panelChart, baseConstraints);
    }

    private JPanel setInputPanel(){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();

        JLabel descriptionFunctionLabel = new JLabel("y' = f(x,y) ");
        descriptionFunctionLabel.setFont(new Font(descriptionFunctionLabel.getFont().getName(), Font.BOLD, 14));
        JLabel functionLabel = new JLabel("f(x,y) =");
        functionField = new JTextField(15);

        JLabel descriptionBeginLabel = new JLabel("y0 = y(x0)");
        descriptionBeginLabel.setFont(new Font(descriptionBeginLabel.getFont().getName(), Font.BOLD, 14));
        JLabel beginXLabel = new JLabel("x0 =");
        beginXField = new JTextField(15);
        JLabel beginYLabel = new JLabel("y0 =");
        beginYField = new JTextField(15);

        JLabel endXLabel = new JLabel("Конец отрезка: ");
        endXField = new JTextField(15);

        JLabel accuracyLabel = new JLabel("Точность: ");
        accuracyField = new JTextField(15);

        button = new JButton("Построить график");

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);

        inputPanel.add(descriptionFunctionLabel);

        baseConstraints.gridy = 1;
        baseConstraints.gridx = 0;
        inputPanel.add(functionLabel, baseConstraints);
        baseConstraints.gridy = 1;
        baseConstraints.gridx = 1;
        inputPanel.add(functionField, baseConstraints);

        baseConstraints.gridx = 0;
        baseConstraints.gridy = 2;
        inputPanel.add(descriptionBeginLabel, baseConstraints);
        baseConstraints.gridy = 3;
        baseConstraints.gridx = 0;
        inputPanel.add(beginXLabel, baseConstraints);
        baseConstraints.gridy = 3;
        baseConstraints.gridx = 1;
        inputPanel.add(beginXField, baseConstraints);
        baseConstraints.gridy = 3;
        baseConstraints.gridx = 2;
        inputPanel.add(beginYLabel, baseConstraints);
        baseConstraints.gridx = 3;
        inputPanel.add(beginYField, baseConstraints);

        baseConstraints.gridy = 4;
        baseConstraints.gridx = 0;
        inputPanel.add(endXLabel, baseConstraints);
        baseConstraints.gridy = 4;
        baseConstraints.gridx = 1;
        inputPanel.add(endXField, baseConstraints);

        baseConstraints.gridy = 5;
        baseConstraints.gridx = 0;
        inputPanel.add(accuracyLabel, baseConstraints);
        baseConstraints.gridy = 5;
        baseConstraints.gridx = 1;
        inputPanel.add(accuracyField, baseConstraints);

        baseConstraints.gridy = 6;
        baseConstraints.gridx = 1;
        inputPanel.add(button, baseConstraints);

        baseConstraints.gridy = 7;
        baseConstraints.gridx = 1;
        inputPanel.add(errorLabel, baseConstraints);
        return inputPanel;
    }

    public JScrollPane initTable(double[][] data) {
        //Vector of swing documentation
        int size = data.length;
        Vector columnNames = new Vector<String>(3);
        columnNames.addElement("№");
        columnNames.addElement("X");
        columnNames.addElement("Y");

        Vector<Vector<String>> dataTable = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            Vector preData = new Vector<String>(3);
            preData.addElement(i+1);
            preData.addElement(data[i][0]);
            preData.addElement(data[i][1]);
            dataTable.addElement(preData);
        }

        JTable valueTable = new JTable(dataTable, columnNames);
        valueTable.getTableHeader().setOpaque(false);
        valueTable.setEnabled(false);
        JPanel panelWrapperTable = new JPanel();
        panelWrapperTable.setLayout(new BorderLayout());
        panelWrapperTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelWrapperTable.add(valueTable.getTableHeader(), BorderLayout.NORTH);
        panelWrapperTable.add(valueTable, BorderLayout.CENTER);

        JScrollPane scroller = new JScrollPane(panelWrapperTable);
        int height = 45;
        if (data.length!=0) {
            if (data.length - 1 < 20) {
                height += (data.length - 1) * 16;
            } else {
                height += 20 * 16;
            }
        }
        scroller.setPreferredSize(new Dimension(300, height));
        scroller.setBorder(null);
        return scroller;
    }

    public JScrollPane getPanelTable() {
        return panelTable;
    }

    public void setPanelTable(JScrollPane panelTable) {
        this.panelTable = panelTable;
    }


    public JPanel getView() {
        return view;
    }

    public JButton getButton() {
        return button;
    }

    public JTextField getAccuracyField() {
        return accuracyField;
    }

    public JTextField getBeginXField() {
        return beginXField;
    }

    public JTextField getBeginYField() {
        return beginYField;
    }

    public JTextField getEndXField() {
        return endXField;
    }

    public JTextField getFunctionField() {
        return functionField;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public JPanel getPanelChart() {
        return panelChart;
    }

}
