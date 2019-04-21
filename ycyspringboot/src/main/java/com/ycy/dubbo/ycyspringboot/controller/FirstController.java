package com.ycy.dubbo.ycyspringboot.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ycy.dubbo.ycyspringboot.entity.Product;
import com.ycy.dubbo.ycyspringboot.entity.ProductPar;
import com.ycy.dubbo.ycyspringboot.service.impl.OrderServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class FirstController {

  @Resource
  OrderServiceImpl orderService;

  @RequestMapping("/test")
  @ResponseBody
  public String firstReturn() {
    return "hello word!!!";
  }

  @RequestMapping(value = "/getProductById", method = RequestMethod.POST
          ,consumes = "application/json;charset=UTF-8",
          produces = "application/json;charset=UTF-8"
  )
  @ResponseBody
  public List<Product> getProductById(@RequestBody List<ProductPar> productPars) {
    Map<String, Set<Long>> idsByTypeMap = Maps.newHashMap();
    for (ProductPar productPar : productPars) {
      String type = productPar.type;
      Long id = productPar.id;
      if (type != null) {
        Set<Long> ids = idsByTypeMap.containsKey(id) ? idsByTypeMap.get(id) : Sets.newHashSet();
        ids.add(id);
        idsByTypeMap.put(type, ids);
      }
    }

    List<Product> productList = Lists.newArrayList();
    for (Map.Entry<String, Set<Long>> longSetEntry : idsByTypeMap.entrySet()) {
      //TODO 做成异步
      List<Product> products = orderService.get(longSetEntry.getKey(), Lists.newLinkedList(longSetEntry.getValue()));
      productList.addAll(products);
    }
    return productList;
  }

}
