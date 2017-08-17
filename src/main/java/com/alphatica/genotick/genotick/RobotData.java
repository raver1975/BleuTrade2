package com.alphatica.genotick.genotick;

import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.processor.NotEnoughDataException;

import java.util.ArrayList;
import java.util.List;

public class RobotData {
    private final List<double[]> data;
    private final double actualChange;
    private final DataSetName name;

    public static RobotData createData(List<double[]> newData, DataSetName name, double actualChange) {
        return new RobotData(newData,name,actualChange);
    }

    public static RobotData createEmptyData(DataSetName name) {
        List<double []> list = new ArrayList<>();
        list.add(new double[0]);
        return createData(list,name,Double.NaN);
    }

    private RobotData(List<double[]> newData, DataSetName name, double actualChange) {
        data = newData;
        this.name = name;
        this.actualChange = actualChange;
    }

    public double getData(int dataColumn, int dataOffset) {
        if (dataOffset >= data.get(dataColumn).length)
            throw new NotEnoughDataException();
        else
            return data.get(dataColumn)[dataOffset];
    }

    public DataSetName getName() {
        return name;
    }

    public boolean isEmpty() {
        return data.get(0).length == 0;
    }

    public Double getActualChange() {
        return actualChange;
    }

    public int getColumns() {
        return data.size();
    }
}
