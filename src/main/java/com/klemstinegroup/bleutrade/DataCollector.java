package com.klemstinegroup.bleutrade;

//import com.klemstinegroup.bleutrade.json.*;
//import com.klemstinegroup.bleutrade.json.Currency;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.klemstinegroup.bleutrade.LocalDataListen.COIN1;
import static com.klemstinegroup.bleutrade.LocalDataListen.COIN2;

/**
 * Created by Paul on 4/16/2017.
 */
public class DataCollector {

    ArrayList<Currency> currencies = new ArrayList<Currency>();
    HashMap<String, Double> currencyCost = new HashMap<String, Double>();
//    ArrayList<Market> markets = new ArrayList<Market>();
//    private ArrayList<Ticker> tickers = new ArrayList<Ticker>();
//    private HashMap<String, Ticker> tickerHM = new HashMap<String, Ticker>();

    public static String apikey;
    public static String apisecret;
    public static boolean debug;
    public static String market;
    public static String coin1;
    public static String coin2;
    public static int bots;
    public static long timeout;

    static DecimalFormat dfdollars = new DecimalFormat("+0000.00;-0000.00");
    static DecimalFormat dfcoins = new DecimalFormat("+0000.00000000;-0000.00000000");
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public DataCollector(){
        getSecret();
        if (!new File("./data").exists())new File("./data").mkdir();

        while (true) {
            try {
                //get currency

                System.out.println(BleutradeExchange.class.getName());
                Exchange exchange = ExchangeFactory.INSTANCE.createExchange(Class.forName("org.knowm.xchange.bleutrade."+market+"Exchange").getName());
                ExchangeSpecification exchangeSpecification = exchange.getDefaultExchangeSpecification();
                exchangeSpecification.setApiKey(DataCollector.apikey);
                exchangeSpecification.setSecretKey(DataCollector.apisecret);
                exchange.applySpecification(exchangeSpecification);
                MarketDataService marketDataService = exchange.getMarketDataService();

                Ticker ticker = null;
                try {
                    ticker=marketDataService.getTicker(new CurrencyPair(COIN1,COIN2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    if (ticker == null) continue;
                    double bid = ticker.getBid().doubleValue();
                    double ask = ticker.getAsk().doubleValue();
                    double last = 0;
                    if (ticker.getLast() != null) last = ticker.getLast().doubleValue();
//                try {
//                    String writeOut=time+","+dfcoins.format(bid)+","+dfcoins.format(ask)+"\n";
                        String btcdge = ticker.getTimestamp().getTime() + "," + dfcoins.format(bid) + "," + dfcoins.format(ask);
//                    allout = allout + "," + dfcoins.format(bid) + "," + dfcoins.format(ask);
///                }
//
                FileWriter fwall = null;
                try {
                    fwall = new FileWriter(new File("./data/all.txt"), true);
                    fwall.write(btcdge + "\n");
                    fwall.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("time=" + new Date());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new LocalDataListen();
                    }
                }).start();

                System.out.println("waiting for " + timeout + "s");
            }
            catch (Exception ee){
                ee.printStackTrace();
            }
            try {
                long sleep = timeout * 1000l;
                if (sleep < 1000) sleep = 1000;
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(dateFormat.format(new Date())+"\t"+"-------------------------------------------------------------------------------------------------");




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
            debug = Boolean.parseBoolean(prop.getProperty("debug"));
            bots=Integer.parseInt(prop.getProperty("bots"));
            market=prop.getProperty("market");
            coin1=prop.getProperty("coin1");
            coin2=prop.getProperty("coin2");

            System.out.println("apikey=" + apikey);
            System.out.println("apisecret=" + apisecret);
            System.out.println("timeout=" + timeout);
            System.out.println("debug=" + debug);
            System.out.println("bots=" + bots);
            System.out.println("market=" + market);
            System.out.println("coin1=" + coin1);
            System.out.println("coin2=" + coin2);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new DataCollector();

    }
}
