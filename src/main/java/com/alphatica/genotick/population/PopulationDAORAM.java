package com.alphatica.genotick.population;

import com.alphatica.genotick.genotick.RandomGenerator;
import com.alphatica.genotick.ui.UserOutput;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class PopulationDAORAM implements PopulationDAO {
    private final Map<RobotName,Robot> map = new HashMap<>();
    private final Random random;

    public PopulationDAORAM() {
        random = RandomGenerator.assignRandom();
    }
    @Override
    public Iterable<Robot> getRobotList() {
        return new Iterable<Robot> () {
            @Override
            public Iterator<Robot> iterator() {
                return map.values().iterator();
            }
        };
    }

    @Override
    public int getAvailableRobotsCount() {
        return map.size();
    }

    @Override
    public Robot getRobotByName(RobotName name) {
        return map.get(name);
    }

    @Override
    public void saveRobot(Robot robot) {
        if(robot.getName() == null) {
            robot.setName(getAvailableRobotName());
        }
        map.put(robot.getName(), robot);
    }

    @Override
    public void removeRobot(RobotName robotName) {
        map.remove(robotName);
    }


    private RobotName getAvailableRobotName() {
        long l;
        RobotName name;
        boolean nameExist;
        do {
            l = random.nextLong();
            if(l < 0)
                l = -l;
            name =  new RobotName(l);
            nameExist = map.containsKey(name);
        } while(nameExist);
        return name;
    }

    @Override
    public RobotName[] listRobotNames() {
        return map.keySet().toArray(new RobotName[map.size()]);
    }
}
