import example.Jmetal_cst;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThirdWindow {
    private JPanel panel3;
    private JPanel objFunPanel;
    private JPanel navigationPanel;
    private JButton nextThirdWindow;
    private JButton previousThirdWindow;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel populationPanel;
    private JTextField textField3;
    private JTextField textField4;

    public ThirdWindow() {
        nextThirdWindow.addActionListener(new ActionListener() {
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

        previousThirdWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SecondWindow.run();
            }
        });
    }

    public static void run() {
        FirstWindow.frame.setContentPane(new ThirdWindow().panel3);
        FirstWindow.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //FirstWindow.frame.pack();
        FirstWindow.frame.setVisible(true);
    }
}
