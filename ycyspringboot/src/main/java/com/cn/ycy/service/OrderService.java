package com.cn.ycy.service;

import com.cn.ycy.entity.Product;

import java.util.List;

public interface OrderService {

  String findOrder();

  public List<Product> get(String Type, List<Long> list);

}
