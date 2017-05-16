package com.alphatica.genotick.timepoint;

import com.alphatica.genotick.genotick.DataSetExecutor;
import com.alphatica.genotick.processor.RobotExecutorFactory;
import com.alphatica.genotick.ui.UserOutput;

public class TimePointExecutorFactory {
    public static TimePointExecutor getDefaultExecutor(DataSetExecutor dataSetExecutor,
                                                       RobotExecutorFactory robotExecutorFactory,
                                                       UserOutput output) {
        TimePointExecutor executor = new SimpleTimePointExecutor(output);
        executor.setSettings(dataSetExecutor, robotExecutorFactory);
        return executor;
    }


}
