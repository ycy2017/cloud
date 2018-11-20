package com.ycy.ycyspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class YcyspringbootApplication {

  public static ConcurrentTaskScheduler threadPoolExecutor;

  public static void main(String[] args) {
    SpringApplication.run(YcyspringbootApplication.class, args);
  }


  /**
   * 使用api的方式配置,schedule
   * @return
   */
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

}
