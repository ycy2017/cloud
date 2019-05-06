package com.cn.ycy.cache;

import com.google.common.collect.Maps;
import com.cn.ycy.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class ArticleCache {

  private static final Logger logger = LoggerFactory.getLogger(ArticleCache.class);

  private Map<Long, Product> map = Maps.newHashMap();

  public ArticleCache() {
    load();
  }

  private static ArticleCache ArticleCache = new ArticleCache();

  public static ArticleCache getInstance(){
    return ArticleCache;
  }

  public Product get(Long id){
    return map.get(id);
  }

  public void load() {
    logger.info("start ...");
    for (int i = 1; i <= 10000000; i++) {
      Product product = new Product();
      Long id = Long.valueOf(i);
      product.id = id;
      product.isDelete = (i % 2 == 0);
      product.price = Float.valueOf(i);
      map.put(id, product);
    }
    logger.info("end !!!");
  }

}
