package com.alphatica.genotick.genotick;

public enum Prediction {
    UP(1),
    DOWN(-1),
    OUT(0);
    private final double value;

    Prediction(double value) {
        this.value = value;
    }
    public static Prediction getPrediction(double change) {
        if(change > 0)
            return Prediction.UP;
        if(change < 0)
            return Prediction.DOWN;
        return Prediction.OUT;
    }

    public boolean isCorrect(double actualFutureChange) {
        return actualFutureChange * value > 0;
    }

    @Override
    public String toString() {
        return name();
    }
}
