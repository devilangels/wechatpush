package com.study.wechatpush;

import com.study.wechatpush.config.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;

/**
 * @ClassName WechatpushApplication
 * @Author: Li ChengGang
 * @DateTime: 2022/9/20 17:13
 * @Description: TODO
 * @Version 1.0
 */
@SpringBootApplication
@EnableScheduling
public class WechatpushApplication {

    public static void main(String[] args) {
//        SpringApplication.run(WechatpushApplication.class, args);
        SpringApplication app = new SpringApplication(WechatpushApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "7777"));
        app.run(args);
    }

    @Scheduled(cron = "0 0 7,13,19 * * ?")
    public void goodMorning(){
        Pusher.push();
    }
}
