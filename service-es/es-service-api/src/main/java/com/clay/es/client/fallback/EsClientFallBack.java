package com.clay.es.client.fallback;

import com.clay.es.client.EsServiceClient;
import com.clay.es.dto.HouseSourceDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/31 09:35
 * @Description:
 */
@Component
public class EsClientFallBack implements EsServiceClient {
    @Override
    public List<HouseSourceDto> searchByKeyWord(String keyWord) {
        List<HouseSourceDto> list = new ArrayList<>();
        return list;
    }

    @Override
    public List<String> suggest(String prefix) {
        List<String> list = new ArrayList<>();
        list.add("这是EsClientFallBack降级处理");
        return list;
    }
}