package com.klemstinegroup.bleutrade;

import java.io.*;

import java.util.List;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import org.nd4j.linalg.api.ndarray.INDArray;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SMOreg;
import weka.core.Instances;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.SelectedTag;


import javax.swing.*;

/**
 * Example of using the time series forecasting API. To compile and
 * run the CLASSPATH will need to contain:
 * <p>
 * weka.jar (from your weka distribution)
 * pdm-timeseriesforecasting-ce-TRUNK-SNAPSHOT.jar (from the time series package)
 * jcommon-1.0.14.jar (from the time series package lib directory)
 * jfreechart-1.0.13.jar (from the time series package lib directory)
 */
public class TimeSeriesExample {
    static JFrame f;
    static JPanel panel;

    static {
        f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setTitle("Training Data");
        f.setSize(1200,800);

        RefineryUtilities.centerFrameOnScreen(f);
        f.setVisible(true);
    }

    private static int stepsToPredict = 30;
    private static int cnter;
    public static final int howManyRealToShow = 100;

    public TimeSeriesExample() throws Exception {
        String data = "@relation stock\n" +
                "@attribute time numeric\n" +
                "@attribute bid numeric\n" +
                "@attribute ask numeric\n" +
                "@data\n";
        Scanner sc = new Scanner(new File("./rnnRegression/passengers_raw.csv"));
        XYSeries s1 = new XYSeries("bid");
        XYSeries s2 = new XYSeries("ask");
        long cnt = 0;
        while (sc.hasNext()) {
            String ggh = sc.nextLine();
            String[] sp = ggh.split(",");
            s1.add(Long.parseLong(sp[0]) / 900000L, Double.parseDouble(sp[1]));
            s2.add(Long.parseLong(sp[0]) / 900000L, Double.parseDouble(sp[2]));
            data += ggh + "\n";
            cnt = Math.max(cnt, Long.parseLong(sp[0]) / 900000L);
        }
        System.out.println(data);
        while (s1.getItemCount() > howManyRealToShow) s1.remove(0);
        while (s2.getItemCount() > howManyRealToShow) s2.remove(0);
        // load the wine data
        Instances wine = new Instances(new BufferedReader(new StringReader(data)));

        // new forecaster
        WekaForecaster forecaster = new WekaForecaster();

        // set the targets we want to forecast. This method calls
        // setFieldsToLag() on the lag maker object for us
        forecaster.setFieldsToForecast("bid,ask");

        // default underlying classifier is SMOreg (SVM) - we'll use
        // gaussian processes for regression instead
//        GaussianProcesses gp=new GaussianProcesses();
//        forecaster.setBaseForecaster(gp);

//        forecaster.getTSLagMaker().setTimeStampField("time"); // date time stamp
//        forecaster.getTSLagMaker().setMinLag(1);
//        forecaster.getTSLagMaker().setMaxLag(96); // daily lag

        // build the model
        forecaster.buildForecaster(wine, System.out);

        // prime the forecaster with enough recent historical data
        // to cover up to the maximum lag. In our case, we could just supply
        // the 12 most recent historical instances, as this covers our maximum
        // lag period
        forecaster.primeForecaster(wine);

        // forecast for 12 units (months) beyond the end of the
        // training data
        List<List<NumericPrediction>> forecast = forecaster.forecast(stepsToPredict, System.out);

        // output the predictions. Outer list is over the steps; inner list is over
        // the targets
        XYSeries s3 = new XYSeries("predict bid");
        XYSeries s4 = new XYSeries("predict ask");
        for (int i = 0; i < stepsToPredict; i++) {
            List<NumericPrediction> predsAtStep = forecast.get(i);
            NumericPrediction bibb = predsAtStep.get(0);
            NumericPrediction askb = predsAtStep.get(1);
            s3.add(i + cnt, bibb.predicted());
            s4.add(i + cnt, askb.predicted());
            System.out.println(bibb.predicted() + "," + askb.predicted());
        }

        String title = "Regression example";
        String xAxisLabel = "Timestep";
        String yAxisLabel = "Coin Price";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;
        XYSeriesCollection c = new XYSeriesCollection();
//        createSeries(c, fullArray, 0, "Train data");
//        createSeries(c, testArray, trainSize - 1, "Actual test data");
//        createSeries(c, predicted, trainSize - 1, "Predicted test data");
//        createSeries(c, predicted1, trainSize+testSize - 1, "Predicted future data");
//         createSeries(c, predicted1, trainSize+numberOfTimesteps - 1, "Predicted test data");

        c.addSeries(s1);
        c.addSeries(s2);
        c.addSeries(s3);
        c.addSeries(s4);

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, c, orientation, legend, tooltips, urls);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();

        // Auto zoom to fit time series in initial window
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(false);

        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        domainAxis.setAutoRange(true);

        try {
            ChartUtilities.saveChartAsPNG(new File("./images/image" + (cnter++) + ".png"), chart, 1400, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (panel == null) {
            panel = new ChartPanel(chart);
        } else {
            f.remove(panel);
            panel = new ChartPanel(chart);

        }
        f.add(panel);
         panel.revalidate();
        f.revalidate();
        Thread.sleep(200);
//


        // we can continue to use the trained forecaster for further forecasting
        // by priming with the most recent historical data (as it becomes available).
        // At some stage it becomes prudent to re-build the model using current
        // historical data.

    }

}