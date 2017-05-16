package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.Tools;
import com.alphatica.genotick.timepoint.TimePoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvOutput implements UserOutput {
    private final ConsoleOutput console;
    private final SimpleTextWriter profitWriter;
    private final SimpleTextWriter predictionWriter;
    private final String pidString;
    private Boolean debug = false;

    public CsvOutput() throws IOException {
        console = new ConsoleOutput();
        pidString = Tools.getPidString();
        profitWriter = new SimpleTextWriter("profit_" + pidString + ".csv");
        predictionWriter = new SimpleTextWriter("predictions_" + pidString + ".csv");
    }

    @Override
    public void errorMessage(String message) {
        console.errorMessage(message);
    }

    @Override
    public void warningMessage(String message) {
        console.warningMessage(message);
    }

    @Override
    public void reportProfitForTimePoint(TimePoint timePoint, double cumulativeProfit, double timePointProfit) {
        String line = timePoint.toString() + "," + String.valueOf(cumulativeProfit) + "," + String.valueOf(timePointProfit);
        profitWriter.writeLine(line);
    }

    @Override
    public void showPrediction(TimePoint timePoint, DataSetName name, Prediction prediction) {
        String line = String.format("%s,%s,%s",timePoint.toString(),name.toString(),prediction.toString());
        predictionWriter.writeLine(line);
    }

    @Override
    public Thread.UncaughtExceptionHandler createExceptionHandler() {
        return console.createExceptionHandler();
    }

    @Override
    public void infoMessage(String s) {
        console.infoMessage(s);
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
			console.infoMessage(message);		
	}

}

class SimpleTextWriter {
    private final PrintWriter writer;
    SimpleTextWriter(String fileName) throws IOException {
        File file = new File(fileName);
        writer = new PrintWriter(new FileOutputStream(file));
    }

    void writeLine(String line) {
        writer.println(line);
        writer.flush();
    }
}
