package com.clay.search.impl;


import com.clay.es.dto.HouseSourceDto;
import com.clay.model.House_source;

import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/25 16:37
 * @Description:
 */
public interface ISearchService {

    public void save(House_source house_source);

    public List<HouseSourceDto> queryByCondition(House_source house_source);


    public List<HouseSourceDto> queryByKeyWord(String keyWord);

    List<String> sugget(String prefix);

}