package com.azxx.picture.utils;

import com.azxx.picture.modal.weather.HeWeather;
import com.azxx.picture.modal.weather.TodayWeather;
import com.google.gson.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


@Ignore
public class HttpUtilsTest {

    @Test
    public void testGet(){
        String url = "https://free-api.heweather.com/s6/weather/forecast?location=%E6%B7%B1%E5%9C%B3&key=be429c34e84e48f19e41f6072f7563a3";
//        String result = "{\"data\":\"abc\"}";
        String result = HttpUtils.httpGet(url,null);
//        String result = "{\"data\":{\"yesterday\":{\"date\":\"9日 星期二\",\"temphigh\":\"30\",\"wind\":\"无持续风向\",\"templow\":\"24\",\"windforce\":\"<![CDATA[<3级]]>\",\"weather\":\"多云\"},\"city\":\"深圳\",\"aqi\":\"92\",\"forecast\":[{\"date\":\"10日 星期三\",\"temphigh\":\"28\",\"windforce\":\"<![CDATA[<3级]]>\",\"templow\":\"23\",\"wind\":\"无持续风向\",\"weather\":\"阵雨\"},{\"date\":\"11日 星期四\",\"temphigh\":\"26\",\"windforce\":\"<![CDATA[3-4级]]>\",\"templow\":\"20\",\"wind\":\"东北风\",\"weather\":\"小雨\"},{\"date\":\"12日 星期五\",\"temphigh\":\"27\",\"windforce\":\"<![CDATA[<3级]]>\",\"templow\":\"21\",\"wind\":\"无持续风向\",\"weather\":\"多云\"},{\"date\":\"13日 星期六\",\"temphigh\":\"29\",\"windforce\":\"<![CDATA[<3级]]>\",\"templow\":\"23\",\"wind\":\"无持续风向\",\"weather\":\"多云\"},{\"date\":\"14日 星期天\",\"temphigh\":\"28\",\"windforce\":\"<![CDATA[<3级]]>\",\"templow\":\"23\",\"wind\":\"无持续风向\",\"weather\":\"多云\"}],\"life\":\"各项气象条件适宜，无明显降温过程，发生感冒机率较低。\",\"temp\":\"23\"},\"status\":0,\"msg\":\"实时+4day+昨天+AQI\"}";
        System.out.println(result);
        Gson gson = new Gson();
        JsonObject object =  new JsonParser().parse(result).getAsJsonObject();
        JsonObject data = object.get("data").getAsJsonObject();
        System.out.println(data);
        TodayWeather weather = gson.fromJson(data,TodayWeather.class);
        System.out.println(weather.toString());
    }

    @Test
    public void testHeWeatherGet(){
//        String url = "https://free-api.heweather.com/s6/weather/forecast?location=%E6%B7%B1%E5%9C%B3&key=be429c34e84e48f19e41f6072f7563a3";
//        String result = "{\"data\":\"abc\"}";
//        String result = HttpUtils.httpGet(url,null);
        String result = "{\"HeWeather6\":[{\"basic\":{\"cid\":\"CN101280601\",\"location\":\"深圳\",\"parent_city\":\"深圳\",\"admin_area\":\"广东\",\"cnty\":\"中国\",\"lat\":\"22.54700089\",\"lon\":\"114.08594513\",\"tz\":\"+8.00\"},\"update\":{\"loc\":\"2018-10-10 23:46\",\"utc\":\"2018-10-10 15:46\"},\"status\":\"ok\",\"daily_forecast\":[{\"cond_code_d\":\"300\",\"cond_code_n\":\"300\",\"cond_txt_d\":\"阵雨\",\"cond_txt_n\":\"阵雨\",\"date\":\"2018-10-10\",\"hum\":\"71\",\"mr\":\"07:07\",\"ms\":\"19:07\",\"pcpn\":\"0.0\",\"pop\":\"23\",\"pres\":\"1015\",\"sr\":\"06:18\",\"ss\":\"18:01\",\"tmp_max\":\"28\",\"tmp_min\":\"20\",\"uv_index\":\"4\",\"vis\":\"10\",\"wind_deg\":\"-1\",\"wind_dir\":\"无持续风向\",\"wind_sc\":\"1-2\",\"wind_spd\":\"6\"},{\"cond_code_d\":\"101\",\"cond_code_n\":\"104\",\"cond_txt_d\":\"多云\",\"cond_txt_n\":\"阴\",\"date\":\"2018-10-11\",\"hum\":\"65\",\"mr\":\"08:05\",\"ms\":\"19:49\",\"pcpn\":\"1.0\",\"pop\":\"55\",\"pres\":\"1018\",\"sr\":\"06:18\",\"ss\":\"18:00\",\"tmp_max\":\"27\",\"tmp_min\":\"21\",\"uv_index\":\"2\",\"vis\":\"18\",\"wind_deg\":\"33\",\"wind_dir\":\"东北风\",\"wind_sc\":\"3-4\",\"wind_spd\":\"17\"},{\"cond_code_d\":\"104\",\"cond_code_n\":\"101\",\"cond_txt_d\":\"阴\",\"cond_txt_n\":\"多云\",\"date\":\"2018-10-12\",\"hum\":\"69\",\"mr\":\"09:02\",\"ms\":\"20:32\",\"pcpn\":\"0.0\",\"pop\":\"7\",\"pres\":\"1020\",\"sr\":\"06:19\",\"ss\":\"18:00\",\"tmp_max\":\"27\",\"tmp_min\":\"21\",\"uv_index\":\"4\",\"vis\":\"20\",\"wind_deg\":\"-1\",\"wind_dir\":\"无持续风向\",\"wind_sc\":\"1-2\",\"wind_spd\":\"9\"}]}]}";
        System.out.println(result);
        Gson gson = new Gson();
        JsonObject object =  new JsonParser().parse(result).getAsJsonObject();
        JsonArray data = object.get("HeWeather6").getAsJsonArray();
        JsonObject dataObj = data.get(0).getAsJsonObject();
        JsonArray weathers = dataObj.get("daily_forecast").getAsJsonArray();
        List<HeWeather> weatherList = new ArrayList<>();
        if(weathers!=null&&weathers.size()>0){
            HeWeather heWeather1 = gson.fromJson(weathers.get(0).getAsJsonObject(),HeWeather.class);
            HeWeather heWeather2 = gson.fromJson(weathers.get(1).getAsJsonObject(),HeWeather.class);
            HeWeather heWeather3 = gson.fromJson(weathers.get(2).getAsJsonObject(),HeWeather.class);
            weatherList.add(heWeather1);
            weatherList.add(heWeather2);
            weatherList.add(heWeather3);
        }
    }

}