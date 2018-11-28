package guicode;

import example.Jmetal_cst;

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
    private static final String TYPE_OF_VARIABLE = "Variable type";
    private static Vector<String> nameOfVariablesString = new Vector<String>(0);
    private static Vector<Double> minIntervalOfVariablesDouble = new Vector<Double>(0);
    private static Vector<Double> maxIntervalOfVariablesDouble = new Vector<Double>(0);
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


    public static Vector<Double> getMinIntervalOfVariablesDouble() {
        return minIntervalOfVariablesDouble;
    }
    public static Vector<Double> getMaxIntervalOfVariablesDouble() {
        return maxIntervalOfVariablesDouble;
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

        // Fill out vectors
        for (int i=4; i<components.length; i=i+4) {
                if (components[i] instanceof JTextField)
                    nameOfVariablesString.add(((JTextField) components[i]).getText());
                if (components[i+1] instanceof JTextField)
                    minIntervalOfVariablesDouble.add(Double.valueOf(((JTextField) components[i+1]).getText()));
                if (components[i+2] instanceof JTextField)
                    maxIntervalOfVariablesDouble.add(Double.valueOf(((JTextField) components[i+2]).getText()));
                //if (components[i+3] instanceof JTextField)
                //type[j] = Double.valueOf(((JTextField) components[i+3]).getText());
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
        for (int i=0; i<nameOfVariablesString.size(); i++)
            System.out.println(nameOfVariablesString.get(i));
        for (int i=0; i<minIntervalOfVariablesDouble.size(); i++)
            System.out.println(minIntervalOfVariablesDouble.get(i));

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
        JLabel typeVariablesLabel = new JLabel(TYPE_OF_VARIABLE);

        JTextField nameOfVariablesText[] = new JTextField[numOfVariablesFirstInt];
        JTextField minIntervalOfVariablesText[] = new JTextField[numOfVariablesFirstInt];
        JTextField maxIntervalOfVariablesText[] = new JTextField[numOfVariablesFirstInt];
        String[] typeVariable = {"Double","Int"};
        JComboBox typesVariableCombo[] = new JComboBox[numOfVariablesFirstInt];
        
        // Adding
        parameterPanelSecond.add(nameOfVariablesLabel);
        parameterPanelSecond.add(minIntervalOfVariablesLabel);
        parameterPanelSecond.add(maxIntervalOfVariablesLabel);
        parameterPanelSecond.add(typeVariablesLabel);

        for (int i=0; i<numOfVariablesFirstInt; i++) {
            nameOfVariablesText[i] = new JTextField();
            minIntervalOfVariablesText[i] = new JTextField();
            maxIntervalOfVariablesText[i] = new JTextField();
            typesVariableCombo[i] = new JComboBox(typeVariable);
            typesVariableCombo[i].setSelectedIndex(0);
            typesVariableCombo[i].setEnabled(false); // TODO: check with previous window

            parameterPanelSecond.add(nameOfVariablesText[i]);
            parameterPanelSecond.add(minIntervalOfVariablesText[i]);
            parameterPanelSecond.add(maxIntervalOfVariablesText[i]);
            parameterPanelSecond.add(typesVariableCombo[i]);
        }

        createSecondView = false;

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
