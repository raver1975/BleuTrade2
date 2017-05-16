package com.klemstinegroup.bleutrade;

/**
 * Created by Paul on 5/11/2017.
 */
public class PriceData {
    long time;
    double bid;
    double ask;

    public PriceData(long time, double bid, double ask) {
        this.time = time;
        this.bid = bid;
        this.ask = ask;
    }
}
