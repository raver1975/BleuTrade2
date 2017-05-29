package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Main;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.Tools;
import com.alphatica.genotick.timepoint.TimePoint;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

class ConsoleOutput implements UserOutput {

    private File logFile = new File(String.format("genotick-log-%s.txt", Tools.getPidString()));
    private Boolean debug = true;    
    
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
        Main.s1.add(timePoint.getValue(),cumulativeProfit);
        Main.s2.add(timePoint.getValue(),timePointProfit);
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
        final XYPlot plot = chart.getXYPlot();

        // Auto zoom to fit time series in initial window
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(false);

        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        domainAxis.setAutoRange(true);

//        try {
//            ChartUtilities.saveChartAsPNG(new File("./images/image" + (cnter++) + ".png"), chart, 1400, 800);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (Main.panel == null) {
            Main.panel = new ChartPanel(chart);
        } else {
            Main.f.remove(Main.panel);
            Main.panel = new ChartPanel(chart);

        }
        Main.f.add(Main.panel);
        Main.panel.revalidate();
        Main.f.revalidate();
        log("Profit for " + timePoint.toString() + ": "
                + "Cumulative profit: " + cumulativeProfit + " "
                + "TimePoint's profit: " + timePointProfit);
    }

    @Override
    public void showPrediction(TimePoint timePoint, DataSetName name, Prediction prediction) {
        log(String.format("%s prediction on %s for the next trade: %s",
                name.toString(),timePoint.toString(),prediction.toString()));
    }

    @Override
    public Thread.UncaughtExceptionHandler createExceptionHandler() {
        return (thread, throwable) -> {
            log("Exception in thread: " + thread.getName());
            for(StackTraceElement element: throwable.getStackTrace()) {
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
		if(this.debug)
			log(message);		
	}

}
