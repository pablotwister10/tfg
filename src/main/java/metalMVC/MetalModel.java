package metalMVC;

import algorithmExecutors.MetalAlgorithm;
import algorithmExecutors.MetalSolution;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;

import java.io.*;
import java.util.Vector;

/**
 * MetalModel Class for data structuration
 * @param <> Type of variable (Double, Integer)
 */
// TODO: Change vectors of variables to @param type <T>
public class MetalModel {

    /* FIRST CARD */

    private static int numOfVariables;
    private static String variableType;
    private static String algorithmType;
    private static int numOfObjFuncts;

    /* SECOND CARD */

    private static Vector<String> nameOfVariables = new Vector<>(0);
/*
    private Vector<T> minIntervalOfVariables;
    private Vector<T> maxIntervalOfVariables;
    private Vector<T> stepVariables;
*/
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
    private String pathMacroBas;
    private String pathResultsTxt;


    /** CONSTRUCTOR */
    MetalModel() {
        reset();
    }


    /* RESET MODEL */
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
        /*
        pathMacroBas = null;
        pathResultsTxt = null;
        */
    }

    /* SAVE CARDS */
    boolean saveFirstCard(MetalView view) {
        boolean upView2 = true;
        boolean upView3 = true;

        if (numOfVariables != view.getNumOfVariables())
            numOfVariables = view.getNumOfVariables();
        else if (numOfVariables == 0)
            upView2 = false;
        if (variableType == null || !variableType.equalsIgnoreCase(view.getVariableType()))
            variableType = view.getVariableType();
        if (algorithmType == null || !algorithmType.equalsIgnoreCase(view.getAlgorithmType()))
            algorithmType = view.getAlgorithmType();
        if (numOfObjFuncts != view.getNumOfObjFuncts())
            numOfObjFuncts = view.getNumOfObjFuncts();
        else if (numOfObjFuncts == 0)
            upView3 = false;

        // Card 1 affects View of Cards 2 and 3
        if (numOfVariables != 0 && variableType != null && algorithmType != null && numOfObjFuncts != 0) {
            if (upView2)
                view.updateView(this,"Card 2");
        }
        if (upView3)
            view.updateView(this,"Card 3");

        return (numOfVariables != 0 && variableType != null && algorithmType != null && numOfObjFuncts != 0);
    }

    boolean saveSecondCard(MetalView view) {

        boolean saved;

        if (nameOfVariables.isEmpty() || !nameOfVariables.equals(view.getNameOfVariables()))
            nameOfVariables = view.getNameOfVariables();
        if (minIntervalOfVariablesDouble.isEmpty() || !minIntervalOfVariablesDouble.equals(view.getMinIntervalOfVariablesDouble()))
            minIntervalOfVariablesDouble = view.getMinIntervalOfVariablesDouble();
        if (maxIntervalOfVariablesDouble.isEmpty() || !maxIntervalOfVariablesDouble.equals(view.getMaxIntervalOfVariablesDouble()))
            maxIntervalOfVariablesDouble = view.getMaxIntervalOfVariablesDouble();
        saved = (!nameOfVariables.isEmpty() && !minIntervalOfVariablesDouble.isEmpty()
                && !maxIntervalOfVariablesDouble.isEmpty());
        if (variableType.equalsIgnoreCase("Integer")) {
            if (minIntervalOfVariablesInteger.isEmpty() || !minIntervalOfVariablesInteger.equals(view.getMinIntervalOfVariablesInteger()))
                minIntervalOfVariablesInteger = view.getMinIntervalOfVariablesInteger();
            if (maxIntervalOfVariablesInteger.isEmpty() || !maxIntervalOfVariablesInteger.equals(view.getMaxIntervalOfVariablesInteger()))
                maxIntervalOfVariablesInteger = view.getMaxIntervalOfVariablesInteger();
            if (stepVariablesDouble.isEmpty() || !stepVariablesDouble.equals(view.getStepVariablesDouble()))
                stepVariablesDouble = view.getStepVariablesDouble();
            if (stepVariablesInteger.isEmpty() || !stepVariablesInteger.equals(view.getStepVariablesInteger()))
                stepVariablesInteger = view.getStepVariablesInteger();
            saved = (!nameOfVariables.isEmpty() && !minIntervalOfVariablesInteger.isEmpty()
                    && !maxIntervalOfVariablesInteger.isEmpty() && !stepVariablesDouble.isEmpty()
                    && !stepVariablesInteger.isEmpty());
        }

        return saved;
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
        String[] optChoices = new String[] {"GUI","CST","MATLAB"};
        optimizationChoice = optChoices[view.getOptimizationChoice()];
        System.out.println("Chose to optimize with "+optimizationChoice); // LOGGER
        if (optimizationChoice.equalsIgnoreCase("CST")) {
            if (projectPath.isEmpty() || projectPath.equalsIgnoreCase(view.getProjectPath()))
                projectPath = view.getProjectPath();
            /*
            if (!pathMacroBas.equalsIgnoreCase(view.getPathMacroBas()))
                pathMacroBas = view.getPathMacroBas();
            if (!pathResultsTxt.equalsIgnoreCase(view.getPathResultsTxt()))
                pathResultsTxt = view.getPathResultsTxt();
            */
            return (!objFuncts.isEmpty() || (evaluations != 0 && populationSize != 0 && !optimizationChoice.isEmpty() && projectPath != null));
        } else
            return (!objFuncts.isEmpty() || (evaluations != 0 && populationSize != 0 && !optimizationChoice.isEmpty()));
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
/*
    public Vector<T> getMinIntervalOfVariables() {
        return minIntervalOfVariables;
    }

    public Vector<T> getMaxIntervalOfVariables() {
        return maxIntervalOfVariables;
    }

    public Vector<T> getStepVariables() {
        return stepVariables;
    }
*/
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

    public String getPathMacroBas() {
        return pathMacroBas;
    }

    public String getPathResultsTxt() {
        return pathResultsTxt;
    }

    /* SETTERS */
/*
    public void setMinIntervalOfVariables(Vector<T> minIntervalOfVariables) {
        this.minIntervalOfVariables = minIntervalOfVariables;
    }

    public void setMaxIntervalOfVariables(Vector<T> maxIntervalOfVariables) {
        this.maxIntervalOfVariables = maxIntervalOfVariables;
    }

    public void setStepVariables(Vector<T> stepVariables) {
        this.stepVariables = stepVariables;
    }
*/
    /* UPDATE MODEL */


    /* EXECUTION OF ALGORITHM ==> new MetalAlgorithm class creation */
    boolean execute(MetalView view) throws Exception {
        boolean done = false;

        if (variableType.equalsIgnoreCase("Double")) {
            metalSolution = new MetalSolution<Double,DoubleSolution>(this);
            MetalAlgorithm<Double,DoubleSolution,DoubleProblem> algo = new MetalAlgorithm<>(getMetalModel());
            done = algo.run();
        } else if (variableType.equalsIgnoreCase("Integer")) {
            metalSolution = new MetalSolution<Integer,IntegerSolution>(this);
            MetalAlgorithm<Integer,IntegerSolution,IntegerProblem> algo = new MetalAlgorithm<>(getMetalModel());
            done = algo.run();
        }

        if (algorithmType.equalsIgnoreCase("Genetic Algorithm")) {
            if (done)
                view.displayGraph(this);
        } else if (algorithmType.equalsIgnoreCase("NSGAII")) {
            if (done)
                view.displayPareto(this);
        }

        return done;
    }

    void updatesMacro() throws IOException {

        File macroFile = new File(projectPath + "\\macro.bas\"");
        RandomAccessFile raf = new RandomAccessFile(macroFile,"rw");

        raf.writeChars();

        String[] name_variables = new String[getNumOfVariables()];
        double[] value_variables = new double[getNumOfVariables()];

        // Optimizing functions from GUI
        for (int i=0; i<getNumOfVariables(); i++) {
            // Variables que se van modificar
            name_variables[i] = getNameOfVariables().elementAt(i);
            // Valor de la variables que se van a modificar
            value_variables[i] = getMinIntervalOfVariablesDouble().elementAt(i);
        }

        for (int index=0; index<getNumOfVariables(); index++){
            String lineref;
            String line;

            lineref = raf.readLine();

            while(lineref != null) {

                if(lineref.equalsIgnoreCase("'Define Variables")) {
                    lineref = raf.readLine();

                    String variableLine = name_variables[index] + "=" + (Double.toString(value_variables[index]));
                    String oldMacro="";
                    raf.seek(0);
                    while((line=raf.readLine()) != null) {
                        oldMacro+=line+"\r\n";
                    }
                    String newasignation=oldMacro.replaceAll(lineref,newline);
                    FileWriter writer = new FileWriter(path);
                    writer.write(newasignation);
                    writer.close();
                    break;
                }
                lineref = raf.readLine();
            }

            raf.close();
            raf=new RandomAccessFile(macroFile, "rw");

        }

        raf.close();


    }

}
