package com.klemstinegroup.bleutrade;

import java.io.Serializable;

public class HistoricalData implements Serializable {
    public String prediction;
    public double currentPrice;
    public double nextPrice=-1;
    public long timestamp;
    public boolean correct=false;

    public HistoricalData(String prediction, double currentPrice) {
        this.prediction = prediction;
        this.currentPrice = currentPrice;
//        this.lastPrice=lastPrice;
        this.timestamp = System.currentTimeMillis();
    }

    public void setNextPrice(double nextPrice) {
        this.nextPrice = nextPrice;
        if (prediction.equals("OUT")) correct = false;
        else if (prediction.equals("UP") && currentPrice < nextPrice) correct = true;
        else if (prediction.equals("DOWN") && currentPrice > nextPrice) correct = true;

    }

    public boolean isCorrect() {
        if (nextPrice==-1d)return false;
        return correct;
    }

    public boolean hasNext(){
        return nextPrice!=-1;
    }
}
