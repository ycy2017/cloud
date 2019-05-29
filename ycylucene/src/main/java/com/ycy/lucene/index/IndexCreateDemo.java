package com.ycy.lucene.index;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ycy.entity.ShopBean;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.nlpcn.commons.lang.finger.SimHashService;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IndexCreateDemo {

  static String FIELD1 = "filename";

  static String FIELD2 = "context";

  public static String srcpath = "C:\\Users\\Administrator\\Desktop\\conf\\luceneindex\\json.txt";

  public static String indexpath = "C:\\Users\\Administrator\\Desktop\\conf\\luceneindex\\ix";

  Directory directory;

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

  public IndexCreateDemo() {
  }


  public static void main(String[] args) {
    Path path = Paths.get(indexpath);
    Directory directory = null;
    try {
      directory = FSDirectory.open(path);
      IndexCreateDemo indexCreateDemo = new IndexCreateDemo();

      indexCreateDemo.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void before() {

  }

  @Test
  public void deleteTest() {
    System.out.println("deleteTest");
  }


  public void createIndex() {

    try {
      System.out.println("start create index ... ");
      Path path = Paths.get(indexpath);
      try {
        directory = FSDirectory.open(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
      Analyzer standardAnalyzer = new StandardAnalyzer();
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
      IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
      indexWriter.deleteAll();
      indexWriter.commit();

      JsonFactory jsonFactory = new JsonFactory();
      jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);
      ObjectMapper mapper = new ObjectMapper(jsonFactory);
      BufferedReader br = new BufferedReader(new InputStreamReader(
              IndexCreateDemo.class.getClassLoader().getResourceAsStream("shop.json")));
      String line;
      File file = new File("C:\\Users\\Administrator\\Desktop\\conf\\luceneindex\\shop.json");
      List<String> shopEntityStr = Lists.newArrayList();
      int count = 0;
      while ((line = br.readLine()) != null) {
        count++;
        ShopBean shopBean = mapper.readValue(line, ShopBean.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse("20190505"));
        calendar.set(Calendar.MONTH, -count);
        shopBean.openDate = simpleDateFormat.format(calendar.getTime());
        shopBean.shape = null;
        shopEntityStr.add(JSONObject.toJSONString(shopBean));
        //这里的x,y即经纬度，x为Longitude(经度),y为Latitude(纬度)
        Document document = newSampleDocument(shopBean);

        //加权
//        document.
        indexWriter.addDocument(document);
      }
      FileUtils.writeLines(file, Lists.newArrayList(shopEntityStr), false);
      indexWriter.commit();
      indexWriter.close();
      System.out.println("end create index ... ");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private static Document newSampleDocument(ShopBean shopBean) {
    Document doc = new Document();
    doc.add(new NumericDocValuesField("id",shopBean.getId()));

    doc.add(new TextField("name", shopBean.getName(), Field.Store.YES));
    try {
      //添加数值类型的索引,但是不做store
//      doc.add(new NumericDocValuesField("opendate", simpleDateFormat.parse(shopBean.openDate).getTime()));
      //添加索引,做store
      doc.add(new StoredField("opendate", simpleDateFormat.parse(shopBean.openDate).getTime()));
    } catch (ParseException e) {
      e.printStackTrace();
    }
//    doc.add(new TextField("shape", shapeEnclosure, Field.Store.YES));
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

  public static String getContent(String bsolutePath) {
    String s = null;

    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(bsolutePath))));
      s = bufferedReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return s;
  }

  /**
   * 删除
   */
  public void delete() {

  }

  /**
   * 删除
   */
  public void deleteAll() {

  }

  /**
   * 恢复
   */
  public void undelete() {

  }

  /**
   * 没有更新,  实质上是删除之后在新增
   */
  public void update() {

  }

}
