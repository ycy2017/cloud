package com.cn.ycy.schedule.task;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class TasksMutex {

  private AtomicBoolean idle = new AtomicBoolean(true);

  /**
   * 如果idle为true,那么把它设置成false
   * <p>
   * 无论是否设置成false,都返回调用函数之前的值
   */
  public synchronized boolean getAndSet() {
    return idle.getAndSet(false);
  }

  public synchronized void setIdle() {
    idle.set(true);
  }

}
