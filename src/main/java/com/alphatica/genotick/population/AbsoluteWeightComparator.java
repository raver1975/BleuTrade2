package com.alphatica.genotick.population;

import java.io.Serializable;
import java.util.Comparator;

class AbsoluteWeightComparator implements Comparator<RobotInfo>, Serializable {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 4466317313399016583L;

    @Override
    public int compare(RobotInfo robotInfo1, RobotInfo robotInfo2) {
        return Double.compare(robotInfo1.getWeight(), robotInfo2.getWeight());
    }
}
