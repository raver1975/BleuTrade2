package com.alphatica.genotick.population;

import com.alphatica.genotick.genotick.Prediction;
import com.alphatica.genotick.genotick.RobotData;

public interface RobotExecutor {
    byte totalRegisters = 4;

    Prediction executeRobot(RobotData robotData, Robot robot);

    void setSettings(RobotExecutorSettings settings);
}
