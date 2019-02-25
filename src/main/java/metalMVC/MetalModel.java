package metalMVC;

import algorithmExecutors.MetalAlgorithm;
import algorithmExecutors.MetalSolution;

import java.util.Vector;

// MetalModel Class for data structuration
public class MetalModel {

    /* FIRST CARD */

    private static int numOfVariables;
    private static String variableType;
    private static String algorithmType;
    private static int numOfObjFuncts;

    /* SECOND CARD */

    private static Vector<String> nameOfVariables = new Vector<String>(0);
    private static Vector<Double> minIntervalOfVariablesDouble = new Vector<Double>(0);
    private static Vector<Double> maxIntervalOfVariablesDouble = new Vector<Double>(0);
    private static Vector<Double> stepVariablesDouble = new Vector<Double>(0);
    // intervals to int
    private static Vector<Integer> minIntervalOfVariablesInteger = new Vector<Integer>(0);
    private static Vector<Integer> maxIntervalOfVariablesInteger = new Vector<Integer>(0);
    private static Vector<Integer> stepVariablesInteger = new Vector<Integer>(0);

    /* THIRD CARD */

    private static Vector<String> objFuncts = new Vector<String>(0);
    private static boolean graphChecks[] = new boolean[4];
    private static int evaluations;
    private static int populationSize;

    /* SOLUTION */
    MetalSolution metalSolution;


    /** CONSTRUCTOR */
    MetalModel() {
        reset();
    }


    /* RESET MODEL */
    public void reset() {
        // FIRST CARD
        numOfVariables = 0;
        variableType = null;
        algorithmType = null;
        numOfObjFuncts = 0;

        // SECOND CARD
        nameOfVariables = new Vector<String>(0);
        minIntervalOfVariablesDouble = new Vector<Double>(0);
        maxIntervalOfVariablesDouble = new Vector<Double>(0);
        stepVariablesDouble = new Vector<Double>(0);
        // intervals to int
        minIntervalOfVariablesInteger = new Vector<Integer>(0);
        maxIntervalOfVariablesInteger = new Vector<Integer>(0);
        stepVariablesInteger = new Vector<Integer>(0);

        // THIRD CARD
        objFuncts = new Vector<String>(0);
        evaluations = 0;
        populationSize = 0;
    }

    /* SAVE CARDS */
    boolean saveFirstCard(MetalView view) {
        boolean upView = false;

        if (numOfVariables != view.getNumOfVariables()) {
            numOfVariables = view.getNumOfVariables();
            upView = true;
        }
        if (variableType == null || !variableType.equalsIgnoreCase(view.getVariableType())) {
            variableType = view.getVariableType();
            upView = true;
        }
        if (algorithmType == null || algorithmType.equalsIgnoreCase(view.getAlgorithmType())) {
            algorithmType = view.getAlgorithmType();
            upView = true;
        }
        if (numOfObjFuncts != view.getNumOfObjFuncts()) {
            numOfObjFuncts = view.getNumOfObjFuncts();
            upView = true;
        }

        // Card 1 affects View of Cards 2 and 3
        if (upView) {
            view.updateView(this,"Card 2");
            view.updateView(this,"Card 3");
        }

        return (numOfVariables != 0 || !variableType.isEmpty() || !algorithmType.isEmpty() || numOfObjFuncts != 0);
    }

    boolean saveSecondCard(MetalView view) {

        if (nameOfVariables.isEmpty() || !nameOfVariables.equals(view.getNameOfVariables()))
            nameOfVariables = view.getNameOfVariables();
        if (minIntervalOfVariablesDouble.isEmpty() || !minIntervalOfVariablesDouble.equals(view.getMinIntervalOfVariablesDouble()))
            minIntervalOfVariablesDouble = view.getMinIntervalOfVariablesDouble();
        if (maxIntervalOfVariablesDouble.isEmpty() || maxIntervalOfVariablesDouble.equals(view.getMaxIntervalOfVariablesDouble()))
            maxIntervalOfVariablesDouble = view.getMaxIntervalOfVariablesDouble();
        if (stepVariablesDouble.isEmpty() || stepVariablesDouble.equals(view.getStepVariablesDouble()))
            stepVariablesDouble = view.getStepVariablesDouble();

        return (!nameOfVariables.isEmpty() || !minIntervalOfVariablesDouble.isEmpty()
                || !maxIntervalOfVariablesDouble.isEmpty() || !stepVariablesDouble.isEmpty());
    }

    boolean saveThirdCard(MetalView view) {

        if (!objFuncts.equals(view.getObjFunctions()))
            objFuncts = view.getObjFunctions();
        if (graphChecks != view.getGraphChecks())
            graphChecks = view.getGraphChecks();
        if (evaluations != view.getEvaluations())
            evaluations = view.getEvaluations();
        if (populationSize != view.getPopulationSize())
            populationSize = view.getPopulationSize();

        return (!objFuncts.isEmpty() || evaluations != 0 || populationSize != 0);
    }

    /* GETTERS */
    public int getNumOfVariables() {
        return numOfVariables;
    }

    public String getVariableType() {
        return variableType;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public int getNumOfObjFuncts() {
        return numOfObjFuncts;
    }

    public Vector<String> getNameOfVariables() {
        return nameOfVariables;
    }

    public Vector<Double> getMinIntervalOfVariablesDouble() {
        return minIntervalOfVariablesDouble;
    }

    public Vector<Double> getMaxIntervalOfVariablesDouble() {
        return maxIntervalOfVariablesDouble;
    }

    public Vector<Double> getStepVariablesDouble() {
        return stepVariablesDouble;
    }

    public Vector<Integer> getMinIntervalOfVariablesInteger() {
        return minIntervalOfVariablesInteger;
    }

    public Vector<Integer> getMaxIntervalOfVariablesInteger() {
        return maxIntervalOfVariablesInteger;
    }

    public Vector<Integer> getStepVariablesInteger() {
        return stepVariablesInteger;
    }

    public Vector<String> getObjFuncts() {
        return objFuncts;
    }

    public String getObjFuncts(int functNumber) {
        return objFuncts.elementAt(functNumber-1); // elements start at 0
    }

    public boolean[] getGraphChecks() {
        return graphChecks;
    }

    public int getEvaluations() {
        return evaluations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public MetalSolution getMetalSolution() {
        return metalSolution;
    }

    public MetalModel getMetalModel() {
        return this;
    }


    /* UPDATE MODEL */


    /* EXECUTION OF ALGORITHM ==> new MetalAlgorithm class creation */
    boolean execute(MetalView view) throws Exception {
        boolean done = false;
        metalSolution = new MetalSolution(this);

        MetalAlgorithm algo = new MetalAlgorithm(getMetalModel());
        done = algo.run();

        if (done) {
            view.displayGraph(this);
        }

        return done;
    }

}
