package com.ycy.dubbo.ycyspringboot.service.impl;

import com.ycy.dubbo.ycyspringboot.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

  Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

  public OrderServiceImpl() {
    logger.info("OrderServiceImpl  new了一个对象");
  }

  @Override
  public String findOrder() {
    return "无订单";
  }

}
