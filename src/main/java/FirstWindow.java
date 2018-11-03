import example.Jmetal_cst;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow {
    private JButton nextFirstWindow;
    private JPanel panel1;
    private JTextField numOfVariables;
    private JComboBox algorithmType;
    private JComboBox variableType;

    public FirstWindow() {
        nextFirstWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Jmetal_cst cst = new Jmetal_cst();
                    cst.run();

                    // create chart
                    Chart chart = new Chart(null,null);
                    chart.run();

                    // show message dialog
                    JOptionPane.showMessageDialog(null,"Hello jMetal\n" +
                            "Algorithm executed\n" +
                            "Computing time took: " + Long.toString(cst.computingTime)
                    );


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("jMetal GUI");
        //frame.setSize(200,100);
        frame.setContentPane(new FirstWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
