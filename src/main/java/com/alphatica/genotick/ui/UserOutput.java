package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.timepoint.TimePoint;

public interface UserOutput {
	
	void setDebug(Boolean debug);
	
	Boolean getDebug();	
	
    void errorMessage(String message);

    void warningMessage(String message);
    
    void debugMessage(String message);

    void reportProfitForTimePoint(TimePoint timePoint, double cumulativeProfit, double timePointProfit);

    void showPrediction(TimePoint timePoint, DataSetName name, Prediction prediction);

    Thread.UncaughtExceptionHandler createExceptionHandler();

    void infoMessage(String s);
}
