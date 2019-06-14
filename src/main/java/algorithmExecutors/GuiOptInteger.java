package algorithmExecutors;

import metalMVC.MetalModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GuiOptInteger Class
 *
 * Implements GUI optimization with type of variable Integer
 *
 * TODO: set parameters
 * @param <> Type of variable (Double, Integer)
 * @param <> Type of problem (DoubleProblem, IntegerProblem)
 */
public class GuiOptInteger extends AbstractIntegerProblem {

    private MetalModel model;
    int i;


    /**
     * GuiOptDouble constructor
     *
     * TODO: Check if lowerBounds and upperBounds is needed or can be got from model.getMin(Max)IntervalOfVariablesDouble
     * @param model MetalModel for data abstraction
     * @param lowerBounds lower limits for variables
     * @param upperBounds upper limits for variables
     */
    GuiOptInteger(MetalModel model, List<Integer> lowerBounds, List<Integer> upperBounds)  {
        this.model = model;
        this.i = 0;

        setNumberOfVariables(model.getNumOfVariables());
        setNumberOfObjectives(model.getNumOfObjFuncts());
        setNumberOfConstraints(0);
        setName("prueba_GA");

        setLowerLimit(lowerBounds);
        setUpperLimit(upperBounds);
    }


    // Evaluation method extracting data from macro and setting it to solution
    @Override
    public void evaluate(IntegerSolution solution) {

        System.out.println("Iteration " + i++);
        if (i%1000 == 0) {
            System.out.println("Printing to text file \"Scores_" + i + ".txt\"");
            List<IntegerSolution> population = new ArrayList<>(1);
            population.add(solution);
            new SolutionListOutput(population)
                    .setSeparator("\t")
                    .setVarFileOutputContext(new DefaultFileOutputContext("VAR_" + i + ".txt"))
                    .setFunFileOutputContext(new DefaultFileOutputContext("FUN_" + i + ".txt"))
                    .print();
        }

        int numberOfVariables = getNumberOfVariables();

        // Vector of variables to evaluate cost function
        double[] x = new double[numberOfVariables];

        // Initializing and extracting the value for the variables
        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i);
        }

        // Optimizing functions from GUI
        Map<String, Double> vars = new HashMap<String, Double>();
        // Mapping vars to a hashmap and converting them from double to integer
        for (int i = 0; i < model.getNumOfVariables(); i++) {
            Double min = model.getMinIntervalOfVariablesDouble().elementAt(i);
            Double paso = model.getStepVariablesDouble().elementAt(i);
            Double varInDouble = min + paso * (x[i] - 1);
            vars.put(model.getNameOfVariables().elementAt(i), varInDouble);
        }
        // Creating expressions with objective functions and building them
        for (int i = 0; i < model.getNumOfObjFuncts(); i++) {
            Expression e = new ExpressionBuilder(model.getObjFuncts(i + 1))
                    .build()
                    .variables(vars);
            // Evaluating the expressions and storing scores and solution
            double fcost = e.evaluate();
            model.getMetalSolution().scores[i].add(fcost);
            solution.setObjective(i,fcost);
        }

    }

}