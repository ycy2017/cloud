package com.cn.ycy.service;

import com.cn.ycy.entity.Product;

import java.util.List;

public interface ProductService {

  public List<Product> get(List<Long> list);

}
