package metalMVC;

import algorithmExecutors.MetalAlgorithm;
import algorithmExecutors.MetalSolution;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.Vector;

/**
 * MetalModel Class
 *
 * Data structuration and container
 *
 * TODO: Change vectors of variables to @param type <T>
 * @param <> Type of variable (Double, Integer)
 */
public class MetalModel {

    /* FIRST CARD */
    private static int numOfVariables;
    private static String variableType;
    private static String algorithmType;
    private static int numOfObjFuncts;

    /* SECOND CARD */
    private static Vector<String> nameOfVariables = new Vector<>(0);
    // intervals in Double
    private static Vector<Double> minIntervalOfVariablesDouble = new Vector<>(0);
    private static Vector<Double> maxIntervalOfVariablesDouble = new Vector<>(0);
    private static Vector<Double> stepVariablesDouble = new Vector<>(0);
    // intervals in Integer
    private static Vector<Integer> minIntervalOfVariablesInteger = new Vector<>(0);
    private static Vector<Integer> maxIntervalOfVariablesInteger = new Vector<>(0);
    private static Vector<Integer> stepVariablesInteger = new Vector<>(0);

    /* THIRD CARD */
    private static Vector<String> objFuncts = new Vector<>(0);
    private static boolean graphChecks[] = new boolean[4];
    private static int evaluations;
    private static int populationSize;
    private static String optimizationChoice;

    /* SOLUTION */
    private MetalSolution metalSolution;

    /* CST VARS */
    private String projectPath;


    /**
     * MetalModel constructor
     */
    MetalModel() {
        // Resets view
        reset();
    }


    // Reset method to initialize variables to 0
    private void reset() {
        // FIRST CARD
        numOfVariables = 0;
        variableType = null;
        algorithmType = null;
        numOfObjFuncts = 0;

        // SECOND CARD
        nameOfVariables = new Vector<>(0);
        minIntervalOfVariablesDouble = new Vector<>(0);
        maxIntervalOfVariablesDouble = new Vector<>(0);
        stepVariablesDouble = new Vector<>(0);
        // intervals to int
        minIntervalOfVariablesInteger = new Vector<>(0);
        maxIntervalOfVariablesInteger = new Vector<>(0);
        stepVariablesInteger = new Vector<>(0);

        // THIRD CARD
        objFuncts = new Vector<>(0);
        evaluations = 0;
        populationSize = 0;

        // SOLUTION
        metalSolution = null;

        // CST VARS
        projectPath = "";
    }

    // Save Card methods, always check if the view is empty and illegal type or value
    boolean saveFirstCard(MetalView view) {

        // Boolean variables to update second and third cards
        boolean upView2 = true;
        boolean upView3 = true;

        // Save number of variables, variable type, algorithm type and number of objective functions
        if (numOfVariables != view.getNumOfVariables())
            numOfVariables = view.getNumOfVariables();
        else if (numOfVariables == 0)
            // Second card cannot be of 0 variable length
            upView2 = false;
        if (variableType == null || !variableType.equalsIgnoreCase(view.getVariableType()))
            variableType = view.getVariableType();
        if (algorithmType == null || !algorithmType.equalsIgnoreCase(view.getAlgorithmType()))
            algorithmType = view.getAlgorithmType();
        if (numOfObjFuncts != view.getNumOfObjFuncts())
            numOfObjFuncts = view.getNumOfObjFuncts();
        else if (numOfObjFuncts == 0)
            upView3 = false;  // Third card cannot have 0 objective functions

        // Card 1 affects MetalView of Cards 2 and 3, so it updates them depending on Boolean values
        if (numOfVariables != 0 && variableType != null && algorithmType != null && numOfObjFuncts != 0) {
            if (upView2)
                view.updateView(this,"Card 2");
        }
        if (upView3)
            view.updateView(this,"Card 3");

        // For error checking and to ensure that the values are set
        return (numOfVariables != 0 && variableType != null && algorithmType != null && numOfObjFuncts != 0);
    }

    boolean saveSecondCard(MetalView view) {

        boolean saved;

        // Save name of variables and the minimum and maximum intervals of these variables
        if (nameOfVariables.isEmpty() || !nameOfVariables.equals(view.getNameOfVariables()))
            nameOfVariables = view.getNameOfVariables();
        if (minIntervalOfVariablesDouble.isEmpty()
                || !minIntervalOfVariablesDouble.equals(view.getMinIntervalOfVariablesDouble()))
            minIntervalOfVariablesDouble = view.getMinIntervalOfVariablesDouble();
        if (maxIntervalOfVariablesDouble.isEmpty()
                || !maxIntervalOfVariablesDouble.equals(view.getMaxIntervalOfVariablesDouble()))
            maxIntervalOfVariablesDouble = view.getMaxIntervalOfVariablesDouble();
        saved = (!nameOfVariables.isEmpty() && !minIntervalOfVariablesDouble.isEmpty()
                && !maxIntervalOfVariablesDouble.isEmpty());

        // If the variable type is Integer, then save as well the minimum and maximum intervals and the step in Double
        // and Integer
        if (variableType.equalsIgnoreCase("Integer")) {
            if (minIntervalOfVariablesInteger.isEmpty()
                    || !minIntervalOfVariablesInteger.equals(view.getMinIntervalOfVariablesInteger()))
                minIntervalOfVariablesInteger = view.getMinIntervalOfVariablesInteger();
            if (maxIntervalOfVariablesInteger.isEmpty()
                    || !maxIntervalOfVariablesInteger.equals(view.getMaxIntervalOfVariablesInteger()))
                maxIntervalOfVariablesInteger = view.getMaxIntervalOfVariablesInteger();
            if (stepVariablesDouble.isEmpty() || !stepVariablesDouble.equals(view.getStepVariablesDouble()))
                stepVariablesDouble = view.getStepVariablesDouble();
            if (stepVariablesInteger.isEmpty() || !stepVariablesInteger.equals(view.getStepVariablesInteger()))
                stepVariablesInteger = view.getStepVariablesInteger();
            saved = (!nameOfVariables.isEmpty() && !minIntervalOfVariablesInteger.isEmpty()
                    && !maxIntervalOfVariablesInteger.isEmpty() && !stepVariablesDouble.isEmpty()
                    && !stepVariablesInteger.isEmpty());
        }

        // For error checking and to ensure that the values are saved
        return saved;
    }

    boolean saveThirdCard(MetalView view) {

        // Save objective functions, the graph checks, the evaluations and the population size
        if (!objFuncts.equals(view.getObjFunctions()))
            objFuncts = view.getObjFunctions();
        if (graphChecks != view.getGraphChecks())
            graphChecks = view.getGraphChecks();
        if (evaluations != view.getEvaluations())
            evaluations = view.getEvaluations();
        if (populationSize != view.getPopulationSize())
            populationSize = view.getPopulationSize();

        // Saving optimization choice
        String[] optChoices = new String[] {"GUI","CST","MATLAB"};
        optimizationChoice = optChoices[view.getOptimizationChoice()];
        System.out.println("Chose to optimize with "+optimizationChoice); // LOGGER

        // If optimizing with CST, save the project path for the macros and results
        if (optimizationChoice.equalsIgnoreCase("CST")) {
            if (projectPath.isEmpty() || projectPath.equalsIgnoreCase(view.getProjectPath()))
                projectPath = view.getProjectPath();
            return (!objFuncts.isEmpty() || (evaluations != 0 && populationSize != 0 && !optimizationChoice.isEmpty() && projectPath != null));
        } else
            return (!objFuncts.isEmpty() || (evaluations != 0 && populationSize != 0 && !optimizationChoice.isEmpty()));
        // The return is for error checking and to ensure that the values are saved
    }

    // Execution method for running algorithm, creating a new MetalAlgorithm object
    boolean execute(MetalView view) throws Exception {
        // For error checking
        boolean done = false;

        // MetalAlgorithm class creation depending on type of variable
        if (variableType.equalsIgnoreCase("Double")) {
            metalSolution = new MetalSolution<Double,DoubleSolution>(this);
            MetalAlgorithm<Double,DoubleSolution,DoubleProblem> algo = new MetalAlgorithm<>(getMetalModel());
            // Run algorithm
            done = algo.run();
        } else if (variableType.equalsIgnoreCase("Integer")) {
            metalSolution = new MetalSolution<Integer,IntegerSolution>(this);
            MetalAlgorithm<Integer,IntegerSolution,IntegerProblem> algo = new MetalAlgorithm<>(getMetalModel());
            // Run algorithm
            done = algo.run();
        }

        // Display graph method calling
        if (algorithmType.equalsIgnoreCase("Genetic Algorithm")) {
            // Normal graph
            if (done)
                view.displayGraph(this);
        } else if (algorithmType.equalsIgnoreCase("NSGAII")) {
            // Pareto graph
            if (done)
                view.displayPareto(this);
        }

        return done;
    }


    // Getters
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

    public String getObjFuncts(int functNumber) {
        return objFuncts.elementAt(functNumber-1); // elements start at 0
    }

    boolean[] getGraphChecks() {
        return graphChecks;
    }

    public int getEvaluations() {
        return evaluations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public String getOptimizationChoice() { return optimizationChoice; }

    public MetalSolution getMetalSolution() {
        return metalSolution;
    }

    private MetalModel getMetalModel() {
        return this;
    }

    public String getProjectPath() {
        return projectPath;
    }

}
