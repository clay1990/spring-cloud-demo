package com.clay.es.param;

import lombok.Data;

/**
 * @Auther: yuyao
 * @Date: 2019/7/30 12:44
 * @Description:
 */
@Data
public class HouseSourceParam {

    private Integer id;
    private String province;
    private String city;
    private String village;
    private String region;
    private String region1;
    private String src;
    private String area;
    private String orientations;
    private String apartment;
    private String tags;
    private Integer min_price;
    private Integer max_price;
    private String publish_man;
    private Integer publish_time;
    private String room_left;
}