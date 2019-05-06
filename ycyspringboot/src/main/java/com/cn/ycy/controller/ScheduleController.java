package com.cn.ycy.controller;

import com.cn.ycy.schedule.ycy.AbstractSchedular;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class ScheduleController {

  private  final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  public ScheduleController() {
    System.out.println("controller ................................");
  }

  @Autowired
  @Qualifier("vacationScheduleImpl")
  private AbstractSchedular vacationScheduleImpl;


  @RequestMapping("/executefull")
  @ResponseBody
  public String doTask() {
    vacationScheduleImpl.wakeUpTask(1);
    return "true";
  }


  @RequestMapping("/dolike")
  @ResponseBody
  public String doLike() {

    return "true";
  }

}
