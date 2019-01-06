package com.cn.ycy.httpserver;

import com.google.gson.Gson;
import io.netty.util.concurrent.ThreadPerTaskExecutor;

import java.util.LinkedList;
import java.util.List;

public class LoggerEntity {

  public  List<Entity> list = new LinkedList<>();

  public void wirteLog(Entity e) {
    list.add(e);
    //一天的量
    if (this.list.size() > 3) {
      list.remove(0);
    }
  }

  public static void main(String[] args) {

    LoggerEntity loggerEntity = new LoggerEntity();

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true){
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          Entity entity = new Entity();
          loggerEntity.wirteLog(entity);
        }
      }
    });
    thread.start();

    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true){
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          System.out.println(new Gson().toJson(loggerEntity.list));

        }
      }
    });
    thread1.start();


  }

}
