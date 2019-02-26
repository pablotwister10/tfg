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
    private long computingTime;
    private E solutionAlgorithm;


    public MetalSolution(MetalModel model) {
        computingTime = (long) 0;
        scores = new Vector[model.getNumOfObjFuncts()];
        for (int i=0; i<model.getNumOfObjFuncts(); i++)
            scores[i] = new Vector<T>(model.getEvaluations());
        solutionAlgorithm = null;
    }


    /* GETTERS */
    public long getComputingTime() {
        return computingTime;
    }

    public Vector<T>[] getScores() {
        return scores;
    }

    public E getSolutionAlgorithm() {
        return solutionAlgorithm;
    }

    /* SETTERS */
    void setComputingTime(long computingTime) {
        this.computingTime = computingTime;
    }

    protected void setScores(Vector<T>[] scores) {
        this.scores = scores;
    }

    void setSolutionAlgorithm(E solutionAlgorithm) {
        this.solutionAlgorithm = solutionAlgorithm;
    }

}
