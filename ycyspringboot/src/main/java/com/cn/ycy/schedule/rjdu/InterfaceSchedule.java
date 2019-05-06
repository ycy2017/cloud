package com.cn.ycy.schedule.rjdu;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 接口类数据，定时指定方法
 * 
 */
public abstract class InterfaceSchedule {
  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  protected long MAX_TIMEOUT_SECEND = 5 * 60 * 60;// 5个小时

  protected List<InterfaceAbstractWorker> workerList = new ArrayList<InterfaceAbstractWorker>();

  protected ExecutorService executor = Executors.newFixedThreadPool(5);
  public DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
  public String lastExecuteDateTime = "1970-01-01 00:00:00";
  public int INTERVAL_MINITE = 10;// 10分钟做一次增量

  protected boolean needExecuteFull = false;//全量

  private boolean scheduleRunning = false;//任务正在运行

  public InterfaceSchedule() {
    try {
      addWorkers();
      logger.info("finish create schedule");
    } catch (Exception e) {
      logger.error("create schedule fail", e);
      System.out.println(e);
    }
  }

  /**
   * quartz定时执行,增量同步
   * 
   * @throws Exception
   */
  public void execute() throws Exception {
    long startTime = System.currentTimeMillis();
    DateTime now = DateTime.now();
    String lastExecuteDateTimeTemp = lastExecuteDateTime;
    if (needExecuteFull && !scheduleRunning) {
      needExecuteFull = false;// 更新状态直到下一次更改
      scheduleRunning = true;
      try {
        logger.info("start execute");
        CountDownLatch downLatch = new CountDownLatch(workerList.size());
        initWorkerList(workerList, downLatch, lastExecuteDateTimeTemp);
        // TODO 特殊情况下线程池可能用尽，需清理；老框架也没做处理，暂缓
        addWorkerToExecute();
        // 超时终止
        logger.info("max timeout second is:" + MAX_TIMEOUT_SECEND);
        downLatch.await(MAX_TIMEOUT_SECEND, TimeUnit.SECONDS);
        if (downLatch.getCount() == 0) {// 全部任务完成
          lastExecuteDateTime = now.toString(format);
        } else {
          logger.error("not all work is success");
        }
        logger.info(
            "finish exe unfinish count is:" + downLatch.getCount() + ";use time is:" + CommonUtil.getUseTime(startTime));
      } catch (Exception e) {
        logger.error("execute() cause a exception", e);
        System.out.println("execute() cause a exception"+ e);
      } finally {
        scheduleRunning = false;
      }
    } else {
      logger.info("not overSchedule lastTime is:"
          + (DateTime.parse(lastExecuteDateTime, format).plusMinutes(INTERVAL_MINITE).getMillis()
              - System.currentTimeMillis()) / 1000
          + "s");
    }
  }

  public void diffSchedule() throws Exception {
    execute();
  }

  public void fullSchedule() throws Exception {
    needExecuteFull();
  }

  /**
   * 每天1点开始执行
   */
  public void needExecuteFull() {
    needExecuteFull = true;
  }

  private void addWorkerToExecute() {
    for (InterfaceAbstractWorker worker : workerList) {
      executor.submit(worker);
    }
  }

  /**
   * 清理worker
   *
   * @param lastExecuteDateTime
   */
  private void initWorkerList(List<InterfaceAbstractWorker> workList, CountDownLatch downLatch,
      String lastExecuteDateTime) {
    for (InterfaceAbstractWorker worker : workList) {
      worker.init(downLatch, lastExecuteDateTime);
    }
  }

  public abstract void addWorkers() throws Exception;
}
