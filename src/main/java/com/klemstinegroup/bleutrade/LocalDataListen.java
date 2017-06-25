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

import javax.swing.*;
import javax.websocket.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

@ClientEndpoint
public class BleutradeDataListen {

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
            System.out.println(s);
            String[] bb = s.split(",");
            priceData.add(new PriceData(bb));
            String out = (Long.parseLong(bb[0])) + "," + Double.parseDouble(bb[1]) + "," + (Double.parseDouble(bb[2]));
            messageCnt++;
            System.out.println(messageCnt + "\t" + out);
        }

    }


    public static long maximumKey(int j, long[] ar) {
        long max = 0;
        for (int i = j; i < ar.length; i++) {
            if (max < ar[i])
                max = ar[i];
        }
        return max;
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
            session = container.connectToServer(BleutradeDataListen.class, URI.create("ws://ec2-52-91-187-205.compute-1.amazonaws.com:9001"));
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