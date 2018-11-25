package com.ycy.dubbo.ycyspringboot.schedule.ycy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractSchedular implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(AbstractSchedular.class);

  // 是否是本地调试模式,如果为true,那么所有task只执行一次
  protected boolean debugMode = false;

  private volatile boolean exit = false;

  private AtomicBoolean AorB = new AtomicBoolean(true);

  // 所有待执行的任务, 需要在子类中赋值
  public List<AbstractTask> allTasks = new LinkedList<>();

  public AbstractSchedular() {
    initTask();
    run();
  }

  // 测试治理中心连接是否有效
  abstract protected boolean isGovConnValid();

  // 子类负责实现初始化Mutext的具体细节
  // TasksMutex.init("Poi", "Youth");
//  abstract protected void initMutex();

  // 子类负责初始化allTasks
  // allTasks = new AbstractTask[] {
  // new PoiFullTask(),new PoiDiffTask() };
//  abstract protected void initAllTasks();

/*  public void exit() {
    exit = true;
  }*/

  /**
   * 初始化allTasks, Mutext等
   */
  private void initEnv() {
//    initMutex();
//    initAllTasks();
  }

  private boolean checkEnvOK() {
    if (allTasks.isEmpty()) {
      logger.error("allTasks is emppty, please check!");
      return false;
    }
 /*   if (!TasksMutex.isInitialed()) {
      logger.error("TasksMutex is not initialed, please check!");
      return false;
    }*/
    return true;
  }

  /**
   * 把task加入后,就开始执行
   */
  public abstract void initTask();

  /**
   * 一个task,启动一个线程,进行任务轮询
   */
  public void run() {
    initEnv();
    if (!checkEnvOK()) {
      // 如果检查失败,表示有代码编写错误,直接退出Schedular
      return;
    }
    //AorB通过这个对象,控制task任务互斥
    AtomicBoolean AorB = new AtomicBoolean(true);
    //让每个task跑起来
    for (AbstractTask task : allTasks) {
      task.setOneLock(AorB);//为所有
      Thread thread = new Thread(task);
      thread.setName(task.getClass().getName());
      thread.start();
    }
  }


  /**
   *
   */
  public void doTask(int abstractTaskIndex) {
    AbstractTask abstractTask = allTasks.get(abstractTaskIndex);
    if (shouldScheduleTask(abstractTask)) {
        abstractTask.wakeUp();//获取lock,才能获取有执行任务的权利
      }
  }

  /**
   *

  public void shouldFullscheduleTasks(List<AbstractTask> tasks) {
    for (AbstractTask task : tasks) {
      if (shouldScheduleTask(task)) {
        task.lock();//获取lock,才能获取有执行任务的权利
      }
    }
  }   */

  private boolean shouldScheduleTask(AbstractTask task) {
//    if (task.isWait()) {
//      // 如果已经处于唤醒状态(非等待状态),那么无需重复ScheduleTask
//      return true;
//    }
    if (!task.isValid()) {
      // 该任务暂时无效,不执行
      return false;
    }

 /*   if (!task.isOverInterval()) {
      return false;
    }*/

    return true;//以上ok就可以执行
  }
}
