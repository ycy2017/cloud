package com.ctrip.search;

import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class Demo {

  private String name = "我是个DEMO";


  public static void main(String[] args) {
    Demo demo = new Demo();
    demo.loadProperties();
    System.out.println(JSON.toJSONString(demo));
  }

  private void loadProperties() {

    Properties properties = new Properties();
    try {
      String path = this.getClass().getClassLoader().getResource("datasource.properties").getPath();
      System.out.println(" " + path);

      InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("datasource.properties");
      properties.load(new InputStreamReader(resourceAsStream));
//      properties.load(new InputStreamReader(new FileInputStream(path)));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }
    String user = properties.getProperty("user");
    String pass = properties.getProperty("pass");
    System.out.println(user + " " + pass);

  }

  public void


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
