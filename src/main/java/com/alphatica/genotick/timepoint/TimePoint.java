package com.alphatica.genotick.timepoint;

import java.io.Serializable;

public class TimePoint implements Comparable<TimePoint>, Serializable {

    private static final long serialVersionUID = -6541300869299964665L;
    private final long value;
    public TimePoint(long i) {
        this.value = i;
    }

    public TimePoint(TimePoint startTimePoint) {
        this(startTimePoint.value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public int compareTo(@SuppressWarnings("NullableProblems") TimePoint timePoint) {
        return Long.compare(this.value, timePoint.value);
    }

    public long getValue() {
        return value;
    }

    public TimePoint next() {
        return new TimePoint(value + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimePoint timePoint = (TimePoint) o;

        return value == timePoint.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
