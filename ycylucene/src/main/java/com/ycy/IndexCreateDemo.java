package com.ycy;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycy.entity.ShopBean;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexCreateDemo {

  static String FIELD1 = "filename";

  static String FIELD2 = "context";

  static String srcpath = "C:\\Users\\Administrator\\Desktop\\conf\\luceneindex\\json.txt";

  static String indexpath = "C:\\Users\\Administrator\\Desktop\\conf\\luceneindex\\ix";

  public static void main(String[] args) {

    try {
      System.out.println("start create index ... ");

      Path path = Paths.get(indexpath);
      Directory directory = FSDirectory.open(path);
      Analyzer standardAnalyzer = new StandardAnalyzer();
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
      IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
      indexWriter.deleteAll();
      indexWriter.commit();
 /*      File file = new File(srcpath);
     for (File listFile : file.listFiles()) {
        if (listFile.isFile() && listFile.getName().endsWith("txt")) {
          String absolutePath = listFile.getAbsolutePath();
          System.out.println(absolutePath+" "+  getContent(absolutePath));
          Document document = new Document();

          //复杂玩法
//          FieldType fieldType = new FieldType();
//          fieldType.setStored(true);
//          fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
//          fieldType.setTokenized(true);//是否分词
//          IndexableField indexableField1 = new Field(FIELD1, absolutePath,fieldType);
//
//          FieldType fieldType2 = new FieldType();
//          fieldType2.setStored(true);
//          fieldType2.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
//          fieldType2.setTokenized(true);//是否分词
//          IndexableField indexableField2 = new Field(FIELD1,  getContent(absolutePath),fieldType2);

          //一般玩法
//          Field indexableField1 = new Field(FIELD1, absolutePath, TextField.TYPE_STORED);
//          Field indexableField2 = new Field(FIELD2, getContent(absolutePath), TextField.TYPE_STORED);

          //简单玩法
          //TextField 和 StringField 的区别???  TextField分词
          IndexableField indexableField1 = new TextField(FIELD1, absolutePath, Field.Store.YES);
          IndexableField indexableField2 = new TextField(FIELD2,  getContent(absolutePath),Field.Store.YES);

//          IndexableField indexableField1 = new StringField(FIELD1, absolutePath, Field.Store.YES);
//          IndexableField indexableField2 = new StringField(FIELD1,  getContent(absolutePath),Field.Store.YES);

          document.add(indexableField1);
          document.add(indexableField2);
          indexWriter.addDocument(document);
        }
      }*/

      JsonFactory jsonFactory = new JsonFactory();
      jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);
      ObjectMapper mapper = new ObjectMapper(jsonFactory);
      BufferedReader br = new BufferedReader(new InputStreamReader(
              IndexCreateDemo.class.getClassLoader().getResourceAsStream("shop.json")));
      String line;
      while ((line = br.readLine()) != null) {
        ShopBean shopBean = mapper.readValue(line, ShopBean.class);

        //这里的x,y即经纬度，x为Longitude(经度),y为Latitude(纬度)
        Document document = newSampleDocument(shopBean.getId(), shopBean.getName(),shopBean.getShape() ,null);
        indexWriter.addDocument(document);
      }


      indexWriter.commit();
      indexWriter.close();
      System.out.println("end create index ... ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Document newSampleDocument(int id, String title, String shapeEnclosure ,Shape... shapes) {
    Document doc = new Document();
    doc.add(new StoredField("id", id));
    doc.add(new NumericDocValuesField("id", id));
    doc.add(new TextField("name", title, Field.Store.YES));
    doc.add(new TextField("shape", shapeEnclosure, Field.Store.YES));


    // Potentially more than one shape in this field is supported by some
    // strategies; see the javadocs of the SpatialStrategy impl to see.
   /* for (Shape shape : shapes) {
      for (Field f : strategy.createIndexableFields(shape)) {
        doc.add(f);
      }
      // store it too; the format is up to you
      // (assume point in this example)
      Point pt = (Point) shape;
      doc.add(new StoredField(strategy.getFieldName(), pt.getX() + " " + pt.getY()));
    }
*/
    return doc;
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
