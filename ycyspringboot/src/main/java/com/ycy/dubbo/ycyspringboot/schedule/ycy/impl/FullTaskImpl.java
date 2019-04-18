package com.ycy.dubbo.ycyspringboot.schedule.ycy.impl;

import com.ycy.dubbo.ycyspringboot.schedule.ycy.AbstractTask;

import java.util.concurrent.atomic.AtomicBoolean;

public class FullTaskImpl extends AbstractTask {

  public FullTaskImpl(AtomicBoolean oneLock) {

    super(oneLock);
  }

  @Override
  public boolean isValid() {
    return true;
  }



  @Override
  public void executeTask() {
    System.out.println("do full ....");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public initDbPoolStatus initDbPool() {
    return initDbPoolStatus.SUCCESS;
  }
}
