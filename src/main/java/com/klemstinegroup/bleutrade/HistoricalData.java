package com.klemstinegroup.bleutrade;

import java.io.Serializable;

public class HistoricalData implements Serializable {
    public String realPrediction;
    public String virtualPrediction;
    public double currentPrice;
    public double nextPrice=-1;
    public long timestamp;
    public boolean correct=false;

    public HistoricalData(String prediction, double currentPrice) {
        this.realPrediction = prediction;
        this.virtualPrediction=prediction;
        this.currentPrice = currentPrice;
//        this.lastPrice=lastPrice;
        this.timestamp = System.currentTimeMillis();
    }

    public void setNextPrice(double nextPrice) {
        this.nextPrice = nextPrice;
        if (virtualPrediction.equals("OUT")) correct = false;
        else if (virtualPrediction.equals("UP") && currentPrice < nextPrice) correct = true;
        else if (virtualPrediction.equals("DOWN") && currentPrice > nextPrice) correct = true;

    }

    public void reversePrediction(){
        if (realPrediction.equals("OUT"))return;
        if (realPrediction.equals("UP"))virtualPrediction="DOWN";
        if (realPrediction.equals("DOWN"))virtualPrediction="UP";

    }

    public boolean isCorrect() {
        if (nextPrice==-1d)return false;
        return correct;
    }

    public boolean hasNext(){
        return nextPrice!=-1;
    }
}
