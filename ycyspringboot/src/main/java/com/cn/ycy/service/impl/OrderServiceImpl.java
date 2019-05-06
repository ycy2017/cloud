package com.cn.ycy.service.impl;

import com.cn.ycy.entity.DataType;
import com.cn.ycy.entity.Product;
import com.cn.ycy.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

  Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

  @Resource
  ArticleProductServiceImpl articleProductService;

  @Resource
  HotelProductServiceImpl hotelProductService;

  public OrderServiceImpl() {
    logger.info("new了一个对象");
  }

  @Override
  public String findOrder() {
    return "无订单";
  }

  @Override
  public List<Product> get(String Type, List<Long> list) {
    if(StringUtils.isBlank(Type)) return null;
    DataType dataType = DataType.valueOf(Type.toUpperCase());
    List<Product> products = null;
    switch (dataType) {
      case HOTEL:
        products = hotelProductService.get(list);
        break;
      case ARTICLE:
        products = articleProductService.get(list);
        break;
    }
    return products;
  }


}
