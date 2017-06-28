package com.klemstinegroup.bleutrade;

import com.alphatica.genotick.genotick.MainModified;
import com.klemstinegroup.bleutrade.json.Balance;
import com.klemstinegroup.bleutrade.json.Market;
import com.klemstinegroup.bleutrade.json.Ticker;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocalDataListen {

    ArrayList<PriceData> priceData = new ArrayList<PriceData>();
    String file = "./data/all.txt";
    static final String MARKET = "DOGE_BTC";
    int fileSizeLimit = 1000;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public LocalDataListen() {
        File[] list = new File(".").listFiles();

        for (File f : list) {
            if (f.isDirectory() && f.toPath().toString().contains("savedPopulation")) {
                try {
                    FileUtils.deleteDirectory(f);
                    System.out.println("Deleted dir:" + f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList<String> stringlist = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(file));
            while (sc.hasNext()) {
                stringlist.add(sc.nextLine());
            }
            if (stringlist.size() > fileSizeLimit) {
                while (stringlist.size() > fileSizeLimit) stringlist.remove(0);
                sc.close();
                PrintWriter out = new PrintWriter(file);
                for (String s : stringlist) {
                    out.println(s);
                }
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


      /*  Scanner sc=null;
        try {
            sc=new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<PriceData> priceData = new ArrayList<PriceData>();
        while(sc.hasNextLine()){
            String line=sc.nextLine();
            String[] bbb=line.split(",");
            PriceData pd=new PriceData(bbb);
            priceData.add(pd);
        }*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
//        final boolean[] run = {true};
        new Thread(new Runnable() {
            @Override
            public void run() {
                String oldString = "";
                long lastTime = System.currentTimeMillis();
//                boolean final1 = false;
                boolean run = true;
                top:
                while (run) {
                    String output = baos.toString();
                    if (System.currentTimeMillis() - lastTime > 1200000) {
                        System.setOut(old);
                        System.out.println("Genotick timed out!");
                        break top;
                    }
//                    if (System.currentTimeMillis() - lastTime > 300000) {
//                        final1 = true;
//                        oldString = "";
//                    }
                    if (!output.equals(oldString)) {
                        lastTime = System.currentTimeMillis();
                        System.err.print(output.substring(oldString.length()));
                        oldString = output;
                        String[] split = output.split("\n");
//                        System.err.println(split.length);
                        Collections.reverse(Arrays.asList(split));
                        if (split[0].startsWith("ended")) {
                            for (String s : split) {
                                if (s.contains("all.txt") && s.contains("prediction") && !s.contains("reverse_all.txt")) {
                                    System.setOut(old);
                                    System.out.println("--------");
                                    String prediction = s.substring(s.indexOf(": ") + 2);
                                    if (prediction.startsWith("OUT")) prediction = "OUT";
                                    if (prediction.startsWith("DOWN")) prediction = "DOWN";
                                    if (prediction.startsWith("UP")) prediction = "UP";
                                    System.out.println("PREDICTION: " + prediction + "\t" + dateFormat.format(new Date()));
                                    predict(prediction);
                                    break top;
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.setOut(old);
                System.out.println("genotick finished");
            }
        }).start();

        try {
            MainModified.main(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("ended");
//        System.setOut(old);
//        String output=baos.toString();
//        System.out.println(output);
//        String[] split=output.split("\n");
//        System.out.println(split.length);
//        System.out.println(output);
//        String nextPos=split[2].substring(split[2].indexOf(":")+1);
//        System.out.println(nextPos);
    }

    private void predict(String prediction) {
        if (prediction == null || prediction.equals("OUT")) return;
        ArrayList<Balance> balances = Http.getBalances();
        double dogecoin = -1;
        double bitcoin = -1;
        for (Balance b : balances) {
            if (b.getCurrency().equals("BTC")) bitcoin = b.getAvailable();
            if (b.getCurrency().equals("DOGE")) dogecoin = b.getAvailable();
        }
        System.out.println("DOGE:" + dogecoin + "\t" + "BTC:" + bitcoin);
        dogecoin /= 10;
        bitcoin /= 10;
        HashMap<String, Ticker> tickerHM = new HashMap<String, Ticker>();
        ArrayList<Market> markets = new ArrayList<Market>();
        ArrayList<Market> temp2 = new ArrayList<Market>();
        try {
            temp2 = Http.getMarkets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Market> temp3 = new ArrayList<Market>();
        for (Market m : temp2) {
            if (m.getIsActive()) temp3.add(m);
        }
        markets.clear();
        markets.addAll(temp3);

        ArrayList<String> al = new ArrayList<String>();
        Market msaved = null;
        for (Market m : markets) {
            al.add(m.getMarketName());
            if (m.getMarketName().equals(MARKET)) msaved = m;
        }
        ArrayList<Ticker> tickers = Http.getTickers(al);
        for (int i = 0; i < tickers.size(); i++) {
            tickerHM.put(markets.get(i).getMarketName(), tickers.get(i));
        }

        System.out.println(msaved);
        if (prediction.equals("UP")) {      //BUY
            double cc = bitcoin / tickerHM.get(MARKET).getAsk();
            Http.buyselllimit(MARKET, tickerHM.get(MARKET).getAsk(), cc, true);
        }
        if (prediction.equals("DOWN")) {    //SELL
            Http.buyselllimit(MARKET, tickerHM.get(MARKET).getBid(), dogecoin, false);
        }
    }

    public static void main(String[] args) {
        new LocalDataListen();
    }
}