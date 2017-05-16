package com.alphatica.genotick.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("WeakerAccess")
public class DataUtils {

    public static List<String> listFiles(final String extension, final String... paths) {
        List<String> list = new ArrayList<>();
        for(String path: paths) {
            list.addAll(namesFromPath(path,extension));
        }
        return list;
    }

    private static List<String> namesFromPath(String path, final String extension) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if(file.isDirectory()) {
            list.addAll(getFullPaths(path,extension));
        } else {
            list.add(path);
        }
        return list;
    }

    private static List<String> getFullPaths(String path, final String extension) {
        File file = new File(path);
        String [] names = file.list((file1, name) -> name.endsWith(extension));
        List<String> list = new ArrayList<>();
        for(String name: names) {
            list.add(path + File.separator + name);
        }
        return list;
    }

    public static List<Number []> createLineList(BufferedReader br) {
        List<Number []> list = new ArrayList<>();
        int linesRead = 1;
        try {
            buildLines(br, list);
            return list;
        } catch(IOException | NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            DataException de = new DataException("Error reading line " + linesRead);
            de.initCause(ex);
            throw de;
        }
    }

    private static void buildLines(BufferedReader br, List<Number[]> list) throws IOException {
        String line;
        line = br.readLine();
        processFirstLine(line,list);
        while ((line = br.readLine())!=null){
            Number [] row = processLine(line);
            list.add(row);
        }
    }

    private static void processFirstLine(String line, List<Number[]> list) {
        try {
            Number [] row = processLine(line);
            list.add(row);
        } catch (NumberFormatException ignore) {
            /* If it's the first line then it's probably just a heading. Let's ignore NFE */
        }
    }


    public static Number[] processLine(String line) {
        String separator = ",";
        String[] fields = line.split(separator);
        Number [] array = new Number[fields.length];
        String timePointString = getTimePointString(fields[0]);
        array[0] = Long.valueOf(timePointString);
        for(int i = 1; i < fields.length; i++) {
            array[i] = Double.valueOf(fields[i]);
        }
        return array;
    }

    public static String getTimePointString(String field) {
        if(field.contains("-"))
            return field.replaceAll("-","");
        else
            return field;

    }

    public static String getDateTimeString() {
        DateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        return format.format(Calendar.getInstance().getTime());
    }
}
