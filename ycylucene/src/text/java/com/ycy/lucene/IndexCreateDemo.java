package com.ycy.lucene;

import com.ycy.lucene.search.BasicQuery;
import com.ycy.lucene.search.RangeQuery;
import org.junit.Before;
import org.junit.Test;

public class IndexCreateDemo {
  com.ycy.lucene.index.IndexCreateDemo indexCreateDemo;
  BasicQuery basicQuery;
  RangeQuery rangeQuery;

  @Before
  public void creatIndex() {
    indexCreateDemo = new com.ycy.lucene.index.IndexCreateDemo();
    basicQuery = new BasicQuery();
    rangeQuery = new RangeQuery();
  }

  @Test
  public void create() {
    indexCreateDemo.createIndex();
  }

  @Test
  public void search() {
    basicQuery.search0(); //精确查询
    basicQuery.search1(); //短语查询
    basicQuery.search2(); //通配符
    basicQuery.search3(); //是非
  }

  @Test
  public void search1() {
    System.out.println("范围查询");
    rangeQuery.search01(); //范围查询
//    rangeQuery.search02(); //范围查询  数值类型
    rangeQuery.search03();

  }


  @Test
  public void delete() {
    System.out.println("delete");
  }


}
