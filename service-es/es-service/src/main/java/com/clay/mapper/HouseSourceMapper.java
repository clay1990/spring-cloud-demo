package com.clay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clay.model.House_source;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/26 17:42
 * @Description:
 */
public interface HouseSourceMapper extends BaseMapper<House_source> {


    @Select("select * from house_source where id > #{start} and id <= #{end}")
    List<House_source> queryByLimit(@Param("start") Integer start,@Param("end") Integer end);

}