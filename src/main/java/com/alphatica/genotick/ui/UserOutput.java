package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.timepoint.TimePoint;

public interface UserOutput {
	
	void setDebugEnabled(Boolean debugEnabled);
	
	Boolean getDebugEnabled();
	
    void errorMessage(String message);

    void warningMessage(String message);
    
    void debugMessage(String message);

    void reportProfitForTimePoint(TimePoint timePoint, double cumulativeProfit, double timePointProfit);

    void showPrediction(TimePoint timePoint, DataSetName name, Prediction prediction);

    void showCumulativeProfit(TimePoint timePoint, DataSetName name, double profit);

    void infoMessage(String s);
}
