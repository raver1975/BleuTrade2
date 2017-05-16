package com.alphatica.genotick.timepoint;

import com.alphatica.genotick.genotick.Outcome;
import com.alphatica.genotick.genotick.Prediction;


public class SetResult {

    private double profit = 0;
    private Outcome outcome = Outcome.OUT;

    public Outcome getOutcome() {
        return outcome;
    }

    void update(Double actualFutureChange, Prediction prediction) {
        switch (Outcome.getOutcome(prediction, actualFutureChange)) {
            case CORRECT:
                profit = Math.abs(actualFutureChange);
                outcome = Outcome.CORRECT;
                break;
            case INCORRECT:
                profit = -Math.abs(actualFutureChange);
                outcome = Outcome.INCORRECT;
                break;
        }
    }

    @Override
    public String toString() {
        return "Predicted %: " + String.valueOf(profit);
    }

    public double getProfit() {
        return profit;
    }

}
