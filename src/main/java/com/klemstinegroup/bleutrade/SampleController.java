package com.klemstinegroup.bleutrade;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/api")
    @ResponseBody
    String api() {
        HistoricalData hd = LocalDataListen.last500.get(LocalDataListen.last500.size() - 1);
        return "{\"expires\":" + (hd.timestamp + DataCollector.timeout * 1000) + ", \"time\":\"" + hd.timestamp + "\", \"prediction\":" + hd.prediction + ", \"currentPrice\":\"" + hd.currentPrice + "\" }";
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        File f = new File("predictions.txt");
        String labels = "";
        String data = "";
        String pointBackgroundColor = "";
        int total = -1;
        int correct = 0;
        long lastTime = 0;
        String prediction = "";
        double gain=0;
        for (HistoricalData hd : LocalDataListen.last500) {
            if (!hd.prediction.equals("OUT")){
                total++;
                if (hd.correct) correct++;
            }
            labels += "\"" + DateFormat.getDateTimeInstance(
                    DateFormat.SHORT, DateFormat.MEDIUM).format(hd.timestamp) + "\",";
            data += "{x:\"" + DateFormat.getDateTimeInstance(
                    DateFormat.SHORT, DateFormat.MEDIUM).format(hd.timestamp) + "\",y:" + hd.currentPrice + "},";
            if (hd.prediction.equals("OUT")||hd.equals(LocalDataListen.last500.get(LocalDataListen.last500.size()-1)))pointBackgroundColor+="\"#aaaaaa\",";
            else pointBackgroundColor += hd.isCorrect() ? "\"#00ff00\"," : "\"#ff0000\",";
            double tempGain=Math.abs(hd.nextPrice-hd.currentPrice);
            if (hd.isCorrect())gain+=tempGain;
            else gain=-tempGain;
            lastTime = hd.timestamp;
            prediction = hd.prediction;
        }
        if (labels.length() > 0) labels = labels.substring(0, labels.length() - 1);
        if (data.length() > 0) data = data.substring(0, data.length() - 1);
        if (pointBackgroundColor.length() > 0)
            pointBackgroundColor = pointBackgroundColor.substring(0, pointBackgroundColor.length() - 1);
        long millis=(lastTime + DataCollector.timeout * 1000)-System.currentTimeMillis();
        if (millis<0)millis=0;
        int refresh= (int) (millis/1000);
        if (refresh<30)refresh=30;
        String timeLeft=String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        String chart = "new Chart(document.getElementById(\"myChart\"), {\n" +
                "  type: 'line',\n" +
                "  data: {\n" +
                "    labels: [" + labels + "],\n" +
                "    datasets: [{ \n" +
                "        label: \"currentPrice\",\n" +
                "        data: [" + data + "],\n" +
                "        pointBackgroundColor: [" + pointBackgroundColor + "],\n" +
                "        borderColor: \"#3e95cd\",\n" +
                "        fill: false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  options: {\n" +
                " legend: {\n" +
                "        display: false\n" +
                "    }," +
                "    title: {\n" +
                "      fontSize:50,"+
                "      display: true,\n" +
                "      text: '" + DataCollector.market + " " + DataCollector.coin1 + "_" + DataCollector.coin2 + " Prediction'\n" +
                "    }\n" +
                "  }\n" +
                "});";
        String content = "<html><head><meta http-equiv=\"refresh\" content=\""+refresh+"\"><script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.bundle.min.js\"></script></head><body> <div style=\"width:100%; margin:0 auto;text-align:center;\">";
        content+="<div width=\"100%\" height=\"200px\"><script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>\n" +
                "<!-- Predictor -->\n" +
                "<ins class=\"adsbygoogle\"\n" +
                "     style=\"display:block\"\n" +
                "     data-ad-client=\"ca-pub-1813447557997883\"\n" +
                "     data-ad-slot=\"8779058011\"\n" +
                "     data-ad-format=\"auto\"></ins>\n" +
                "<script>\n" +
                "(adsbygoogle = window.adsbygoogle || []).push({});\n" +
                "</script></div>";

        content += "<div style=\" margin:0 auto;max-height:90%;width:95%;height:90%;\"><canvas id=\"myChart\"></canvas><script type=\"text/javascript\">var ctx = document.getElementById(\"myChart\");" + chart + "</script>";
        content+="<div style=\"text-align:center;width:100%\"><a href=\"http://github.com/raver1975/bleutrade2\">GitHub</a></div>";
        content+="<div style=\"text-align:center;width:100%\"><a href=\"mailto:paulklemstine@gmail.com\">&copy;2017 Paul Klemstine</a></div>";
        content+="<div style=\"text-align:center;width:100%\">Donate Ethereum: 0x0f209f410548D17E8760D39fb84E7B8dd2e002BC</div></div>";
                content+="</div>";
//        content += "<div>" + DateFormat.getDateTimeInstance(
//                DateFormat.SHORT, DateFormat.MEDIUM).format(lastTime) + "</div>";
//        content += "<div>" + DateFormat.getDateTimeInstance(
//                DateFormat.SHORT, DateFormat.MEDIUM).format(System.currentTimeMillis()) + "</div>";
//        content += "<div>" + DateFormat.getDateTimeInstance(
//                DateFormat.SHORT, DateFormat.MEDIUM).format(lastTime + DataCollector.timeout * 1000) + "</div>";
//        content += "<div>" + prediction + "</div>";
//        content += "<div>" + correct + "/" + total + " correct</div>";

        int percent=0;
        if (total>0)percent=(int)((correct*100)/total);
        String color="red";
        if (gain>0)color="green";
        content+="<div style=\"z-index: 1; position: absolute; top: 160px; left: 150px; height:250px; width:500px;\"><h3><span style=\"color:red;\">"+percent+"% Correct!</span><br/>Gain/Loss: <span style=\"color:"+color+";\">"+formatter.format(gain)+"</span><br/>Current prediction: <span style=\"color:red;\">"+prediction+"</span><br/>Next in "+timeLeft+"</h3></div>";


        content += "</body></html>";
        return content;
    }

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataCollector.main(new String[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        SpringApplication.run(SampleController.class, args);
    }
}