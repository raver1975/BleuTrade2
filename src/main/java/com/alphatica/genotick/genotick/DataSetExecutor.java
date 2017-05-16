package com.alphatica.genotick.genotick;

import com.alphatica.genotick.population.*;

import java.util.List;

public interface DataSetExecutor {

    List<RobotResult> execute(List<RobotData> robotDataList, Robot robot, RobotExecutor robotExecutor);

}
