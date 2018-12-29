package com.ycy;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class FileReadText {

  public static void main(String[] args) {

    /*File file1 = new File("C:\\Users\\Administrator\\Desktop\\源.txt");
    Map<String, String> map1 = read(file1);
    for (Map.Entry<String, String> stringStringEntry : map1.entrySet()) {
      System.out.println(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
    }*/
    File file2 = new File("C:\\Users\\Administrator\\Desktop\\爬虫同义词.txt");
    Map<String, String> map2 = read(file2);
    for (Map.Entry<String, String> stringStringEntry : map2.entrySet()) {
      System.out.println(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
    }
    System.out.println("===========================");
    /*Map<String, String> resMap = new LinkedHashMap<String, String>();
    Map<String, String> dupMap = new LinkedHashMap<String, String>();
    for (Map.Entry<String, String> stringStringEntry : map2.entrySet()) {
      String key = stringStringEntry.getKey();
      String value = stringStringEntry.getValue();
      if (!map1.containsKey(key)) {
        resMap.put(key, value);
      } else {
        dupMap.put(key, value);
      }
    }
    System.out.println("===========================res");
    for (Map.Entry<String, String> stringStringEntry : resMap.entrySet()) {
      System.out.println(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
    }
    System.out.println("===========================dup");
    for (Map.Entry<String, String> stringStringEntry : dupMap.entrySet()) {
      System.out.println(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
    }*/
  }

  private static Map<String, String> read(File file1) {
    Map<String, String> keyValueMap = new LinkedHashMap<String, String>();
    try {
      FileInputStream fileInputStream = new FileInputStream(file1);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

      String s = null;
      while ((s = bufferedReader.readLine()) != null) {
        String[] split = s.split(",");
        keyValueMap.put(split[0], split[1]);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return keyValueMap;
  }

}
