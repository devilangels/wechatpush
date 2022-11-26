package com.study.wechatpush.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.wechatpush.entity.Weather;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WeatherUtils
 * @Author: Li ChengGang
 * @DateTime: 2022/9/20 17:09
 * @Description: TODO
 * @Version 1.0
 */
public class WeatherUtils {

    public static void main(String[] args) {
        System.out.println(getWeather());
    }

    public static Weather getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<String,String>();
        map.put("district_id","370600"); // 烟台行政代码
        map.put("data_type","all");//这个是数据类型
        map.put("ak","zSAdy1l08ItjFnhLrr7gmKUVXZGyxfpd");//百度开放平台服务端
        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,
                map);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        Weather weather = weathers.get(0);
        weather.setText_now(now.getString("text"));
        weather.setTemp(now.getString("temp"));
        weather.setWind_class(now.getString("wind_class"));
        weather.setWind_dir(now.getString("wind_dir"));
        return weather;
    }
}