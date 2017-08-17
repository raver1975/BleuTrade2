package com.alphatica.genotick.genotick;

public enum Outcome {
    CORRECT,
    INCORRECT,
    OUT;


    public static Outcome getOutcome(Prediction prediction, double actualChange) {
        if(prediction == Prediction.OUT)
            return OUT;
        return prediction.isCorrect(actualChange) ? CORRECT : INCORRECT;
    }
}
