package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.Tools;
import com.alphatica.genotick.timepoint.TimePoint;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.ChartUtilities;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.annotations.XYAnnotation;
//import org.jfree.chart.annotations.XYDrawableAnnotation;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYDataItem;

import java.io.File;

class ConsoleOutput implements UserOutput {

    private File logFile = new File(String.format("genotick-log-%s.txt", Tools.getPidString()));
    private Boolean debug = true;
    private static int j;

    @Override
    public void errorMessage(String message) {
        log("Error: " + message);
    }

    @Override
    public void warningMessage(String message) {
        log("Warning: " + message);
    }

    @Override
    public void reportProfitForTimePoint(TimePoint timePoint, double cumulativeProfit, double timePointProfit) {
//    new Thread(new Runnable() {
//    @Override
//    public void run() {
//        Main.s1.add(timePoint.getValue(), cumulativeProfit);
//        Main.s2.add(timePoint.getValue(), 0d);
//        Main.s2.add(timePoint.getValue() + 1, timePointProfit);
//        Main.s2.add(timePoint.getValue() + 2, 0d);
//
//        while (Main.s1.getItemCount() > 33) {
//            Main.s1.remove(0);
//        }
//        while (Main.s2.getItemCount() > 99) {
//            Main.s2.remove(0);
//        }
//        boolean legend = true;
//        boolean tooltips = false;
//        boolean urls = false;
//
//        XYSeriesCollection c = new XYSeriesCollection();
//        c.addSeries(Main.s1);
//        c.addSeries(Main.s2);
//
//
//        // get a reference to the plot for further customisation...
//
//        final CircleDrawer anomaly1a = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.green);
//        final CircleDrawer anomaly1b = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.red);
//
//        final CircleDrawer anomaly2a = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.green);
//        final CircleDrawer anomaly2b = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.red);
//
//        final CircleDrawer blue = new CircleDrawer(Color.white, new BasicStroke(1.0f), Color.yellow);
//
//
//        log("Profit for " + timePoint.toString() + ": "
//                + "Cumulative profit: " + cumulativeProfit + " "
//                + "TimePoint's profit: " + timePointProfit);
//
//
//    }
//}).start();

    }

    @Override
    public void showPrediction(TimePoint timePoint, DataSetName name, Prediction prediction) {
        log(String.format("%s prediction on %s for the next trade: %s",
                name.toString(), timePoint.toString(), prediction.toString()));
    }

    @Override
    public Thread.UncaughtExceptionHandler createExceptionHandler() {
        return (thread, throwable) -> {
            log("Exception in thread: " + thread.getName());
            for (StackTraceElement element : throwable.getStackTrace()) {
                log(element.toString());
            }
        };
    }

    @Override
    public void infoMessage(String s) {
        log(s);
    }

    private void log(String string) {
        System.out.println(string);
//        try {
//            FileUtils.write(logFile, string + System.lineSeparator(), true);
//        } catch (IOException e) {
//            System.err.println("Unable to write to file " + logFile.getPath() + ": " + e.getMessage());
//            System.exit(1);
//        }
    }

    @Override
    public void setDebug(Boolean debug) {
        this.debug = debug;

    }

    @Override
    public Boolean getDebug() {
        return this.debug;
    }

    @Override
    public void debugMessage(String message) {
        if (this.debug)
            log(message);
    }

}
