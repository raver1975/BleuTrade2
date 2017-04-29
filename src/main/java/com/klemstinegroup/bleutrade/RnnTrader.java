package com.klemstinegroup.bleutrade;

import com.klemstinegroup.bleutrade.json.*;
import com.klemstinegroup.bleutrade.json.Currency;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Paul on 4/16/2017.
 */
public class RnnTrader {

    public static boolean debug;
    private double bitcoinprice;
    ArrayList<Currency> currencies = new ArrayList<Currency>();
    HashMap<String, Double> currencyCost = new HashMap<String, Double>();
    ArrayList<Market> markets = new ArrayList<Market>();
    private ArrayList<Ticker> tickers = new ArrayList<Ticker>();
    private HashMap<String, Ticker> tickerHM = new HashMap<String, Ticker>();
    private ArrayList<TickerData> saved=new ArrayList<>();
    HashMap<String, TickerData> nowhm = new HashMap<String, TickerData>();
    HashMap<String, TickerData> maxhm = new HashMap<String, TickerData>();
    HashMap<String, TickerData> minhm = new HashMap<String, TickerData>();
    private ArrayList<Balance> balance=new ArrayList<>();
    private HashMap<String, Balance> balanceHM = new HashMap<String, Balance>();
    static String apikey;
    static String apisecret;
    static DecimalFormat dfdollars = new DecimalFormat("+0000.00;-0000.00");
    static DecimalFormat dfcoins = new DecimalFormat("+0000.00000000;-0000.00000000");


    public RnnTrader(){
        getSecret();
        startStdinListener();

        try {
            saved = Serializer.loadSaved();
            if (saved == null) {
                saved = new ArrayList<TickerData>();
                Serializer.saveSaved(saved);
            }

        } catch (Exception e) {
            try {
                saved = Serializer.loadSavedBackup();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("saved size=" + saved.size());

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
            for (String s : tickerHM.keySet()) {
                maxhm.clear();
                minhm.clear();
                nowhm.clear();
                ArrayList<TickerData> remove = new ArrayList<TickerData>();
//

                String g1 = s.substring(0, s.indexOf('_'));
                String g2 = s.substring(s.indexOf('_') + 1);
                Ticker t = tickerHM.get(s);
                if (t == null) continue;
                double bid = t.getBid();
                double ask = t.getAsk();
                double last = 0;
                if (t.getLast() != null) last = t.getLast();
//                        String bidS = dfdollars.format(new BigDecimal(t.getBid()));
//                        String askS = dfdollars.format(new BigDecimal(t.getAsk()));
//                        String lastS = dfdollars.format(new BigDecimal(t.getLast()));
//                       String insert="INSERT INTO ticker(time,coin,base,bid,ask,last) VALUES ("+time+",'"+g1+"','"+g2+"',"+bid+","+ask+","+last+")";
                saved.add(new TickerData(g1, g2, bid, ask, last, time));

                for (TickerData td : saved) {
                    //set number of days here
                    if (td.time < System.currentTimeMillis() - (86400000 * 1)) {
                        remove.add(td);
                    } else {
                        String bb = td.coin + "_" + td.base;
                        if (!maxhm.containsKey(bb) || td.ask > maxhm.get(bb).ask) {
                            maxhm.put(bb, td);
                        }
                        if (!minhm.containsKey(bb) || td.ask < minhm.get(bb).ask) {
                            minhm.put(bb, td);
                        }
                        if (!nowhm.containsKey(bb) || td.time > nowhm.get(bb).time) {
                            nowhm.put(bb, td);
                        }
                    }
                }
                for (TickerData td : remove) saved.remove(td);

                // System.out.println(g+"\t\t"+(now/range));


            }
            ArrayList<String> negativeCyclesLow = new ArrayList<String>();
            ArrayList<String> negativeCyclesHigh = new ArrayList<String>();
//
            for (String g : maxhm.keySet()) {
//                            System.out.println(g+"\t"+dfdollars.format(minhm.get(g))+"\t"+dfdollars.format(maxhm.get(g)));
                double range = maxhm.get(g).ask - minhm.get(g).ask;
                double now = (nowhm.get(g).ask - minhm.get(g).ask);
                //if (range!=0d)System.out.println("range="+g+"\t"+range);
                if (Math.abs(range) < .000000001d) {
                    continue;
                }

                double perc = now / range;
                // System.out.println("perc="+g+"\t"+perc);
                if (perc > 0.000001d && perc < .05d) {
                    String s = dfdollars.format((perc) * 100d) + "\t" + g + "\t" + dfcoins.format(minhm.get(g).ask) + "\t" + dfcoins.format(nowhm.get(g).ask) + "\t" + dfcoins.format(maxhm.get(g).ask) + "\t" + new Date(minhm.get(g).time) + "\t" + new Date(maxhm.get(g).time);
                    negativeCyclesLow.add(s);
//                            System.out.println(s);
                }
                if (perc > .95d && perc < 1.99995d) {
                    String s = dfdollars.format((perc) * 100d) + "\t" + g + "\t" + dfcoins.format(minhm.get(g).ask) + "\t" + dfcoins.format(nowhm.get(g).ask) + "\t" + dfcoins.format(maxhm.get(g).ask) + "\t" + new Date(minhm.get(g).time) + "\t" + new Date(maxhm.get(g).time);
                    negativeCyclesHigh.add(s);
//                            System.out.println(s);
                }
            }
//                    try {
//                        query("select * from ticker");
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }

            //arbitrage

            HashMap<String, Double> hm = new HashMap<String, Double>();

            for (int i = 0; i < markets.size(); i++) {
                Market m = markets.get(i);
                String g = m.getMarketName();
                hm.put(g, tickers.get(i).getAsk());
                String g1 = g.substring(0, g.indexOf('_'));
                String g2 = g.substring(g.indexOf('_') + 1);
                hm.put(g2 + "_" + g1, 1d / tickers.get(i).getAsk());
            }

//                    negativeCycles=new ArrayList<String>();
//                    ArrayList<String> negativeCycles = negativeCycle(hm);
//                    Collections.sort(negativeCycles);

            //negative cycles
//                    System.out.println("----------------------------");
//                    System.out.println("Negative Cycles");
//                    for (String s : negativeCycles) System.out.println(s);


            Collections.sort(negativeCyclesLow);
            Collections.sort(negativeCyclesHigh);
            //Collections.reverse(negativeCycles);


            try {
                bitcoinprice = Http.bitcoinPrice();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                balance = Http.getBalances();
                if (balance == null) {
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
                for (Balance b : balance) {
                    balanceHM.put(b.getCurrency(), b);
                }
                System.out.println("----------------------------");
                double bittot = 0;
                System.out.println("Balances:");
                Collections.sort(balance);
                double bl = 0;
                for (Balance b : balance) {
                    try {
                        if (b.getAvailable() * bitcoinprice > 0.000000009d) {
                            System.out.print("  " + b.getCurrency() + "\t" + dfcoins.format(b.getAvailable()));
                            if (b.getCurrency().equals("BTC")) {
                                System.out.print("\t" + dfcoins.format(b.getAvailable()) + "\t$" + dfdollars.format(b.getAvailable() * bitcoinprice));
                                bittot += b.getAvailable();
                            } else {
                                double coinshave = b.getAvailable();
                                double coinrate = tickerHM.get(b.getCurrency() + "_BTC").getBid();
                                double cointotal = coinrate * coinshave;

                                System.out.print("\t" + dfcoins.format(cointotal) + "\t$" + dfdollars.format(cointotal * bitcoinprice));
                                bittot += cointotal;
                                if (b.getCurrency().equals("BLEU")) {
                                    bl = cointotal;
                                }

                            }

                            System.out.println();
                        }
                    }
                    catch (Exception e2){e2.printStackTrace();}
                }
                System.out.println("  TOT-B" + "\t--------------\t" + dfcoins.format(bittot - bl) + "\t$" + dfdollars.format((bittot - bl) * bitcoinprice));
                System.out.println("  TOT" + "\t--------------\t" + dfcoins.format(bittot) + "\t$" + dfdollars.format(bittot * bitcoinprice));

                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");
                String xx = sdf.format(System.currentTimeMillis()) + "\t" + dfcoins.format(bittot) + "\t$" + dfdollars.format(bittot * bitcoinprice) + "\n";
                try {
                    Files.write(Paths.get("totals.txt"), xx.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    //exception handling left as an exercise for the reader
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("DCR:"+nowhm.get("DCR_BTC").ask+"\t"+nowhm.get("DCR_BTC").bid+"\t"+(nowhm.get("DCR_BTC").ask-nowhm.get("DCR_BTC").bid));
            System.out.println("time=" + new Date());

            System.out.println("-------------------------------------------------------------------------------------------------");

            try {
                Serializer.saveSaved(saved);
            } catch (Exception e) {
                e.printStackTrace();
            }


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
            System.out.println("apikey=" + apikey);
            System.out.println("apisecret=" + apisecret);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startStdinListener(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        String line = br.readLine();
                        if (line != null) {
                            if (line.equals("exit")) {
                                System.exit(0);
                            }
                            if (line.equals(" ")) {
//                                refresh = true;
                            }
                            if (line.startsWith("buy ")) {
//                                buy(line);
                            }


                            if (line.startsWith("sell ")) {
//                                sell(line);
                            }
                            if (line.startsWith("reset")) {
                                String market = line.substring(6);

                                ArrayList<Order> remove = new ArrayList<Order>();
//                                for (Order o : history) {
//                                    System.out.println(":" + market + ":" + o.getExchange());
//                                    if (o.getExchange().startsWith(market + "_")) {
//                                        System.out.println("removing " + o.getExchange());
//                                        remove.add(o);
//                                    }
//                                }
//                                for (Order o : remove) history.remove(o);
//                                try {
//                                    Serializer.saveHistory(history);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                System.out.println("Removed all " + market);
                            }

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    public static void main(String[] args) throws Exception {
        new RnnTrader();

    }
}
