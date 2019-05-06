package com.cn.ycy.service.impl;

import com.google.common.collect.Lists;
import com.cn.ycy.cache.HotelCache;
import com.cn.ycy.entity.Product;
import com.cn.ycy.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelProductServiceImpl implements ProductService {

  @Override
  public List<Product> get(List<Long> list) {
    List<Product> products = Lists.newArrayList();
    HotelCache hotelCache = HotelCache.getInstance();
    for (Long aLong : list) {
      Product product = hotelCache.get(aLong);
      if(product!=null){
        products.add(product);
      }
    }
    return products;
  }



}
