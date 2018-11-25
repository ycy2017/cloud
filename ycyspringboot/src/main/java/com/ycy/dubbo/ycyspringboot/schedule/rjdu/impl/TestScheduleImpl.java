package com.ycy.dubbo.ycyspringboot.schedule.rjdu.impl;

import com.ycy.dubbo.ycyspringboot.schedule.rjdu.InterfaceSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 这是一个spring管理的任务task
 * 每隔2秒钟触发调度任务,
 */
//@Component("testScheduleImpl")
public class TestScheduleImpl extends InterfaceSchedule {

  private final Logger logger = LoggerFactory.getLogger(TestScheduleImpl.class);

  @Override
  public void addWorkers() throws Exception {
    workerList.add(new TeskWorkImpl());
  }

  //通过接口调用时，让全量生效；每30秒触发一次
  @Scheduled(cron = "*/2 * * * * * ")
  public void diffSchedule() throws Exception {
    System.out.println("2");
    logger.warn("Can be prepared ......");
    if (!needExecuteFull) {
      return;
    }
    super.diffSchedule();
  }

  // 每隔十秒钟执行一次
  @Scheduled(cron = "0/30 * * * * * ")
  public void fullSchedule() throws Exception {
    logger.warn("TestScheduleImpl start ......," + LocalDateTime.now());
    super.fullSchedule();
  }


}
