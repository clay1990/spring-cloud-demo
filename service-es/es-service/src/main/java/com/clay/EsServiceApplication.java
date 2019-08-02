package com.clay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: yuyao
 * @Date: 2019/7/25 16:41
 * @Description:
 */
@MapperScan("com.clay.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class EsServiceApplication {

    public static void main(String[] args) {
        System.out.println("有重新编译吗");
        SpringApplication.run(EsServiceApplication.class,args);
    }
}