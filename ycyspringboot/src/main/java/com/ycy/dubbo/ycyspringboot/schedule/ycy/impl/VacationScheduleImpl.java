package com.ycy.dubbo.ycyspringboot.schedule.ycy.impl;

import com.ycy.dubbo.ycyspringboot.schedule.ycy.AbstractSchedular;
import com.ycy.dubbo.ycyspringboot.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Component("vacationScheduleImpl")
public class VacationScheduleImpl extends AbstractSchedular {

  Logger logger = LoggerFactory.getLogger(VacationScheduleImpl.class);

  public VacationScheduleImpl() {
    logger.info("VacationScheduleImpl.....................................");
  }

  @Override
  public void initTask() {
    AtomicBoolean oneLock = new AtomicBoolean(true);
    this.allTasks.add(new DiffTaskImpl(oneLock));
    this.allTasks.add(new FullTaskImpl(oneLock));
  }

  @Scheduled(cron = "*/5 * * * * * ")
  public void doDiff() {
//    System.out.println("call diff...");
//    wakeUpTask(0);
  }

  // 每隔十秒钟执行一次
  @Scheduled(cron = "*/10 * * * * * ")
  public void doFull() {
//    System.out.println("call full...");
//    wakeUpTask(1);
  }

  Date date = new Date();
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  // 每隔十秒钟执行一次
  @Scheduled(cron = "*/10 * * * * * ")
  public void exc() {
    Date date = new Date();
    Long start = date.getTime();
    String format = simpleDateFormat.format(date);
    System.out.println(format + " 执行sql 区间 : > "+  simpleDateFormat.format( this.date ));
    this.date = date;
  }

  @Override
  protected boolean isGovConnValid() {
    return true;
  }


}
