package com.ycy.ycyspringboot.controller;

import com.ycy.ycyspringboot.YcyspringbootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.SimpleFormatter;


/**
 * spring 的调度任务
 * (1)  被加了@Schedule的方法,调用任务是由线程池来执行的,
 * (2)　只是说是 fixedRate 任务两次执行时间间隔是任务的开始点，而 fixedDelay 的间隔是前次任务的结束与下次任务的开始。
 * (3) 当前任务没有完成,即使到了执行时间也会等待
 */
//@Component
public class ScheduleController {

  /**
   * 每分钟触发一次?
   */
  @Scheduled(cron = "2/10 * * * * ? ")
  public void schedule() {
    System.out.println(Thread.currentThread().getName() + " 任务1 start time " + LocalTime.now() + "");
  }

  /**
   * 每个10秒来一次,如果中间的任务,长耗时超过20秒?? 这个任务是多线程的吗?
   * <p>
   * 调度任务只有一个线程
   */
  @Scheduled(cron = "2/10 * * * * ? ")
  public void schedule2() {
    SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH:mm:ss");
    Long start = System.currentTimeMillis();
    Date st = new Date(start);
    System.out.println(Thread.currentThread().getName() + " 任务2 start time " + simpleFormatter.format(st) + "");
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Long end = System.currentTimeMillis();
    Date en = new Date(end);
    System.out.println(Thread.currentThread().getName() + " 任务2 end time " + simpleFormatter.format(en) + " ,cost " + (end - start));
  }

  @Scheduled(cron = "2/2 * * * * ? ")
  public void schedule3() {
    SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH:mm:ss");
    Long start = System.currentTimeMillis();
    Date st = new Date(start);
    System.out.println(Thread.currentThread().getName() + " 任务3 start time " + simpleFormatter.format(st));
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Long end = System.currentTimeMillis();
    Date en = new Date(end);
    System.out.println(Thread.currentThread().getName() + " 任务3 end time " + simpleFormatter.format(en) + " ,cost " + (end - start));
  }


  @Scheduled(cron = "1/1 * * * * ? ")
  public void schedule4() {
    ScheduledThreadPoolExecutor threadPoolExecutor = (ScheduledThreadPoolExecutor) YcyspringbootApplication.threadPoolExecutor.getConcurrentExecutor();
    /**
     * 线程池需要执行的任务数
     */
    long taskCount = threadPoolExecutor.getTaskCount();
    /**
     * 线程池在运行过程中已完成的任务数
     */
    long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
    /**
     * 曾经创建过的最大线程数
     */
    long largestPoolSize = threadPoolExecutor.getLargestPoolSize();
    /**
     * 线程池里的线程数量
     */
    long poolSize = threadPoolExecutor.getPoolSize();
    /**
     * 线程池里活跃的线程数量
     */
    long activeCount = threadPoolExecutor.getActiveCount();


  /*  System.out.println(String.format("总共执行了{%d},已经完成{%d},最大{%d},目前线程池{%d},活跃{%d}",
            taskCount
            , completedTaskCount
            , largestPoolSize
            , poolSize
            , activeCount));*/

  }
}
