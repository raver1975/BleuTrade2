package com.alphatica.genotick.genotick;

import com.alphatica.genotick.timepoint.TimePointExecutor;
import com.alphatica.genotick.timepoint.TimePointStats;
import com.alphatica.genotick.breeder.RobotBreeder;
import com.alphatica.genotick.data.MainAppData;
import com.alphatica.genotick.killer.RobotKiller;
import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.ui.UserOutput;

import java.util.List;

public interface Engine {
    List<TimePointStats> start();

    void setSettings(EngineSettings engineSettings, TimePointExecutor timePointExecutor, MainAppData data, RobotKiller killer, RobotBreeder breeder, Population population);
}
