package com.clay.city.service;

import com.clay.es.client.EsServiceClient;
import com.clay.es.dto.HouseSourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/30 14:09
 * @Description:
 */
@Service
public class CityServiceImpl {

    @Autowired
    EsServiceClient esServiceClient;

    public List<String> getHouseSourceSuggest(String prefix){
        return esServiceClient.suggest(prefix);
    }


    public List<HouseSourceDto> searchHouseSourceByKeyWord(String keyWord){
        return esServiceClient.searchByKeyWord(keyWord);
    }


}