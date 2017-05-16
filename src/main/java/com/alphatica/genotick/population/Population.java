package com.alphatica.genotick.population;


import java.util.List;

public interface Population {

    void setDesiredSize(int size);

    int getDesiredSize();

    int getSize();

    double getAverageAge();

    void setDao(PopulationDAO dao);

    void saveRobot(Robot robot);

    Robot getRobot(RobotName key);

    void removeRobot(RobotName robotName);

    List<RobotInfo> getRobotInfoList();

    boolean haveSpaceToBreed();

    void savePopulation(String path);

    RobotName[] listRobotsNames();

    double getAverageWeight();
}
