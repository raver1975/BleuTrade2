package com.alphatica.genotick.data;

import com.alphatica.genotick.ui.UserInputOutputFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileSystemDataLoader implements DataLoader {
    private final String [] paths;
    private final UserOutput output = UserInputOutputFactory.getUserOutput();
    public FileSystemDataLoader(String... args) {
        paths = args;
    }

    @Override
    public MainAppData createRobotData() throws DataException {
        return loadData();
    }

    private MainAppData loadData() {
        MainAppData data = new MainAppData();
        String extension = ".csv";
        List<String> names = DataUtils.listFiles(extension,paths);
        if(names == null) {
            throw new DataException("Unable to list files");
        }
        for (String fileName : names) {
            output.infoMessage("Reading file " + fileName);
            data.addDataSet(createDataSet(fileName));
        }
        if(data.isEmpty()) {
            throw new DataException("No files to read!");
        }
        return data;

    }
    private DataSet createDataSet(String fileName) {
        File file = new File(fileName);
        dataFileSanityCheck(file);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Number []> lines = DataUtils.createLineList(br);
            output.infoMessage("Got " + lines.size() + " lines");
            return new DataSet(lines, fileName);
        } catch (IOException  | DataException e) {
            DataException de = new DataException("Unable to process file: " + fileName);
            de.initCause(e);
            throw de;
        }
    }

    private void dataFileSanityCheck(File file) {
        if(!file.isFile()) {
            String message = String.format("Unable to process file '%s' - not a file.", file.getName());
            output.errorMessage(message);
            throw new DataException(message);
        }
    }
}