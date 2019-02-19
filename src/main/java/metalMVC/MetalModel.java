package metalMVC;

import java.util.Vector;

// MetalModel Class for data structuration
public class MetalModel {

    /* FIRST CARD */

    private static int numOfVariables;
    private static String variableType;
    private static String algorithmType;
    private static int numOfObjFunct;

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
        numOfObjFunct = 0;

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
        numOfVariables = view.getNumOfVariables();
        variableType = view.getVariableType();
        algorithmType = view.getAlgorithmType();
        numOfObjFunct = view.getNumOfObjFunct();

        return (numOfVariables != 0 || !variableType.isEmpty() || !algorithmType.isEmpty() || numOfObjFunct != 0);
    }

    boolean saveSecondCard(MetalView view) {
        nameOfVariables = view.getNameOfVariables();
        minIntervalOfVariablesDouble = view.getMinIntervalOfVariablesDouble();
        maxIntervalOfVariablesDouble = view.getMaxIntervalOfVariablesDouble();
        stepVariablesDouble = view.getStepVariablesDouble();

        return (!nameOfVariables.isEmpty() || !minIntervalOfVariablesDouble.isEmpty()
                || !maxIntervalOfVariablesDouble.isEmpty() || !stepVariablesDouble.isEmpty());
    }

    boolean saveThirdCard(MetalView view) {
        objFuncts = view.getObjFunctions();
        evaluations = view.getEvaluations();
        populationSize = view.getPopulationSize();

        return (!objFuncts.isEmpty() || evaluations != 0 || populationSize != 0);
    }

    /* GET NUMBER OF VARIABLES */
    public static int getNumOfVariables() {
        return numOfVariables;
    }


    /* UPDATE MODEL */


}
