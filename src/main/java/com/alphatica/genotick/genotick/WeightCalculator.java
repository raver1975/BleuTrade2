package com.alphatica.genotick.genotick;

import com.alphatica.genotick.population.Robot;

public class WeightCalculator {

    private WeightCalculator() {}

    public static double calculateWeight(Robot robot) {
        return calculateSquareOfDifference(robot);
        //return calculateCorrectVsIncorrectPredictions(robot);
    }

    @SuppressWarnings("unused")
    private static double calculateCorrectVsIncorrectPredictions(Robot robot) {
        int totalPrediction = robot.getTotalPredictions();
        if(totalPrediction == 0)
            return 0;
        double correct = robot.getCorrectPredictions();
        double incorrect = robot.getTotalPredictions() - correct;
        return correct - incorrect;
    }

    private static double calculateSquareOfDifference(Robot robot) {
        double correct = robot.getCorrectPredictions();
        double incorrect = robot.getTotalPredictions() - correct;
        boolean positive = correct > incorrect;
        double weightAbs = Math.pow(correct - incorrect,2);
        return positive ? weightAbs : -weightAbs;
    }


}
