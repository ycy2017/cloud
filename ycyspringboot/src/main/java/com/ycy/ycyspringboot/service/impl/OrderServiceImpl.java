package com.ycy.ycyspringboot.service.impl;

import com.ycy.ycyspringboot.service.OrderService;
import org.springframework.stereotype.Service;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

  public OrderServiceImpl() {
    System.out.println("OrderServiceImpl  new了一个对象");
  }

  @Override
  public String findOrder() {
    return "无订单";
  }

}
