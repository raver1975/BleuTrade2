package com.klemstinegroup.bleutrade;

import java.util.Arrays;

/**
 * Created by Paul on 5/11/2017.
 */
public class PriceData {
    long time;
    double bid[];
    double ask[];

//    public PriceData(long time, double bid, double ask) {
//        this.time = time;
//        this.bid = bid;
//        this.ask = ask;
//    }

    public PriceData(String[] bb) {
        bid=new double[bb.length/2];
        ask=new double[bb.length/2];
        time=Long.parseLong(bb[0]);
        for (int i=1;i<bb.length;i+=2){
            bid[i/2]=Double.parseDouble(bb[i]);
            ask[i/2]=Double.parseDouble(bb[i+1]);
        }
    }

    @Override
    public String toString(){
        return time+"\t"+ Arrays.toString(bid)+"\t"+Arrays.toString(ask);
    }

}
