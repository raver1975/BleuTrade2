package com.alphatica.genotick.data;

import com.alphatica.genotick.genotick.RobotData;
import com.alphatica.genotick.timepoint.TimePoint;

import java.util.*;

public class MainAppData {
    private final Map<DataSetName, DataSet> sets;

    public MainAppData() {
        sets = new HashMap<>();
    }

    public void addDataSet(DataSet set) {
        sets.put(set.getName(), set);
    }

    public List<RobotData> prepareRobotDataList(final TimePoint timePoint) {
        List<RobotData> list = Collections.synchronizedList(new ArrayList<>());
        sets.entrySet().parallelStream().forEach((Map.Entry<DataSetName, DataSet> entry) -> {
            RobotData robotData = entry.getValue().getRobotData(timePoint);
            if (!robotData.isEmpty())
                list.add(robotData);

        });
        return list;
    }

    public double getActualChange(DataSetName name, TimePoint timePoint) {
        return sets.get(name).calculateFutureChange(timePoint);
    }

    public TimePoint getFirstTimePoint() {
        if (sets.isEmpty())
            return null;
        TimePoint firstTimePoint = null;
        for (DataSet set : sets.values()) {
            TimePoint first = set.getFirstTimePoint();
            if (firstTimePoint == null) {
                firstTimePoint = first;
            } else if (first.compareTo(firstTimePoint) < 0) {
                firstTimePoint = first;
            }
        }
        return firstTimePoint;
    }

    public TimePoint getLastTimePoint() {
        if (sets.isEmpty())
            return null;
        TimePoint lastTimePoint = null;
        for (DataSet set : sets.values()) {
            TimePoint last = set.getLastTimePoint();
            if (lastTimePoint == null) {
                lastTimePoint = last;
            } else if (last.compareTo(lastTimePoint) > 0) {
                lastTimePoint = last;
            }
        }
        return lastTimePoint;
    }

    public Collection<DataSet> listSets() {
        return sets.values();
    }


    public boolean isEmpty() {
        return sets.isEmpty();
    }
}
