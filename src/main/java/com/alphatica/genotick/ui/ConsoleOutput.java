package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Main;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.Tools;
import com.alphatica.genotick.timepoint.TimePoint;
import com.klemstinegroup.bleutrade.CircleDrawer;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
new Thread(new Runnable() {
    @Override
    public void run() {
        Main.s1.add(timePoint.getValue(), cumulativeProfit);
        Main.s2.add(timePoint.getValue(), 0d);
        Main.s2.add(timePoint.getValue() + 1, timePointProfit);
        Main.s2.add(timePoint.getValue() + 2, 0d);

        while (Main.s1.getItemCount() > 100) {
            Main.s1.remove(0);
        }
        while (Main.s2.getItemCount() > 300) {
            Main.s2.remove(0);
        }
        String title = "Coin Price Future Prediction";
        String xAxisLabel = "Timestep";
        String yAxisLabel = "Profit";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;

        XYSeriesCollection c = new XYSeriesCollection();
        c.addSeries(Main.s1);
        c.addSeries(Main.s2);
        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, c, orientation, legend, tooltips, urls);


        // get a reference to the plot for further customisation...

        final CircleDrawer anomaly1a = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.green);
        final CircleDrawer anomaly1b = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.red);

        final CircleDrawer anomaly2a = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.green);
        final CircleDrawer anomaly2b = new CircleDrawer(Color.black, new BasicStroke(1.0f), Color.red);

        final CircleDrawer blue = new CircleDrawer(Color.white, new BasicStroke(1.0f), Color.yellow);

        final XYPlot plot = chart.getXYPlot();

        List bb1 = Main.s1.getItems();
        for (Object gg : bb1) {
            XYDataItem gg1 = (XYDataItem) gg;
            final XYAnnotation point = new XYDrawableAnnotation(gg1.getXValue(), gg1.getYValue(), 8, 8, (gg1.getYValue() > 0 ? anomaly1a : anomaly1b));
            plot.addAnnotation(point);
        }
        List bb2 = Main.s2.getItems();
        int cnnt = 0;
        for (Object gg : bb2) {
            XYDataItem gg1 = (XYDataItem) gg;
            if (cnnt++ % 3 != 1) {
                final XYAnnotation point = new XYDrawableAnnotation(gg1.getXValue(), gg1.getYValue(), 8, 8, (gg1.getYValue() > 0 ? anomaly2a : anomaly2b));
                plot.addAnnotation(point);
            } else {
                final XYAnnotation point = new XYDrawableAnnotation(gg1.getXValue(), gg1.getYValue(), 8, 8, blue);
                plot.addAnnotation(point);
            }
            cnnt++;
        }

        plot.setRangeZeroBaselineVisible(true);
        BasicStroke bs = new BasicStroke(10);
        plot.setRangeZeroBaselinePaint(Color.BLUE);
        plot.setRangeZeroBaselineStroke(bs);

        // Auto zoom to fit time series in initial window
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(true);

        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);


        domainAxis.setAutoRange(true);

//        try {
//            ChartUtilities.saveChartAsPNG(new File("./images/image" + (cnter++) + ".png"), chart, 1400, 800);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ChartPanel panel = new ChartPanel(chart);
        try {
            ChartUtilities.saveChartAsPNG(new File("./images/imageChart" + (j++) + ".png"), chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
//while (Main.f.getComponentCount()>1)Main.f.remove(1);
        if (Main.f.getContentPane()!=null) {
            while (Main.f.getContentPane().getComponentCount()>0) {
                Main.f.getContentPane().remove(0);
            }
        }
        System.gc();
        Main.f.setContentPane(panel);
        Main.f.revalidate();


        log("Profit for " + timePoint.toString() + ": "
                + "Cumulative profit: " + cumulativeProfit + " "
                + "TimePoint's profit: " + timePointProfit);


    }
}).start();

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
        try {
            FileUtils.write(logFile, string + System.lineSeparator(), true);
        } catch (IOException e) {
            System.err.println("Unable to write to file " + logFile.getPath() + ": " + e.getMessage());
            System.exit(1);
        }
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
