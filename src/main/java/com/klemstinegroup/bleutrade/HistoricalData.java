package com.klemstinegroup.bleutrade;

public class HistoricalData {
    public  String prediction;
    public  double price;
    public double lastPrice;
    public long timestamp;
    public boolean correct;

    public HistoricalData(String prediction, double price, double lastPrice){
        this.prediction=prediction;
        this.price=price;
        this.lastPrice=lastPrice;
        this.timestamp=System.currentTimeMillis();
        if (prediction.equals("OUT"))correct=true;
        else if (prediction.equals("UP")&&price>lastPrice)correct=true;
        else if (prediction.equals("DOWN")&&price<lastPrice)correct=true;
        else correct=false;
    }


    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "HistoricalData{" +
                "prediction='" + prediction + '\'' +
                ", price=" + price +
                ", lastPrice=" + lastPrice +
                ", correct=" + correct +
                '}';
    }
}
