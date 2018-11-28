package guicode;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import example.Jmetal_cst;

import javax.swing.*;

public class Chart extends JFrame {

    public Chart (String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "iterations","cost",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        int iterations = 1;

        for (int i=0; i<Jmetal_cst.scores.size(); i++) {
            dataset.addValue(Jmetal_cst.scores.elementAt(i),"cost",Integer.toString(iterations));
            iterations++;
        }

        return dataset;
    }

    public static void run() {
        Chart chart = new Chart(
                "JMetal Graph" ,
                "Cost over iterations");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
        chart.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
