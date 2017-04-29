package com.klemstinegroup.bleutrade;

import com.klemstinegroup.bleutrade.json.Order;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Paul on 3/26/2016.
 */
public class Serializer {

    static String savedFile = "saved.ser";
    static String historyFile = "history.ser";

    public static Object load(String file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream oos = new ObjectInputStream(fin);
        Object o = oos.readObject();
        fin.close();
        return o;

    }

    public static void save(Object o, String file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(o);
        fout.close();
    }

    public static ArrayList<TickerData> loadSaved() throws Exception {
        return (ArrayList<TickerData>) load(savedFile);
    }

    public static void saveSaved(ArrayList<TickerData> saved) throws Exception {
        save(saved, savedFile);
        save(saved, "backup_"+savedFile);
    }

    public static ArrayList<Order> loadHistory() throws Exception {
        return (ArrayList<Order>) load(historyFile);
    }

    public static void saveHistory(ArrayList<Order> saved) throws Exception {
        save(saved, historyFile);
        save(saved, "backup_"+historyFile);
    }

    public static ArrayList<TickerData> loadSavedBackup() throws Exception{
        return (ArrayList<TickerData>) load("backup_"+savedFile);
    }

    public static ArrayList<Order> loadHistoryBackup() throws Exception {
        return (ArrayList<Order>) load("backup_"+historyFile);
    }
}
