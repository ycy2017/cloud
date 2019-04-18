package com.ycy.dubbo.ycyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * springbootapplication 告诉spring这是一个配置类 功能和application.xml一样
 */

//@ImportResource(locations = {"classpath:springcontext.xml"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@Configuration
public class YcyspringbootApplication implements SchedulingConfigurer {

//  public static ConcurrentTaskScheduler threadPoolExecutor;

  public static void main(String[] args) {
    SpringApplication.run(YcyspringbootApplication.class, args);
  }


  /**
   * 使用api的方式配置,schedule
   * 注释掉,使用spring的配置文件,配置调度任务
   * * @return
   *    */
  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setPoolSize(20);
    scheduler.setThreadNamePrefix("task-");
    scheduler.setAwaitTerminationSeconds(60);
    scheduler.setWaitForTasksToCompleteOnShutdown(true);
    scheduler.initialize();
    return scheduler;
  }


  public ThreadPoolTaskScheduler scheduler = null;

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    //创建一个线程池调度器
    scheduler = new ThreadPoolTaskScheduler();
    //设置线程池容量
    scheduler.setPoolSize(3);
    //线程名前缀
    scheduler.setThreadNamePrefix("task-");
    //等待时常
    scheduler.setAwaitTerminationSeconds(60);
    //当调度器shutdown被调用时等待当前被调度的任务完成
    scheduler.setWaitForTasksToCompleteOnShutdown(true);
    //设置当任务被取消的同时从当前调度器移除的策略
    scheduler.setRemoveOnCancelPolicy(true);

    scheduler.initialize();
    //设置任务注册器的调度器
    scheduledTaskRegistrar.setTaskScheduler(scheduler);
  }



}
