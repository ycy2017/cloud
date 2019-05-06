package com.ycy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexQueryDemo {


  public static void main(String[] args) {

    search0();
    search1();
//    search2();
//    search3();
  }


  /**
   * 普通查询
   */
  public static void search0() {
    try {
      String queryStr = "name:跳脱";
      Analyzer analyzer = new StandardAnalyzer();
      QueryParser queryParser = new QueryParser(IndexCreateDemo.FIELD1, analyzer);
      Query parse = queryParser.parse(queryStr);
      search(parse,20,Sort.RELEVANCE);//
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 段落查询
   */
  public static void search1() {
    try {
//      String queryStr = "context:\"hello word\"";
//      Analyzer analyzer = new StandardAnalyzer();
//      QueryParser queryParser = new QueryParser(IndexCreateDemo.FIELD1, analyzer);
//      Query parse = queryParser.parse(queryStr);
//      search(parse);

      PhraseQuery.Builder builder = new PhraseQuery.Builder();
      builder.add(new Term("context", "hello"));
      builder.add(new Term("context", "word"));
      //默认情况下slop 为0, 所以只能查到hello word
      builder.setSlop(100                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           );
      PhraseQuery build = builder.build();
      search(build, 1,null);
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
      String queryStr = "context:hel*o";
      Analyzer analyzer = new SimpleAnalyzer();
      QueryParser queryParser = new QueryParser(IndexCreateDemo.FIELD1, analyzer);
      Query parse = queryParser.parse(queryStr);
      search(parse, 2,null);

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

      search(build, 3,null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void search(Query query, int j,Sort sort) {
    try {
      Path path = Paths.get(IndexCreateDemo.indexpath);
      IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
      IndexSearcher isearch = new IndexSearcher(indexReader);

      TopDocs search = isearch.search(query, j,sort);
//      TopDocs search = isearch.search(query, j);
//        TopDocs search = isearch.doc( );
      System.out.println("共检索出 " + search.totalHits + " 条记录");
      for (ScoreDoc scoreDoc : search.scoreDocs) {
        Document doc = isearch.doc(scoreDoc.doc);
        System.out.println("docid " + scoreDoc.doc + " score:" + scoreDoc.score + " " + doc);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
