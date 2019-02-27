package algorithmExecutors;

import metalMVC.MetalModel;

import java.util.Vector;

/**
 *
 * @param <T> Type of variable (Double, Integer)
 * @param <E> Type of algorithm (DoubleSolution, IntegerSolution)
 */
public class MetalSolution<T,E> {

    Vector<T>[] scores;
    Vector<T>[] scoresPareto;
    private long computingTime;
    private E solutionAlgorithm;


    public MetalSolution(MetalModel model) {
        computingTime = (long) 0;
        scores = new Vector[model.getNumOfObjFuncts()];
        for (int i=0; i<model.getNumOfObjFuncts(); i++)
            scores[i] = new Vector<>(model.getEvaluations());
        scoresPareto = new Vector[model.getPopulationSize()];
        for (int i=0; i<model.getPopulationSize(); i++)
            scoresPareto[i] = new Vector<>(model.getNumOfVariables());
        solutionAlgorithm = null;
    }


    /* GETTERS */
    public long getComputingTime() {
        return computingTime;
    }

    public Vector<T>[] getScores() {
        return scores;
    }

    public Vector<T>[] getScoresPareto() {
        return scoresPareto;
    }

    public E getSolutionAlgorithm() {
        return solutionAlgorithm;
    }

    /* SETTERS */
    void setComputingTime(long computingTime) {
        this.computingTime = computingTime;
    }

    void setScores(Vector<T>[] scores) {
        this.scores = scores;
    }

    public void setScoresPareto(Vector<T>[] scoresPareto) {
        this.scoresPareto = scoresPareto;
    }

    void setSolutionAlgorithm(E solutionAlgorithm) {
        this.solutionAlgorithm = solutionAlgorithm;
    }

}
