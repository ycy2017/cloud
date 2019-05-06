package com.cn.ycy.cache;

import com.google.common.collect.Maps;
import com.cn.ycy.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class HotelCache {

  private static final Logger logger = LoggerFactory.getLogger(ArticleCache.class);

  private Map<Long, Product> map = Maps.newHashMap();

  private HotelCache() {
    load();
  }

  private static HotelCache demoCache = new HotelCache();

  public static HotelCache getInstance() {
    return demoCache;
  }

  public Product get(Long id) {
    return map.get(id);
  }

  public void load() {
    logger.info("start ...");
    for (int i = 1; i <= 1000000; i++) {
      Product product = new Product();
      Long id = Long.valueOf(i);
      product.id = id;
      product.isDelete = (i % 2 == 0);
      product.price = Float.valueOf(i);
      map.put(id, product);
    }
    logger.info("end !!!");
  }

  public static void main(String[] args) {
    HotelCache hotelCache = HotelCache.getInstance();
    hotelCache.load();
    for (Map.Entry<Long, Product> longProductEntry : hotelCache.map.entrySet()) {
      Product product = hotelCache.get(longProductEntry.getKey());
      System.out.println(product);
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }



}
