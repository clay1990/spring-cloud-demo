package com.clay.city.controller;

import com.clay.city.service.CityServiceImpl;
import com.clay.es.dto.HouseSourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/30 14:13
 * @Description:
 */
@RestController
public class CityController {

    @Autowired
    CityServiceImpl cityService;


//    @HystrixCommand(fallbackMethod = "getHouseSourceSuggestFallBack",
//            commandProperties= {
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),//滑动窗口的大小，默认为20
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),//过多长时间，熔断器再次检测是否开启，默认为5000，即5s钟
//                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50")//错误率，默认50%
//            }
//    )
    @GetMapping("/city/getHouseSourceSuggest")
    public List<String> getHouseSourceSuggest(String prefix){
        return cityService.getHouseSourceSuggest(prefix);
    }

    public List<String> getHouseSourceSuggestFallBack(String prefix){
        List<String> list = new ArrayList<>();
        list.add("服务异常.");
        return list;
    }


    @GetMapping("/city/searchHouseSourceByKeyWord")
    public List<HouseSourceDto> searchHouseSourceByKeyWord(String keyWord){
        return cityService.searchHouseSourceByKeyWord(keyWord);
    }

}