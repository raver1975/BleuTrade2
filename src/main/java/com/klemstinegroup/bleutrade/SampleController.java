package com.klemstinegroup.bleutrade;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home()
    {
        File f=new File("predictions.txt");
        String content="<html><head></head><body>";
        for (HistoricalData hd:LocalDataListen.last500){
            content+="<div>"+hd.price+" "+hd.lastPrice+" "+hd.prediction+" "+hd.isCorrect()+"</div>";
        }
        content+="</body></html>";
        return content;
    }

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable(){
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