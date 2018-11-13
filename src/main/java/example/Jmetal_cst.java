package example; //Nombre de tu Package

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
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import gui.FirstWindow;

/**
 * @author angel
 */

public class Jmetal_cst {

    public static long computingTime;
    public static Vector<Double> scores;

    public void run() throws Exception{
        Algorithm<DoubleSolution> algorithm;
        List<Double> lowerLimit = Arrays.asList(0.1, 0.1); //Limite inferior del rango de soluciones (2 variables de optimización en este caso)
        List<Double> upperLimit = Arrays.asList(4.0, 4.0); //Límite superior del rango de soluciones
        // TODO: implementar en GUI
        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        DoubleProblem problem = new CST_opt(2,lowerLimit,upperLimit,ProjectPath) ; // Clase problema creada --> especifica el número de variables, objetivos y la función de coste del problema a optimizar

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0, 20.0) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 20.0) ;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<DoubleSolution>();

        //Elección del tipo del algoritmo genético que se va a utilizar, asi como el tamaño de su población y el número máximo de generaciones
        algorithm = new GeneticAlgorithmBuilder<DoubleSolution>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(FirstWindow.getPopulationSizeInt()) // Set to 2
                .setMaxEvaluations(FirstWindow.getMaxEvaluationsInt()) // Set to 25000
                .setSelectionOperator(selectionOperator)
                .build() ;

        scores = new Vector<>(25000);

        //Ejecucion del algoritmo de optimizacion
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute() ;

        //Obtención de la mejor solución alcanzada por el optimizador
        DoubleSolution solution = algorithm.getResult() ;
        List<DoubleSolution> population = new ArrayList<>(1) ;
        population.add(solution) ;

        computingTime = algorithmRunner.getComputingTime() ;

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
    