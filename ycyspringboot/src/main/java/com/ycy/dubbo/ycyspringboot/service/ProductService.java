package com.ycy.dubbo.ycyspringboot.service;

import com.ycy.dubbo.ycyspringboot.entity.Product;

import java.util.List;

public interface ProductService {

  public List<Product> get(List<Long> list);

}
