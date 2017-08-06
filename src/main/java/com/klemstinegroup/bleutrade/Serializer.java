package com.klemstinegroup.bleutrade;

import com.klemstinegroup.bleutrade.json.Order;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Paul on 3/26/2016.
 */
public class Serializer {

    public static Object load(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream oos = new ObjectInputStream(fin);
        Object o = oos.readObject();
        fin.close();
        return o;

    }

    public static void save(Object o, File file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(o);
        fout.close();
    }
}
