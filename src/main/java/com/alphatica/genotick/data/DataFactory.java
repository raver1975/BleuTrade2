package com.alphatica.genotick.data;

public class DataFactory {
    public static DataLoader getDefaultLoader(String args) {
        return new FileSystemDataLoader(args);
    }
}
