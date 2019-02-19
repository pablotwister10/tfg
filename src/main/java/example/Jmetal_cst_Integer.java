package example; //Nombre de tu Package

import chart.Chart;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import chart.FirstWindow;

/**
 * @author angel
 */

public class Jmetal_cst_Integer {

    public static long computingTime;
    public static Vector<Double> scores;
    public static IntegerSolution sol;

    public void run() throws Exception {

        // INTEGER
        Algorithm<IntegerSolution> algorithmInteger;
        ArrayList<Integer> lowersInteger = new ArrayList<>(FirstWindow.getNumOfVariablesFirstInt());
        ArrayList<Integer> uppersInteger = new ArrayList<>(FirstWindow.getNumOfVariablesFirstInt());

        // INTEGER
        lowersInteger.addAll(FirstWindow.getYMinOfVariables());
        uppersInteger.addAll(FirstWindow.getYMaxOfVariables());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        // INTEGER
        IntegerProblem problemInteger = new CST_opt_Integer(FirstWindow.getNumOfVariablesFirstInt(),lowersInteger,uppersInteger,ProjectPath);

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0, 20.0) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0 / problemInteger.getNumberOfVariables(), 20.0) ;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<DoubleSolution>();
        // INTEGER
        CrossoverOperator<IntegerSolution> crossoverOperatorInteger = new IntegerSBXCrossover(1.0,2.0);
        MutationOperator<IntegerSolution> mutationOperatorInteger = new IntegerPolynomialMutation(1.0/problemInteger.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperatorInteger = new BinaryTournamentSelection<IntegerSolution>();

        // INTEGER
        algorithmInteger = new GeneticAlgorithmBuilder<IntegerSolution>(problemInteger,crossoverOperatorInteger,mutationOperatorInteger)
                .setPopulationSize(FirstWindow.getPopulationSizeInt())
                .setMaxEvaluations(FirstWindow.getMaxEvaluationsInt())
                .setSelectionOperator(selectionOperatorInteger)
                .build();

        scores = new Vector<>(FirstWindow.getMaxEvaluationsInt());

        // INTEGER
        AlgorithmRunner algorithmRunnerInteger = new AlgorithmRunner.Executor(algorithmInteger)
                .execute();

        // INTEGER
        IntegerSolution solutionInteger = algorithmInteger.getResult();
        List<IntegerSolution> populationInteger = new ArrayList<>(1);
        populationInteger.add(solutionInteger);


        computingTime = algorithmRunnerInteger.getComputingTime() ;
        sol = solutionInteger;

        if (FirstWindow.getDoGraph()) {
            Chart chartJMetal = new Chart(null,null, null);
            chartJMetal.run(scores);
            //chartJMetal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }

        new SolutionListOutput(populationInteger)
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
