package com.klemstinegroup.bleutrade;

import com.klemstinegroup.bleutrade.json.*;
import com.klemstinegroup.bleutrade.json.Currency;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Paul on 4/16/2017.
 */
public class DataCollector {

    ArrayList<Currency> currencies = new ArrayList<Currency>();
    HashMap<String, Double> currencyCost = new HashMap<String, Double>();
    ArrayList<Market> markets = new ArrayList<Market>();
    private ArrayList<Ticker> tickers = new ArrayList<Ticker>();
    private HashMap<String, Ticker> tickerHM = new HashMap<String, Ticker>();
    static String apikey;
    static String apisecret;
    static DecimalFormat dfdollars = new DecimalFormat("+0000.00;-0000.00");
    static DecimalFormat dfcoins = new DecimalFormat("+0000.00000000;-0000.00000000");
    private long timeout;


    public DataCollector(){
        getSecret();
        if (!new File("./data").exists())new File("./data").mkdir();

        while (true) {
            ArrayList<Currency> temp1 = null;
            try {
                temp1 = Http.getCurrencies();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (temp1 == null) {
                System.out.println("Connection problems?");
                int cnt = 60;
                try {
                    while (cnt > 0) {
                        System.out.print((cnt--) + " ");
                        Thread.sleep(1000);
                    }
                    System.out.println();
                    continue;
                } catch (Exception e) {

                }
            }
            ArrayList<Currency> temp = new ArrayList<Currency>();
            for (Currency c : temp1) {
                if (c.getIsActive() && !c.getMaintenanceMode()) temp.add(c);
            }
            currencies.clear();
            currencies.addAll(temp);
            for (Currency c : currencies) {
                currencyCost.put(c.getCurrency(), c.getTxFee());
            }

            ArrayList<Market> temp2 = null;
            try {
                temp2 = Http.getMarkets();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (temp2 == null) {
                if (temp1 == null) {
                    System.out.println("Connection problems?");
                    int cnt = 60;
                    try {
                        while (cnt > 0) {
                            System.out.print((cnt--) + " ");
                            Thread.sleep(1000);
                        }
                        System.out.println();
                        continue;
                    } catch (Exception e) {

                    }
                }
            }
            ArrayList<Market> temp3 = new ArrayList<Market>();
            for (Market m : temp2) {
                if (m.getIsActive()) temp3.add(m);
            }
            markets.clear();
            markets.addAll(temp3);

            ArrayList<String> al = new ArrayList<String>();
            for (Market m : markets) {
                al.add(m.getMarketName());
            }
            try {
                tickers = Http.getTickers(al);
                for (int i = 0; i < tickers.size(); i++) {
                    tickerHM.put(markets.get(i).getMarketName(), tickers.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tickers == null || tickerHM == null || tickerHM.isEmpty()) {
                if (temp1 == null) {
                    System.out.println("Connection problems?");
                    int cnt = 60;
                    try {
                        while (cnt > 0) {
                            System.out.print((cnt--) + " ");
                            Thread.sleep(1000);
                        }
                        System.out.println();
                        continue;
                    } catch (Exception e) {

                    }
                }
            }

            long time = System.currentTimeMillis();
            String allout = "";
            String btcdge = "";
            for (String s : tickerHM.keySet()) {
                String g1 = s.substring(0, s.indexOf('_'));
                String g2 = s.substring(s.indexOf('_') + 1);
                Ticker t = tickerHM.get(s);
                if (t == null) continue;
                double bid = t.getBid();
                double ask = t.getAsk();
                double last = 0;
                if (t.getLast() != null) last = t.getLast();
//                try {
//                    String writeOut=time+","+dfcoins.format(bid)+","+dfcoins.format(ask)+"\n";
                    if ((g1.equals("DOGE")) && (g2.equals("BTC"))) {
                        btcdge = time + "," + dfcoins.format(bid) + "," + dfcoins.format(ask);
                    }
                    allout = allout + "," + dfcoins.format(bid) + "," + dfcoins.format(ask);
//                    FileWriter fw=new FileWriter(new File("./data/"+g1+"_"+g2+".txt"),true);
//                    fw.write(writeOut);
//                    fw.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                saved.add(new TickerData(g1, g2, bid, ask, last, time));
            }
//
            FileWriter fwall = null;
            try
            {
                fwall = new FileWriter(new File("./data/all.txt"), true);
                fwall.write(btcdge + allout + "\n");
                fwall.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            HashMap<String, Double> hm = new HashMap<String, Double>();

            for (int i = 0; i < markets.size(); i++) {
                Market m = markets.get(i);
                String g = m.getMarketName();
                hm.put(g, tickers.get(i).getAsk());
                String g1 = g.substring(0, g.indexOf('_'));
                String g2 = g.substring(g.indexOf('_') + 1);
                hm.put(g2 + "_" + g1, 1d / tickers.get(i).getAsk());
            }
            System.out.println("time=" + new Date());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    new LocalDataListen();
                }
            }).start();
            long sleep=timeout*1000l;
            if (sleep<1000)sleep=1000;
            System.out.println("waiting for "+timeout+"s" );
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-------------------------------------------------------------------------------------------------");




        }//end of while loop

    }

    public void getSecret(){
        Properties prop = new Properties();

        InputStream input;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            apikey = prop.getProperty("apikey");
            apisecret = prop.getProperty("apisecret");
            timeout =Long.parseLong(prop.getProperty("dataCollectTimeoutSeconds"));

            System.out.println("apikey=" + apikey);
            System.out.println("apisecret=" + apisecret);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new DataCollector();

    }
}
