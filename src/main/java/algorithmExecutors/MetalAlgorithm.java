package algorithmExecutors;

import metalMVC.MetalModel;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
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

public class MetalAlgorithm {

    private static MetalSolution metalSolution;
    private MetalModel model;


    public MetalAlgorithm(MetalModel model) {
        this.model = model;
        this.metalSolution = model.getMetalSolution(); // there's really no need to because it is inside the model instance
    }


    public boolean run() throws Exception {

        boolean done = false;

        System.out.println(model.getAlgorithmType() + " chosen");
        switch (model.getAlgorithmType()) {
            // TODO: Try implementing different algorithms
            case "Genetic Algorithm": {
                runGenetic();
                done = true;
                break;
            }
            case "NSGAII": {
                runNSGAII();
                done = true;
                break;
            }
        }
        if (done) System.out.println(model.getAlgorithmType() + " executed");

        return done;

    }

    private void runGenetic() throws Exception {

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

        //Ejecucion del algoritmo de optimizacion (SCORES WILL BE CHANGED)
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        //Obtención de la mejor solución alcanzada por el optimizador
        DoubleSolution solution = algorithm.getResult();
        List<DoubleSolution> population = new ArrayList<>(1);
        population.add(solution);

        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithmGenetic(solution);

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

    private void runNSGAII() throws Exception {

        NSGAII<DoubleSolution> algorithm ;
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
        algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize()) // Set to 2
                .setMaxEvaluations(model.getEvaluations()) // Set to 25000
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        //Ejecucion del algoritmo de optimizacion
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        //model.setSolution(scores);
        //metalSolution.setScoresMulti(scores);

        //Obtención de la mejor solución alcanzada por el optimizador
        List<DoubleSolution> solution = algorithm.getResult();

        //model.setSolution(computingTime);

        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        //metalSolution.setSolutionAlgorithmGenetic(solution);

        // TODO: Solve solution passing to MetalModel
/*

        algorithm.getResult();
        List<DoubleSolution> population = new ArrayList<>(1);
        population.add(algorithm.getResult());

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
*/
    }

}
