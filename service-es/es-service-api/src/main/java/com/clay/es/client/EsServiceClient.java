package com.clay.es.client;

import com.clay.es.client.fallback.EsClientFallBack;
import com.clay.es.dto.HouseSourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/30 13:48
 * @Description:
 */
@FeignClient(name =  "es-service"
        ,fallback = EsClientFallBack.class
)
public interface EsServiceClient {

    @GetMapping("/search/keyword")
    public List<HouseSourceDto> searchByKeyWord(@RequestParam("keyWord") String keyWord);


    @GetMapping("/house/suggest")
    public List<String> suggest(@RequestParam("prefix") String prefix);

}