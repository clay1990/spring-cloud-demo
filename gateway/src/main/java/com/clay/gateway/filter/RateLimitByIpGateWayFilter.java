package com.clay.gateway.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: yuyao
 * @Date: 2019/7/31 15:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitByIpGateWayFilter<补充量> implements GatewayFilter, Ordered {

    //桶的最大容量，即能装载 Token 的最大数量
    int capacity = 5;
    //每次 Token 补充量
    int refillTokens = 1;
    //补充 Token 的时间间隔
    Duration refillDuration = Duration.ofSeconds(1);

    private static final Map<String,Bucket> CACHE = new ConcurrentHashMap<>();

    private Bucket createNewBucket(){
        Refill of = Refill.of(refillTokens, refillDuration);
        Bandwidth classic = Bandwidth.classic(capacity, of);
        return Bucket4j.builder().addLimit(classic).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        Bucket bucket = CACHE.computeIfAbsent(hostAddress, k -> createNewBucket());
        if(bucket.tryConsume(1)){
            return chain.filter(exchange);
        }

        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}