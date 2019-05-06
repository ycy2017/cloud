package com.cn.ycy.schedule;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

/**
 * spring 的调度任务
 * (1)  被加了@Schedule的方法,调用任务是由线程池来执行的,
 * (2)　只是说是 fixedRate 任务两次执行时间间隔是任务的开始点，而 fixedDelay 的间隔是前次任务的结束与下次任务的开始。
 * (3) 当前任务没有完成,即使到了执行时间也会等待
 */
@Component
public class ScheduleImpl {

  /**
   * 每分钟触发一次?
   */
//  @Scheduled(cron = "2/10 * * * * ? ")
  public void schedule() {
    System.out.println(Thread.currentThread().getName() + " 任务1 start time " + LocalTime.now() + "");
  }

  /**
   * 每个10秒来一次,如果中间的任务,长耗时超过20秒?? 这个任务是多线程的吗?
   * <p>
   * 调度任务只有一个线程
   */
//  @Scheduled(cron = "2/10 * * * * ? ")
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

  //  @Scheduled(cron = "2/2 * * * * ? ")
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


}
