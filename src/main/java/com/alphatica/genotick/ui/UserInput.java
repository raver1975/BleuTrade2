package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.MainAppData;
import com.alphatica.genotick.genotick.Simulation;
import com.alphatica.genotick.genotick.MainSettings;

public interface UserInput {
    MainSettings getSettings();
    void setSimulation(Simulation simulation);
    @SuppressWarnings("unused")
    Simulation getSimulation();
    MainAppData getData(String settings);
}
