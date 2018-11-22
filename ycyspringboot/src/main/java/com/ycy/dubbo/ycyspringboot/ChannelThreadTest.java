package com.ycy.dubbo.ycyspringboot;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChannelThreadTest {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while(true){
          try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " 每隔三秒告诉世界自己萌萌哒");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    executorService.submit(thread);

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        while(true){
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          //我要让那个觉的自己么么哒的人,从这个线程池里面消失
        }

      }
    });



  }

}
