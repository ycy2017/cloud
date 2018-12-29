package com.ycy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexCreateDemo {

  static String filed1 = "filename";

  static String key2 = "context";

  static String srcpath = "C:\\Users\\Administrator\\Desktop\\conf\\luceneindex";
  static String indexpath = "C:\\Users\\Administrator\\Desktop\\conf\\luceneindex\\ix";

  public static void main(String[] args) {

    File file = new File(srcpath);

    Path path = Paths.get(indexpath);
    try {
      Directory directory = new SimpleFSDirectory(path);
      Analyzer standardAnalyzer = new StandardAnalyzer();
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
      IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

      for (File listFile : file.listFiles()) {
        if (listFile.isFile() && listFile.getName().endsWith("txt")) {
          String absolutePath = listFile.getAbsolutePath();
          System.out.println(absolutePath);
          Document document = new Document();

//          String text = "This is the text to be indexed.";
//          document.add(new Field(filed1, text, TextField.TYPE_STORED));

//          Field filenamefield = new StringField(filed1, absolutePath, TextField.TYPE_STORED);
//          Field filepathfield = new StringField(key2, absolutePath, TextField.TYPE_STORED);
          //Field 和 StringField 的区别???
          Field filenamefield = new Field(filed1, absolutePath, TextField.TYPE_STORED);
          Field filepathfield = new Field(key2, getContent(absolutePath), TextField.TYPE_STORED);

          document.add(filenamefield);
          document.add(filepathfield);
          indexWriter.addDocument(document);
        }

      }
      indexWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public static String getContent(String bsolutePath){
    String s = null;

    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(bsolutePath))));
      s = bufferedReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return s;
  }
}
