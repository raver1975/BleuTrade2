package com.klemstinegroup.bleutrade;

import com.alphatica.genotick.genotick.MainModified;
import com.alphatica.genotick.genotick.MainSettings;
import com.alphatica.genotick.timepoint.TimePoint;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
//import com.klemstinegroup.bleutrade.json.Balance;
//import com.klemstinegroup.bleutrade.json.Market;
//import com.klemstinegroup.bleutrade.json.Ticker;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocalDataListen {
    PrintStream old = System.out;
    ArrayList<PriceData> priceData = new ArrayList<PriceData>();
    String file = "./data/all.txt";
    //    static final String MARKET = "ETH_BTC";
    static String COIN1 = DataCollector.coin1;
    static String COIN2 = DataCollector.coin2;
    static String MARKET = COIN1 + "_" + COIN2;
    int dataSizeLimit = 500;
    static DecimalFormat dfcoins = new DecimalFormat("+0000.00000000;-0000.00000000");
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static int histdatasize = 101;
    public static ArrayList<HistoricalData> last500 = new ArrayList<HistoricalData>();

    static {
        File file = new File("history.ser");
        if (file.exists()) {
            try {
                last500 = (ArrayList<HistoricalData>) Serializer.load(file);
            } catch (Exception e) {
                File file1 = new File("history1.ser");
                if (file1.exists()) {
                    try {
                        last500 = (ArrayList<HistoricalData>) Serializer.load(file1);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
//        else {
//            for (int i = 0; i < histdatasize; i++) {
//                HistoricalData hd = new HistoricalData("OUT", 0);
//                //hd.correct = Math.random() < 0.5d;
//                hd.timestamp = System.currentTimeMillis() - (histdatasize - i) * DataCollector.timeout * 1000;
//                hd.setNextPrice(0d);
//                last500.add(hd);
//            }
//        }
    }

    //public static double lastPrice = 0d;

    public LocalDataListen() {
        try {
            if (new File("data/reverse_all.txt").exists()) {
                new File("data/reverse_all.txt").delete();
            }
            File[] list = new File(".").listFiles();
            for (File f : list) {
//                if (f.isDirectory() && f.toPath().toString().contains("savedPopulation")) {
//                    final File[] files = f.listFiles();
//                    for (File ft : files) ft.delete();
//                    f.delete();
//                }

                if (f.isDirectory() && f.toPath().toString().contains("savedPopulation")) {

                    File pop = new File("populationDAO");
                    if (pop.exists() && pop.isDirectory()) {
                        final File[] files = pop.listFiles();
                        for (File ft : files) ft.delete();
                        pop.delete();
                    }
                    boolean success = f.renameTo(new File("populationDAO"));
                    System.out.println("Saved copy succeeded:" + success);
                }
            }

            if (new File("populationDAO").exists()) {
                try {
                    Scanner sc = new Scanner(new File(file));
                    ArrayList<String> al = new ArrayList<>();
                    while (sc.hasNext()) al.add(sc.nextLine());
                    String s = al.get(al.size() - 1);
                    String[] s1 = s.split(",");
                    MainSettings.startTimePoint = new TimePoint(Long.parseLong(s1[0]));
                    MainSettings.endTimePoint = new TimePoint(Long.MAX_VALUE);
//                    MainSettings.populationDAO = "populationDAO";
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {


            }


            ArrayList<String> stringlist = new ArrayList<>();
            try {
                Scanner sc = new Scanner(new File(file));
                while (sc.hasNext()) {
                    stringlist.add(sc.nextLine());
                }
                if (stringlist.size() > dataSizeLimit) {
                    while (stringlist.size() > dataSizeLimit) stringlist.remove(0);
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

            // Tell Java to use your special stream
            System.setOut(ps);
//        final boolean[] run = {true};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
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
                                        if (s.contains("all") && s.contains("prediction") && !s.contains("reverse_all")) {
                                            System.setOut(old);
                                            System.out.println("--------");

                                            double finalResult = -1;
                                            try {
                                                String[] pp = split[1].split(": ");
                                                finalResult = Double.parseDouble(pp[1]);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            String prediction = s.substring(s.indexOf(": ") + 2);
                                            if (prediction.startsWith("OUT")) prediction = "OUT";
                                            if (prediction.startsWith("DOWN")) prediction = "DOWN";
                                            if (prediction.startsWith("UP")) prediction = "UP";
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
                    }catch (Exception e){
                        System.setOut(old);
                        System.out.println("-------------------");
                        e.printStackTrace();
                        System.out.println("-------------------");
                    }
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
        } catch (Exception e) {
            System.setOut(old);
            e.printStackTrace();
        }
    }

    private void predict(String prediction) {
        if (prediction == null) return;
        // get balance
        //get currency
        Exchange exchange = null;
        try {
            exchange = ExchangeFactory.INSTANCE.createExchange(Class.forName("org.knowm.xchange."+DataCollector.market.toLowerCase()+"."+DataCollector.market+"Exchange").getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ExchangeSpecification exchangeSpecification = exchange.getDefaultExchangeSpecification();
        exchangeSpecification.setApiKey(DataCollector.apikey);
        exchangeSpecification.setSecretKey(DataCollector.apisecret);
        exchange.applySpecification(exchangeSpecification);
        MarketDataService marketDataService = exchange.getMarketDataService();

        CurrencyPair currencyPair=new CurrencyPair(COIN1, COIN2);

        Ticker ticker = null;
        try {
            ticker = marketDataService.getTicker(currencyPair);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ticker==null)return;

        AccountService accountService = exchange.getAccountService();
        AccountInfo accountInfo = null;
        try {
            accountInfo = accountService.getAccountInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ArrayList<Balance> balances = Http.getBalances();
//        double dogecoin = -1;
//        double bitcoin = -1;

        double dogecoin = accountInfo.getWallet().getBalance(Currency.getInstance(COIN1)).getAvailable().doubleValue();
        double bitcoin = accountInfo.getWallet().getBalance(Currency.getInstance(COIN2)).getAvailable().doubleValue();

//        System.out.println(COIN1 + ":" + dogecoin + "\t" + COIN2 + ":" + bitcoin);

        double finalResult = bitcoin + dogecoin * ticker.getBid().doubleValue();
        String ss = dateFormat.format(new Date()) + "\t" + "FINAL: " + dfcoins.format(finalResult) + "\tPRE: " + prediction + "\t" + "PRICE: " + dfcoins.format(ticker.getBid().doubleValue());
        System.out.println(ss);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File("predictions.txt"), true));
            pw.println(ss);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HistoricalData hs = new HistoricalData(prediction, ticker.getBid().doubleValue());
        if (last500.size() > 0) {
            HistoricalData hd1 = last500.get(last500.size() - 1);
            hd1.setNextPrice(ticker.getBid().doubleValue());
        }
        last500.add(hs);
        File file = new File("history.ser");
        try {
            Serializer.save(last500, file);
        } catch (Exception e) {
        }
        file = new File("history1.ser");
        try {
            Serializer.save(last500, file);
        } catch (Exception e) {
        }
//        lastPrice = tickerHM.get(MARKET).getBid();
        while (last500.size() > histdatasize) last500.remove(0);
        if (prediction.equals("OUT")) return;
        if (prediction.equals("UP")) {      //BUY
            double cc = (bitcoin / 10d) / ticker.getAsk().doubleValue();
            //buy
//            Http.buyselllimit(MARKET, ticker.getAsk().doubleValue(), cc, true);
//            LimitOrder mo=new LimitOrder(Order.OrderType.BID,new BigDecimal(cc),currencyPair);
            System.out.println("buying "+cc+" at "+dfcoins.format(ticker.getAsk().doubleValue()));
            LimitOrder mo=new LimitOrder((Order.OrderType.BID), new BigDecimal(dfcoins.format(cc)), currencyPair, null, null,new BigDecimal(dfcoins.format(ticker.getAsk().doubleValue())) );
            try {
                String result =exchange.getTradeService().placeLimitOrder(mo);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (prediction.equals("DOWN")) {    //SELL
            // sell
            double cc=dogecoin / 10d;
//            MarketOrder mo=new MarketOrder(Order.OrderType.ASK,new BigDecimal(cc),currencyPair);
            System.out.println("selling "+cc+" at "+dfcoins.format(ticker.getBid().doubleValue()));
            LimitOrder mo=new LimitOrder((Order.OrderType.ASK), new BigDecimal(dfcoins.format(cc)), currencyPair, null, null,new BigDecimal(dfcoins.format(ticker.getBid().doubleValue())) );

            try {
                exchange.getTradeService().placeLimitOrder(mo);
            } catch (Exception e) {
                e.printStackTrace();
            }
// Http.buyselllimit(MARKET, ticker.getBid().doubleValue(), dogecoin / 10d, false);
        }
    }

    public static void main(String[] args) {
        new LocalDataListen();
    }
}