package com.alphatica.genotick.population;

import java.io.Serializable;
import java.util.Comparator;

class AgeComparator implements Comparator<RobotInfo>, Serializable {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 8272762774160538039L;

    @Override
    public int compare(RobotInfo robotInfo1, RobotInfo robotInfo2) {
        return robotInfo1.getTotalOutcomes() - robotInfo2.getTotalOutcomes();
    }
}
