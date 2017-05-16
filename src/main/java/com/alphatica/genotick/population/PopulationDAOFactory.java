package com.alphatica.genotick.population;

public class PopulationDAOFactory {
    public static PopulationDAO getDefaultDAO(String dao) {
        switch (dao) {
            case "": return new PopulationDAORAM();
            default: return new PopulationDAOFileSystem(dao);
        }
    }
}
