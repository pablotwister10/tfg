import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecondWindow {
    private JPanel panel2;
    private JTextField nameOfVariables;
    private JTextField intervalOfVariables;
    private JButton nextSecondWindow;
    private JButton previousSecondWindow;
    private JPanel parameterPanel;
    private JPanel navigationPanel;
    private JComboBox algorithmType;
    private JComboBox variableType;

    public SecondWindow() {
        nextSecondWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThirdWindow.run();
            }
        });

        previousSecondWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FirstWindow.run();
            }
        });
    }

    public static void run() {
        FirstWindow.frame.setContentPane(new SecondWindow().panel2);
        FirstWindow.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //FirstWindow.frame.pack();
        FirstWindow.frame.setVisible(true);
    }
}
