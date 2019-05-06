package com.cn.ycy.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class DASUtils {

  public static Timestamp todayStart() {
    long now = System.currentTimeMillis();
    Date nowd = new Date(now);
    nowd.setHours(0);
    nowd.setSeconds(0);
    nowd.setMinutes(0);
    long todaystart = nowd.getTime();
    todaystart = todaystart - (todaystart % 1000);
    return new Timestamp(todaystart);
  }

  /**
   * @param timeStr format is "HH:mm:ss"
   * @return the specific moment of today
   * @throws ParseException
   */
  @SuppressWarnings("deprecation")
  public static Date todayTime(String timeStr) throws Exception {
    Date now = new Date();
    now.setHours(0);
    now.setSeconds(0);
    now.setMinutes(0);
    long todaystart = now.getTime();
    todaystart = todaystart - (todaystart % 1000);
    String[] timeParts = timeStr.split(":");
    if (timeParts != null && timeParts.length == 3 && timeParts[0].length() == 2 && timeParts[1].length() == 2 && timeParts[2].length() == 2) {
      int h = Integer.parseInt(timeParts[0]);
      int m = Integer.parseInt(timeParts[1]);
      int s = Integer.parseInt(timeParts[2]);
      if (h >= 0 && h < 24 && m >= 0 && m < 60 && s >= 0 && s < 60) {
        return new Date(todaystart + (h * 3600 + m * 60 + s) * 1000);
      }
    }
    throw new Exception("time string error:" + timeStr + ", use HH:mm:ss");
  }

}
