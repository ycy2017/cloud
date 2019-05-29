package com.ycy.lucene.search;

import com.ycy.lucene.index.IndexReadDemo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

public class BasicQuery {

  /**
   * 普通查询
   */
  public static void search0() {
    try {
      String queryStr = "name:\"MC\"";
      Analyzer analyzer = new StandardAnalyzer();
      QueryParser queryParser = new QueryParser(null, analyzer);
      Query parse = queryParser.parse(queryStr);
      IndexReadDemo.search(parse, 20, Sort.RELEVANCE);//
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 段落查询
   */
  public static void search1() {
    try {
//      String queryStr = "name:\"MC 店\"~100";
//      Analyzer analyzer = new StandardAnalyzer();
//      QueryParser queryParser = new QueryParser(IndexCreateDemo.FIELD1, analyzer);
//      Query parse = queryParser.parse(queryStr);
//      search(parse);

      PhraseQuery.Builder builder = new PhraseQuery.Builder();
      //查询filed 声明的顺序,会影响结果吗?
      builder.add(new Term("name", "脱"));
      builder.add(new Term("name", "密"));
      //默认情况下slop 为0, 所以不设置slop时,只能查到hello word
      builder.setSlop(100);
      PhraseQuery phraseQuery = builder.build();
      IndexReadDemo.search(phraseQuery, 10, Sort.RELEVANCE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 通配符
   */
  public static void search2() {
    try {
      /*
       *  hello*: 零个或者多个
       *  hello?: 一个
       */
      String queryStr = "name:hel*o";
      Analyzer analyzer = new SimpleAnalyzer();
      QueryParser queryParser = new QueryParser(null, analyzer);
      Query parse = queryParser.parse(queryStr);
      IndexReadDemo.search(parse, 2, null);

//      BooleanQuery.Builder builder = new BooleanQuery.Builder();
//      builder.add(new TermQuery(new Term("context", "hello")), BooleanClause.Occur.MUST);
//      builder.add(new TermQuery(new Term("context", "word")), BooleanClause.Occur.MUST_NOT);
//      BooleanQuery build = builder.build();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 是 或 非 查询
   */
  public static void search3() {
    try {
/*    String queryStr = "+filename:hello +filename:word\"";
    Analyzer analyzer = new SimpleAnalyzer();
    QueryParser queryParser = new QueryParser(IndexCreateDemo.FIELD1, analyzer);
    Query parse = queryParser.parse(queryStr);*/

      BooleanQuery.Builder builder = new BooleanQuery.Builder();
      builder.add(new TermQuery(new Term("context", "hello")), BooleanClause.Occur.MUST);
      builder.add(new TermQuery(new Term("context", "word")), BooleanClause.Occur.MUST_NOT);
      BooleanQuery build = builder.build();

      IndexReadDemo.search(build, 3, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 自定义 querypharse
   */
  public static void search4() {

//    QueryParser queryParser  = new QueryParser(){
//      @Override
//      protected Query getFieldQuery(String field, String queryText, boolean quoted) throws ParseException {
//        return super.getFieldQuery(field, queryText, quoted);
//      }
//    };
//
  }


}
