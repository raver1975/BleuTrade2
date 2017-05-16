package com.alphatica.genotick.timepoint;

import com.alphatica.genotick.genotick.DataSetExecutor;
import com.alphatica.genotick.genotick.RobotData;
import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.RobotInfo;
import com.alphatica.genotick.processor.RobotExecutorFactory;

import java.util.List;

public interface TimePointExecutor {
    List<RobotInfo> getRobotInfos();

    TimePointResult execute(List<RobotData> robotDataList,
                            Population population,
                            boolean updateRobots);

    void setSettings(DataSetExecutor dataSetExecutor, RobotExecutorFactory robotExecutorFactory);
}
