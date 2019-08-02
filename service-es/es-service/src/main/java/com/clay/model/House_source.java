package com.clay.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Auther: yuyao
 * @Date: 2019/7/26 14:37
 * @Description:
 */
@Data
@TableName("house_source")
public class House_source {

    @TableId
    private Integer id;
    private String village;
    private String province;
    private String city;
    private String region;
    private String region1;
    private String src;
    private String area;
    private String orientations;
    private String apartment;
    @TableField(value = "tags")
    private String tags;
    @TableField(exist = true,value = "min_price")
    private Integer min_price;
    @TableField(value = "max_price")
    private Integer max_price;
    @TableField(value = "publish_man")
    private String publish_man;
    @TableField(value = "publish_time")
    private Integer publish_time;
    @TableField(value = "room_left")
    private String room_left;
    @TableField(select = false)
    private List<HouseSourceSuggest> suggest;

}