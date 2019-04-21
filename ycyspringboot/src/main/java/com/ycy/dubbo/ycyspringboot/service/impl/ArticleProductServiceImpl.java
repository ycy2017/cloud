package com.ycy.dubbo.ycyspringboot.service.impl;

import com.google.common.collect.Lists;
import com.ycy.dubbo.ycyspringboot.cache.ArticleCache;
import com.ycy.dubbo.ycyspringboot.entity.Product;
import com.ycy.dubbo.ycyspringboot.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleProductServiceImpl  implements ProductService {
  @Override
  public List<Product> get(List<Long> list) {
    List<Product> products = Lists.newArrayList();
    ArticleCache hotelCache = ArticleCache.getInstance();
    for (Long aLong : list) {
      Product product = hotelCache.get(aLong);
      if(product!=null){
        products.add(product);
      }
    }
    return products;
  }
}
