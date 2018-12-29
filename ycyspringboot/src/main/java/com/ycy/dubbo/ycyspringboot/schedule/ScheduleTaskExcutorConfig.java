package com.ycy.dubbo.ycyspringboot.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Configuration
public class ScheduleTaskExcutorConfig implements SchedulingConfigurer {

  public ThreadPoolTaskScheduler scheduler = null;

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    //创建一个线程池调度器
    scheduler = new ThreadPoolTaskScheduler();
    //设置线程池容量
    scheduler.setPoolSize(1);
    //线程名前缀
    scheduler.setThreadNamePrefix("task-");
    //等待时常
    scheduler.setAwaitTerminationSeconds(60);
    //当调度器shutdown被调用时等待当前被调度的任务完成
    scheduler.setWaitForTasksToCompleteOnShutdown(true);
    //设置当任务被取消的同时从当前调度器移除的策略
    scheduler.setRemoveOnCancelPolicy(true);
    //设置任务注册器的调度器
    scheduledTaskRegistrar.setTaskScheduler(scheduler);
  }
}
