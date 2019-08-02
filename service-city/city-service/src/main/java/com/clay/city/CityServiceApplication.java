package com.clay.city;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yuyao
 * @Date: 2019/6/20 21:15
 * @Description:
 */
@EnableDiscoveryClient
@EnableFeignClients("com.clay.es.client")
@SpringBootApplication
@EnableCircuitBreaker
@RestController
@ComponentScan({"com.clay.city","com.clay.es.client"})
public class CityServiceApplication {
    public static void main(String[] args) {
        System.out.println("启动服务xxxxxxxxxxxx12345678911111");
        SpringApplication.run(CityServiceApplication.class,args);
    }

//    @Bean
//    public Retryer retryer() { return Retryer.NEVER_RETRY; }

//    @Autowired
//    RestTemplate restTemplate;
//
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }



//    @Bean
//    public EnvironmentRepository environmentRepository(){
//        return new EnvironmentRepository() {
//            @Override
//            public Environment findOne(String application, String profile, String label) {
//                System.out.println(application+"----------"+profile+"-------"+label);
//                Environment environment = new Environment("config-server",profile);
//                List<PropertySource> propertySources = environment.getPropertySources();
//                Map<String,String> map = new HashMap<>();
//                map.put("redis.host","127.0.0.1");
//                map.put("redis.port","6379");
//                PropertySource propertySource = new PropertySource("redisConfig",map);
//                propertySources.add(propertySource);
//
//                Map<String,String> mongoMap = new HashMap<>();
//                mongoMap.put("mongo.host","127.0.0.1");
//                mongoMap.put("mongo.port","10040");
//                PropertySource mongoPropertySource = new PropertySource("mongoConfig",mongoMap);
//                propertySources.add(mongoPropertySource);
//                return environment;
//            }
//        };
//    }

}