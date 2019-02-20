package algorithmExecutors; //Nombre de tu Package

import metalMVC.MetalModel;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author angel
 */

public class Jmetal_cst {

    private static long computingTime;
    protected static Vector<Double> scores;
    private static DoubleSolution sol;
    private MetalModel model;

    public Jmetal_cst() {
        computingTime = (long) 0;
        scores = null;
        sol = null;
        model = null;
    }

    public Jmetal_cst(MetalModel model) {
        computingTime = (long) 0;
        scores = null;
        sol = null;
        this.model = model;
    }

    public void run() throws Exception {

        Algorithm<DoubleSolution> algorithm;
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables()); // 0.1 INTEGER
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables()); // 4.0

        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        DoubleProblem problem = new CST_opt(model,lowers,uppers,ProjectPath) ; // Clase problema creada --> especifica el número de variables, objetivos y la función de coste del problema a optimizar

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0, 20.0) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 20.0) ;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<DoubleSolution>();

        //Elección del tipo del algoritmo genético que se va a utilizar, asi como el tamaño de su población y el número máximo de generaciones
        algorithm = new GeneticAlgorithmBuilder<DoubleSolution>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize()) // Set to 2
                .setMaxEvaluations(model.getEvaluations()) // Set to 25000
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        scores = new Vector<>(model.getEvaluations());

        //Ejecucion del algoritmo de optimizacion
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        model.setSolution(scores);

        //Obtención de la mejor solución alcanzada por el optimizador
        DoubleSolution solution = algorithm.getResult();
        List<DoubleSolution> population = new ArrayList<>(1);
        population.add(solution);

        computingTime = algorithmRunner.getComputingTime();
        sol = solution;

        model.setSolution(computingTime);
        model.setSolution(sol);

        //for (int i=0; i<solution.getObjectives().length; i++) {
        //    Jmetal_cst.scores.add(solution.getObjective(i));
        //}

        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }
    // TODO code application logic here

    protected MetalModel getMetalModel() {
        return model;
    }

}
    