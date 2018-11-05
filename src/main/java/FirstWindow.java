import example.Jmetal_cst;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow {
    private JButton nextFirstWindow;
    private JButton button1;
    private JPanel panel1;
    private JTextField numOfVariables;
    private JTextField numOfObjFunct;
    private JComboBox algorithmType;
    private JComboBox variableType;
    private JPanel navigationPanel;
    private JPanel parameterPanel;

    static JFrame frame;

    public FirstWindow() {
        nextFirstWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container.numOfVariablesString = numOfVariables.getText();
                Container.numOfObjFunctString = numOfObjFunct.getText();
                SecondWindow.run();
            }
        });
    }

    public static void run() {
        frame.setContentPane(new FirstWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
    }

    public void save() {
        Container.numOfObjFunctString = numOfObjFunct.toString();
        Container.numOfVariablesString = numOfVariables.toString();
    }

    public void load() {
        frame.setContentPane(new FirstWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
        this.numOfVariables.setText(Container.numOfVariablesString);
        this.numOfObjFunct.setText(Container.numOfObjFunctString);
    }

    public static void main(String[] args) {
        frame = new JFrame("jMetal GUI");
        frame.setSize(500,300);
        frame.setContentPane(new FirstWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
    }
}
