package com.ycy.dubbo.ycyspringboot.schedule.ycy.impl;

import com.ycy.dubbo.ycyspringboot.schedule.ycy.AbstractTask;

public class DiffTaskImpl extends AbstractTask {

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public void executeTask() {
    System.out.println("do diff ");
  }

  @Override
  public initDbPoolStatus initDbPool() {
    return initDbPoolStatus.SUCCESS;
  }
}
