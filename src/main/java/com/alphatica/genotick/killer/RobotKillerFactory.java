package com.alphatica.genotick.killer;


public class RobotKillerFactory {
    public static RobotKiller getDefaultRobotKiller(RobotKillerSettings killerSettings) {
        RobotKiller killer = SimpleRobotKiller.getInstance();
        killer.setSettings(killerSettings);
        return killer;
    }
}
