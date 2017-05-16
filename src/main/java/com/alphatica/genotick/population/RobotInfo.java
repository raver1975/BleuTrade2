package com.alphatica.genotick.population;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class RobotInfo {
    public static final Comparator<RobotInfo> comparatorByAge = new AgeComparator();
    public static final Comparator<RobotInfo> comparatorByAbsoluteWeight = new AbsoluteWeightComparator();
    private static final DecimalFormat format = new DecimalFormat("0.00");
    private final RobotName name;
    private final double weight;
    private final long lastChildOutcomes;
    private final long totalChildren;
    private final long length;
    private final int totalPredictions;
    private final int totalOutcomes;
    private final int bias;

    @Override
    public String toString() {
        return name.toString() + ": Outcomes: " + String.valueOf(totalPredictions) + " weight: " + format.format(weight) +
                " bias: " + String.valueOf(bias) + " length: " + String.valueOf(length) +
                " totalChildren: " + String.valueOf(totalChildren);
    }

    public RobotName getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getTotalPredictions() {
        return totalPredictions;
    }

    public RobotInfo(Robot robot) {
        name = new RobotName(robot.getName().getName());
        weight = robot.getWeight();
        lastChildOutcomes = robot.getOutcomesAtLastChild();
        totalChildren = robot.getTotalChildren();
        length = robot.getLength();
        totalPredictions = robot.getTotalPredictions();
        totalOutcomes = robot.getTotalOutcomes();
        bias = robot.getBias();
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canBeParent(long minimumParentAge, long timeBetweenChildren) {
        if(totalPredictions < minimumParentAge)
            return false;
        long outcomesSinceLastChild = totalPredictions - lastChildOutcomes;
        return outcomesSinceLastChild >= timeBetweenChildren;
    }

    public int getTotalOutcomes() {
        return totalOutcomes;
    }

    public int getBias() {
        return bias;
    }

    private static double getTotalWeight(List<RobotInfo> list) {
        double weight = 0;
        for(RobotInfo robotInfo: list) {
            weight += Math.abs(robotInfo.getWeight());
        }
        return weight;
    }

    public static double getAverageWeight(List<RobotInfo> list) {
        if(list.isEmpty()) {
            return 0;
        } else {
            return getTotalWeight(list) / list.size();
        }
    }

    public static double getAverageLength(List<RobotInfo> robotsInfos) {
        if(robotsInfos.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for(RobotInfo robotInfo: robotsInfos) {
            sum += robotInfo.length;
        }
        return sum / robotsInfos.size();
    }
}
