package metalMVC;

import layout.ChartUtilities;
import layout.ComboItem;
import layout.SpringUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

// MetalView Class implementing GUI
class MetalView extends JFrame {

    // Main Panel (using CardLayout for navigation of different windows)
    private JPanel panelMain = new JPanel();
    JPanel panelCards = new JPanel();
    CardLayout cards = new CardLayout();


    /* FIRST CARD */
    // TODO: Set fix height to text fields and combo boxes

    private JPanel cardFirst = new JPanel();
    private final String CARD_FIRST = "Card 1";

    private JPanel parameterPanelFirst = new JPanel(new SpringLayout());

    private JLabel numOfVariablesLabel = new JLabel("Number of variables (introduce an integer): ", JLabel.TRAILING);
    private JLabel variableTypeLabel = new JLabel("Variable type: ", JLabel.TRAILING);
    private JLabel algorithmTypeLabel = new JLabel("Algorithm type: ", JLabel.TRAILING);
    private JLabel numOfObjFunctsLabel = new JLabel("Number of objective functions (from 1 to 4): ", JLabel.TRAILING);

    private JTextField numOfVariablesText = new JTextField(10);
    private JComboBox variableTypeBox = new JComboBox();
    private JComboBox algorithmTypeBox = new JComboBox();
    private JTextField numOfObjFunctsText = new JTextField(10);

    private ComboItem doubleVarTypeComboBox = new ComboItem("Double",1);
    private ComboItem intVarTypeComboBox = new ComboItem("Integer",2);
    private ComboItem algorithmGeneticComboBox = new ComboItem("Genetic Algorithm",1);
    private ComboItem algorithmNSGAIIComboBox = new ComboItem("NSGAII",2);


    /* SECOND CARD */
    // TODO: Set fix height to text fields and combo boxes

    private JPanel cardSecond = new JPanel();
    private final String CARD_SECOND = "Card 2";

    private JPanel parameterPanelSecond = new JPanel();

    private JLabel nameOfVariablesLabel = new JLabel("Name of Variables");
    private JLabel minIntervalOfVariablesLabel = new JLabel("Min of Interval");
    private JLabel maxIntervalOfVariablesLabel = new JLabel("Max of Interval");
    private JLabel stepVariablesLabel = new JLabel("Step");

    private JTextField nameOfVariablesText[] = new JTextField[100]; // reserving for 100 variables
    private JTextField minIntervalOfVariablesText[] = new JTextField[100];
    private JTextField maxIntervalOfVariablesText[] = new JTextField[100];
    private JTextField stepVariablesText[] = new JTextField[100];



    /* THIRD CARD */
    // TODO: Set fix height to text fields and combo boxes

    private JPanel cardThird = new JPanel();
    private final String CARD_THIRD = "Card 3";

    private JPanel objFunctPanel = new JPanel(new SpringLayout());

    private JLabel objFunctOneLabel = new JLabel("Objective function 1: ");
    private JLabel objFunctTwoLabel = new JLabel("Objective function 2: ");
    private JLabel objFunctThreeLabel = new JLabel("Objective function 3: ");
    private JLabel objFunctFourLabel = new JLabel("Objective function 4: ");

    private JTextField objFunctOneText = new JTextField(30);
    private JTextField objFunctTwoText = new JTextField(30);
    private JTextField objFunctThreeText = new JTextField(30);
    private JTextField objFunctFourText = new JTextField(30);

    private JCheckBox graphOneCheck = new JCheckBox("Do Graph f1");
    private JCheckBox graphTwoCheck = new JCheckBox("Do Graph f2");
    private JCheckBox graphThreeCheck = new JCheckBox("Do Graph f3");
    private JCheckBox graphFourCheck = new JCheckBox("Do Graph f4");

    private JPanel populationPanel = new JPanel(new SpringLayout());

    private JLabel evaluationsLabel = new JLabel("Maximum evaluations: ");
    private JLabel populationSizeLabel = new JLabel("Population size: ");
    private JTextField evaluationsText = new JTextField(10);
    private JTextField populationSizeText = new JTextField(10);


    /* NAVIGATION PANEL */

    private JPanel panelNav = new JPanel();
    private JButton previousBtn = new JButton("Previous");
    private JButton nextBtn = new JButton("Next");
    // TODO: Set fixed dimensions to buttons
    final Dimension BTN_DIM = new Dimension(20,5);


    /** CONSTRUCTOR */
    MetalView() {

        // Main Panel Layout in two panels, one with cards and another with navigation panel
        panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));

        // Adding both panels
        panelMain.add(panelCards);
        panelMain.add(panelNav);

        // Setting cards as layout in cards' panel
        panelCards.setLayout(cards);

        // Making cards
        makeFirstCard();
        makeSecondCard();
        makeThirdCard();

        // Making navigation panel
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


    /* METHODS FOR CONSTRUCTION */
    private void makeFirstCard() {

        cardFirst.setLayout(new BoxLayout(cardFirst,BoxLayout.Y_AXIS));

        cardFirst.setName(CARD_FIRST);

        // Parameter panel

        cardFirst.add(parameterPanelFirst);

        parameterPanelFirst.add(numOfVariablesLabel);
        numOfVariablesLabel.setLabelFor(numOfVariablesText);
        parameterPanelFirst.add(numOfVariablesText);

        parameterPanelFirst.add(variableTypeLabel);
        variableTypeLabel.setLabelFor(variableTypeBox);
        parameterPanelFirst.add(variableTypeBox);
        variableTypeBox.addItem(doubleVarTypeComboBox);
        variableTypeBox.addItem(intVarTypeComboBox);

        parameterPanelFirst.add(algorithmTypeLabel);
        algorithmTypeLabel.setLabelFor(algorithmTypeBox);
        parameterPanelFirst.add(algorithmTypeBox);
        algorithmTypeBox.addItem(algorithmGeneticComboBox);
        algorithmTypeBox.addItem(algorithmNSGAIIComboBox);

        parameterPanelFirst.add(numOfObjFunctsLabel);
        numOfObjFunctsLabel.setLabelFor(numOfObjFunctsText);
        parameterPanelFirst.add(numOfObjFunctsText);

        SpringUtilities.makeCompactGrid(parameterPanelFirst,
                4, 2, //rows, cols
                6, 6, //initX, initY
                6, 6); //xPad, yPad

    }

    private void makeSecondCard() {

        cardSecond.setLayout(new BoxLayout(cardSecond,BoxLayout.Y_AXIS));

        cardSecond.setName(CARD_SECOND);

    }

    private void makeThirdCard() {

        cardThird.setLayout(new BoxLayout(cardThird, BoxLayout.Y_AXIS));

        cardThird.setName(CARD_THIRD);

        // Objective panel

        cardThird.add(objFunctPanel);

        objFunctPanel.add(objFunctOneLabel);
        objFunctOneLabel.setLabelFor(objFunctOneText);
        objFunctPanel.add(objFunctOneText);
        objFunctOneText.setEnabled(false);
        objFunctPanel.add(graphOneCheck);
        graphOneCheck.setEnabled(false);

        objFunctPanel.add(objFunctTwoLabel);
        objFunctTwoLabel.setLabelFor(objFunctTwoText);
        objFunctPanel.add(objFunctTwoText);
        objFunctTwoText.setEnabled(false);
        objFunctPanel.add(graphTwoCheck);
        graphTwoCheck.setEnabled(false);

        objFunctPanel.add(objFunctThreeLabel);
        objFunctThreeLabel.setLabelFor(objFunctThreeText);
        objFunctPanel.add(objFunctThreeText);
        objFunctThreeText.setEnabled(false);
        objFunctPanel.add(graphThreeCheck);
        graphThreeCheck.setEnabled(false);

        objFunctPanel.add(objFunctFourLabel);
        objFunctFourLabel.setLabelFor(objFunctFourText);
        objFunctPanel.add(objFunctFourText);
        objFunctFourText.setEnabled(false);
        objFunctPanel.add(graphFourCheck);
        graphFourCheck.setEnabled(false);

        SpringUtilities.makeCompactGrid(objFunctPanel,
                4, 3,
                6, 6,
                6, 6);

        // Population panel

        cardThird.add(populationPanel);

        populationPanel.add(evaluationsLabel);
        evaluationsLabel.setLabelFor(evaluationsText);
        populationPanel.add(evaluationsText);

        populationPanel.add(populationSizeLabel);
        populationSizeLabel.setLabelFor(populationSizeText);
        populationPanel.add(populationSizeText);

        SpringUtilities.makeCompactGrid(populationPanel,
                2, 2,
                6, 6,
                6, 6);

    }

    private void makePanelNav() {
        panelNav.add(previousBtn);
        panelNav.add(nextBtn);
    }


    /* METHODS FOR LISTENERS */
    void addNextBtnListener(ActionListener next) {
        nextBtn.addActionListener(next);
    }

    void addPreviousBtnListener(ActionListener previous) {
        previousBtn.addActionListener(previous);
    }


    /* METHOD FOR GETTING CARD IDENTIFIER */
    String getCardIdentifier() {
        //now we want to get the String identifier of the top card:
        JPanel card = null;
        for (Component comp : this.panelCards.getComponents()) {
            if (comp.isVisible()) {
                card = (JPanel) comp;
            }
        }
        assert card != null;
        return card.getName();
    }


    /* METHODS FOR UPDATING VIEW */

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
        if (cardIdentifier.equalsIgnoreCase(CARD_FIRST) || getCardIdentifier().equalsIgnoreCase(CARD_FIRST)) {
            previousBtn.setEnabled(false);
        }
        // This statement overwrites the previous one in case the card identifier is different from CARD_FIRST even if
        // the current card is CARD_FIRST
        // TODO: should be avoided (figure out way) BUG BC UPDATES BEFORE NEXT CARD IS SHOWN !!!
        if (cardIdentifier.equalsIgnoreCase(CARD_SECOND) || cardIdentifier.equalsIgnoreCase(CARD_THIRD)) {
            previousBtn.setEnabled(true);
        }
    }

    private void updateViewSecondCard(MetalModel model) {

        int numOfVars = model.getNumOfVariables();

        // Clearing out panel
        cardSecond.removeAll();
        parameterPanelSecond.removeAll(); // TODO: Check if not needed to be deleted
        cardSecond.add(parameterPanelSecond);

        // Setting grids as many as variables
        parameterPanelSecond.setLayout(new SpringLayout());

        // Adding
        parameterPanelSecond.add(nameOfVariablesLabel);
        parameterPanelSecond.add(minIntervalOfVariablesLabel);
        parameterPanelSecond.add(maxIntervalOfVariablesLabel);
        parameterPanelSecond.add(stepVariablesLabel);

        for (int i=0; i<numOfVars; i++) {
            nameOfVariablesText[i] = new JTextField();
            minIntervalOfVariablesText[i] = new JTextField();
            maxIntervalOfVariablesText[i] = new JTextField();
            stepVariablesText[i] = new JTextField();
            if (model.getVariableType().equalsIgnoreCase("Double"))
                stepVariablesText[i].setEnabled(false);

            parameterPanelSecond.add(nameOfVariablesText[i]);
            parameterPanelSecond.add(minIntervalOfVariablesText[i]);
            parameterPanelSecond.add(maxIntervalOfVariablesText[i]);
            parameterPanelSecond.add(stepVariablesText[i]);
        }

        SpringUtilities.makeCompactGrid(parameterPanelSecond,
                numOfVars+1,4, //rows, cols
                6,6, //initX, initY
                6,6); //xPad, yPad

        updateViewPanelNav("Card 2");

        JScrollPane jsp = new JScrollPane(parameterPanelSecond);
        cardSecond.add(jsp);

    }

    private void updateViewThirdCard(MetalModel model) {
        int numOfObjFuncts = model.getNumOfObjFuncts();

        objFunctOneText.setEnabled(false);
        graphOneCheck.setEnabled(false);
        objFunctTwoText.setEnabled(false);
        graphTwoCheck.setEnabled(false);
        objFunctThreeText.setEnabled(false);
        graphThreeCheck.setEnabled(false);
        objFunctFourText.setEnabled(false);
        graphFourCheck.setEnabled(false);

        if (numOfObjFuncts > 0) {
            objFunctOneText.setEnabled(true);
            graphOneCheck.setEnabled(true);
        }
        if (numOfObjFuncts > 1) {
            objFunctTwoText.setEnabled(true);
            graphTwoCheck.setEnabled(true);
        }
        if (numOfObjFuncts > 2) {
            objFunctThreeText.setEnabled(true);
            graphThreeCheck.setEnabled(true);
        }
        if (numOfObjFuncts > 3) {
            objFunctFourText.setEnabled(true);
            graphFourCheck.setEnabled(true);
        }
    }


    /* METHODS FOR UPDATING MODEL */

    // CARD 1
    int getNumOfVariables() {
        int numVars = 0;

        if (!numOfVariablesText.getText().trim().isEmpty())
            numVars = Integer.valueOf(numOfVariablesText.getText());

        return numVars;
    }

    String getVariableType() {
        return Objects.requireNonNull(variableTypeBox.getSelectedItem()).toString();
    }

    String getAlgorithmType() {
        return Objects.requireNonNull(algorithmTypeBox.getSelectedItem()).toString();
    }

    int getNumOfObjFuncts() {
        int numFuncts = 0;

        if (!numOfObjFunctsText.getText().trim().isEmpty())
            numFuncts = Integer.valueOf(numOfObjFunctsText.getText());

        return numFuncts;
    }

    // CARD 2
    Vector<String> getNameOfVariables() {
        Component[] components = parameterPanelSecond.getComponents();
        Vector<String> nameVars = new Vector<>(0);

        // Fill out vectors
        for (int i=4; i<components.length; i=i+4) {
            if (components[i] instanceof JTextField && components[i] != null)
                nameVars.add(Objects.requireNonNull(((JTextField) components[i]).getText()));
        }

        return nameVars;
    }
/*
    Vector<T> getMinIntervalOfVariables(MetalModel<T> model) {
        return null;
    }

    Vector<T> getMaxIntervalOfVariables() {
        return null;
    }

    Vector<T> getStepVariables() {
        return null;
    }
*/

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
        Component[] components = parameterPanelSecond.getComponents();
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

    // CARD 3
    Vector<String> getObjFunctions() {
        Vector<String> objFuncts = new Vector<String>(0);

        objFuncts.add(objFunctOneText.getText());
        if (getNumOfObjFuncts() > 1)
            objFuncts.add(objFunctTwoText.getText());
        if (getNumOfObjFuncts() > 2)
            objFuncts.add(objFunctThreeText.getText());
        if (getNumOfObjFuncts() > 3)
            objFuncts.add(objFunctFourText.getText());

        return objFuncts;
    }

    boolean[] getGraphChecks() {
        boolean[] graphs = new boolean[4];

        graphs[0] = graphOneCheck.isSelected();
        graphs[1] = graphTwoCheck.isSelected();
        graphs[2] = graphThreeCheck.isSelected();
        graphs[3] = graphFourCheck.isSelected();

        return graphs;
    }

    int getEvaluations() {
        int evals = 0;

        if (!evaluationsText.getText().trim().isEmpty())
            evals = Integer.valueOf(evaluationsText.getText());

        return evals;
    }

    int getPopulationSize() {
        int popSize = 0;

        if (!populationSizeText.getText().trim().isEmpty())
            popSize = Integer.valueOf(populationSizeText.getText());

        return popSize;
    }


    /* METHODS FOR ENABLING GRAPHS */
    void displayGraph(MetalModel model) {
        // TODO: Maybe implement in another class inside this file (class Graph)
        boolean graphs[] = model.getGraphChecks();
        String graphNames[] = new String[]{"One","Two","Three","Four"};

        for (int i=0; i<4; i++) {
            if (!graphs[i])
                continue;

            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Graph: " + graphNames[i],
                    "iterations","cost", // TODO: best cost with value
                    ChartUtilities.createDataset(model.getMetalSolution().getScores()[i]),
                    PlotOrientation.VERTICAL,
                    true,true,false);

            JFrame chartFrame = new JFrame();

            ChartPanel chartPanel = new ChartPanel(lineChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560 ,367));
            chartFrame.setContentPane(chartPanel);

            chartFrame.pack();
            RefineryUtilities.centerFrameOnScreen(chartFrame);
            chartFrame.setVisible(true);
            chartFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }
    }

    /* METHOD FOR ERRORS */
    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

}