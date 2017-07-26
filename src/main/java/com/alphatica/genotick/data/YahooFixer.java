package com.alphatica.genotick.data;

import com.alphatica.genotick.ui.UserInputOutputFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class YahooFixer {
    private final String path;
    private final UserOutput output = UserInputOutputFactory.getUserOutput();

    public YahooFixer(String yahooValue) {
        this.path = yahooValue;
    }

    public void fixFiles() {
        String extension = ".csv";
        List<String> names = DataUtils.listFiles(extension,path);
        for(String name: names) {
            fixFile(name);
        }
    }

    private void fixFile(String name) {
        output.infoMessage("Fixing file: " + name);
        List<Number[]> originalList = buildOriginalList(name);
        List<List<Number>> newList = fixList(originalList);
        saveListToFile(newList,name);
    }

    private void saveListToFile(List<List<Number>> newList, String name) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(name)))) {
            writeList(newList,bw);
        } catch (IOException e) {
            throw new DataException("Unable to write file " + name, e);
        }
    }

    private void writeList(List<List<Number>> newList, BufferedWriter bw) throws IOException {
        for(List<Number> line: newList) {
            writeLine(line,bw);
        }
    }

    private void writeLine(List<Number> line, BufferedWriter bw) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Number> iterator = line.iterator();
        while(iterator.hasNext()) {
            Number number = iterator.next();
            stringBuilder.append(String.valueOf(number));
            if(iterator.hasNext())
                stringBuilder.append(",");
        }
        stringBuilder.append("\n");
        bw.append(stringBuilder.toString());
    }

    private List<List<Number>> fixList(List<Number[]> originalList) {
        List<List<Number>> newList = new ArrayList<>(originalList.size());
        for (Number[] line : originalList) {
            List<Number> fixedLine = fixLine(line);
            newList.add(fixedLine);
        }
        return newList;
    }

    private List<Number []> buildOriginalList(String name) {
        try(BufferedReader br = new BufferedReader(new FileReader(new File(name)))) {
            ignoreFirstLine(br);
            return DataUtils.createLineList(br);
        } catch (IOException e) {
            throw new DataException("Unable to read file " + name, e);
        }
    }

    /*
    This is how it works:
    0th number is time - so it's unchanged
    1st number is open: calculate difference from open to close. Use adjusted close (number at index 6)
        to calculate new value.
    The same for numbers 2 and 3.
    4th number - replace with adjusted close
    5th number - volume. Recalcute according to adjusted close
     */
    private List<Number> fixLine(Number[] line) {
        List<Number> newLine = new ArrayList<>(line.length);
        double originalClose = line[4].doubleValue();
        double adjustedClose = line[6].doubleValue();
        // Nothing to be done with Date
        newLine.add(line[0]);
        double open = calculateNew(line[1],originalClose,adjustedClose);
        newLine.add(open);
        double high = calculateNew(line[2],originalClose,adjustedClose);
        newLine.add(high);
        double low = calculateNew(line[3],originalClose,adjustedClose);
        newLine.add(low);
        // add adjusted close as 'close'
        newLine.add(adjustedClose);
        // recalcute volume
        double volumeValue = originalClose * line[5].doubleValue();
        double volumeCount = volumeValue / adjustedClose;
        newLine.add(volumeCount);
        return newLine;
    }

    private double calculateNew(Number number, double originalClose, double adjustedClose) {
        double change = number.doubleValue() / originalClose;
        return adjustedClose * change;
    }

    private void ignoreFirstLine(BufferedReader br) throws IOException {
        br.readLine();
    }

}
