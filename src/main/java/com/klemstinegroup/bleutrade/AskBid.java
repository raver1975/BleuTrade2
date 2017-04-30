package com.klemstinegroup.bleutrade;

class AskBid{
        private final double bid;
        private final double ask;

        AskBid(double ask, double bid){
            this.ask=ask;
            this.bid=bid;
        }
        @Override
    public String toString(){
            return "ask:"+ask+"\t"+"bid:"+bid;
        }
    }