package layout;

import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Vector;

public class ChartUtilities {

    public static DefaultCategoryDataset createDataset(Vector<Double> scores) {
        if (scores == null) {
            return null;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int iterations = 1;

        for (int i=0; i<scores.size(); i++) {
            dataset.addValue(scores.elementAt(i),"cost",Integer.toString(iterations));
            iterations++;
        }

        return dataset;
    }

}
