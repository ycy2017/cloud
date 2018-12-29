package com.ycy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexQueryDemo {


  public static void main(String[] args) {

    //写个测试
    System.out.println("start ...");
    try {
      Path path = Paths.get(IndexCreateDemo.indexpath);
      Directory directory = new SimpleFSDirectory(path);
      IndexReader indexReader = DirectoryReader.open(directory);
      IndexSearcher isearch = new IndexSearcher(indexReader);

      try {
//    这个方法查不到
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser(IndexCreateDemo.filed1, analyzer);
//        QueryParser parser = new QueryParser(IndexCreateDemo.filed1, analyzer);
        Query query = parser.parse("txt");
//    这个方法查不到
//        Term term=new Term(IndexCreateDemo.filed1, "txt");
//        Query query=new TermQuery(term);

        TopDocs search = isearch.search(query, 10);
//        TopDocs search = isearch.doc( );
        System.out.println("共检索出 " + search.totalHits + " 条记录");
        ScoreDoc[] hits = search.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
          Document hitDoc = isearch.doc(hits[i].doc);
          System.out.println(hitDoc);
        }



      } catch (Exception e) {
        e.printStackTrace();
      }

      System.out.println("end ...");

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
