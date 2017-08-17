package com.alphatica.genotick.data;

import com.alphatica.genotick.genotick.RobotData;
import com.alphatica.genotick.timepoint.TimePoint;

import java.sql.Time;
import java.util.*;

import static java.util.Collections.binarySearch;

public class MainAppData {
    private final Map<DataSetName, DataSet> sets;
    private final Map<DataSetName, Double> profits;

    private final List<TimePoint> timePoints;

    public MainAppData() {
        sets = new HashMap<>();
        timePoints = new ArrayList<>();
        profits = new HashMap<>();
    }

    public double recordProfit(DataSetName setName, double profit) {
        double totalProfit = profits.get(setName);
        totalProfit *= (profit / 100.0) + 1;
        profits.put(setName, totalProfit);
        return totalProfit;
    }

    public void addDataSet(DataSet set) {
        sets.put(set.getName(), set);
        profits.put(set.getName(), 1.0);
        updateTimePoints(set.getTimePoints());
    }

    private void updateTimePoints(List<TimePoint> newTimePoints) {
        Set<TimePoint> set = new HashSet<>(this.timePoints);
        set.addAll(newTimePoints);
        timePoints.clear();
        timePoints.addAll(set);
        timePoints.sort(TimePoint::compareTo);
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
        return timePoints.get(0);
    }

    public TimePoint getNextTimePint(TimePoint now) {
        int index = binarySearch(timePoints, now);
        if(index < 0) {
            index = Math.abs(index + 1);
        }
        if(index > timePoints.size() - 2) {
            return null;
        } else  {
            return timePoints.get(index+1);
        }
    }

    public TimePoint getLastTimePoint() {
        if (sets.isEmpty())
            return null;
        return timePoints.get(timePoints.size()-1);
    }


    public Collection<DataSet> listSets() {
        return sets.values();
    }

    boolean isEmpty() {
        return sets.isEmpty();
    }

}
