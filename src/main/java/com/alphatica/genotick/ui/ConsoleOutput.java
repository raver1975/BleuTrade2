package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.Tools;
import com.alphatica.genotick.timepoint.TimePoint;
import org.apache.commons.io.FileUtils;

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
