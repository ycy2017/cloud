package com.ycy.dubbo.ycyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * springbootapplication 高数spring这是一个配置类 功能和application.xml一样
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableScheduling
@ImportResource(locations = {"classpath:springcontext.xml"})
public class YcyspringbootApplication {

//  public static ConcurrentTaskScheduler threadPoolExecutor;

  public static void main(String[] args) {
    SpringApplication.run(YcyspringbootApplication.class, args);
  }


  /**
   * 使用api的方式配置,schedule
   * 注释掉,使用spring的配置文件,配置调度任务
  @Bean
  public TaskScheduler taskScheduler() {
    AtomicInteger number = new AtomicInteger(1);
    threadPoolExecutor = new ConcurrentTaskScheduler(
            Executors.newScheduledThreadPool(10, r -> {
              Thread thread = new Thread(r);
              thread.setName("TaskPool-thread-" + number.getAndIncrement());
              thread.setDaemon(true);   //daemon 为 true 导致主线程很快退出，从而进程退出
              return thread;
            }));
    return threadPoolExecutor;
  }
   * @return
   */

}
