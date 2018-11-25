package com.ycy.dubbo.ycyspringboot.schedule.ycy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractTask implements Runnable {

  public Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  protected static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static final long ONE_DAY_MILLISECONDS = TimeUnit.DAYS.toMillis(1);

  protected TaskExceptionAction taskExcpAct = TaskExceptionAction.CONT_WITH_TASK_ERROR;

  public String startTime;

  public String endTime;

  public String configTime;

  public boolean isVaild;

  public boolean isRunning;

  //激活任务
  private AtomicBoolean wakeUp = new AtomicBoolean(false);

  /**
   * 激活之后,task尝试获取Lock,因为同一时刻只能有一个任务执行
   */
  private AtomicBoolean oneLock = null;


  public void wakeUp(){
    wakeUp.set(true);
  }

  public void setOneLock(AtomicBoolean oneLock) {
    this.oneLock = oneLock;
  }

  /**
   * 持有这个钥匙才能可以执行task
   */
  public boolean lock() {
    return this.oneLock.getAndSet(false);
  }

//  private boolean getLock() {
//    return oneLock.getAndSet(false);
//  }

  /**
   * 赋值为true,其他work才能争取这个locak
   *
   * @return
   */
  private boolean relaseLock() {
    return oneLock.getAndSet(true);
  }

  /**
   * 该任务是否有效, 可在治理中心添加配置禁止无需跑的任务
   */
  public abstract boolean isValid();

  /**
   * 是否到了任务可以运行的时间了

  public abstract boolean isOverInterval();
   */
  /**
   * 业务自己实现该方法
   * <p>
   * 方法中中调用多个worker，多线程的完成拉取数据的任务，为后面数据组装做准备
   */
  public abstract void executeTask();

  /**
   * 业务自己实现该方法
   * <p>
   * 初始化改task需要使用的DB连接池(需先检查该连接池是否已被初始化)
   *
   * @return Enumeration value of {@link initDbPoolStatus}
   */
  public abstract initDbPoolStatus initDbPool();

  @Override
  public final void run() {
    while (true) {
      while(!wakeUp.getAndSet(false)){
        try {
          Thread.sleep(3000);//放慢空轮询
        } catch (InterruptedException e) {
          logger.error(" ",e);
        }
      }
      if (lock()) {//激活之后,尝试获取lock
        try {
          if (initDbPoolStatus.SUCCESS == initDbPool()) {
            executorAndRecordStatus();
          } else {
            logger.error("initDbPool fail! wait for next excute.");
          }
        } catch (Throwable t) {
          logger.error("executeTask failed", t);
          if (taskExcpAct == TaskExceptionAction.STOP_WITH_TASK_ERROR) {
            logger.error("stop the task since");
            return;
          }
        } finally {
          relaseLock();//释放
        }
      }
//      try {
//        Thread.sleep(5000);//放慢空轮询
//      } catch (InterruptedException e) {
//        logger.error(" ",e);
//      }
    }
  }

  public void executorAndRecordStatus() {
    long start = System.currentTimeMillis();
    startTime = SIMPLE_DATE_FORMAT.format(new Date(start));
    isRunning = true;
    logger.info("start at {}" , startTime);
    executeTask();

    isRunning = false;
    long end = System.currentTimeMillis();
    endTime = SIMPLE_DATE_FORMAT.format(new Date(end));
    logger.info("end at {} ,cost {}" , startTime,(end - start));
  }

  protected enum TaskExceptionAction {
    // 即使在Task里捕获异常,仍然继续下一次的Task调度执行
    CONT_WITH_TASK_ERROR,
    // Task出现异常,结束该Task线程,以后也不会执行
    STOP_WITH_TASK_ERROR;
  }

  protected enum initDbPoolStatus {
    SUCCESS, FAIL;
  }

/*	public static void main(String[] args){
		Date d = new Date(2015, 2, 4, 0, 1, 22);
		isFullTaskOverInterval(d, "00:01:00");
	}*/

}

























