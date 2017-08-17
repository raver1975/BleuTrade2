package com.alphatica.genotick.breeder;

public class BreederSettings {
    public final long outcomesBetweenBreeding;
    public final double inheritedWeightPercent;
    public final long minimumOutcomesToAllowBreeding;
    public final double randomRobots;
    public final int dataMaximumOffset;
    public final int ignoreColumns;

    public BreederSettings(long timeBetweenChildren, double inheritedWeightPercent, long minimumParentAge, double randomRobots, int dataMaximumOffset, int ignoreColumns) {
        this.outcomesBetweenBreeding = timeBetweenChildren;
        this.inheritedWeightPercent = inheritedWeightPercent;
        this.minimumOutcomesToAllowBreeding = minimumParentAge;
        this.randomRobots = randomRobots;
        this.dataMaximumOffset = dataMaximumOffset;
        this.ignoreColumns = ignoreColumns;
    }
}
