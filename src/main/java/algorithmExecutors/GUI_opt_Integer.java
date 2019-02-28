package algorithmExecutors;

import metalMVC.MetalModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI_opt_Integer extends AbstractIntegerProblem {

    private MetalModel model;


    // Constructor de la clase DoubleProblem
    GUI_opt_Integer(MetalModel model, List<Integer> lowerbounds, List<Integer> upperbounds, String ProjectPath)  {
        this.model = model;

        setNumberOfVariables(model.getNumOfVariables());
        setNumberOfObjectives(model.getNumOfObjFuncts());
        setNumberOfConstraints(0) ;
        setName("prueba_GA");

        setLowerLimit(lowerbounds);
        setUpperLimit(upperbounds);
    }


    // Método que evalua la función de coste a partir de una entrada (solution)
    @Override
    public void evaluate(IntegerSolution solution) {

        int numberOfVariables = getNumberOfVariables();

        //La variable Double x guarda los valores que se van a evaluar en la función de coste (
        double[] x = new double[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i);
        }

        /* Optimizing functions from GUI */
        Map<String, Double> vars = new HashMap<String, Double>();
        for (int i = 0; i < model.getNumOfVariables(); i++) {
            Double min = model.getMinIntervalOfVariablesDouble().elementAt(i);
            Double paso = model.getStepVariablesDouble().elementAt(i);
            Double varInDouble = min + paso * (x[i] - 1);
            vars.put(model.getNameOfVariables().elementAt(i), varInDouble);
        }
        for (int i = 0; i < model.getNumOfObjFuncts(); i++) {
            Expression e = new ExpressionBuilder(model.getObjFuncts(i + 1))
                    .build()
                    .variables(vars);
            double fcost = e.evaluate();
            model.getMetalSolution().scores[i].add(fcost);
            solution.setObjective(i, fcost);
        }

    }

}