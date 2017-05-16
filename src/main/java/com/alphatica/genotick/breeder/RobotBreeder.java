package com.alphatica.genotick.breeder;

import com.alphatica.genotick.mutator.Mutator;
import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.RobotInfo;

import java.util.List;

public interface RobotBreeder {

    void breedPopulation(Population population, List<RobotInfo> robotInfos);

    void setSettings(BreederSettings breederSettings, Mutator mutator);

}
