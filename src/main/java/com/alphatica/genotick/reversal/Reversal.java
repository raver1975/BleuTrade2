package com.alphatica.genotick.reversal;

import com.alphatica.genotick.data.DataSet;
import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.data.FileSystemDataLoader;
import com.alphatica.genotick.data.MainAppData;
import com.alphatica.genotick.ui.UserInputOutputFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Reversal {
    private final String reverseValue;
    private final UserOutput output = UserInputOutputFactory.getUserOutput();

    public Reversal(String reverseValue) {
        this.reverseValue = reverseValue;
    }

    public void reverse() {
        FileSystemDataLoader loader = new FileSystemDataLoader(reverseValue);
        MainAppData data = loader.createRobotData();
        reverseData(data);
    }

    @SuppressWarnings("unused")
    public static MainAppData reverse(MainAppData data) {
        MainAppData reversedData = new MainAppData();
        for(DataSet set: data.listSets()) {
            List<Number []> originalNumbers = getOriginalNumbers(set);
            List<Number []> reversedNumbers = reverseList(originalNumbers);
            String reverseName = getReverseFileName(set.getName());
            DataSet reversedSet = new DataSet(reversedNumbers,reverseName);
            reversedData.addDataSet(reversedSet);
        }
        return reversedData;
    }
    private void reverseData(MainAppData data) {
        data.listSets().forEach(this::reverseSet);
    }

    private void reverseSet(DataSet set) {
        String reverseFileName = getReverseFileName(set.getName());
        File reverseFile = new File(reverseFileName);
        if(reverseFile.exists()) {
            reverseFile.delete();
//            output.warningMessage("File " + reverseFileName + " already exists. Not reversing " + set.getName());
//            return;
        }
        List<Number[]> original = getOriginalNumbers(set);
        List<Number[]> reverse = reverseList(original);
        writeReverseToFile(reverse, reverseFileName);
    }

    private static String getReverseFileName(DataSetName name) {
        Path full = Paths.get(name.getName());
        Path parent = full.getParent();
        String newName = "reverse_" + full.getFileName().toString();
        return Paths.get(parent.toString(), newName).toString();
    }

    private static List<Number[]> reverseList(List<Number[]> original) {
        List<Number []> reverse = new ArrayList<>();
        Number [] lastOriginal = null;
        Number [] lastReversed = null;
        for(Number[] table: original) {
            Number[] last = reverseLineOHLCV(table, lastOriginal, lastReversed);
            reverse.add(last);
            lastOriginal = table;
            lastReversed = last;
        }
        return reverse;
    }

    /*
     * This method is for reversing traditional open-high-low-close stock market data.
     * What happens with numbers (by column):
     * 0 - TimePoint: Doesn't change.
     * 1 - Open: It goes opposite direction to original, by the same percent.
     * 2 and 3 - High and Low: First of all they swapped. This is because data should be a mirror reflection of
     * original, so high becomes low and low becomes high. Change is calculated comparing to open column
     * (column 1). So it doesn't matter what High was in previous TimePoint, it matters how much higher it was comparing
     * to the open on the same line. When High becomes low - it goes down by same percent as original high was higher
     * than open.
     * 4 - Close. Goes opposite to original close by the same percent.
     * 5 and more - Volume, open interest or whatever. These don't change.
     */

    private static Number[] reverseLineOHLCV(Number[] table, Number[] lastOriginal, Number[] lastReversed) {
        Number [] reversed = new Number[table.length];
        // Column 0 is unchanged
        reversed[0] = table[0];
        // Column 1. Rewrite if first line
        for (int i=1;i<table.length;i++) {
            reversed[i] = 1d / table[i].doubleValue();
        }
            return reversed;
    }

    private static Number getReverseValue(Number from, Number to, Number compare) {
        double diff = Math.abs((from.doubleValue() / to.doubleValue()) -2);
        return diff * compare.doubleValue();
    }

    private void writeReverseToFile(List<Number[]> reverse, String reversedFileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reversedFileName))) {
            for(Number[] table: reverse) {
                    String row = mkString(table, ",");
                    bw.write(row + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String mkString(Number[] table, @SuppressWarnings("SameParameterValue") String string) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(Number number: table) {
            if (number!=null && !Double.valueOf(number.doubleValue()).isNaN()) {
                sb.append(number);
                count++;
                if (count < table.length) {
                    sb.append(string);
                }
            }
        }
        return sb.toString();
    }

    private static List<Number[]> getOriginalNumbers(DataSet set) {
        List<Number[]> list = new ArrayList<>();
        for (int i = 0; i < set.getLinesCount(); i++) {
            list.add(set.getLine(i));
        }
        return list;
    }
}
