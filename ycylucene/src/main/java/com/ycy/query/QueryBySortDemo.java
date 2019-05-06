package com.ycy.query;

import com.ycy.IndexCreateDemo;
import com.ycy.IndexQueryDemo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

public class QueryBySortDemo {

  /**
   * 1 使用Sort 排序 lucene就不会打分
   *
   * @param args
   */
  public static void main(String[] args) {
//    searchBySort1();
    searchBySort2();
  }

  private static void searchBySort1() {
    try {
      String queryStr = "name:跳脱";
      Analyzer analyzer = new StandardAnalyzer();
      QueryParser queryParser = new QueryParser("name", analyzer);
      Query parse = queryParser.parse(queryStr);
      IndexQueryDemo.search(parse, 20, Sort.INDEXORDER);//docid 排序
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private static void searchBySort2() {
    try {
      String queryStr = "name:跳脱";
      Analyzer analyzer = new StandardAnalyzer();
      QueryParser queryParser = new QueryParser("name", analyzer);
      Query parse = queryParser.parse(queryStr);
      IndexQueryDemo.search(parse, 5, new Sort(new SortField("id", SortField.Type.INT)));//按照id排序
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}
