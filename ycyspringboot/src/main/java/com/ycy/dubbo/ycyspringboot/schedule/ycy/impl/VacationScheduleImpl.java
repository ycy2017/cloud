package com.ycy.dubbo.ycyspringboot.schedule.ycy.impl;

import com.ycy.dubbo.ycyspringboot.schedule.ycy.AbstractSchedular;
import com.ycy.dubbo.ycyspringboot.schedule.ycy.AbstractTask;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component("vacationScheduleImpl")
public class VacationScheduleImpl extends AbstractSchedular {

  public VacationScheduleImpl() {
    System.out.println("VacationScheduleImpl.....................................");
  }

  @Scheduled(cron = "*/10 * * * * * ")
  public void doDiff() {
    System.out.println("call diff...");
    doTask(0);
  }

  // 每隔十秒钟执行一次
  @Scheduled(cron = "0 0/1 * * * *")
  public void doFull() {
    System.out.println("call full...");
    doTask(1);
  }

  @Override
  protected boolean isGovConnValid() {
    return true;
  }

  @Override
  public void initTask() {
    this.allTasks.add(new DiffTaskImpl());
    this.allTasks.add(new FullTaskImpl());
  }


}
