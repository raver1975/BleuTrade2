package com.klemstinegroup.bleutrade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class WSClient {

    private static FileWriter fileWriter;
    private int messageCount = 0;
    private static boolean firstRun=true;


    private static Object waitLock = new Object();

    @OnMessage
    public synchronized void onMessage(String message) {
//the new USD rate arrives from the websocket server side.
        String[] parsed = message.split("\n");
        for (String s : parsed) {
            if (fileWriter != null) {
                String[] bb = s.split(",");
                String out = (Double.parseDouble(bb[1]) * 100000) + "," + (Double.parseDouble(bb[2]) * 100000);
                System.out.println(messageCount+"\t"+out);
                messageCount++;
                File file = new File("./rnnRegression/passengers_raw.csv");
                try {
                    fileWriter = new FileWriter(file,true);
                    fileWriter.write(out + "\n");
                    fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        }
        if (!firstRun) {
            try {
                new MultiTimestepRegressionExampleoLD();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
        File file = new File("./rnnRegression/passengers_raw.csv");
        try {
            fileWriter = new FileWriter(file);
            fileWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("firstrun");
                firstRun=false;
                try {
                    new MultiTimestepRegressionExampleoLD();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

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