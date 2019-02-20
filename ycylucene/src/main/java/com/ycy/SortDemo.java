package com.ycy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自定义排序
 */
public class SortDemo {

  @Test
  public void doTest() {
    try {
      Analyzer analyzer = new AnsjAnalyzer(AnsjAnalyzer.TYPE.index);
      PerFieldAnalyzerWrapper perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(analyzer);
      Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\conf\\luceneindex2");
      Directory directory = FSDirectory.open(path);
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
      IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
      writer.deleteAll();
      writer.commit();
      Document doc = new Document();

      doc.add(new TextField("placeName", "杭州西湖 测试开发", Field.Store.YES));
      doc.add(new StringField("placeName2", "杭州西湖 测试开发", Field.Store.YES));
      writer.addDocument(doc);
      doc  = new Document();
      doc.add(new TextField("placeName", "西湖 九天", Field.Store.YES));
      doc.add(new StringField("placeName2", "西湖 九天", Field.Store.YES));
      writer.addDocument(doc);
      doc  = new Document();
      doc.add(new TextField("placeName", "扬州廋西湖 高级开发", Field.Store.YES));
      doc.add(new StringField("placeName2", "扬州廋西湖 高级开发", Field.Store.YES));
      writer.addDocument(doc);
      doc  = new Document();
      doc.add(new TextField("placeName", "苏州西湖", Field.Store.YES));
      doc.add(new StringField("placeName2", "苏州西湖", Field.Store.YES));
      writer.addDocument(doc);

      writer.close();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
