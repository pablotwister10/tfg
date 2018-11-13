package gui;

import example.Jmetal_cst;

import javax.swing.*;

public class FirstWindow extends JFrame {

    static JFrame frame;
    private JPanel panel;

    // FIRST WINDOW
    private JPanel panelFirst;
    private JPanel navigationPanelFirst;
    private JPanel parameterPanelFirst;
    private JButton nextFirst;
    private JTextField numOfVariablesFirst;
    private JTextField numOfObjFunctFirst;
    private JComboBox algorithmTypeFirst;
    private JComboBox variableTypeFirst;

    private static int numOfVariablesFirstInt;
    private static int numOfObjFunctFirstInt;
    private static String algorithmTypeFirstString;
    private static String variableTypeFirstString;

    // SECOND WINDOW
    private JPanel panelSecond;
    private JPanel navigationPanelSecond;
    private JPanel parameterPanelSecond;
    private JTextField nameOfVariables;
    private JTextField intervalOfVariables;
    private JButton nextSecond;
    private JButton previousSecond;

    static String[] nameOfVariablesString;
    //ArrayList<T> intervalOfVariablesType;

    // THIRD WINDOW
    private JPanel panelThird;
    private JPanel parameterPanelThird;
    private JPanel navigationPanelThird;
    private JPanel objectiveFunctPanelThird;
    private JPanel populationPanelThird;
    private JButton previousThird;
    private JButton nextThird;
    private JTextField objectiveFunctionOne;
    private JTextField objectiveFunctionTwo;
    private JTextField objectiveFunctionThird;
    private JTextField objectiveFunctionFour;
    private JTextField maxEvaluations;
    private JTextField populationSize;

    private static int maxEvaluationsInt;
    private static int populationSizeInt;


    private FirstWindow() {

        // TODO: In nextViews implement error handling
        // TODO: Replace isEmpty() with isBlank() where needed
        // TODO: Check user input is correct type

        nextFirst.addActionListener(e -> {
            if (numOfVariablesFirst.getText().isEmpty()) { // TODO: Change with isBlank()
                // Error
                JOptionPane.showMessageDialog(null,
                        "Enter Number of Variables");
            }
            else if (numOfObjFunctFirst.getText().isEmpty()) { // TODO: Change with isBlank()
                // Error
                JOptionPane.showMessageDialog(null,
                        "Enter Number of Objective Functions");
            }
            else if (Integer.valueOf(numOfObjFunctFirst.getText()) < 1 ||
                    Integer.valueOf(numOfObjFunctFirst.getText()) > 4) {
                JOptionPane.showMessageDialog(null,
                        "Number of Objective Functions has to be between 1 and 4");
            }
            else {
                saveFirstView();
                loadSecondView();
                //Chart chart = new Chart(null,null);
                //chart.run();
            }
        });

        previousSecond.addActionListener(e -> {
            loadFirstView();
        });

        nextSecond.addActionListener(e -> {
            if (nameOfVariables.getText().isEmpty()) { // TODO: Change with isBlank()
                // Error
                JOptionPane.showMessageDialog(null, "Enter Name of Variables");
            }
            else if (intervalOfVariables.getText().isEmpty()) { // TODO: Change with isBlank()
                // Error
                JOptionPane.showMessageDialog(null,"Enter Interval of Variables");
            }
            else {
                saveSecondView();
                loadThirdView();
            }
        });

        previousThird.addActionListener(e -> {
            loadSecondView();
        });

        nextThird.addActionListener(e -> {
            if (maxEvaluations.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Enter Maximum Evaluations");
            }
            else if (Integer.valueOf(maxEvaluations.getText()) < 0) {
                JOptionPane.showMessageDialog(null,"Maximum Evaluations has to be a positive integer");
            }
            else if (populationSize.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Enter Population Size");
            }
            else if (Integer.valueOf(populationSize.getText()) < 0) {
                JOptionPane.showMessageDialog(null,"Population Size has to be a positive integer");
            }
            else {
                saveThirdView();
                try {
                    Jmetal_cst cst = new Jmetal_cst();
                    cst.run();
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


    public static int getNumOfVariablesFirstInt() {
        return numOfVariablesFirstInt;
    }
    public static int getNumOfObjFunctFirstInt() {
        return numOfObjFunctFirstInt;
    }
    public static String[] getNameOfVariablesString() {
        return nameOfVariablesString;
    }
    public static int getMaxEvaluationsInt() {
        return maxEvaluationsInt;
    }
    public static int getPopulationSizeInt() {
        return populationSizeInt;
    }


    private void saveFirstView() {
        numOfVariablesFirstInt = Integer.valueOf(numOfVariablesFirst.getText());
        numOfObjFunctFirstInt = Integer.valueOf(numOfObjFunctFirst.getText());
        algorithmTypeFirstString = algorithmTypeFirst.toString();
        variableTypeFirstString = variableTypeFirst.toString();
    }

    private void saveSecondView() {

    }

    private void saveThirdView() {
        maxEvaluationsInt = Integer.valueOf(maxEvaluations.getText());
        populationSizeInt = Integer.valueOf(populationSize.getText());
    }

    private void loadFirstView() {
        panelFirst.setVisible(true);
        panelSecond.setVisible(false);
        panelThird.setVisible(false);
    }

    private void loadSecondView() {
        // Clearing out the setEditable for ThirdView
        objectiveFunctionFour.setEditable(true);
        objectiveFunctionThird.setEditable(true);
        objectiveFunctionTwo.setEditable(true);

        panelFirst.setVisible(false);
        panelSecond.setVisible(true);
        panelThird.setVisible(false);
    }

    private void loadThirdView() {
        if (numOfObjFunctFirstInt < 4)
            objectiveFunctionFour.setEditable(false);
        if (numOfObjFunctFirstInt < 3)
            objectiveFunctionThird.setEditable(false);
        if (numOfObjFunctFirstInt < 2)
            objectiveFunctionTwo.setEditable(false);

        panelFirst.setVisible(false);
        panelSecond.setVisible(false);
        panelThird.setVisible(true);
    }

    public static void main(String[] args) {
        frame = new JFrame("jMetal GUI");
        frame.setSize(500,300);
        FirstWindow activity = new FirstWindow();
        frame.setContentPane(activity.panel);
        //frame.pack();

        activity.panelSecond.setVisible(false);
        activity.panelThird.setVisible(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
