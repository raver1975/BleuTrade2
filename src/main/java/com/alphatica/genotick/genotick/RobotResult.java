package com.alphatica.genotick.genotick;

import com.alphatica.genotick.population.Robot;
import com.alphatica.genotick.population.RobotName;

public class RobotResult {

    private final Prediction prediction;
    private final RobotName name;
    private final RobotData data;
    private final Double weight;

    public RobotResult(Prediction prediction, Robot robot, RobotData data) {

        this.prediction = prediction;
        this.name = robot.getName();
        this.weight = robot.getWeight();
        this.data = data;
    }

    @Override
    public String toString() {
        return "Name: " + name.toString() + " Prediction: " + prediction.toString() + " Weight: " + String.valueOf(weight);
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public Double getWeight() {
        return weight;
    }

    public RobotData getData() {
        return data;
    }
}
