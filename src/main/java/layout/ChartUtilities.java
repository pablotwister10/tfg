package layout;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Vector;

public class ChartUtilities {

    public static DefaultCategoryDataset createDefaultCategoryDataset(Vector<Double> scores) {
        if (scores == null)
            return null;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int iterations = 1;

        for (int i=0; i<scores.size(); i++) {
            dataset.addValue(scores.elementAt(i),"cost",Integer.toString(iterations));
            iterations++;
        }

        return dataset;
    }

    public static XYDataset createXYDataset(Vector<Double>[] scores, int variableNumFirst, int variableNumSecond) {
        if (scores == null || variableNumFirst > scores.length || variableNumSecond > scores.length)
            return null;

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = createXYSeries(scores,variableNumFirst,variableNumSecond);

        dataset.addSeries(series);

        return dataset;
    }

    public static XYSeries createXYSeries(Vector<Double>[] scores, int variableNumFirst, int variableNumSecond) {
        if (scores == null)
            return null;

        XYSeries series = new XYSeries("Default Series");
        for (int i=0; i<scores.length; i++) {
            series.add(scores[i].get(variableNumFirst),scores[i].get(variableNumSecond));
        }

        return series;

    }

    public XYSeries createBestSeries(Vector<Double> scores) {
        if (scores == null)
            return null;

        XYSeries seriesDefault = new XYSeries("Best Series");

        int iterations = 1;

        Double bestScore = scores.elementAt(0);

        seriesDefault.add((double) iterations,bestScore);

        for (int i=1; i<scores.size(); i++) {
            if (scores.elementAt(i) < bestScore) {
                bestScore = scores.elementAt(i);
            }
            seriesDefault.add((double) iterations,bestScore);
            iterations++;
        }

        return seriesDefault;
    }

}
