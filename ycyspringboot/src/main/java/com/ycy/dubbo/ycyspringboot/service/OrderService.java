package com.ycy.dubbo.ycyspringboot.service;

import com.ycy.dubbo.ycyspringboot.entity.Product;

import java.util.List;

public interface OrderService {

  String findOrder();

  public List<Product> get(String Type,List<Long> list);

}
