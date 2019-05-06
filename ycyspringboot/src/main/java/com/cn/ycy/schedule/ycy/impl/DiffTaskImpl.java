package com.cn.ycy.schedule.ycy.impl;

import com.cn.ycy.schedule.ycy.AbstractTask;

import java.util.concurrent.atomic.AtomicBoolean;

public class DiffTaskImpl extends AbstractTask {

  public DiffTaskImpl(AtomicBoolean oneLock) {
    super(oneLock);
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public void executeTask() {
    System.out.println("do diff.... ");
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public initDbPoolStatus initDbPool() {
    return initDbPoolStatus.SUCCESS;
  }
}
