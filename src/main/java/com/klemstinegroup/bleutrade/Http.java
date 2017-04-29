package com.klemstinegroup.bleutrade;


import com.klemstinegroup.bleutrade.json.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Paul on 2/21/2016.
 */


public class Http {

    static String uri = "https://bleutrade.com/api/v2";

    public static double bitcoinPrice() throws IOException {
        String url = "https://api.coinbase.com/v2/prices/spot?currency=USD";
        URL website = null;
            website = new URL(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget=new HttpGet(website.toString());
        CloseableHttpResponse res = httpclient.execute(httpget);

        final InputStream is = res.getEntity().getContent();;
        final Reader reader = new InputStreamReader(is);
        final char[] buf = new char[1024];
        int read;
        final StringBuilder response = new StringBuilder();
        while ((read = reader.read(buf)) > 0) {
            response.append(buf, 0, read);
        }
        reader.close();
        httpclient.close();
        String rest = response.toString();
        JSONObject obj = new JSONObject(rest);
        return obj.getJSONObject("data").getDouble("amount");

    }


    public static JSONObject open(String url, Map<String, String> params) throws IOException {
        url = uri + url;
        if (params == null) params = new HashMap<String, String>();

        if (params.size() > 0) {

            url += "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url += entry.getKey() + "=" + entry.getValue();
                url += "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        System.out.println("opening url=" + (url.length() < 80 ? url : url.substring(0, 80) + "..."));
        URL website = new URL(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget=new HttpGet(website.toString());
        CloseableHttpResponse res = httpclient.execute(httpget);

        final InputStream is = res.getEntity().getContent();;
        final Reader reader = new InputStreamReader(is);
        final char[] buf = new char[1024];
        int read;
        final StringBuilder response = new StringBuilder();
        while ((read = reader.read(buf)) > 0) {
            response.append(buf, 0, read);
        }
        reader.close();
        httpclient.close();
        String rest = response.toString();
        // System.out.println("response="+res);
        return new JSONObject(rest);
    }

    public static JSONObject openPrivate(String url, Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        url = uri + url;
        if (params == null) params = new HashMap<String, String>();
        params.put("apikey", RnnTrader.apikey);

        if (params.size() > 0) {

            url += "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url += entry.getKey() + "=" + entry.getValue();
                url += "&";
            }
            url = url.substring(0, url.length() - 1);
        }

        Mac sha_HMAC = Mac.getInstance("HmacSHA512");

        SecretKeySpec secret_key = new SecretKeySpec(RnnTrader.apisecret.getBytes(), "HmacSHA512");
        sha_HMAC.init(secret_key);

        String hash = toHex(sha_HMAC.doFinal(url.getBytes()));

        System.out.println("opening url=" + (url.length() < 80 ? url : url.substring(0, 80) + "..."));
        URL website = new URL(url);


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget=new HttpGet(website.toString());
        httpget.setHeader("apisign",hash);
        CloseableHttpResponse res = httpclient.execute(httpget);

        final InputStream is = res.getEntity().getContent();
        final Reader reader = new InputStreamReader(is);
        final char[] buf = new char[1024];
        int read;
        final StringBuilder response = new StringBuilder();
        while ((read = reader.read(buf)) > 0) {
            response.append(buf, 0, read);
        }
        reader.close();
        httpclient.close();

//        URLConnection connection = website.openConnection(Proxy.NO_PROXY);
//        connection.setConnectTimeout(10000);
//        connection.setReadTimeout(60000);
//        connection.setRequestProperty("apisign", hash);
//
//        connection.connect();
//        final InputStream is = connection.getInputStream();
//        final Reader reader = new InputStreamReader(is);
//        final char[] buf = new char[1024];
//        int read;
//        final StringBuilder response = new StringBuilder();
//        while ((read = reader.read(buf)) > 0) {
//            response.append(buf, 0, read);
//        }
//        reader.close();
        String rest = response.toString();
        return new JSONObject(rest);
    }

    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }

    public static ArrayList<Currency> getCurrencies() {
        JSONObject json = null;
        try {
            json = open("/public/getcurrencies", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) return null;
        boolean success = json.getBoolean("success");
        if (!success) System.out.println(json.getString("message"));

        JSONArray array = json.getJSONArray("result");
        ArrayList<Currency> arr = new ArrayList<Currency>();
        for (int i = 0; i < array.length(); i++) {
            arr.add(Currency.fromJson(array.getJSONObject(i)));
        }
        return arr;
    }

    public static ArrayList<Market> getMarkets() {
        JSONObject json = null;
        try {
            json = open("/public/getmarkets", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) return null;

        boolean success = json.getBoolean("success");
        if (!success) System.out.println(json.getString("message"));

        JSONArray array = json.getJSONArray("result");
        ArrayList<Market> arr = new ArrayList<Market>();
        for (int i = 0; i < array.length(); i++) {
            arr.add(Market.fromJson(array.getJSONObject(i)));
        }
        return arr;
    }

    public static Ticker getTicker(String ticker) {
        ArrayList<String> al = new ArrayList<String>();
        al.add(ticker);
        return getTickers(al).get(0);
    }

    public static ArrayList<Ticker> getTickers(List<String> tickers) {
        JSONObject json = null;
        HashMap<String, String> params = new HashMap<String, String>();
        if (tickers != null) {
            String s = "";
            for (String t : tickers) s += t + ",";
            s = s.substring(0, s.length() - 1);
            params.put("market", s);
        }
        try {
            json = open("/public/getticker", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean success = json.getBoolean("success");
        if (!success) System.out.println(json.getString("message"));

        JSONArray array = json.getJSONArray("result");
        ArrayList<Ticker> arr = new ArrayList<Ticker>();
        for (int i = 0; i < array.length(); i++) {
            arr.add(Ticker.fromJson(array.getJSONObject(i)));
        }
        return arr;

    }

    public static ArrayList<Balance> getBalances() {
        JSONObject json = null;
        try {
            json = openPrivate("/account/getbalances", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) return null;
        boolean success = json.getBoolean("success");
        if (!success) System.out.println(json.getString("message"));
        JSONArray array = json.getJSONArray("result");
        ArrayList<Balance> arr = new ArrayList<Balance>();
        for (int i = 0; i < array.length(); i++) {
            arr.add(Balance.fromJson(array.getJSONObject(i)));
        }
        return arr;
    }

    //orderstatus (ALL, OK, OPEN, CANCELED)
    //ordertype (ALL, BUY, SELL)

    public static ArrayList<Order> getOrders(String orderStatus,String market) {
        JSONObject json = null;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("market", market);
        params.put("orderstatus", orderStatus);
        params.put("ordertype", "ALL");

        try {
            json = openPrivate("/account/getorders", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = json.getBoolean("success");
        if (!success) System.out.println(json.getString("message"));
        JSONArray array = json.getJSONArray("result");
        ArrayList<Order> arr = new ArrayList<Order>();
        for (int i = 0; i < array.length(); i++) {
            arr.add(Order.fromJson(array.getJSONObject(i)));
        }
        return arr;
    }

    public static long buyselllimit(String market, double rate, double quantity, boolean buy) {
        if (!buy && market.contains("BLEU")) return -1;
        JSONObject json = null;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("market", market);
        params.put("rate", RnnTrader.dfcoins.format(rate));
        params.put("quantity", RnnTrader.dfcoins.format(quantity));
//        params.put("comments", comments);
        System.out.println("placing " + (buy ? "buy" : "sell") + " order:" + market + "\t" + RnnTrader.dfcoins.format(rate) + "\t#" + RnnTrader.dfcoins.format(quantity));
        if (RnnTrader.debug) return 1;
        try {
            if (buy) json = openPrivate("/market/buylimit", params);
            else json = openPrivate("/market/selllimit", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = json.getBoolean("success");
        if (!success) {
            System.out.println(json.getString("message"));
            return -1;
        }
        JSONObject result = json.getJSONObject("result");
        return result.getLong("orderid");
    }

    public static boolean cancel(long id) {
        JSONObject json = null;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderid", Long.toString(id));
        System.out.println("canceling order " + id);
        if (RnnTrader.debug) return true;
        try {
            json = openPrivate("/market/cancel", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = json.getBoolean("success");
        if (!success) System.out.println(json.getString("message"));
        return success;
    }
}
