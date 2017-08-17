package com.alphatica.genotick.instructions;

import com.alphatica.genotick.population.RobotExecutor;

class Registers {
    public static byte validateRegister(byte register) {
        return (byte)((register < 0 ? -register : register) % RobotExecutor.totalRegisters);
    }
}
