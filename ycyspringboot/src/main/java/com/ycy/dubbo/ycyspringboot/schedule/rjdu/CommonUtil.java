package com.ycy.dubbo.ycyspringboot.schedule.rjdu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author sxzhao
 */
public class CommonUtil {

  /**
   * 获得执行用时，单位秒
   *
   * @param startTime
   * @return
   */
  public static String getUseTime(long startTime) {
    return ((float) (System.currentTimeMillis() - startTime) / 1000) + "s";
  }

  /**
   * 返回的子list都是父list的映射，父list做改动会影响子list
   *
   * @param <T>
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> List<List<T>> subListByCount(List<T> list, int size) {
    List subListArray = new ArrayList();
    if (list.size() <= size) {
      subListArray.add(list);
      return subListArray;
    } else {
      int times = list.size() / size; //可以平分几次
      int last = list.size() % size;//剩余几个
      int firstIndex = 0;
      for (int i = 0; i < times; i++) {
        List<T> subList = list.subList(firstIndex, firstIndex + size);
        subListArray.add(subList);
        firstIndex += size;
      }
      if (last != 0) {
        List<T> subList = list.subList(firstIndex, firstIndex + last);
        subListArray.add(subList);
      }
    }
    return subListArray;
  }

  /**
   * 连接字符串
   *
   * @param spliter
   * @param entity
   * @return
   */
  public static String joinStr(String spliter, Object... entity) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean start = false;
    for (Object var : entity) {
      if (entity == null) {
        continue;
      }
      if (start) {
        stringBuilder.append(spliter);
      } else {
        start = true;
      }
      stringBuilder.append(var.toString());
    }
    return stringBuilder.toString();
  }
  
  /** jpg或者png结尾的图片格式*/
  private static final String STOP_WORD_REG = ".[jJ][pP][gG]$|.[pP][nN][gG]$";
  private static final Pattern STOP_WORD_PATTERN = Pattern.compile(STOP_WORD_REG);
  
  public static String resetImageUrl (String url){
    Matcher m = STOP_WORD_PATTERN.matcher(url);
    if(m.find()){
      String ma = m.group();
      return url.replace(ma,"_R_{width}_{height}" + ma);
    }
    return url;
  }
}
