package com.ycy.lucene.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexReadDemo {

  public static void search(Query query, int j, Sort sort) {
    IndexReader indexReader = null;
    try {
      Path path = Paths.get(IndexCreateDemo.indexpath);
      indexReader = DirectoryReader.open(FSDirectory.open(path));
      IndexSearcher isearch = new IndexSearcher(indexReader);
      TopDocs search = null;
      if (sort == null) {
        search = isearch.search(query, j);
      } else {
        search = isearch.search(query, j, sort);
      }
//      TopDocs search = isearch.search(lucene, j);
//        TopDocs search = isearch.doc( );
      for (ScoreDoc scoreDoc : search.scoreDocs) {
        Document doc = isearch.doc(scoreDoc.doc);
        String res = String.format("docid--> %s scoreDoc--> %s id--> %s name--> %s opendate--> %s ",
                scoreDoc.doc, scoreDoc.score, doc.get("id"), doc.get("name"), doc.get("opendate"));
        System.out.println(res);
      }
      System.out.println("共检索出 " + search.totalHits + " 条记录");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        indexReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }


  public static void search(Query query) {
    try {
      Path path = Paths.get(IndexCreateDemo.indexpath);
      IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
      IndexSearcher isearch = new IndexSearcher(indexReader);

      TopDocs search = isearch.search(query, 10);

      for (ScoreDoc scoreDoc : search.scoreDocs) {
        Document doc = isearch.doc(scoreDoc.doc);
        System.out.println("docid " + scoreDoc.doc + " score:" + scoreDoc.score + " " + doc);
//        System.out.println(Arrays.toString(doc.getValues("name")));
      }
      System.out.println("共检索出 " + search.totalHits + " 条记录");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
