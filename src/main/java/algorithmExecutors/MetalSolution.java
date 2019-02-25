package algorithmExecutors;

import metalMVC.MetalModel;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.Vector;

public class MetalSolution {

    private long computingTime;
    private Vector<Double> scoresMono;
    protected Vector<Double>[] scores;
    private DoubleSolution solutionAlgorithmGenetic;



    public MetalSolution(MetalModel model) {
        // TODO: Simplify with one score
        if (model.getNumOfObjFuncts() == 1)
            scoresMono = new Vector<>(model.getEvaluations());

        scores = new Vector[model.getNumOfObjFuncts()];
        for (int i=0; i<model.getNumOfObjFuncts(); i++)
            scores[i] = new Vector<Double>(model.getEvaluations());

        computingTime = (long) 0;
        solutionAlgorithmGenetic = null;
    }


    /* GETTERS */
    public long getComputingTime() {
        return computingTime;
    }

    public Vector<Double> getScoresMono() {
        return scoresMono;
    }

    public Vector<Double>[] getScores() {
        return scores;
    }

    public DoubleSolution getSolutionAlgorithmGenetic() {
        return solutionAlgorithmGenetic;
    }

    /* SETTERS */
    void setComputingTime(long computingTime) {
        this.computingTime = computingTime;
    }

    void setScoresMono(Vector<Double> scoresMono) {
        this.scoresMono = scoresMono;
    }

    protected void setScores(Vector<Double>[] scores) {
        this.scores = scores;
    }

    void setSolutionAlgorithmGenetic(DoubleSolution solutionAlgorithmGenetic) {
        this.solutionAlgorithmGenetic = solutionAlgorithmGenetic;
    }

}
