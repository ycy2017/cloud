package com.cn.ycy.schedule.task;

import com.cn.ycy.util.DASUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Administrator
 */
public abstract class AbstractTask implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(AbstractTask.class);

  public static final long ONE_DAY_MILLISECONDS = TimeUnit.DAYS.toMillis(1);

  /**
   * 运行标识
   */
  protected AtomicBoolean isvalid = new AtomicBoolean(false);

  /**
   * 运行标识
   */
  protected AtomicBoolean isWakeUp = new AtomicBoolean(false);

  public void run() {
    while (true) {
      while (isWakeUp.getAndSet(false)) {

        TasksMutex mutex = getTasksMutex();
        while (mutex.getAndSet()) {
          // 令牌只有一个,同一时间只允许一个任务执行
          try {
            excutorTask();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            isWakeUp.set(false);
            // 释放令牌
            mutex.setIdle();
          }
        }

        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  protected abstract TasksMutex getTasksMutex();

  public abstract void excutorTask();

  public boolean getIsvalid() {
    return isvalid.get();
  }

  public void setIsvalid(boolean isvalid) {
    this.isvalid.set(isvalid);
  }

  public void wakeUp() {
    isWakeUp.set(true);
  }

//  final String conf

  public boolean isTimeOver(){
    //TODO

    return false;
  }

  // 用于通用的全量任务的时间判断
  public static boolean isFullTaskOverInterval(Date lastEnd, String cfgtimestr) {
    if (null == lastEnd) {
      // 未记录上次增量完成时间,或许治理中心没有配置,认为isOverInterval为false
      logger.warn("lastEnd is null");
      return false;
    }

    long now = System.currentTimeMillis();
    long todaystart = DASUtils.todayStart().getTime();
    if (lastEnd.getTime() - todaystart < 0) {
      // need check config time
      try {
        Date cfgtime = DASUtils.todayTime(cfgtimestr);
        // 当前时间到最后时间超过一天也要跑
        if (now >= cfgtime.getTime() || (now - lastEnd.getTime()) > ONE_DAY_MILLISECONDS) {
          // schedule full data
          return true;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  // 用于通用的增量任务的时间判断
  public static boolean isDiffTaskOverInterval(Date lastEnd, String cfgIntervalstr) {
    if (lastEnd == null) {
      logger.warn("lastEnd is null");
      return false;
    }
    int interval = 300;
    if (cfgIntervalstr.endsWith("s")) {
      interval = Integer.parseInt(cfgIntervalstr.substring(0, cfgIntervalstr.length() - 1));
    } else {
      interval = Integer.parseInt(cfgIntervalstr);
    }
    if (System.currentTimeMillis() - lastEnd.getTime() > interval * 1000) {
      return true;
    }
    return false;
  }

}
