package com.ycy.com.ycy.log;

public class LoggerDemo {

  public void  doTest(){
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (StackTraceElement stackTraceElement : stackTrace) {
      System.out.println(stackTraceElement);
    }
  }

  public static void doTest1(){
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (StackTraceElement stackTraceElement : stackTrace) {
      System.out.println(stackTraceElement);
    }
  }

}
