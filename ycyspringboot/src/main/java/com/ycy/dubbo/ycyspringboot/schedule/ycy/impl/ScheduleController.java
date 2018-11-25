package com.ycy.dubbo.ycyspringboot.schedule.ycy.impl;

import com.ycy.dubbo.ycyspringboot.schedule.rjdu.impl.TestScheduleImpl;
import com.ycy.dubbo.ycyspringboot.schedule.ycy.AbstractSchedular;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
    vacationScheduleImpl.doTask(1);
    return "true";
  }


  @RequestMapping("/dolike")
  @ResponseBody
  public String doLike() {

    return "true";
  }

}
