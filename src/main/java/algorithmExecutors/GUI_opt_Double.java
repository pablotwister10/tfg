package algorithmExecutors;

import metalMVC.MetalModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <> Type of variable (Double, Integer)
 * @param <> Type of problem (DoubleProblem, IntegerProblem)
 */
@SuppressWarnings("serial")
public class GUI_opt_Double extends AbstractDoubleProblem {

    private MetalModel model;


    // Constructor de la clase DoubleProblem
    GUI_opt_Double(MetalModel model, List<Double> lowerbounds, List<Double> upperbounds, String ProjectPath)  {
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
    public void evaluate(DoubleSolution solution) {

        int numberOfVariables = getNumberOfVariables();

        //La variable Double x guarda los valores que se van a evaluar en la función de coste (
        double[] x = new double[numberOfVariables] ;

        for (int i=0; i<numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i) ;
        }

        /* Optimizing functions from GUI */
        Map<String, Double> vars = new HashMap<String, Double>();
        for (int i = 0; i< model.getNumOfVariables(); i++) {
            vars.put(model.getNameOfVariables().elementAt(i),x[i]);
        }
        for (int i=0; i<model.getNumOfObjFuncts(); i++) {
            Expression e = new ExpressionBuilder(model.getObjFuncts(i+1))
                    .build()
                    .variables(vars);
            double fcost = e.evaluate();
            model.getMetalSolution().scores[i].add(fcost);
            solution.setObjective(i,fcost);
        }

    }

}