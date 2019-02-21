package com.azxx.picture.schedule;

import com.azxx.picture.modal.weather.HeWeather;
import com.azxx.picture.modal.weather.TodayWeather;
import com.azxx.picture.service.EmailService;
import com.azxx.picture.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherJob {

    private static Logger logger = LoggerFactory.getLogger(WeatherJob.class);

    @Autowired
    private EmailService emailService;

    @Value("${weather.query.url}")
    private String url;

//    @Scheduled(cron = "0/5 * * * * *")
    public void queryWeather(){
        String result = HttpUtils.httpGet(url,null);
        logger.info(result);
        Gson gson = new Gson();
        JsonObject object =  new JsonParser().parse(result).getAsJsonObject();
        JsonObject data = object.get("data").getAsJsonObject();
        logger.info(data.toString());
        TodayWeather weather = gson.fromJson(data,TodayWeather.class);
        logger.info(weather.toString());
    }

    @Scheduled(cron = "0 0 0,6,7,8,12,17,22 * * ? ")
//    @Scheduled(cron = "0/20 * * * * *")
    public void queryHeWeather(){
        String response = HttpUtils.httpGet(url,null);
        logger.info(response);
        Gson gson = new Gson();
        JsonObject object =  new JsonParser().parse(response).getAsJsonObject();
        JsonArray data = object.get("HeWeather6").getAsJsonArray();
        JsonObject dataObj = data.get(0).getAsJsonObject();
        JsonArray weathers = dataObj.get("daily_forecast").getAsJsonArray();
        String result = "";
        if(weathers!=null&&weathers.size()>0){
            HeWeather heWeather1 = gson.fromJson(weathers.get(0).getAsJsonObject(),HeWeather.class);
            HeWeather heWeather2 = gson.fromJson(weathers.get(1).getAsJsonObject(),HeWeather.class);
            HeWeather heWeather3 = gson.fromJson(weathers.get(2).getAsJsonObject(),HeWeather.class);
            HeWeather tmp = heWeather1;
            result += String.format("时间：%s，最高温度：%s，最低温度：%s，天气状况：%s，紫外线强度：%s.\n",tmp.getDate(),tmp.getTmp_max(),tmp.getTmp_min(),tmp.getCond_txt_d(),tmp.getUv_index());
            tmp = heWeather2;
            result += String.format("时间：%s，最高温度：%s，最低温度：%s，天气状况：%s，紫外线强度：%s.\n",tmp.getDate(),tmp.getTmp_max(),tmp.getTmp_min(),tmp.getCond_txt_d(),tmp.getUv_index());
            tmp = heWeather3;
            result += String.format("时间：%s，最高温度：%s，最低温度：%s，天气状况：%s，紫外线强度：%s.\n",tmp.getDate(),tmp.getTmp_max(),tmp.getTmp_min(),tmp.getCond_txt_d(),tmp.getUv_index());
        }
        emailService.sendSimpleEmail("xuzheng_13@qq.com","【天气预报】",result);
        logger.info(result);
    }

//    @Scheduled(cron = "0/5 * * * * *")
    public void sendMail(){
        emailService.sendSimpleEmail("xuzheng_13@qq.com","【天气预报】","时间：2018-10-10，最高温度：28，最低温度：20，天气状况：阵雨，紫外线强度：4.");
    }
}
