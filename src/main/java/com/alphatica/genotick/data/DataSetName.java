package com.alphatica.genotick.data;

import org.apache.commons.io.FilenameUtils;

import java.io.Serializable;

public class DataSetName implements Serializable{
    private static final long serialVersionUID = -7504682119928833833L;
    private final String path;
    private final String name;
    public DataSetName(String path) {
        this.path = path;
        this.name = FilenameUtils.getBaseName(path);
    }

    @Override
    public String toString() {
        return path;
    }

    public String getPath() {
        return path;
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
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
