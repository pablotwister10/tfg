package algorithmExecutors;

import metalMVC.MetalModel;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCell;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * MetalAlgorithm Class
 *
 * Runs the algorithm
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


    /**
     * MetalAlgorithm Constructor
     *
     * @param model MetalModel for data abstraction
     */
    public MetalAlgorithm(MetalModel model) {
        this.model = model;
        this.metalSolution = model.getMetalSolution(); // there's really no need to because it is inside the model instance

        // This right now is useless, need to parametrize in order to write these functions
        lowers = new ArrayList<T>(model.getNumOfVariables());
        uppers = new ArrayList<T>(model.getNumOfVariables());

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


    // Main Running method, calls other methods depending of Optimization and Type of Variable
    public boolean run() throws IOException {

        boolean done = false;

        System.out.println(model.getAlgorithmType() + " chosen"); // LOGGER
        // Switch to run different methods depending on algorithm and optimization choice
        switch (model.getAlgorithmType()) {
            case "Genetic Algorithm": {
                // TODO: Parametrize type of variable, problem and solution
                if (model.getOptimizationChoice().equalsIgnoreCase("GUI")) {
                    if (model.getVariableType().equalsIgnoreCase("Double")) {
                        runGeneticGuiDouble();
                    } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                        runGeneticGuiInteger();
                    }
                    done = true;
                    break;
                } else if (model.getOptimizationChoice().equalsIgnoreCase("CST")) {
                    updateMacros();
                    if (model.getVariableType().equalsIgnoreCase("Double")) {
                        runGeneticCSTDouble();
                    } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                        runGeneticCSTInteger();
                    }
                    done = true;
                    break;
                } else if (model.getOptimizationChoice().equalsIgnoreCase("MATLAB")) {
                }
            }
            case "NSGAII": {
                if (model.getOptimizationChoice().equalsIgnoreCase("GUI")) {
                    if (model.getVariableType().equalsIgnoreCase("Double")) {
                        runNSGA2GuiDouble();
                    } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                        runNSGA2GuiInteger();
                    }
                    done = true;
                    break;
                } else if (model.getOptimizationChoice().equalsIgnoreCase("CST")) {
                    updateMacros();
                    if (model.getVariableType().equalsIgnoreCase("Double")) {
                        runNSGA2CSTDouble();
                    } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                        runNSGA2CSTInteger();
                    }
                    done = true;
                    break;
                } else if (model.getOptimizationChoice().equalsIgnoreCase("MATLAB")) {
                }
            }
            case "MOCell": {
                if (model.getOptimizationChoice().equalsIgnoreCase("GUI")) {
                    if (model.getVariableType().equalsIgnoreCase("Double")) {
                        runMOCellGuiDouble();
                    } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                        runMOCellGuiInteger();
                    }
                    done = true;
                    break;
                } else if (model.getOptimizationChoice().equalsIgnoreCase("CST")) {
                    updateMacros();
                    if (model.getVariableType().equalsIgnoreCase("Double")) {
                        runMOCellCSTDouble();
                    } else if (model.getVariableType().equalsIgnoreCase("Integer")) {
                        runMOCellCSTInteger();
                    }
                    done = true;
                    break;
                } else if (model.getOptimizationChoice().equalsIgnoreCase("MATLAB")) {
                }
            }
        }
        if (done) System.out.println(model.getAlgorithmType() + " executed"); // LOGGER

        return done;

    }


    // Run Genetic Algorithm type Double with GUI functions
    private void runGeneticGuiDouble() {

        // Setting algorithm of type Double, with DoubleSolution and type of variable Double
        Algorithm<DoubleSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        // Creating problem with model for data abstraction and lower and upper bounds
        DoubleProblem problem = new GuiOptDouble(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type DoubleSolution
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        // Genetic Algorithm, setting population size, evaluations and selection operator
        algorithm = new GeneticAlgorithmBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        DoubleSolution solution = algorithm.getResult();
        // TODO: Check what this does
        List<DoubleSolution> population = new ArrayList<>(1);
        // Best solution obtained
        population.add(solution);

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);

        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.getVariableValue(i));
            metalSolution.solutionVariables.add(solution.getVariableValue(i));
        }

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }

    // Run Genetic Algorithm type Integer with GUI functions
    private void runGeneticGuiInteger() {

        // Setting algorithm of type Integer, with IntegerSolution and type of variable Integer
        Algorithm<IntegerSolution> algorithmInteger;
        // Lower and Upper bounds instantiation
        ArrayList<Integer> lowersInteger = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppersInteger = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowersInteger.addAll(model.getMinIntervalOfVariablesInteger());
        uppersInteger.addAll(model.getMaxIntervalOfVariablesInteger());

        // Creating problem with model for data abstraction and lower and upper bounds
        IntegerProblem problemInteger = new GuiOptInteger(model,lowersInteger,uppersInteger);

        // Crossover, Mutation and Selection parameters for the algorithm of type IntegerSolution
        CrossoverOperator<IntegerSolution> crossoverOperatorInteger = new IntegerSBXCrossover(1.0,2.0);
        MutationOperator<IntegerSolution> mutationOperatorInteger = new IntegerPolynomialMutation(1.0/problemInteger.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperatorInteger = new BinaryTournamentSelection<>();

        // Genetic Algorithm, setting population size, evaluations and selection operator
        algorithmInteger = new GeneticAlgorithmBuilder<>(problemInteger, crossoverOperatorInteger, mutationOperatorInteger)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperatorInteger)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunnerInteger = new AlgorithmRunner.Executor(algorithmInteger)
                .execute();

        // Initialization of solution to set it
        IntegerSolution solutionInteger = algorithmInteger.getResult();
        List<IntegerSolution> populationInteger = new ArrayList<>(1);
        // Best solution obtained
        populationInteger.add(solutionInteger);

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunnerInteger.getComputingTime());
        metalSolution.setSolutionAlgorithm(solutionInteger);

        new SolutionListOutput(populationInteger)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solutionInteger.getVariableValue(i));
            metalSolution.solutionVariables.add(solutionInteger.getVariableValue(i));
        }

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }

    // Run Genetic Algorithm type Double with CST
    private void runGeneticCSTDouble() {

        // Setting algorithm of type Double, with DoubleSolution and type of variable Double
        Algorithm<DoubleSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        // Creating problem with model for data abstraction and lower and upper bounds
        DoubleProblem problem = new CstOptDouble(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type DoubleSolution
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        // Genetic Algorithm, setting population size, evaluations and selection operator
        algorithm = new GeneticAlgorithmBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        DoubleSolution solution = algorithm.getResult();
        List<DoubleSolution> population = new ArrayList<>(1);
        // Best solution obtained
        population.add(solution);

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);

        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.getVariableValue(i));
            metalSolution.solutionVariables.add(solution.getVariableValue(i));
        }

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
    }

    // Run Genetic Algorithm type Integer with CST
    private void runGeneticCSTInteger() {

        // Setting algorithm of type Integer, with IntegerSolution and type of variable Integer
        Algorithm<IntegerSolution> algorithmInteger;
        // Lower and Upper bounds instantiation
        ArrayList<Integer> lowersInteger = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppersInteger = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowersInteger.addAll(model.getMinIntervalOfVariablesInteger());
        uppersInteger.addAll(model.getMaxIntervalOfVariablesInteger());

        // Creating problem with model for data abstraction and lower and upper bounds
        IntegerProblem problemInteger = new CstOptInteger(model,lowersInteger,uppersInteger);

        // Crossover, Mutation and Selection parameters for the algorithm of type IntegerSolution
        CrossoverOperator<IntegerSolution> crossoverOperatorInteger = new IntegerSBXCrossover(1.0,2.0);
        MutationOperator<IntegerSolution> mutationOperatorInteger = new IntegerPolynomialMutation(1.0/problemInteger.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperatorInteger = new BinaryTournamentSelection<>();

        // Genetic Algorithm, setting population size, evaluations and selection operator
        algorithmInteger = new GeneticAlgorithmBuilder<>(problemInteger, crossoverOperatorInteger, mutationOperatorInteger)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperatorInteger)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunnerInteger = new AlgorithmRunner.Executor(algorithmInteger)
                .execute();

        // Initialization of solution to set it
        IntegerSolution solutionInteger = algorithmInteger.getResult();
        List<IntegerSolution> populationInteger = new ArrayList<>(1);
        // Best solution obtained
        populationInteger.add(solutionInteger);

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunnerInteger.getComputingTime());
        metalSolution.setSolutionAlgorithm(solutionInteger);

        new SolutionListOutput(populationInteger)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solutionInteger.getVariableValue(i));
            metalSolution.solutionVariables.add(solutionInteger.getVariableValue(i));
        }

        //JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        //JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        //JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }

    // Run NSGAII Algorithm type Double with GUI functions
    private void runNSGA2GuiDouble() {

        // Setting algorithm of type Double, with DoubleSolution and type of variable Double
        NSGAII<DoubleSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        // Creating problem with model for data abstraction and lower and upper bounds
        DoubleProblem problem = new GuiOptDouble(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type DoubleSolution
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<DoubleSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<DoubleSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run NSGAII Algorithm type Integer with GUI functions
    private void runNSGA2GuiInteger() {

        // Setting algorithm of type Integer, with IntegerSolution and type of variable Integer
        NSGAII<IntegerSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Integer> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesInteger());
        uppers.addAll(model.getMaxIntervalOfVariablesInteger());

        // Creating problem with model for data abstraction and lower and upper bounds
        IntegerProblem problem = new GuiOptInteger(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type IntegerSolution
        CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(1.0,20.0);
        MutationOperator<IntegerSolution> mutationOperator = new IntegerPolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<IntegerSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<IntegerSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run NSGAII Algorithm type Double with CST
    private void runNSGA2CSTDouble() {

        // Setting algorithm of type Double, with DoubleSolution and type of variable Double
        NSGAII<DoubleSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        // Creating problem with model for data abstraction and lower and upper bounds
        DoubleProblem problem = new CstOptDouble(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type DoubleSolution
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<DoubleSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<DoubleSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run NSGAII Algorithm type Integer with CST
    private void runNSGA2CSTInteger() {

        // Setting algorithm of type Integer, with IntegerSolution and type of variable Integer
        NSGAII<IntegerSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Integer> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesInteger());
        uppers.addAll(model.getMaxIntervalOfVariablesInteger());

        // Creating problem with model for data abstraction and lower and upper bounds
        IntegerProblem problem = new GuiOptInteger(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type IntegerSolution
        CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(1.0,20.0);
        MutationOperator<IntegerSolution> mutationOperator = new IntegerPolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<IntegerSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<IntegerSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run MOCell Algorithm type Double with GUI functions
    private void runMOCellGuiDouble() {

        // Setting algorithm of type Double, with DoubleSolution and type of variable Double
        MOCell<DoubleSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        // Creating problem with model for data abstraction and lower and upper bounds
        DoubleProblem problem = new GuiOptDouble(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type DoubleSolution
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new MOCellBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<DoubleSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<DoubleSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run MOCell Algorithm type Integer with GUI functions
    private void runMOCellGuiInteger() {

        // Setting algorithm of type Integer, with IntegerSolution and type of variable Integer
        MOCell<IntegerSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Integer> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesInteger());
        uppers.addAll(model.getMaxIntervalOfVariablesInteger());

        // Creating problem with model for data abstraction and lower and upper bounds
        IntegerProblem problem = new GuiOptInteger(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type IntegerSolution
        CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(1.0,20.0);
        MutationOperator<IntegerSolution> mutationOperator = new IntegerPolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new MOCellBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<IntegerSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<IntegerSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run MOCell Algorithm type Double with CST
    private void runMOCellCSTDouble() {

        // Setting algorithm of type Double, with DoubleSolution and type of variable Double
        NSGAII<DoubleSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Double> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Double> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesDouble());
        uppers.addAll(model.getMaxIntervalOfVariablesDouble());

        // Creating problem with model for data abstraction and lower and upper bounds
        DoubleProblem problem = new CstOptDouble(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type DoubleSolution
        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(1.0,20.0);
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<DoubleSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<DoubleSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }

    // Run MOCell Algorithm type Integer with CST
    private void runMOCellCSTInteger() {

        // Setting algorithm of type Integer, with IntegerSolution and type of variable Integer
        NSGAII<IntegerSolution> algorithm;
        // Lower and Upper bounds instantiation
        ArrayList<Integer> lowers = new ArrayList<>(model.getNumOfVariables());
        ArrayList<Integer> uppers = new ArrayList<>(model.getNumOfVariables());
        // Adding lower and upper bounds
        lowers.addAll(model.getMinIntervalOfVariablesInteger());
        uppers.addAll(model.getMaxIntervalOfVariablesInteger());

        // Creating problem with model for data abstraction and lower and upper bounds
        IntegerProblem problem = new GuiOptInteger(model,lowers,uppers);

        // Crossover, Mutation and Selection parameters for the algorithm of type IntegerSolution
        CrossoverOperator<IntegerSolution> crossoverOperator = new IntegerSBXCrossover(1.0,20.0);
        MutationOperator<IntegerSolution> mutationOperator = new IntegerPolynomialMutation(1.0/problem.getNumberOfVariables(),20.0);
        SelectionOperator<List<IntegerSolution>,IntegerSolution> selectionOperator = new BinaryTournamentSelection<>();

        // NSGAII Algorithm, setting population size, evaluations and selection operator
        algorithm = new NSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                .setPopulationSize(model.getPopulationSize())
                .setMaxEvaluations(model.getEvaluations())
                .setSelectionOperator(selectionOperator)
                //.setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
                .build();

        // Execution of the algorithm, changing scores inside method
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // Initialization of solution to set it
        List<IntegerSolution> solution = algorithm.getResult();

        // Initialization to get population
        List<IntegerSolution> finalPopulation = algorithm.getPopulation();

        // Setting computing time and solution to metalSolution class
        metalSolution.setComputingTime(algorithmRunner.getComputingTime());
        metalSolution.setSolutionAlgorithm(solution);
        for (int i=0; i<model.getPopulationSize(); i++) {
            for (int j=0; j<model.getNumOfObjFuncts(); j++) {
                // Setting pareto scores to metalSolution class
                metalSolution.scoresPareto[i].add(j,finalPopulation.get(i).getObjective(j));
            }
        }

        for (int i=0; i<model.getNumOfVariables(); i++) {
            System.out.println("Solution for variable " + model.getNameOfVariables().get(i) + " is: "
                    + solution.get(0).getVariableValue(i));
            metalSolution.solutionVariables.add(solution.get(0).getVariableValue(i));
        }

    }


    // TODO: catch exception and pop window with no macro_template.bas found
    private void updateMacros() throws IOException {

        // Parameters to update macros
        String param = "";

        // Path of macros
        param += "-p \"" + model.getProjectPath() + "\" ";
        // Name of variables
        for (int i=0; i<model.getNumOfVariables(); i++) {
            param += "-var " + model.getNameOfVariables().elementAt(i) + " ";
        }
        // Name of variables to optimize
        for (int i=0; i<model.getNumOfObjFuncts(); i++) {
            if (model.getObjFuncts(i+1).equalsIgnoreCase("S11")
                    || model.getObjFuncts(i+1).equalsIgnoreCase("S1,1"))
                param += "-obj S1,1 ";
            if (model.getObjFuncts(i+1).equalsIgnoreCase("S12")
                    || model.getObjFuncts(i+1).equalsIgnoreCase("S1,2"))
                param += "-obj S1,2 ";
            if (model.getObjFuncts(i+1).equalsIgnoreCase("S21")
                    || model.getObjFuncts(i+1).equalsIgnoreCase("S2,1"))
                param += "-obj S2,1 ";
            if (model.getObjFuncts(i+1).equalsIgnoreCase("S22")
                    || model.getObjFuncts(i+1).equalsIgnoreCase("S2,2"))
                param += "-obj S2,2 ";
        }

        // Path to script
        String pathToProgram = System.getProperty("user.dir");
        String path = pathToProgram + "\\src\\main\\scripts";

        String command ="python \"" + path + "\\update_script.py\" " + param;
        Process p = Runtime.getRuntime().exec(command);

        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        System.out.println("Updating macro python script starts: " + line);
        line = bfr.readLine();
        System.out.println("\tPython output: " + line);
        while ((line = bfr.readLine()) != null){
            System.out.println("\tPython Output: " + line);
        }

    }

}
