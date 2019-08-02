package com.clay.es.dto;

import lombok.Data;

/**
 * @Auther: yuyao
 * @Date: 2019/7/26 14:37
 * @Description:
 */
@Data
public class HouseSourceDto {

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