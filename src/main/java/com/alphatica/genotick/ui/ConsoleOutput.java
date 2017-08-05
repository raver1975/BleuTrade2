package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.exceptions.ExecutionException;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.Tools;
import com.alphatica.genotick.timepoint.TimePoint;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.lang.String.format;

class ConsoleOutput implements UserOutput {

    //private File logFile = new File(format("genotick-log-%s.txt", Tools.getPidString()));
    private Boolean debugEnabled = true;

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
        log("Profit for " + timePoint.toString() + ": "
                + "Cumulative profit: " + cumulativeProfit + " "
                + "TimePoint's profit: " + timePointProfit);
    }

    @Override
    public void showPrediction(TimePoint timePoint, DataSetName name, Prediction prediction) {
        log(format("%s prediction on %s for the next trade: %s",
                name.toString(),timePoint.toString(),prediction.toString()));
    }

    @Override
    public void showCumulativeProfit(TimePoint timePoint, DataSetName name, double profit) {
        log(format("Cumulative profit for %s: %d", name.getName(), profit));
    }

    @Override
    public void infoMessage(String s) {
        log(s);
    }

    private void log(String string) {
        System.out.println(string);
//        try {
//            FileUtils.writeStringToFile(logFile, string + System.lineSeparator(), Charset.defaultCharset().toString());
//        } catch (IOException e) {
//
//            System.err.println("Unable to write to file " + logFile.getPath() + ": " + e.getMessage());
//
//            throw new ExecutionException(format("Unable to write to file %s", logFile.getAbsoluteFile()), e);
//        }
    }

	@Override
	public void setDebugEnabled(Boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
		
	}

	@Override
	public Boolean getDebugEnabled() {
		return debugEnabled;
	}

	@Override
	public void debugMessage(String message) {
		if(debugEnabled) {
            log(message);
        }
	}

}
