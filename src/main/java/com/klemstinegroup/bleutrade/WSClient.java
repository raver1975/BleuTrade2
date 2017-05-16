package com.klemstinegroup.bleutrade;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import weka.classifiers.evaluation.NumericPrediction;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.websocket.*;

@ClientEndpoint
public class WSClient {

    static JFrame f;
    static JPanel panel;

    static {
        f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setTitle("Training Data");
        f.setSize(1200, 800);

        RefineryUtilities.centerFrameOnScreen(f);
        f.setVisible(true);
    }


    //    private static FileWriter fileWriter;
//    private int messageCount = 0;
    ArrayList<PriceData> priceData = new ArrayList<PriceData>();
    int messageCnt = 0;

    private static Object waitLock = new Object();

    @OnMessage
    public synchronized void onMessage(String message) {
//the new USD rate arrives from the websocket server side.
        String[] parsed = message.split("\n");
        for (String s : parsed) {
            String[] bb = s.split(",");
            priceData.add(new PriceData(Long.parseLong(bb[0]), Double.parseDouble(bb[1]), Double.parseDouble(bb[2])));
            String out = (Long.parseLong(bb[0])) + "," + Double.parseDouble(bb[1]) + "," + (Double.parseDouble(bb[2]));
            messageCnt++;
            System.out.println(messageCnt + "\t" + out);
        }

        evaluate();
    }


    public static long maximumKey(int j, long[] ar) {
        long max = 0;
        for (int i = j; i < ar.length; i++) {
            if (max < ar[i])
                max = ar[i];
        }
        return max;
    }

    private void evaluate() {
        boolean[] minBid = new boolean[priceData.size()];
        boolean[] maxBid = new boolean[priceData.size()];
        boolean[] minAsk = new boolean[priceData.size()];
        boolean[] maxAsk = new boolean[priceData.size()];

        for (int i = 1; i < priceData.size() - 1; i++) {
            if (priceData.get(i - 1).ask <= priceData.get(i).ask && priceData.get(i + 1).ask <= priceData.get(i).ask)
                maxAsk[i] = true;
            if (priceData.get(i - 1).bid <= priceData.get(i).bid && priceData.get(i + 1).bid <= priceData.get(i).bid)
                maxBid[i] = true;
            if (priceData.get(i - 1).ask >= priceData.get(i).ask && priceData.get(i + 1).ask >= priceData.get(i).ask)
                minAsk[i] = true;
            if (priceData.get(i - 1).bid >= priceData.get(i).bid && priceData.get(i + 1).bid >= priceData.get(i).bid)
                minBid[i] = true;
        }

/*
        for (int i=0;i<maxBid.length;i++){
            int lowest=Integer.MAX_VALUE;
            int is=-1;
            int js=-1;
            for (int j=0;j<minBid.length;j++){
               if (maxBid[i]&&minBid[j]){
                    int dist=Math.abs(i-j);
                    if (dist<lowest){
                        lowest=dist;
                        is=i;
                        js=j;
                    }
                }
            }
            if (lowest<1){
                maxBid[is]=false;
                minBid[js]=false;
                System.out.println("too close!");
            }
        }
*/
        XYSeries s1 = new XYSeries("real bid");
        XYSeries s2 = new XYSeries("real ask");


        for (int i = 0; i < priceData.size(); i++) {
            s1.add(i, priceData.get(i).bid);
            s2.add(i, priceData.get(i).ask);

        }

        String title = "Coin Price Future Prediction";
        String xAxisLabel = "Timestep";
        String yAxisLabel = "Coin Price";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;
        XYSeriesCollection c = new XYSeriesCollection();
//        createSeries(c, fullArray, 0, "Train data");
//        createSeries(c, testArray, trainSize - 1, "Actual test data");
//        createSeries(c, predicted, trainSize - 1, "Predicted test data");
//        createSeries(c, predicted1, trainSize+testSize - 1, "Predicted future data");
//         createSeries(c, predicted1, trainSize+numberOfTimesteps - 1, "Predicted test data");

        c.addSeries(s1);
        c.addSeries(s2);

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, c, orientation, legend, tooltips, urls);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();

        // Auto zoom to fit time series in initial window
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(false);

        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        domainAxis.setAutoRange(true);

        final CircleDrawer cdMaxAsk = new CircleDrawer(Color.green, new BasicStroke(1.0f), Color.green);
        final CircleDrawer cdMinAsk = new CircleDrawer(Color.orange, new BasicStroke(1.0f), Color.orange);

        final CircleDrawer cdMaxBid = new CircleDrawer(Color.green, new BasicStroke(1.0f), Color.green);
        final CircleDrawer cdMinBid = new CircleDrawer(Color.orange, new BasicStroke(1.0f), Color.orange);

        final CircleDrawer anomaly = new CircleDrawer(Color.red, new BasicStroke(1.0f), Color.white);

        for (int i = 0; i < minAsk.length; i++) {
//            if (priceData.get(i).ask<=priceData.get(i).bid+.00050d){
//                final XYAnnotation point = new XYDrawableAnnotation(i, priceData.get(i).bid, 3, 3, anomaly);
//                plot.addAnnotation(point);
//                final XYAnnotation point1 = new XYDrawableAnnotation(i, priceData.get(i).ask, 3, 3, anomaly);
//                plot.addAnnotation(point1);
//
//            }

            if (minAsk[i]) {
                final XYAnnotation point = new XYDrawableAnnotation(i, priceData.get(i).ask, 5, 5, cdMinAsk);
                plot.addAnnotation(point);
            }
            if (maxAsk[i]) {
                final XYAnnotation point = new XYDrawableAnnotation(i, priceData.get(i).ask, 5, 5, cdMaxAsk);
                plot.addAnnotation(point);
            }
            if (minBid[i]) {
                final XYAnnotation point = new XYDrawableAnnotation(i, priceData.get(i).bid, 5, 5, cdMinBid);
                plot.addAnnotation(point);
            }
            if (maxBid[i]) {
                final XYAnnotation point = new XYDrawableAnnotation(i, priceData.get(i).bid, 5, 5, cdMaxBid);
                plot.addAnnotation(point);
            }


        }

        try {
            ChartUtilities.saveChartAsPNG(new File("./images/imageChart" + ".png"), chart, 1400, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (panel == null) {
            panel = new ChartPanel(chart);
        } else {
            f.remove(panel);
            panel = new ChartPanel(chart);

        }
        f.add(panel);
        panel.revalidate();
        f.revalidate();
    }


    private static void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        WebSocketContainer container = null;//
        Session session = null;
        try {
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();
//WS1 is the context-root of my web.app 
//ratesrv is the  path given in the ServerEndPoint annotation on server implementation
            session = container.connectToServer(WSClient.class, URI.create("ws://ec2-52-91-187-205.compute-1.amazonaws.com:9001"));
            wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}