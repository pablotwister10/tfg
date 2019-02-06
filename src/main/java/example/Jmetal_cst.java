package example; //Nombre de tu Package

import guicode.Chart;
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

import guicode.FirstWindow;

import javax.swing.*;

/**
 * @author angel
 */

public class Jmetal_cst {

    public static long computingTime;
    public static Vector<Double> scores;
    public static DoubleSolution sol;

    public void run() throws Exception {

        Algorithm<DoubleSolution> algorithm;
        ArrayList<Double> lowers = new ArrayList<>(FirstWindow.getNumOfVariablesFirstInt()); // 0.1 INTEGER
        ArrayList<Double> uppers = new ArrayList<>(FirstWindow.getNumOfVariablesFirstInt()); // 4.0

        lowers.addAll(FirstWindow.getMinIntervalOfVariablesDouble());
        uppers.addAll(FirstWindow.getMaxIntervalOfVariablesDouble());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        DoubleProblem problem = new CST_opt(FirstWindow.getNumOfVariablesFirstInt(),lowers,uppers,ProjectPath) ; // Clase problema creada --> especifica el número de variables, objetivos y la función de coste del problema a optimizar

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0, 20.0) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 20.0) ;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<DoubleSolution>();

        //Elección del tipo del algoritmo genético que se va a utilizar, asi como el tamaño de su población y el número máximo de generaciones
        algorithm = new GeneticAlgorithmBuilder<DoubleSolution>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(FirstWindow.getPopulationSizeInt()) // Set to 2
                .setMaxEvaluations(FirstWindow.getMaxEvaluationsInt()) // Set to 25000
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build() ;

        scores = new Vector<>(FirstWindow.getMaxEvaluationsInt());

        //Ejecucion del algoritmo de optimizacion
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute() ;

        //Obtención de la mejor solución alcanzada por el optimizador
        DoubleSolution solution = algorithm.getResult() ;
        List<DoubleSolution> population = new ArrayList<>(1) ;
        population.add(solution) ;

        computingTime = algorithmRunner.getComputingTime() ;
        sol = solution;

        //for (int i=0; i<solution.getObjectives().length; i++) {
        //    Jmetal_cst.scores.add(solution.getObjective(i));
        //}

        if (FirstWindow.getDoGraph()) {
            Chart chartJMetal = new Chart(null,null,null);
            chartJMetal.run(scores);
            //chartJMetal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }

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
}
    