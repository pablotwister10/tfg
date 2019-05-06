package metalMVC;

import layout.ChartUtilities;
import layout.ComboItem;
import layout.MyButtonGroup;
import layout.SpringUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Vector;

/**
 * MetalView Class
 *
 * Implements GUI
 */
class MetalView extends JFrame {

    // Main Panel (using CardLayout for navigation of different windows)
    private JPanel panelMain = new JPanel();
    JPanel panelCards = new JPanel();
    CardLayout cards = new CardLayout();

    // TODO: Set fix height to text fields and combo boxes from every card

    /* FIRST CARD */

    private JPanel cardFirst = new JPanel();
    private final String CARD_FIRST = "Card 1";
    // Parameters
    private JPanel panelParameterFirst = new JPanel(new SpringLayout());
    // Labels
    private JLabel labelNumOfVariables = new JLabel("Number of variables (introduce an integer): ", JLabel.TRAILING);
    private JLabel labelVariableType = new JLabel("Variable type: ", JLabel.TRAILING);
    private JLabel labelAlgorithmType = new JLabel("Algorithm type: ", JLabel.TRAILING);
    private JLabel labelNumOfObjFuncts = new JLabel("Number of objective functions (from 1 to 4): ", JLabel.TRAILING);
    // Texts and Boxes
    private JTextField textNumOfVariables = new JTextField(10);
    private JComboBox boxVariableType = new JComboBox();
    private JComboBox boxAlgorithmType = new JComboBox();
    private JTextField textNumOfObjFuncts = new JTextField(10);
    // Combo Boxes for boxes
    private ComboItem comboBoxDoubleVarType = new ComboItem("Double",1);
    private ComboItem comboBoxIntVarType = new ComboItem("Integer",2);
    private ComboItem comboBoxAlgorithmGenetic = new ComboItem("Genetic Algorithm",1);
    private ComboItem comboBoxAlgorithmNSGAII = new ComboItem("NSGAII",2);
    private ComboItem comboBoxAlgorithmMOCell = new ComboItem("MOCell",3);

    /* SECOND CARD */

    private JPanel cardSecond = new JPanel();
    private final String CARD_SECOND = "Card 2";
    // Parameters
    private JPanel panelParameterSecond = new JPanel();
    // Labels
    private JLabel labelNameOfVariables = new JLabel("Name of Variables");
    private JLabel labelMinIntervalOfVariables = new JLabel("Min of Interval");
    private JLabel labelMaxIntervalOfVariables = new JLabel("Max of Interval");
    private JLabel labelStepVariables = new JLabel("Step");
    // Texts arrays
    private JTextField textNameOfVariables[] = new JTextField[100]; // reserving for 100 variables
    private JTextField textMinIntervalOfVariables[] = new JTextField[100];
    private JTextField textMaxIntervalOfVariables[] = new JTextField[100];
    private JTextField textStepVariables[] = new JTextField[100];

    /* THIRD CARD */

    private JPanel cardThird = new JPanel();
    private final String CARD_THIRD = "Card 3";
    // Objective functions parameters
    private JPanel panelObjFunction = new JPanel(new SpringLayout());
    // Labels for objFunction
    private JLabel labelObjFunctionOne = new JLabel("Objective function 1: ");
    private JLabel labelObjFunctionTwo = new JLabel("Objective function 2: ");
    private JLabel labelObjFunctionThree = new JLabel("Objective function 3: ");
    private JLabel labelObjFunctionFour = new JLabel("Objective function 4: ");
    // Texts for objFunction
    private JTextField textObjFunctionOne = new JTextField(30);
    private JTextField textObjFunctionTwo = new JTextField(30);
    private JTextField textObjFunctionThree = new JTextField(30);
    private JTextField textObjFunctionFour = new JTextField(30);
    // Check boxes for objFunction
    private JCheckBox checkBoxGraphOne = new JCheckBox("Do Graph f1");
    private JCheckBox checkBoxGraphTwo = new JCheckBox("Do Graph f2");
    private JCheckBox checkBoxGraphThree = new JCheckBox("Do Graph f3");
    private JCheckBox checkBoxGraphFour = new JCheckBox("Do Graph f4");

    // Population parameters
    private JPanel panelPopulation = new JPanel(new SpringLayout());
    // Labels for population
    private JLabel labelEvaluations = new JLabel("Maximum evaluations: ");
    private JLabel labelPopulationSize = new JLabel("Population size: ");
    // Texts for population
    private JTextField textEvaluations = new JTextField(10);
    private JTextField textPopulationSize = new JTextField(10);

    // Optimization parameters
    private JPanel panelOptimization = new JPanel();
    // Button Group for optimization choice
    private MyButtonGroup buttonGroup = new MyButtonGroup();
    // Buttons with optimization choices
    private JRadioButton radioButtonGui = new JRadioButton("Optimize with Functions of GUI");
    private JRadioButton radioButtonCst = new JRadioButton("Optimize with CST");
    private JRadioButton radioButtonMatlab = new JRadioButton("Optimize with MATLAB");
    // Project Path
    private JPanel panelProjectPath = new JPanel(new SpringLayout());
    // Label for project path
    private JLabel labelProjectPath = new JLabel("Project path: ");
    // Text for project path
    private JTextField textProjectPath = new JTextField(10);

    /* NAVIGATION PANEL */

    private JPanel panelNavigation = new JPanel();
    // Buttons for navigation
    private JButton buttonPrevious = new JButton("Previous");
    private JButton buttonNext = new JButton("Next");


    /**
     * MetalView constructor
     */
    MetalView() {

        // Main Panel Layout in two panels, one with cards and another with navigation panel
        panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));

        // Adding both panels
        panelMain.add(panelCards);
        panelMain.add(panelNavigation);

        // Setting cards as layout in cards' panel
        panelCards.setLayout(cards);

        // Making cards
        makeFirstCard();
        makeSecondCard();
        makeThirdCard();

        // Making navigation panel, the components are added in the method
        makePanelNav();

        // Arranging cards
        panelCards.add(cardFirst,"1");  // 1 for first
        panelCards.add(cardSecond,"2"); // 2 for second
        panelCards.add(cardThird,"3");  // 3 for third

        // Showing first card by default
        cards.show(panelCards,"1");

        // Navigation panel view
        updateViewPanelNav();

        // Last touches
        this.setTitle("Main Application");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

    }


    // Methods for construction of cards

    private void makeFirstCard() {

        // First card layout
        cardFirst.setLayout(new BoxLayout(cardFirst,BoxLayout.Y_AXIS));

        cardFirst.setName(CARD_FIRST);

        // Adding the first parameter panel to the first card
        cardFirst.add(panelParameterFirst);

        // Adding all components for the first parameter panel
        panelParameterFirst.add(labelNumOfVariables);
        labelNumOfVariables.setLabelFor(textNumOfVariables);
        panelParameterFirst.add(textNumOfVariables);

        panelParameterFirst.add(labelVariableType);
        labelVariableType.setLabelFor(boxVariableType);
        panelParameterFirst.add(boxVariableType);
        boxVariableType.addItem(comboBoxDoubleVarType);
        boxVariableType.addItem(comboBoxIntVarType);

        panelParameterFirst.add(labelAlgorithmType);
        labelAlgorithmType.setLabelFor(boxAlgorithmType);
        panelParameterFirst.add(boxAlgorithmType);
        boxAlgorithmType.addItem(comboBoxAlgorithmGenetic);
        boxAlgorithmType.addItem(comboBoxAlgorithmNSGAII);
        boxAlgorithmType.addItem(comboBoxAlgorithmMOCell);

        panelParameterFirst.add(labelNumOfObjFuncts);
        labelNumOfObjFuncts.setLabelFor(textNumOfObjFuncts);
        panelParameterFirst.add(textNumOfObjFuncts);

        // Grid set with spaces
        SpringUtilities.makeCompactGrid(panelParameterFirst,
                4, 2, //rows, cols
                6, 6, //initX, initY
                6, 6); //xPad, yPad

    }

    private void makeSecondCard() {

        // Second card layout
        cardSecond.setLayout(new BoxLayout(cardSecond,BoxLayout.Y_AXIS));

        cardSecond.setName(CARD_SECOND);

        // Second card will be updated once the number of variables is set in the first view, so there is no need to
        // initialize anything, it's just wasting time

    }

    private void makeThirdCard() {

        // Third card layout
        cardThird.setLayout(new BoxLayout(cardThird, BoxLayout.Y_AXIS));

        cardThird.setName(CARD_THIRD);


        // Adding the objective function panel to the third card
        cardThird.add(panelObjFunction);

        // Adding all components for the objective function panel
        panelObjFunction.add(labelObjFunctionOne);
        labelObjFunctionOne.setLabelFor(textObjFunctionOne);
        panelObjFunction.add(textObjFunctionOne);
        textObjFunctionOne.setEnabled(false);
        panelObjFunction.add(checkBoxGraphOne);
        checkBoxGraphOne.setEnabled(false);

        panelObjFunction.add(labelObjFunctionTwo);
        labelObjFunctionTwo.setLabelFor(textObjFunctionTwo);
        panelObjFunction.add(textObjFunctionTwo);
        textObjFunctionTwo.setEnabled(false);
        panelObjFunction.add(checkBoxGraphTwo);
        checkBoxGraphTwo.setEnabled(false);

        panelObjFunction.add(labelObjFunctionThree);
        labelObjFunctionThree.setLabelFor(textObjFunctionThree);
        panelObjFunction.add(textObjFunctionThree);
        textObjFunctionThree.setEnabled(false);
        panelObjFunction.add(checkBoxGraphThree);
        checkBoxGraphThree.setEnabled(false);

        panelObjFunction.add(labelObjFunctionFour);
        labelObjFunctionFour.setLabelFor(textObjFunctionFour);
        panelObjFunction.add(textObjFunctionFour);
        textObjFunctionFour.setEnabled(false);
        panelObjFunction.add(checkBoxGraphFour);
        checkBoxGraphFour.setEnabled(false);

        // The checks and the texts are not enabled because they will be updated once the first card saves the number
        // of objective functions

        // Grid set with spaces
        SpringUtilities.makeCompactGrid(panelObjFunction,
                4, 3,
                6, 6,
                6, 6);


        // Adding the population panel to the third card
        cardThird.add(panelPopulation);

        // Adding all components to population panel
        panelPopulation.add(labelEvaluations);
        labelEvaluations.setLabelFor(textEvaluations);
        panelPopulation.add(textEvaluations);

        panelPopulation.add(labelPopulationSize);
        labelPopulationSize.setLabelFor(textPopulationSize);
        panelPopulation.add(textPopulationSize);

        // Grid set with spaces
        SpringUtilities.makeCompactGrid(panelPopulation,
                2, 2,
                6, 6,
                6, 6);


        // Adding the optimization panel to the third card
        cardThird.add(panelOptimization);

        // Select the first button of the group as true as initialization
        radioButtonGui.setSelected(true);

        // Group and add the radio buttons to the button group
        buttonGroup.add(radioButtonGui);
        buttonGroup.add(radioButtonCst);
        buttonGroup.add(radioButtonMatlab);

        // Add action listener to enable project path or not
        buttonGroup.addActionListener(e -> updateProjectPathText());

        // Adding all components to optimization panel
        panelOptimization.add(radioButtonGui);
        panelOptimization.add(radioButtonCst);
        panelOptimization.add(radioButtonMatlab);
        radioButtonMatlab.setEnabled(false);


        // Adding the project path panel to the third card
        cardThird.add(panelProjectPath);

        // Adding all components to the project path panel
        panelProjectPath.add(labelProjectPath);
        labelProjectPath.setEnabled(false);
        labelProjectPath.setLabelFor(textProjectPath);
        panelProjectPath.add(textProjectPath);
        textProjectPath.setEnabled(false);

        // Grid set with spaces
        SpringUtilities.makeCompactGrid(panelProjectPath,
                1, 2, //rows, cols
                6, 6, //initX, initY
                6, 6); //xPad, yPad

    }

    private void makePanelNav() {
        // Setting mnemonics to buttons to navigate with keys P (for Previous) and N (for Next)
        buttonPrevious.setMnemonic(KeyEvent.VK_P);
        buttonNext.setMnemonic(KeyEvent.VK_N);
        // Adding all components to the navigation panel
        panelNavigation.add(buttonPrevious);
        panelNavigation.add(buttonNext);
    }


    // Methods for adding listeners

    void addNextBtnListener(ActionListener next) {
        buttonNext.addActionListener(next);
    }

    void addPreviousBtnListener(ActionListener previous) {
        buttonPrevious.addActionListener(previous);
    }


    // Methods for updating view

    void updateView(MetalModel model, String cardIdentifier) {
        if (cardIdentifier.equalsIgnoreCase(CARD_FIRST)) {
            updateViewPanelNav("");
        } else if (cardIdentifier.equalsIgnoreCase(CARD_SECOND)) {
            updateViewSecondCard(model);
        } else if (cardIdentifier.equalsIgnoreCase(CARD_THIRD)) {
            updateViewThirdCard(model);
        }
    }

    void updateViewPanelNav() {
        updateViewPanelNav("");
    }

    private void updateViewPanelNav(String cardIdentifier) {
        // The first card should not be able to go to the third with the Previous button
        if (cardIdentifier.equalsIgnoreCase(CARD_FIRST) || getCardIdentifier().equalsIgnoreCase(CARD_FIRST)) {
            buttonPrevious.setEnabled(false);
        }
        // This statement overwrites the previous one in case the card identifier is different from CARD_FIRST even if
        // the current card is CARD_FIRST
        // TODO: should be avoided (figure out way) BUG BC UPDATES BEFORE NEXT CARD IS SHOWN !!!
        if (cardIdentifier.equalsIgnoreCase(CARD_SECOND) || cardIdentifier.equalsIgnoreCase(CARD_THIRD)) {
            buttonPrevious.setEnabled(true);
        }
    }

    private void updateViewSecondCard(MetalModel model) {

        int numOfVars = model.getNumOfVariables();

        // Clearing out panel and adding the parameter panel
        cardSecond.removeAll();
        panelParameterSecond.removeAll(); // TODO: Check if not needed to be deleted
        cardSecond.add(panelParameterSecond);

        // Setting grids as many as variables
        panelParameterSecond.setLayout(new SpringLayout());

        // Adding labels
        panelParameterSecond.add(labelNameOfVariables);
        panelParameterSecond.add(labelMinIntervalOfVariables);
        panelParameterSecond.add(labelMaxIntervalOfVariables);
        panelParameterSecond.add(labelStepVariables);

        // Adding rows of text depending on number of variables
        for (int i=0; i<numOfVars; i++) {
            textNameOfVariables[i] = new JTextField();
            textMinIntervalOfVariables[i] = new JTextField();
            textMaxIntervalOfVariables[i] = new JTextField();
            textStepVariables[i] = new JTextField();
            if (model.getVariableType().equalsIgnoreCase("Double"))
                textStepVariables[i].setEnabled(false);

            panelParameterSecond.add(textNameOfVariables[i]);
            panelParameterSecond.add(textMinIntervalOfVariables[i]);
            panelParameterSecond.add(textMaxIntervalOfVariables[i]);
            panelParameterSecond.add(textStepVariables[i]);
        }

        // Grid set with spaces
        SpringUtilities.makeCompactGrid(panelParameterSecond,
                numOfVars+1,4, //rows, cols
                6,6, //initX, initY
                6,6); //xPad, yPad

        updateViewPanelNav("Card 2");

        // If too many rows, need of scrollPane
        JScrollPane jsp = new JScrollPane(panelParameterSecond);
        cardSecond.add(jsp);

    }

    private void updateViewThirdCard(MetalModel model) {
        int numOfObjFuncts = model.getNumOfObjFuncts();

        // Set all texts and checkBoxes to not enabled
        textObjFunctionOne.setEnabled(false);
        checkBoxGraphOne.setEnabled(false);
        textObjFunctionTwo.setEnabled(false);
        checkBoxGraphTwo.setEnabled(false);
        textObjFunctionThree.setEnabled(false);
        checkBoxGraphThree.setEnabled(false);
        textObjFunctionFour.setEnabled(false);
        checkBoxGraphFour.setEnabled(false);

        // Now check if they need to be enabled
        if (numOfObjFuncts > 0) {
            textObjFunctionOne.setEnabled(true);
            checkBoxGraphOne.setEnabled(true);
            if (numOfObjFuncts > 1) {
                textObjFunctionTwo.setEnabled(true);
                checkBoxGraphTwo.setEnabled(true);
                if (numOfObjFuncts > 2) {
                    textObjFunctionThree.setEnabled(true);
                    checkBoxGraphThree.setEnabled(true);
                    if (numOfObjFuncts > 3) {
                        textObjFunctionFour.setEnabled(true);
                        checkBoxGraphFour.setEnabled(true);
                    }
                }
            }
        }

    }

    private void updateProjectPathText() {
        // If optimization choice is CST then enable it to get macros and results, otherwise disable it
        if (getOptimizationChoiceString().equalsIgnoreCase("CST")) {
            labelProjectPath.setEnabled(true);
            textProjectPath.setEnabled(true);
        } else {
            labelProjectPath.setEnabled(false);
            textProjectPath.setEnabled(false);
        }
    }


    // Accessors of data in MetalView to set MetalModel values

    // Identifier of current card
    String getCardIdentifier() {
        // Get the String identifier of the top card
        JPanel card = null;
        for (Component comp : this.panelCards.getComponents()) {
            if (comp.isVisible()) {
                card = (JPanel) comp;
            }
        }
        assert card != null;
        return card.getName();
    }

    // Card 1
    int getNumOfVariables() {
        int numVars = 0;

        if (!textNumOfVariables.getText().trim().isEmpty())
            numVars = Integer.valueOf(textNumOfVariables.getText());

        return numVars;
    }

    String getVariableType() {
        return Objects.requireNonNull(boxVariableType.getSelectedItem()).toString();
    }

    String getAlgorithmType() {
        return Objects.requireNonNull(boxAlgorithmType.getSelectedItem()).toString();
    }

    int getNumOfObjFuncts() {
        int numFuncts = 0;

        if (!textNumOfObjFuncts.getText().trim().isEmpty())
            numFuncts = Integer.valueOf(textNumOfObjFuncts.getText());

        return numFuncts;
    }

    // Card 2
    Vector<String> getNameOfVariables() {
        Component[] components = panelParameterSecond.getComponents();
        Vector<String> nameVars = new Vector<>(0);

        // Fill out vectors
        for (int i=4; i<components.length; i=i+4) {
            if (components[i] instanceof JTextField && components[i] != null)
                nameVars.add(Objects.requireNonNull(((JTextField) components[i]).getText()));
        }

        return nameVars;
    }

    Vector<Double> getMinIntervalOfVariablesDouble() {
        return getIntervalsDouble("minDouble");
    }

    Vector<Double> getMaxIntervalOfVariablesDouble() {
        return getIntervalsDouble("maxDouble");
    }

    Vector<Double> getStepVariablesDouble() {
        return getIntervalsDouble("stepDouble");
    }

    private Vector<Double> getIntervalsDouble(String type) {
        Component[] components = panelParameterSecond.getComponents();
        Vector<Double> minIntervalVarsDouble = new Vector<>(0);
        Vector<Double> maxIntervalVarsDouble = new Vector<>(0);
        Vector<Double> stepVarsDouble = new Vector<>(0);

        for (int i=4; i<components.length; i=i+4) {
            if (components[i+1] instanceof JTextField && !((JTextField) components[i+1]).getText().trim().isEmpty())
                minIntervalVarsDouble.add(Double.valueOf(((JTextField) components[i+1]).getText()));
            if (components[i+2] instanceof JTextField && !((JTextField) components[i+2]).getText().trim().isEmpty())
                maxIntervalVarsDouble.add(Double.valueOf(((JTextField) components[i+2]).getText()));
            if (components[i+3] instanceof JTextField && !((JTextField) components[i+3]).getText().trim().isEmpty())
                stepVarsDouble.add(Double.valueOf(((JTextField) components[i+3]).getText()));
        }

        if (type.equalsIgnoreCase("minDouble"))
            return minIntervalVarsDouble;
        else if (type.equalsIgnoreCase("maxDouble"))
            return maxIntervalVarsDouble;
        else if (type.equalsIgnoreCase("stepDouble"))
            return stepVarsDouble;
        else
            return null;
    }

    Vector<Integer> getMinIntervalOfVariablesInteger() {
        return getIntervalsInteger("minInteger");
    }

    Vector<Integer> getMaxIntervalOfVariablesInteger() {
        return getIntervalsInteger("maxInteger");
    }

    Vector<Integer> getStepVariablesInteger() {
        return getIntervalsInteger("stepInteger");
    }

    private Vector<Integer> getIntervalsInteger(String type) {
        Vector<String> nameVars = getNameOfVariables();
        Vector<Double> minIntervalVarsDouble = getMinIntervalOfVariablesDouble();
        Vector<Double> maxIntervalVarsDouble = getMaxIntervalOfVariablesDouble();
        Vector<Double> stepVarsDouble = getStepVariablesDouble();

        Vector<Integer> minIntervalVarsInteger = new Vector<>(0);
        Vector<Integer> maxIntervalVarsInteger = new Vector<>(0);
        Vector<Integer> stepVarsInteger = new Vector<>(0);

        // Integer transform for intervals
        for (int i=0; i<nameVars.size(); i++) {
            Integer yStep = (int) Math.ceil((maxIntervalVarsDouble.elementAt(i)-minIntervalVarsDouble.elementAt(i))
                    /stepVarsDouble.elementAt(i)+0.0000000001);
            Integer yMin = 1;
            Integer yMax = yStep;

            minIntervalVarsInteger.add(yMin);
            maxIntervalVarsInteger.add(yMax);
            stepVarsInteger.add(yStep);
        }

        if (type.equalsIgnoreCase("minInteger"))
            return minIntervalVarsInteger;
        else if (type.equalsIgnoreCase("maxInteger"))
            return maxIntervalVarsInteger;
        else if (type.equalsIgnoreCase("stepInteger"))
            return stepVarsInteger;
        else
            return null;
    }

    // Card 3
    Vector<String> getObjFunctions() {
        Vector<String> objFuncts = new Vector<String>(0);

        objFuncts.add(textObjFunctionOne.getText());
        if (getNumOfObjFuncts() > 1)
            objFuncts.add(textObjFunctionTwo.getText());
        if (getNumOfObjFuncts() > 2)
            objFuncts.add(textObjFunctionThree.getText());
        if (getNumOfObjFuncts() > 3)
            objFuncts.add(textObjFunctionFour.getText());

        return objFuncts;
    }

    boolean[] getGraphChecks() {
        boolean[] graphs = new boolean[4];

        graphs[0] = checkBoxGraphOne.isSelected();
        graphs[1] = checkBoxGraphTwo.isSelected();
        graphs[2] = checkBoxGraphThree.isSelected();
        graphs[3] = checkBoxGraphFour.isSelected();

        return graphs;
    }

    int getEvaluations() {
        int evals = 0;

        if (!textEvaluations.getText().trim().isEmpty())
            evals = Integer.valueOf(textEvaluations.getText());

        return evals;
    }

    int getPopulationSize() {
        int popSize = 0;

        if (!textPopulationSize.getText().trim().isEmpty())
            popSize = Integer.valueOf(textPopulationSize.getText());

        return popSize;
    }

    int getOptimizationChoice() {
        if (radioButtonGui.isSelected()) return 0;
        else if (radioButtonCst.isSelected()) return 1;
        else if (radioButtonMatlab.isSelected()) return 2;
        else return -1;
    }

    private String getOptimizationChoiceString() {
        int optChoice = getOptimizationChoice();
        if (optChoice == 0) return "GUI";
        else if (optChoice == 1) return "CST";
        else if (optChoice == 2) return "MATLAB";
        else return null;
    }

    String getProjectPath() {
        return Objects.requireNonNull(textProjectPath.getText());
    }


    // Methods for displaying graphs

    void displayGraph(MetalModel model) {
        // TODO: Maybe implement in another class inside this file (class Graph)
        boolean graphs[] = model.getGraphChecks();
        String graphNames[] = new String[]{"One","Two","Three","Four"};

        // Check if graphs are selected, if not continue
        for (int i=0; i<4; i++) {
            if (!graphs[i])
                continue;

            // Create chart and dataset with scores
            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Graph: " + graphNames[i],
                    "population","cost", // TODO: best cost with value
                    ChartUtilities.createDefaultCategoryDataset(model.getMetalSolution().getScores()[i]),
                    PlotOrientation.VERTICAL,
                    true,true,false);

            // New frame to display
            JFrame chartFrame = new JFrame();

            ChartPanel chartPanel = new ChartPanel(lineChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
            chartFrame.setContentPane(chartPanel);

            chartFrame.pack();
            RefineryUtilities.centerFrameOnScreen(chartFrame);
            chartFrame.setVisible(true);
            chartFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }
    }

    void displayPareto(MetalModel model) {

        // Check what objective functions are selected to display the pareto and combine all that are chosen, otherwise
        // continue
        for (int objectiveNumFirst=0; objectiveNumFirst<model.getNumOfObjFuncts(); objectiveNumFirst++) {
            for (int objectiveNumSecond=0; objectiveNumSecond<model.getNumOfObjFuncts(); objectiveNumSecond++) {

                if (objectiveNumFirst==objectiveNumSecond || objectiveNumFirst>objectiveNumSecond)
                    continue;
                if (!getGraphChecks()[objectiveNumFirst] || !getGraphChecks()[objectiveNumSecond])
                    continue;

                // Create dataset with pareto scores
                XYDataset dataset = ChartUtilities.createXYDataset(model.getMetalSolution().getScoresPareto(),objectiveNumFirst,objectiveNumSecond);

                // Create chart with dataset
                JFreeChart chart = ChartFactory.createScatterPlot(
                        "Graph: " + Integer.toString(objectiveNumFirst+1) + " vs. " + Integer.toString(objectiveNumSecond+1),
                        Integer.toString(objectiveNumFirst+1),
                        Integer.toString(objectiveNumSecond+1),
                        dataset,
                        PlotOrientation.VERTICAL,
                        true,true,false);

                // New frame to display pareto
                JFrame chartFrame = new JFrame();

                ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
                chartFrame.setContentPane(chartPanel);

                chartFrame.pack();
                RefineryUtilities.centerFrameOnScreen(chartFrame);
                chartFrame.setVisible(true);
                chartFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            }
        }

    }


    // Error option pane

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

}
