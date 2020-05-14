package views;
import javax.swing.*;
import java.awt.*;

public class MainView {
    private JFrame window;
    private JPanel jPanel;
    private int width;
    private int height;
    private JTextField functionField;
    private JTextField beginXField;
    private JTextField beginYField;
    private JTextField endXField;
    private JTextField accuracyField;
    private JButton button;


    public MainView(int width, int height){

        this.width=width;
        this.height=height;
        setElement();

        window = new JFrame("Educational work №4");
        window.getContentPane().add(jPanel);
        setSetting();

    }
    private void setSetting(){
        window.setVisible(true);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        window.setBounds(dimension.width/2-width/2,dimension.height/2-height/2,width,height);
    }

    private void setElement(){
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();

        JLabel descriptionFunctionLabel = new JLabel("y + f(x,y) = 0");
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

        button = new JButton("Ок");

        jPanel.add(descriptionFunctionLabel);

        baseConstraints.gridy = 1;
        baseConstraints.gridx = 0;
        jPanel.add(functionLabel, baseConstraints);
        baseConstraints.gridy = 1;
        baseConstraints.gridx = 1;
        jPanel.add(functionField, baseConstraints);

        baseConstraints.gridx = 0;
        baseConstraints.gridy = 2;
        jPanel.add(descriptionBeginLabel, baseConstraints);
        baseConstraints.gridy = 3;
        baseConstraints.gridx = 0;
        jPanel.add(beginXLabel, baseConstraints);
        baseConstraints.gridy = 3;
        baseConstraints.gridx = 1;
        jPanel.add(beginXField, baseConstraints);
        baseConstraints.gridy = 3;
        baseConstraints.gridx = 2;
        jPanel.add(beginYLabel, baseConstraints);
        baseConstraints.gridx = 3;
        jPanel.add(beginYField, baseConstraints);

        baseConstraints.gridy = 4;
        baseConstraints.gridx = 0;
        jPanel.add(endXLabel, baseConstraints);
        baseConstraints.gridy = 4;
        baseConstraints.gridx = 1;
        jPanel.add(endXField, baseConstraints);

        baseConstraints.gridy = 5;
        baseConstraints.gridx = 0;
        jPanel.add(accuracyLabel, baseConstraints);
        baseConstraints.gridy = 5;
        baseConstraints.gridx = 1;
        jPanel.add(accuracyField, baseConstraints);

        baseConstraints.gridy = 6;
        baseConstraints.gridx = 3;
        jPanel.add(button, baseConstraints);
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
}
