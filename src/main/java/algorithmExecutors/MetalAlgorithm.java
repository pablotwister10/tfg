package algorithmExecutors;

import metalMVC.MetalModel;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T> Type of variable (Double, Integer)
 * @param <E> Type of algorithm (DoubleSolution, IntegerSolution)
 * @param <P> Type of problem (DoubleProblem, IntegerProblem)
 */
public class MetalAlgorithm<T,E,P> {

    private MetalSolution metalSolution;
    private MetalModel model;

    Algorithm<E> algorithm;
    ArrayList<T> lowers;
    ArrayList<T> uppers;
    P problem;


    public MetalAlgorithm(MetalModel model) {
        this.model = model;
        this.metalSolution = model.getMetalSolution(); // there's really no need to because it is inside the model instance

        lowers = new ArrayList<T>(model.getNumOfVariables()); // 0.1 INTEGER
        uppers = new ArrayList<T>(model.getNumOfVariables()); // 4.0

        algorithm = new Algorithm<E>() {
            @Override
            public void run() {

            }

            @Override
            public E getResult() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }
        };

    }


    public boolean run() {

        boolean done = false;

        System.out.println(model.getAlgorithmType() + " chosen");
        switch (model.getAlgorithmType()) {
            case "Genetic Algorithm": {
                // TODO: Parametrize type of variable, problem and solution
                if (model.getVariableType().equalsIgnoreCase("Double")) {
                    runGeneticDouble();
                } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                    runGeneticInteger();
                }
                done = true;
                break;
            }
            case "NSGAII": {
                if (model.getVariableType().equalsIgnoreCase("Double")) {
                    runNSGAIIDouble();
                    done = true;
                    break;
                } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                    runNSGAIIInteger();
                    done = true;
                    break;
                }
            }
        }
        if (done) System.out.println(model.getAlgorithmType() + " executed");

        return done;

    }

    private void runGeneticDouble() {

        Algorithm<DoubleSolution> algorithm;
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables()); // 0.1 INTEGER
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables()); // 4.0

        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        DoubleProblem problem = new CST_opt_Double(model,lowers,uppers,ProjectPath) ; // Clase problema creada --> especifica el número de variables, objetivos y la función de coste del problema a optimizar

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        //Elección del tipo del algoritmo genético que se va a utilizar, asi como el tamaño de su población y el número máximo de generaciones
        algorithm = new GeneticAlgorithmBuilder<>(problem, crossoverOperator, mutationOperator)
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
        metalSolution.setSolutionAlgorithm(solution);

        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }

    private void runGeneticInteger() {

        Algorithm<IntegerSolution> algorithmInteger;
        ArrayList<Integer> lowersInteger = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppersInteger = new ArrayList<>(model.getNumOfVariables());

        lowersInteger.addAll(model.getMinIntervalOfVariablesInteger());
        uppersInteger.addAll(model.getMaxIntervalOfVariablesInteger());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)

        IntegerProblem problemInteger = new CST_opt_Integer(model,lowersInteger,uppersInteger,ProjectPath);

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<IntegerSolution> crossoverOperatorInteger = new IntegerSBXCrossover(1.0,2.0);
        MutationOperator<IntegerSolution> mutationOperatorInteger = new IntegerPolynomialMutation(1.0/problemInteger.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperatorInteger = new BinaryTournamentSelection<>();

        algorithmInteger = new GeneticAlgorithmBuilder<>(problemInteger, crossoverOperatorInteger, mutationOperatorInteger)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperatorInteger)
                .build();

        AlgorithmRunner algorithmRunnerInteger = new AlgorithmRunner.Executor(algorithmInteger)
                .execute();

        IntegerSolution solutionInteger = algorithmInteger.getResult();
        List<IntegerSolution> populationInteger = new ArrayList<>(1);
        populationInteger.add(solutionInteger);

        metalSolution.setComputingTime(algorithmRunnerInteger.getComputingTime());
        metalSolution.setSolutionAlgorithm(solutionInteger);

        new SolutionListOutput(populationInteger)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }

    private void runNSGAIIDouble() {

        NSGAII<DoubleSolution> algorithm;
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables()); // 0.1 INTEGER
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables()); // 4.0

        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        DoubleProblem problem = new CST_opt_Double(model,lowers,uppers,ProjectPath) ; // Clase problema creada --> especifica el número de variables, objetivos y la función de coste del problema a optimizar

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        //Elección del tipo del algoritmo genético que se va a utilizar, asi como el tamaño de su población y el número máximo de generaciones
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
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
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfVariables(); j++) {
                metalSolution.scoresPareto[i].add(j,solution.get(i).getVariableValue(j)); // Change for getVariableValue if desired
            }
        }

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

    private void runNSGAIIInteger() {

        NSGAII<IntegerSolution> algorithm;
        ArrayList<Integer> lowers = new ArrayList<>(model.getNumOfVariables()); // 0.1 INTEGER
        ArrayList<Integer> uppers = new ArrayList<>(model.getNumOfVariables()); // 4.0

        lowers.addAll(model.getMinIntervalOfVariablesInteger());
        uppers.addAll(model.getMaxIntervalOfVariablesInteger());

        String ProjectPath = "C:\\Users\\angel\\Desktop\\PruebasJava"; //Esta es un path que lo necesitaba (en tu caso no hace falta)
        IntegerProblem problem = new CST_opt_Integer(model,lowers,uppers,ProjectPath) ; // Clase problema creada --> especifica el número de variables, objetivos y la función de coste del problema a optimizar

        //Parámetros de Cruce, Mutación y Selección del algoritmo genético (parámetros del optimizador)
        CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(1.0,20.0) ;
        MutationOperator<IntegerSolution> mutationOperator = new IntegerPolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperator = new BinaryTournamentSelection<>();

        //Elección del tipo del algoritmo genético que se va a utilizar, asi como el tamaño de su población y el número máximo de generaciones
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
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
        List<IntegerSolution> solution = algorithm.getResult();

        //model.setSolution(computingTime);

        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfVariables(); j++) {
                metalSolution.scoresPareto[i].add(j,solution.get(i).getVariableValue(j));
            }
        }

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
