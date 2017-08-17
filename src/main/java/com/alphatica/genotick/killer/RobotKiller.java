package com.alphatica.genotick.killer;

import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.RobotInfo;

import java.util.List;

public interface RobotKiller {
    void killRobots(Population population, List<RobotInfo> robotsInfos);

    void setSettings(RobotKillerSettings killerSettings);
}
