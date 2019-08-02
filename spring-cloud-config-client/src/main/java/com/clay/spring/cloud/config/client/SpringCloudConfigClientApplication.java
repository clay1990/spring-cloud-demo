package com.clay.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yuyao
 * @Date: 2019/6/25 15:14
 * @Description:
 */
@SpringBootApplication
@RestController
@RefreshScope
public class SpringCloudConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class,args);
    }

    @Value("${redis.host}")
    String redisHost;
    @Value("${mongo.host}")
    String mongoHost;


    @GetMapping("/getConfig")
    public String getConfig(){
        return "mongoHost:" + mongoHost + ",reidsHost:" + redisHost;
    }
}