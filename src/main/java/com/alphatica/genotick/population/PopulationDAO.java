package com.alphatica.genotick.population;

public interface PopulationDAO {

    Iterable<Robot> getRobotList();

    int getAvailableRobotsCount();

    Robot getRobotByName(RobotName name);

    void saveRobot(Robot robot);

    void removeRobot(RobotName robotName);

    RobotName[] listRobotNames();
}
