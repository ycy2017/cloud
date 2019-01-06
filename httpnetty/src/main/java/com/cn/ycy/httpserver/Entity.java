package com.cn.ycy.httpserver;

import java.util.Date;

public class Entity {

  public String taskname;

  public int updataNum = 10;

  public int insertNum = 9;

  public int deleteNum = 8;

  public Date lastTime = new Date();

  //  public Date endTime = new Date(System.currentTimeMillis()+60000);
  public int costTime = 0;

  public String message;

  public taskstatus status;

//  public

  public enum taskstatus {
    SUCCESS, FAIL;
  }

  public static void main(String[] args) {

    System.out.println(taskstatus.SUCCESS);
    int anInt = getInt();
    System.out.println(anInt);
    System.out.println(i);
  }

  static int i = 0;

  public static int getInt() {
    try {
      i = 2;
      return i;
    } finally {
      i = 0;
    }
  }

}
