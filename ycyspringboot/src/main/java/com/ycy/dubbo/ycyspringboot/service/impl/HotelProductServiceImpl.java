package com.ycy.dubbo.ycyspringboot.service.impl;

import com.google.common.collect.Lists;
import com.ycy.dubbo.ycyspringboot.cache.HotelCache;
import com.ycy.dubbo.ycyspringboot.entity.Product;
import com.ycy.dubbo.ycyspringboot.service.ProductService;
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
