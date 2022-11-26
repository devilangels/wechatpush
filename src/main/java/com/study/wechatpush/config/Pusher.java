package com.study.wechatpush.config;

import com.study.wechatpush.entity.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * @ClassName Pusher
 * @Author: Li ChengGang
 * @DateTime: 2022/9/20 17:08
 * @Description: TODO
 * @Version 1.0
 */
public class Pusher {

    public static void main(String[] args) {
        push();
    }
    private static String appId = "wx3e4e67f3513316f7";
    private static String secret = "33ac60e29d57ec249d19fe67a6d56b6f";



    public static void push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("o1GLm5g020P4ixa4G3_p0DpZ_Whs")
                .templateId("DZf4frj70j8joai4bQRgQVksdR34HQPAGEF27FtylPc")
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();
        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        templateMessage.addData(new WxMpTemplateData("riqi"," " + weather.getDate() + "  "+ weather.getWeek() + "\n\n","#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi",weather.getText_now() + "\n","#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "度\n","#173177"));
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "度\n","#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "度\n","#FF6347" ));
        templateMessage.addData(new WxMpTemplateData("winddir",weather.getWind_dir()+ "\n","#B95EA3" ));
        templateMessage.addData(new WxMpTemplateData("windclass",weather.getWind_class()+ "\n","#42B857" ));
        templateMessage.addData(new WxMpTemplateData("caihongpi",CaiHongPiUtils.getCaiHongPi()+ "\n\n","#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai","第" + JiNianRiUtils.getLianAi() + "天\n","#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri",JiNianRiUtils.getBirthday_Su() + "天\n","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("shengri2",JiNianRiUtils.getBirthday_Li()+"天\n","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("en",map.get("en") +"\n","#C71585"));
        templateMessage.addData(new WxMpTemplateData("zh",map.get("zh") +"","#C71585"));
        String beizhu = "❤嘶唔苏❤";
        if(JiNianRiUtils.getLianAi() % 365 == 0){
            beizhu = "今天是相识" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if(JiNianRiUtils.getBirthday_Su()  == 0){
            beizhu = "今天是嘶唔苏的生日，生日快乐呀！";
        }
//        if(JiNianRiUtils.getBirthday_Li()  == 0){
//            beizhu = "今天是李承罡生日，生日快乐呀！";
//        }
        templateMessage.addData(new WxMpTemplateData("beizhu"," " + beizhu + "\n\n","#FF0000"));

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        int year = c.get(Calendar.YEAR) + 1;
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String springFestival = String.valueOf(CalendarUtils.getSpring_festival());
        if (CalendarUtils.getSpring_festival() == 0) {
            springFestival = "今天是" + year  + "." + month  + "."  + day + "大年初一，祝嘶唔苏新年快乐";
        }
        templateMessage.addData(new WxMpTemplateData("year", "" + year, "#FF3838"));
        templateMessage.addData(new WxMpTemplateData("festival", springFestival + "天\n\n ", "#FF3838"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
