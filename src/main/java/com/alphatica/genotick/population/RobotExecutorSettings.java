package com.alphatica.genotick.population;

import com.alphatica.genotick.genotick.MainSettings;

public class RobotExecutorSettings {
    public final int instructionLimit;

    public RobotExecutorSettings(MainSettings settings) {
        instructionLimit = settings.processorInstructionLimit;
    }
}
