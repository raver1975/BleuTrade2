package com.alphatica.genotick.data;

import java.io.Serializable;

public class DataSetName implements Serializable{
    private static final long serialVersionUID = -7504682119928833833L;
    private final String name;
    public DataSetName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DataSetName that = (DataSetName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
