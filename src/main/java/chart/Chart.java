package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.Vector;

public class Chart extends JFrame {

    public Chart (String applicationTitle, String chartTitle, Vector<Double> scores) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "iterations","cost", //TODO: best cost with value
                createDataset(scores),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560 ,367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(Vector<Double> scores) {
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

    public static void run(Vector<Double> scores) {
        Chart chart = new Chart(
                "JMetal Graph" ,
                "Cost over iterations",
                scores);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        chart.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
