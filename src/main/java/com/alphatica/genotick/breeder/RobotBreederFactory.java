package com.alphatica.genotick.breeder;

import com.alphatica.genotick.mutator.Mutator;

public class RobotBreederFactory {
    public static RobotBreeder getDefaultBreeder(BreederSettings breederSettings, Mutator mutator) {
        RobotBreeder breeder = SimpleBreeder.getInstance();
        breeder.setSettings(breederSettings,mutator);
        return breeder;
    }
}
