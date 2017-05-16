package com.alphatica.genotick.genotick;

import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.PopulationDAO;
import com.alphatica.genotick.population.SimplePopulation;

class PopulationFactory {

    public static Population getDefaultPopulation(PopulationDAO dao) {
        Population population = new SimplePopulation();
        population.setDao(dao);
        return population;
    }
}
