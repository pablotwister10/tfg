package metalMVC;

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
    private static int evaluations;
    private static int populationSize;


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
        if (numOfObjFuncts != view.getnumOfObjFuncts()) {
            numOfObjFuncts = view.getnumOfObjFuncts();
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

        if (objFuncts.equals(view.getObjFunctions()))
            objFuncts = view.getObjFunctions();
        if (evaluations != view.getEvaluations())
            evaluations = view.getEvaluations();
        if (populationSize != view.getPopulationSize())
            populationSize = view.getPopulationSize();

        return (!objFuncts.isEmpty() || evaluations != 0 || populationSize != 0);
    }

    /* GETTERS */
    public static int getNumOfVariables() {
        return numOfVariables;
    }

    public static String getVariableType() {
        return variableType;
    }

    public static String getAlgorithmType() {
        return algorithmType;
    }

    public static int getnumOfObjFuncts() {
        return numOfObjFuncts;
    }

    public static Vector<String> getNameOfVariables() {
        return nameOfVariables;
    }

    public static Vector<Double> getMinIntervalOfVariablesDouble() {
        return minIntervalOfVariablesDouble;
    }

    public static Vector<Double> getMaxIntervalOfVariablesDouble() {
        return maxIntervalOfVariablesDouble;
    }

    public static Vector<Double> getStepVariablesDouble() {
        return stepVariablesDouble;
    }

    public static Vector<Integer> getMinIntervalOfVariablesInteger() {
        return minIntervalOfVariablesInteger;
    }

    public static Vector<Integer> getMaxIntervalOfVariablesInteger() {
        return maxIntervalOfVariablesInteger;
    }

    public static Vector<Integer> getStepVariablesInteger() {
        return stepVariablesInteger;
    }

    public static Vector<String> getObjFuncts() {
        return objFuncts;
    }

    public static int getEvaluations() {
        return evaluations;
    }

    public static int getPopulationSize() {
        return populationSize;
    }


    /* UPDATE MODEL */


}
