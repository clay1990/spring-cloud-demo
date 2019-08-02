package com.clay.controller;

import com.clay.es.client.EsServiceClient;
import com.clay.es.dto.HouseSourceDto;
import com.clay.mapper.HouseSourceMapper;
import com.clay.model.House_source;
import com.clay.search.impl.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/26 15:13
 * @Description:
 */
@RestController
public class SearchController implements EsServiceClient {

    @Autowired
    ISearchService searchService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    HouseSourceMapper houseSourceMapper;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/search")
    public List<HouseSourceDto> search(@RequestBody House_source house_source){
        return searchService.queryByCondition(house_source);
    }

    @GetMapping("/search/keyword")
    public List<HouseSourceDto> searchByKeyWord(String keyWord){
        return searchService.queryByKeyWord(keyWord);
    }


    /**
     * 初始化数据
     * @return
     */
    @GetMapping("/house/init")
    public Integer init(){
        Integer integer = houseSourceMapper.selectCount(null);
        int step = 1000;
        for (Integer i = 0; i < integer/step +1; i++) {
            List<House_source> house_sources = houseSourceMapper.queryByLimit(i * step,step * (i + 1));
            for (House_source house_source : house_sources) {
                searchService.save(house_source);
            }
            System.out.println("=================第" + (i+1) + "页处理完成==============");
        }
        return integer;
    }


    @GetMapping("/house/suggest")
    public List<String> suggest(String prefix){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            System.out.println(name+"=" + request.getHeader(name));
        }
        System.out.println("========================>>>>>   服务调用进来");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return searchService.sugget(prefix);
    }







}