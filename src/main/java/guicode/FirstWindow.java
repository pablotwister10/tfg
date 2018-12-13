package guicode;

import example.Jmetal_cst;
import example.Jmetal_cst_Integer;
import org.uma.jmetal.solution.DoubleSolution;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class FirstWindow extends JFrame {

    // TODO: Get rid of duplication of memory

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
    private static boolean createSecondView = true;
    private JPanel panelSecond;
    private JPanel navigationPanelSecond;
    private JPanel parameterPanelSecond;
    private JButton nextSecond;
    private JButton previousSecond;

    private static final String NAME_OF_VARIABLES = "Name of Variables";
    private static final String MIN_INTERVAL_OF_VARIABLES = "Min of Interval";
    private static final String MAX_INTERVAL_OF_VARIABLES = "Max of Interval";
    private static final String INCREMENT_OF_VARIABLE = "Paso";
    private static Vector<String> nameOfVariablesString = new Vector<String>(0);
    private static Vector<Double> minIntervalOfVariablesDouble = new Vector<Double>(0);
    private static Vector<Double> maxIntervalOfVariablesDouble = new Vector<Double>(0);
    private static Vector<Double> intervalOfVariablesType = new Vector<Double>(0);
    // intervals to int
    private static Vector<Integer> yMinOfVariables = new Vector<Integer>(0);
    private static Vector<Integer> yMaxOfVariables = new Vector<Integer>(0);
    private static Vector<Integer> yIntervalOfVariables = new Vector<Integer>(0);

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
    private JCheckBox checkGraph;

    private static int maxEvaluationsInt;
    private static int populationSizeInt;
    private static Vector<String> costFunctionsString = new Vector<String>(0);
    private static boolean doGraph;


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
                if (createSecondView)
                    createSecondView();
                else
                    loadSecondView();
                //Chart chart = new Chart(null,null);
                //chart.run();
            }
        });

        previousSecond.addActionListener(e -> {
            loadFirstView();
        });

        nextSecond.addActionListener(e -> {
            try {
                saveSecondView();
                loadThirdView();
            } catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null,
                        "MAKE SURE EVERYTHING IS CORRECT");
                // TODO: Implement better error handling
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
                    JOptionPane.showMessageDialog(null,
                            "Algorithm executed!\n\n" +
                            "Computing time took: " + Long.toString(cst.computingTime) + " ms\n" +
                            "Solution: " + cst.sol
                    );

                    Jmetal_cst_Integer cstInteger = new Jmetal_cst_Integer();
                    cstInteger.run();
                    JOptionPane.showMessageDialog(null,
                            "Algorithm executed!\n\n" +
                            "Computing time took: " + Long.toString(cstInteger.computingTime) + " ms\n" +
                            "Solution: " + cstInteger.sol);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    public static Vector<String> getNameOfVariablesString() {
        return nameOfVariablesString;
    }
    public static Vector<Double> getMinIntervalOfVariablesDouble() {
        return minIntervalOfVariablesDouble;
    }
    public static Vector<Double> getMaxIntervalOfVariablesDouble() {
        return maxIntervalOfVariablesDouble;
    }
    public static Vector<Integer> getYMinOfVariables() {
        return yMinOfVariables;
    }
    public static Vector<Integer> getYMaxOfVariables() {
        return yMaxOfVariables;
    }
    public static Vector<Integer> getYIntervalOfVariables() {
        return yIntervalOfVariables;
    }
    public static int getNumOfVariablesFirstInt() {
        return numOfVariablesFirstInt;
    }
    public static int getNumOfObjFunctFirstInt() {
        return numOfObjFunctFirstInt;
    }
    public static int getMaxEvaluationsInt() {
        return maxEvaluationsInt;
    }
    public static int getPopulationSizeInt() {
        return populationSizeInt;
    }
    public static Vector<String> getCostFunctionsString() {
        return costFunctionsString;
    }
    public static boolean getDoGraph() { return doGraph; }


    private void saveFirstView() {
        if (Integer.valueOf(numOfVariablesFirst.getText()) != numOfVariablesFirstInt)
            createSecondView = true;

        numOfVariablesFirstInt = Integer.valueOf(numOfVariablesFirst.getText());
        numOfObjFunctFirstInt = Integer.valueOf(numOfObjFunctFirst.getText());
        algorithmTypeFirstString = algorithmTypeFirst.toString();
        variableTypeFirstString = variableTypeFirst.toString();
    }

    private void saveSecondView() {
        Component[] components = parameterPanelSecond.getComponents();

        // Clear vectors
        nameOfVariablesString.removeAllElements();
        minIntervalOfVariablesDouble.removeAllElements();
        maxIntervalOfVariablesDouble.removeAllElements();
        intervalOfVariablesType.removeAllElements();

        yMinOfVariables.removeAllElements();
        yMaxOfVariables.removeAllElements();
        yIntervalOfVariables.removeAllElements();

        // Fill out vectors
        for (int i=4; i<components.length; i=i+4) {
            if (components[i] instanceof JTextField)
                nameOfVariablesString.add(((JTextField) components[i]).getText());
            if (components[i+1] instanceof JTextField)
                minIntervalOfVariablesDouble.add(Double.valueOf(((JTextField) components[i+1]).getText()));
            if (components[i+2] instanceof JTextField)
                maxIntervalOfVariablesDouble.add(Double.valueOf(((JTextField) components[i+2]).getText()));
            if (components[i+3] instanceof JTextField)
                intervalOfVariablesType.add(Double.valueOf(((JTextField) components[i+3]).getText()));
        }

        // Integer transform for intervals
        for (int i=0; i<nameOfVariablesString.size(); i++) {
            Integer paso = (int) Math.ceil((maxIntervalOfVariablesDouble.elementAt(i)-minIntervalOfVariablesDouble.elementAt(i))
                    /intervalOfVariablesType.elementAt(i)+0.0000000001);
            Integer min = (int) Math.ceil(minIntervalOfVariablesDouble.elementAt(i)/intervalOfVariablesType.elementAt(i)*paso);
            Integer max = min+paso*(paso-1);

            yIntervalOfVariables.add(paso);
            yMinOfVariables.add(1);
            yMaxOfVariables.add(paso);
            System.out.println("s");
        }

        // TODO: Error handling if don't come in order
        /*
        int saveName;
        int saveMin;
        int saveMax;
        int saveType;

        for (int i=0; i<components.length; i++) {
            if (components[i] instanceof JLabel) {
                String text = ((JLabel) components[i]).getText();
                if (text.equalsIgnoreCase(NAME_OF_VARIABLES)) // STORE IN 0
                    saveName = i;
                if (text.equalsIgnoreCase(MIN_INTERVAL_OF_VARIABLES)) // STORE IN 1
                    saveMin = i;
                if (text.equalsIgnoreCase(MAX_INTERVAL_OF_VARIABLES)) // STORE IN 2
                    saveMax = i;
                if (text.equalsIgnoreCase(TYPE_OF_VARIABLE)) // STORE IN 3
                    saveType = i;
            } else if (components[i] instanceof JTextField) {
                String text = ((JTextField) components[i]).getText();
            }
        }
        */

    }

    private void saveThirdView() {
        maxEvaluationsInt = Integer.valueOf(maxEvaluations.getText());
        populationSizeInt = Integer.valueOf(populationSize.getText());
        doGraph = checkGraph.isSelected();

        // Clear vectors
        costFunctionsString.removeAllElements();

        // Fill out vectors
        costFunctionsString.add(objectiveFunctionOne.getText());
        if (numOfObjFunctFirstInt > 1)
            costFunctionsString.add(objectiveFunctionTwo.getText());
        if (numOfObjFunctFirstInt > 2)
            costFunctionsString.add(objectiveFunctionThird.getText());
        if (numOfObjFunctFirstInt > 3)
            costFunctionsString.add(objectiveFunctionFour.getText());

    }

    private void loadFirstView() {
        panelFirst.setVisible(true);
        panelSecond.setVisible(false);
        panelThird.setVisible(false);
    }

    private void loadSecondView() {
        panelFirst.setVisible(false);
        panelSecond.setVisible(true);
        panelThird.setVisible(false);
    }
    
    private void loadThirdView() {
        objectiveFunctionTwo.setEditable(true);
        objectiveFunctionThird.setEditable(true);
        objectiveFunctionFour.setEditable(true);

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

    private void createSecondView() {
        // Clearing out the setEditable for ThirdView
        objectiveFunctionFour.setEditable(true);
        objectiveFunctionThird.setEditable(true);
        objectiveFunctionTwo.setEditable(true);

        panelFirst.setVisible(false);
        panelSecond.setVisible(true);
        panelThird.setVisible(false);

        // Clearing out panel
        parameterPanelSecond.removeAll();

        // Setting grids as many as variables
        parameterPanelSecond.setLayout(new GridLayout(0,4)); // TODO: Set size to normal

        //JScrollBar horBar = new JScrollBar(JScrollBar.HORIZONTAL);
        //parameterPanelSecond.add(horBar,BorderLayout.SOUTH);

        // Parameters
        JLabel nameOfVariablesLabel = new JLabel(NAME_OF_VARIABLES);
        JLabel minIntervalOfVariablesLabel = new JLabel(MIN_INTERVAL_OF_VARIABLES);
        JLabel maxIntervalOfVariablesLabel = new JLabel(MAX_INTERVAL_OF_VARIABLES);
        JLabel typeVariablesLabel = new JLabel(INCREMENT_OF_VARIABLE);

        JTextField nameOfVariablesText[] = new JTextField[numOfVariablesFirstInt];
        JTextField minIntervalOfVariablesText[] = new JTextField[numOfVariablesFirstInt];
        JTextField maxIntervalOfVariablesText[] = new JTextField[numOfVariablesFirstInt];
        JTextField incrementVariableText[] = new JTextField[numOfVariablesFirstInt];
        
        // Adding
        parameterPanelSecond.add(nameOfVariablesLabel);
        parameterPanelSecond.add(minIntervalOfVariablesLabel);
        parameterPanelSecond.add(maxIntervalOfVariablesLabel);
        parameterPanelSecond.add(typeVariablesLabel);

        for (int i=0; i<numOfVariablesFirstInt; i++) {
            nameOfVariablesText[i] = new JTextField();
            minIntervalOfVariablesText[i] = new JTextField();
            maxIntervalOfVariablesText[i] = new JTextField();
            incrementVariableText[i] = new JTextField();
            //incrementVariableText[i].setEnabled(false); // TODO: check with previous window

            parameterPanelSecond.add(nameOfVariablesText[i]);
            parameterPanelSecond.add(minIntervalOfVariablesText[i]);
            parameterPanelSecond.add(maxIntervalOfVariablesText[i]);
            parameterPanelSecond.add(incrementVariableText[i]);
        }

        createSecondView = false;

    }

    public static void main(String[] args) {
        frame = new JFrame("jMetal GUI");
        frame.setSize(500,325);
        FirstWindow activity = new FirstWindow();
        frame.setContentPane(activity.panel);
        //frame.pack();

        activity.panelSecond.setVisible(false);
        activity.panelThird.setVisible(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
