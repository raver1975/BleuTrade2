package com.alphatica.genotick.processor;

import com.alphatica.genotick.population.RobotExecutor;
import com.alphatica.genotick.population.RobotExecutorSettings;

public class RobotExecutorFactory {
    private final RobotExecutorSettings settings;

    public RobotExecutorFactory(RobotExecutorSettings settings) {
        this.settings = settings;
    }

    public RobotExecutor getDefaultRobotExecutor() {
        RobotExecutor executor = SimpleProcessor.createProcessor();
        executor.setSettings(settings);
        return executor;
    }
}
