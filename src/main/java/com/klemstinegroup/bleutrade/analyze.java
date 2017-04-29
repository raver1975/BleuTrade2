package com.klemstinegroup.bleutrade;


import com.klemstinegroup.bleutrade.json.*;
import com.klemstinegroup.bleutrade.json.Currency;
//import edu.princeton.cs.algs4.BellmanFordSP;
//import edu.princeton.cs.algs4.DirectedEdge;
//import edu.princeton.cs.algs4.EdgeWeightedDigraph;
//import edu.princeton.cs.algs4.StdOut;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class Analyze {


    //    private Server hsqlServer;
//    private  Connection conn;
    ArrayList<TickerData> saved = new ArrayList<TickerData>();
    ArrayList<Order> history = new ArrayList<Order>();
    ArrayList<Currency> currencies = new ArrayList<Currency>();
    HashMap<String, Double> currencyCost = new HashMap<String, Double>();
    ArrayList<Market> markets = new ArrayList<Market>();
    private ArrayList<Ticker> tickers = new ArrayList<Ticker>();
    private HashMap<String, Ticker> tickerHM = new HashMap<String, Ticker>();
    HashMap<String, TickerData> nowhm = new HashMap<String, TickerData>();
    HashMap<String, TickerData> maxhm = new HashMap<String, TickerData>();
    HashMap<String, TickerData> minhm = new HashMap<String, TickerData>();

    private double bitcoinprice;

    static DecimalFormat dfdollars = new DecimalFormat("+0000.00;-0000.00");
    static DecimalFormat dfcoins = new DecimalFormat("+0000.00000000;-0000.00000000");
    private ArrayList<Balance> balance;
    private HashMap<String, Balance> balanceHM = new HashMap<String, Balance>();
    private boolean refresh;
    private static int wait = 1;

    public static boolean debug = false;
    private double sellabove = 0.05d;
    private double donotbuybelow = -.02d;
    private double buyfactor = 1000d;
    private double sellfactor = 2d;
    private boolean buyBleu = false;
    private boolean donotbuyconstraint = true;
    private double bleuBuyMult;
    public static String apikey = null;
    public static String apisecret = null;


    //CREATE TABLE TICKER(TIME BIGINT,COIN VARCHAR(10),BASE VARCHAR(10),BID DOUBLE,ASK DOUBLE,LAST DOUBLE)
    public Order buy(String line) {
        line = line.replace("+", "");
        String[] split = line.split(" ");
//                            System.out.println(Arrays.toString(split));
        if (split.length != 3) {
            System.out.println("error args=" + split.length);
        } else {
            String market = split[1];
            double quantity = 0;
            try {
                quantity = Double.parseDouble(split[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
            for (Market mk : markets) {
                if (mk.getMarketName().equals(market)) {
                    double rate = tickerHM.get(mk.getMarketName()).getAsk();
                    double coint = quantity * rate;
//                    if (coint >= mk.getMinTradeSize()) {
//                        System.out.println(dfcoins.format(quantity) + " " + mk.getMarketCurrency() + " x " + dfcoins.format(rate) + " " + mk.getBaseCurrency() + " = " + dfcoins.format(coint) + " " + mk.getBaseCurrency());
                    double fee = coint * .0025;
                    coint += fee;
//                        System.out.println("fee = " + dfcoins.format(fee) + " " + mk.getBaseCurrency());
//                        System.out.println("total=" + dfcoins.format(coint) + " " + mk.getBaseCurrency());
//                                System.out.println("I have " + dfcoins.format(b.getAvailable()) + " " + mk.getBaseCurrency());
//                                System.out.println("buying: " + market + "\t" + dfcoins.format(rate) + "\t#" + dfcoins.format(quantity));
                    try {
                        final long id = Http.buyselllimit(market, rate, quantity, true);

                        if (id != -1)
                            top:
                                    for (int i = 0; i < 10; i++) {
                                        ArrayList<Order> orders = Http.getOrders("OK", market);
                                        for (Order o : orders) {
                                            if (o.getOrderId().equals(id + "") || debug) {
                                                System.out.println("order successful! " + id);
                                                if (!o.getExchange().contains("BLEU")) {
                                                    history.add(o);
                                                    Serializer.saveHistory(history);
                                                }
//                                                                refresh = true;
                                                return o;
                                            }
                                        }
                                        Thread.sleep(1000);
                                    }
                        if (id == -1) return null;
                        System.out.println("Canceling order:" + id + "\t" + Http.cancel(id));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    } else {
//                        System.out.println("buy low volume trade! " + dfcoins.format(coint) + "\t" + dfcoins.format(mk.getMinTradeSize() ));
//                    }
                }
            }
        }
        return null;
    }

    public Order sell(String line) {
        line = line.replace("+", "");
        System.out.println(line);
        String[] split = line.split(" ");
//                            System.out.println(Arrays.toString(split));
        if (split.length != 3) {
            System.out.println("error args=" + split.length);
        } else {
            String market = split[1];
            if (market.contains("BLEU")) return null;
            double quantity = 0;
            try {
                quantity = Double.parseDouble(split[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
            for (Market mk : markets) {
                if (mk.getMarketName().equals(market)) {
                    double rate = tickerHM.get(mk.getMarketName()).getBid();
                    double coint = quantity * rate;
//                    if (coint >= mk.getMinTradeSize() ) {
                    System.out.println(dfcoins.format(quantity) + " " + mk.getMarketCurrency() + " x " + dfcoins.format(rate) + " " + mk.getBaseCurrency() + " = " + dfcoins.format(coint) + " " + mk.getBaseCurrency());
                    double fee = coint * .0025;
                    coint -= fee;
                    try {
                        final long id = Http.buyselllimit(market, rate, quantity, false);
                        System.out.println("order number=" + id);
                        if (id != -1)
                            top:for (int i = 0; i < 10; i++) {
                                ArrayList<Order> orders = Http.getOrders("OK", market);
                                for (Order o : orders) {
                                    if (o.getOrderId().equals(id + "") || debug) {
                                        System.out.println("order successful!");
//                                                        refresh = true;
//                                                o.setQuantity(-o.getQuantity());
//                                                    history.add(o);
                                        double toto = o.getQuantity();
                                        String g1 = market.substring(0, market.indexOf('_'));
                                        String g2 = market.substring(market.indexOf('_') + 1);
                                        int cnn = 0;
                                        Collections.sort(history);
                                        while (cnn++ < 100000 && toto > 0.000000009d) {
                                            ArrayList<Order> remove = new ArrayList<Order>();
                                            for (Order oh : history) {
                                                if (oh.getExchange().startsWith(g1 + "_")) {
                                                    double ohq = oh.getQuantity();
                                                    if (ohq > toto) {
                                                        oh.setQuantity(ohq - toto);
                                                        toto = 0d;
                                                    } else {
                                                        toto -= ohq;
                                                        remove.add(oh);
                                                    }
                                                }
                                            }
                                            for (Order oh : remove) history.remove(oh);
                                        }
                                        Serializer.saveHistory(history);
                                        return o;
                                    }
                                }
                                Thread.sleep(1000);
                            }
                        System.out.println("Cancling order " + id);
                        System.out.println("success = " + Http.cancel(id));
                        return null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    } else {
//                        System.out.println("sell low volume trade! " + dfcoins.format(coint) + "\t" + dfcoins.format(mk.getMinTradeSize() ));
//                    }
                }
            }
        }
        return null;
    }

    public Analyze() {
        Properties prop = new Properties();

        InputStream input;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            debug = Boolean.parseBoolean(prop.getProperty("debug"));
            buyBleu = Boolean.parseBoolean(prop.getProperty("buyBleu"));
            bleuBuyMult = Double.parseDouble(prop.getProperty("bleuBuyMult"));
            sellabove = Double.parseDouble(prop.getProperty("sellabove"));
            donotbuybelow = Double.parseDouble(prop.getProperty("donotbuybelow"));
            buyfactor = Double.parseDouble(prop.getProperty("buyfactor"));
            sellfactor = Double.parseDouble(prop.getProperty("sellfactor"));
            donotbuyconstraint = Boolean.parseBoolean(prop.getProperty("donotbuyconstraint"));
            apikey = prop.getProperty("apikey");
            apisecret = prop.getProperty("apisecret");
            System.out.println("debug=" + debug);
            System.out.println("buyBleu=" + buyBleu);
            System.out.println("bleuBuyMult=" + bleuBuyMult);
            System.out.println("donotbuyconstraint=" + donotbuyconstraint);
            System.out.println("sellabove=" + sellabove);
            System.out.println("donotbuybelow=" + donotbuybelow);
            System.out.println("buyfactor=" + buyfactor);
            System.out.println("sellfactor=" + sellfactor);
            System.out.println("sellfactor=" + donotbuyconstraint);


        } catch (IOException e) {
            e.printStackTrace();
        }


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
                                refresh = true;
                            }
                            if (line.startsWith("buy ")) {
                                buy(line);
                            }


                            if (line.startsWith("sell ")) {
                                sell(line);
                            }
                            if (line.startsWith("reset")) {
                                String market = line.substring(6);

                                ArrayList<Order> remove = new ArrayList<Order>();
                                for (Order o : history) {
                                    System.out.println(":" + market + ":" + o.getExchange());
                                    if (o.getExchange().startsWith(market + "_")) {
                                        System.out.println("removing " + o.getExchange());
                                        remove.add(o);
                                    }
                                }
                                for (Order o : remove) history.remove(o);
                                try {
                                    Serializer.saveHistory(history);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Removed all " + market);
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
        try {
            history = Serializer.loadHistory();

        } catch (Exception e) {
            try {
                history = Serializer.loadHistoryBackup();
            } catch (Exception e1) {
                e1.printStackTrace();

            }
        }
        if (history == null) {
            history = new ArrayList<Order>();
            try {
                Serializer.saveHistory(history);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("history size=" + history.size());

        ArrayList<Order> remove = new ArrayList<Order>();
        for (Order o : history) {
            if (o == null) {
                remove.add(o);
                continue;
            }
            if (o.getExchange().contains("BLEU")) remove.add(o);
        }
        history.removeAll(remove);
        try {
            Serializer.saveHistory(history);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // ---------------------------------------DATABASE------------------------------------------
//        hsqlServer = new Server();
//        hsqlServer.setLogWriter(null);
//        hsqlServer.setSilent(true);
//        hsqlServer.setDatabaseName(0, "iva");
//        hsqlServer.setDatabasePath(0, "file:ivadb");
//        hsqlServer.start();
//
//        try {
//            Class.forName("org.hsqldb.jdbc.JDBCDriver");
//        } catch (Exception e) {
//            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
//            e.printStackTrace();
//        }
//
//
//        try {
//            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/iva", "SA", "");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        // ---------------------------------------DATABASE------------------------------------------

//        mainFrame = new TradeFrame();
//        mainFrame.setVisible(true);
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Thread(new Runnable() {
            @Override
            public void run() {
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

                    System.out.println("----------------------------");
                    System.out.println("Holdings");
                    HashMap<String, Double> hmtotal = new HashMap<String, Double>();
                    HashMap<String, Double> hmquantity = new HashMap<String, Double>();
//                    HashMap<String, Double> hmminsize = new HashMap<String, Double>();

                    for (Order o : history) {
                        String g = o.getExchange();
                        for (Market mk : markets) {
                            if (mk.getMarketName().equals(g)) {
                                if (!hmtotal.containsKey(g)) hmtotal.put(g, 0d);
                                if (!hmquantity.containsKey(g)) hmquantity.put(g, 0d);
                                double price = o.getPrice();
                                double quant = o.getQuantity();
                                hmtotal.put(g, hmtotal.get(g) + price * quant);
                                hmquantity.put(g, hmquantity.get(g) + quant);
                                break;
                            }
                        }
                    }

                    HashMap<String, Double> comprofit = new HashMap<String, Double>();

                    HashSet<String> goodtoorder = new HashSet<String>();
                    HashSet<String> donotsell = new HashSet<String>();
                    HashSet<String> donotbuy = new HashSet<String>();
                    ArrayList<String> sortedout = new ArrayList<String>();

                    double totprofit = 0d;
                    for (String h : hmquantity.keySet()) {
                        double hmqu = hmquantity.get(h);
                        double pricethen = hmtotal.get(h);
                        double pricenow = tickerHM.get(h).getBid() * hmqu;
                        String g1 = h.substring(0, h.indexOf('_'));
                        String g2 = h.substring(h.indexOf('_') + 1);

                        if (!g2.equals("BTC")) {
                            pricethen *= tickerHM.get(g2 + "_" + "BTC").getBid();
                            pricenow *= tickerHM.get(g2 + "_" + "BTC").getBid();
                        }
                        //double pricethen = hmpr * hmqu;
                        double profit = pricenow * .9975d - pricethen * 1.0025d;
//
//                        if (!g2.equals("BTC"))
//                            profit *= tickerHM.get(g2 + "_" + "BTC").getBid();
                        totprofit += profit;
                        if (!comprofit.containsKey(g1)) comprofit.put(g1, profit);
                        else {
                            comprofit.put(g1, comprofit.get(g1) + profit);
                        }

                    }
                    for (String h : comprofit.keySet()) {
                        double profit = comprofit.get(h);
                        sortedout.add(dfcoins.format(profit) + "\t$" + dfdollars.format(profit * bitcoinprice) + "\t" + h);

                        boolean flag = false;
                        if (profit * bitcoinprice >= sellabove) {
                            goodtoorder.add(h);
                            flag = true;
                        }
                        if (profit * bitcoinprice <= donotbuybelow || flag) donotbuy.add(h);
                    }
                    Collections.sort(sortedout);
                    for (String s : sortedout) System.out.println(s);
                    String xx = dfcoins.format(totprofit) + "\t$" + dfdollars.format(totprofit * bitcoinprice) + "\t" + "total";
                    System.out.println(xx);

                    System.out.println("----------------------------");
                    System.out.println("buy low");
                    boolean buyblueonce = false;
                    Collections.shuffle(markets);
                    Collections.shuffle(negativeCyclesLow);
                    for (String s : negativeCyclesLow) {
                        System.out.println(s);
                        String[] split = s.split("\t");
                        String market = split[1];
                        donotsell.add(market);
                        top:
                        for (Market mk : markets) {
                            //buy bleutrade each round
                            if (buyBleu && !buyblueonce && mk.getMarketName().equals("BLEU_BTC")) {
                                buyblueonce = true;
                                System.out.println("----BUY BLEU-------");
                                double rate = tickerHM.get(mk.getMarketName()).getAsk();
                                double total = (mk.getMinTradeSize());
                                int cnt = 0;
                                while (cnt++ < 10000000 && total * rate < 0.00000001d && total > 0.000000009d)
                                    total *= 1.1d;
                                //total *= buyfactor;
                                total *= bleuBuyMult;
                                Balance b = balanceHM.get(mk.getBaseCurrency());
                                System.out.println(dfcoins.format(total) + " " + mk.getMarketCurrency() + " costs :" + dfcoins.format(total * rate) + " " + mk.getBaseCurrency() + "\t" + "have:" + dfcoins.format(b.getAvailable()) + " " + mk.getBaseCurrency());
                                Order o1 = buy("buy BLEU_BTC " + dfcoins.format(total));
                            }

                            if (mk.getMarketName().equals(market)) {
                                double rate = tickerHM.get(mk.getMarketName()).getAsk();
                                double total = (mk.getMinTradeSize());
                                int cnt = 0;
                                while (cnt++ < 10000000 && total * rate < 0.00000001d && total > 0.000000009d)
                                    total *= 1.1d;
                                if (mk.getBaseCurrency().equals("BTC")) if (total * rate < 0.00001d) {

                                    total = 0.00001d / rate;
                                }

                                if (mk.getBaseCurrency().equals("DOGE")) if (total * rate < 10d) {

                                    total = 10d / rate;
                                }

                                total *= buyfactor;
//                                    if (!mk.getBaseCurrency().equals("BTC"))
//                                        total /= tickerHM.get(mk.getBaseCurrency() + "_BTC").getAsk();
                                Balance b = balanceHM.get(mk.getBaseCurrency());
                                //do not buy constraint
                                if (donotbuyconstraint && donotbuy.contains(mk.getMarketCurrency())) {
                                    System.out.println("Do not buy!");
                                    continue top;
                                }
                                System.out.println(dfcoins.format(total) + " " + mk.getMarketCurrency() + " costs :" + dfcoins.format(total * rate) + " " + mk.getBaseCurrency() + "\t" + "have:" + dfcoins.format(b.getAvailable()) + " " + mk.getBaseCurrency());
                                donotsell.add(mk.getMarketName());

                                Order o = buy("buy " + mk.getMarketName() + " " + dfcoins.format(total));
                            }
                        }
                    }


//                    sell high #2
                    System.out.println("----------------------------");
                    System.out.println("sell high #2");
                    for (String s : negativeCyclesHigh) {

                        String[] split = s.split("\t");
                        String market = split[1];
                        if (market.contains("BLEU")) continue;
                        top:
                        for (Market mk : markets) {
                            if (mk.getMarketName().equals(market)) {
                                if (donotsell.contains(market)) {
                                    System.out.println("Do not Sell!");
                                    continue;
                                }


                                Balance b = balanceHM.get(mk.getMarketCurrency());
                                double rate = tickerHM.get(mk.getMarketName()).getBid();
//                                if (!mk.getBaseCurrency().equals("BTC"))
//                                    rate *= tickerHM.get(mk.getBaseCurrency() + "_BTC").getAsk();
                                double total = mk.getMinTradeSize() / rate;// / rate;
                                if (mk.getBaseCurrency().equals("BTC")) if (total * rate < 0.00001d) {
                                    total = 0.00001d / rate;
                                }

                                if (mk.getBaseCurrency().equals("DOGE")) if (total * rate < 10d) {
                                    total = 10d / rate;
                                }


                                total *= sellfactor;
                                if (goodtoorder.contains(mk.getMarketCurrency())) {
                                    System.out.println(s);
//                                    if (b.getAvailable() < total) {
//                                        System.out.println("Insufficient Funds: " + mk.getMarketName() + " \tasking for=" + dfcoins.format(total) + "\thave=" + dfcoins.format(b.getAvailable()));
//                                        continue top;
//                                    }
                                    System.out.println(dfcoins.format(total) + " " + mk.getMarketCurrency() + " costs :" + dfcoins.format(rate * total) + " " + mk.getBaseCurrencyLong() + "\t" + "have:" + dfcoins.format(b.getAvailable()));
                                    Order o = sell("sell " + mk.getMarketName() + " " + dfcoins.format(total));
//                                    if (o != null) {
//                                        o.setQuantity(-o.getQuantity());
//                                        history.add(o);
//                                    }
                                }
                            }
                        }
                    }

                    System.out.println("----------------------------");

                    System.out.println("time=" + new Date());


                    // System.out.println("-------------------------------------------------------------------------------------------------");
                    //compress history
                    HashMap<String, Double> octot = new HashMap<String, Double>();
                    HashMap<String, Double> ocqua = new HashMap<String, Double>();
                    for (Order o : history) {
                        String h = o.getExchange();

                        if (octot.get(h) == null) {
                            octot.put(h, 0d);
                        }
                        if (ocqua.get(h) == null) {
                            ocqua.put(h, 0d);
                        }

                        octot.put(h, octot.get(h) + (o.getPrice() * o.getQuantity()));
                        ocqua.put(h, ocqua.get(h) + o.getQuantity());
                    }
                    history.clear();

                    for (Map.Entry<String, Double> e : octot.entrySet()) {
                        Order bb = new Order();
                        bb.setPrice(octot.get(e.getKey()) / ocqua.get(e.getKey()));
                        bb.setQuantity(ocqua.get(e.getKey()));
                        bb.setExchange(e.getKey());
//                        if (e.getKey().equals("IOC_BTC")){}
//                        else
                        history.add(bb);
                        //System.out.println(dfcoins.format(bb.getQuantity()) + "\t" + bb.getExchange());


                    }
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    // System.out.println("-----------");


                    try {
                        Serializer.saveHistory(history);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Serializer.saveSaved(saved);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    for (int i = 0; i < wait; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (refresh) {
                            refresh = false;
                            break;
                        }
                    }
                }
            }
        }

        ).

                start();

    }

    public static void main(String[] args) throws Exception {
        new Analyze();

    }

//    public ArrayList<String> negativeCycle(HashMap<String, Double> hm) {
//        ArrayList<String> al = new ArrayList<String>();
//        HashSet<String> hs = new HashSet();
//        for (String g : hm.keySet()) {
//            String g1 = g.substring(0, g.indexOf('_'));
//            String g2 = g.substring(g.indexOf('_') + 1);
//            hs.add(g1);
//            hs.add(g2);
//        }
//        int V = hs.size();
//        ArrayList<String> name = new ArrayList<String>();
//        Iterator<String> iter = hs.iterator();
//        while (iter.hasNext()) {
//            String s = iter.next();
//            name.add(s);
//        }
//
//        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
//
//        for (Market m : markets) {
//            String g = m.getMarketName();
//            String g1 = g.substring(0, g.indexOf('_'));
//            String g2 = g.substring(g.indexOf('_') + 1);
//            if (hm.get(g1 + "_" + g2) != null) {
//                DirectedEdge e1 = new DirectedEdge(name.indexOf(g1), name.indexOf(g2), -Math.log(hm.get(g1 + "_" + g2)));
//                DirectedEdge e2 = new DirectedEdge(name.indexOf(g2), name.indexOf(g1), -Math.log(hm.get(g2 + "_" + g1)));
//                G.addEdge(e1);
//                G.addEdge(e2);
//            }
//        }
//
//        BellmanFordSP spt = new BellmanFordSP(G, 0);
//        if (spt.hasNegativeCycle()) {
//            double stake = 1000.0;
//            boolean first = false;
//            String remove1 = null;
//            String remove2 = null;
//
//            String last = null;
//            String list = "";
//
//            for (DirectedEdge e : spt.negativeCycle()) {
//                if (!first) {
//                    first = true;
//                    remove1 = name.get(e.from()) + "_" + name.get(e.to());
//                    remove2 = name.get(e.to()) + "_" + name.get(e.from());
//                }
//
//                StdOut.printf("%10.5f %s ", stake, name.get(e.from()));
//                stake -= .0025 * stake;
//                stake *= Math.exp(-e.weight());
//
//
//                StdOut.printf("= %10.5f %s\n", stake, name.get(e.to()));
//
//                list += name.get(e.from()) + "_";
//                last = name.get(e.to());
//
//            }
//            list += last;
//            String g = String.format("%06.2f", (stake - 1000d) / 10d);
//            list = g + " " + list;
//            if (stake > 1000) {
//                al.add(list);
//                System.out.println("cycle: " + list);
//            }
//            hm.remove(remove1);
//            hm.remove(remove2);
//            al.addAll(negativeCycle(hm));
//        }
//        return al;
//    }


//    //use for SQL command SELECT
//    public synchronized void query(String expression) throws SQLException {
//
//        Statement st = null;
//        ResultSet rs = null;
//        st = conn.createStatement();         // statement objects can be reused with
//        rs = st.executeQuery(expression);    // run the query
//        dump(rs);
//        st.close();    // NOTE!! if you close a statement the associated ResultSet is
//    }
//
//    //use for SQL commands CREATE, DROP, INSERT and UPDATE
//    public synchronized void update(String expression) throws SQLException {
//        Statement st = null;
//        st = conn.createStatement();    // statements
//        int i = st.executeUpdate(expression);    // run the query
//        if (i == -1) {
//            System.out.println("db error : " + expression);
//        }
//        st.close();
//    }
//
//
//    public static void dump(ResultSet rs) throws SQLException {
//        ResultSetMetaData meta   = rs.getMetaData();
//        int               colmax = meta.getColumnCount();
//        int               i;
//        Object            o = null;
//        for (; rs.next(); ) {
//            for (i = 0; i < colmax; ++i) {
//                o = rs.getObject(i + 1);    // Is SQL the first column is indexed
//                System.out.print(o.toString() + " ");
//            }
//            System.out.println(" ");
//        }
//    }
}
