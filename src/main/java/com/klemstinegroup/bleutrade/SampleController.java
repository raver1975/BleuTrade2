package com.klemstinegroup.bleutrade;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.DateFormat;

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
        File f = new File("predictions.txt");
        String labels = "";
        String data = "";
        String pointBackgroundColor = "";
        int total = -1;
        int correct = 0;
        long lastTime = 0;
        String prediction = "";
        for (HistoricalData hd : LocalDataListen.last500) {
            if (!hd.prediction.equals("OUT")){
                total++;
                if (hd.correct) correct++;
            }
            labels += "\"" + DateFormat.getDateTimeInstance(
                    DateFormat.SHORT, DateFormat.MEDIUM).format(hd.timestamp) + "\",";
            data += "{x:\"" + DateFormat.getDateTimeInstance(
                    DateFormat.SHORT, DateFormat.MEDIUM).format(hd.timestamp) + "\",y:" + hd.currentPrice + "},";
            pointBackgroundColor += hd.isCorrect() ? "\"#00ff00\"," : "\"#ff0000\",";
            lastTime = hd.timestamp;
            prediction = hd.prediction;
        }
        if (labels.length() > 0) labels = labels.substring(0, labels.length() - 1);
        if (data.length() > 0) data = data.substring(0, data.length() - 1);
        if (pointBackgroundColor.length() > 0)
            pointBackgroundColor = pointBackgroundColor.substring(0, pointBackgroundColor.length() - 1);

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
                "      display: true,\n" +
                "      text: '" + DataCollector.market + " " + DataCollector.coin1 + "_" + DataCollector.coin2 + " Prediction'\n" +
                "    }\n" +
                "  }\n" +
                "});";
        String content = "<html><head><script type=\"text/javascript\" src=\"https://github.com/chartjs/Chart.js/releases/download/v2.6.0/Chart.bundle.min.js\"></script></head><body>";
        content += "<div style=\"max-height:90%;width:95%;height:90%;\"><canvas id=\"myChart\"></canvas><script type=\"text/javascript\">var ctx = document.getElementById(\"myChart\");" + chart + "</script></div>";
//        content += "<div>" + DateFormat.getDateTimeInstance(
//                DateFormat.SHORT, DateFormat.MEDIUM).format(lastTime) + "</div>";
//        content += "<div>" + DateFormat.getDateTimeInstance(
//                DateFormat.SHORT, DateFormat.MEDIUM).format(System.currentTimeMillis()) + "</div>";
//        content += "<div>" + DateFormat.getDateTimeInstance(
//                DateFormat.SHORT, DateFormat.MEDIUM).format(lastTime + DataCollector.timeout * 1000) + "</div>";
//        content += "<div>" + prediction + "</div>";
//        content += "<div>" + correct + "/" + total + " correct</div>";
        int percent=(int)((correct*100)/total);
        content+="<div style=\"color:red;z-index: 1; position: absolute; top: 75px; left: 100px; height:250px; width:500px;\"><h2>"+percent+"% Correct!</br>Next prediction: "+prediction+"</h2></div>";
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