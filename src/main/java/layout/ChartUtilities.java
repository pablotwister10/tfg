package layout;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import java.util.Vector;

public class ChartUtilities {

    public static DefaultCategoryDataset createDataset(Vector<Double> scores) {
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

    public XYSeries createDefaultSeries(Vector<Double> scores) {
        if (scores == null)
            return null;
        
        XYSeries seriesDefault = new XYSeries("Default Series");

        int iterations = 1;

        for (int i=0; i<scores.size(); i++) {
            seriesDefault.add((double) iterations,scores.elementAt(i));
            iterations++;
        }

        return seriesDefault;
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
