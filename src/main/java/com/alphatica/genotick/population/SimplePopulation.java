package com.alphatica.genotick.population;


import java.util.ArrayList;
import java.util.List;

public class SimplePopulation implements Population {
    private int desiredSize = 1024;
    private PopulationDAO dao;

    @Override
    public void setDesiredSize(int size) {
        desiredSize = size;
    }

    @Override
    public int getDesiredSize() {
        return desiredSize;
    }

    @Override
    public int getSize() {
        return dao.getAvailableRobotsCount();
    }

    @Override
    public void setDao(PopulationDAO dao) {
        this.dao = dao;
    }

    @Override
    public void saveRobot(Robot robot) {
        dao.saveRobot(robot);
    }

    @Override
    public Robot getRobot(RobotName name) {
        return dao.getRobotByName(name);
    }

    @Override
    public void removeRobot(RobotName robotName) {
        dao.removeRobot(robotName);
    }

    @Override
    public List<RobotInfo> getRobotInfoList() {
        List<RobotInfo> list = new ArrayList<>(dao.getAvailableRobotsCount());
        for(Robot robot : dao.getRobotList()) {
            RobotInfo robotInfo = new RobotInfo(robot);
            list.add(robotInfo);
        }
        return list;
    }

    @Override
    public boolean haveSpaceToBreed() {
        return getSize() < getDesiredSize();
    }

    @Override
    public void savePopulation(String path) {
        PopulationDAO fs = new PopulationDAOFileSystem(path);
        for(Robot robot : dao.getRobotList()) {
            fs.saveRobot(robot);
        }
    }

    @Override
    public RobotName[] listRobotsNames() {
        return dao.listRobotNames();
    }

    @Override
    public double getAverageWeight() {
        double sum = 0;
        int count = 0;
        for(Robot robot: dao.getRobotList()) {
            sum += Math.abs(robot.getWeight());
            count++;
        }
        if(count > 0) {
            return sum / count;
        } else {
            return Double.NaN;
        }
    }

    @Override
    public double getAverageAge()
    {
        double age = 0;
        for(Robot robot : dao.getRobotList()) {
            age += robot.getTotalPredictions();
        }
        return age / this.getSize();
    }
}
