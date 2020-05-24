package com.example.supermarket.lh.service;

import com.alibaba.fastjson.JSON;
import com.example.supermarket.lh.mapper.ProviderCommodityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ProviderCommodityService {

    @Resource
    public ProviderCommodityMapper providerCommodityMapper;

    //查询商家拥有的商品
    public String getProviderCommodityByPnum(String pnum){
        return JSON.toJSONString(providerCommodityMapper.getProviderCommodityByPnum(pnum));
    };
    //根据商品号查找商品详情
    public String getComInfo(String cnum){
        return JSON.toJSONString(providerCommodityMapper.getComInfo(cnum));
    };
}
