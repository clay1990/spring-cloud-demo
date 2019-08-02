package com.clay.gateway;

import com.clay.gateway.filter.RateLimitByIpGateWayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @Auther: yuyao
 * @Date: 2019/6/30 11:49
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
//@ServletComponentScan
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }



    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/city-service/**")
                        .filters(f -> f.stripPrefix(1)
                                .filter(new RateLimitByIpGateWayFilter<>()))
                        .uri("lb://city-service")
                        .id("route_city_service"))
                .route(r -> r.path("/es-service/**")
                        .filters(f -> f.stripPrefix(1)
                                .filter(new RateLimitByIpGateWayFilter<>()))
                        .uri("lb://es-service")
                        .id("route_es_service")
                ).build();
    }

}