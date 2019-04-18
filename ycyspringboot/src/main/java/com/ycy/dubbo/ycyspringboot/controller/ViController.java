package com.ycy.dubbo.ycyspringboot.controller;

import com.ycy.dubbo.ycyspringboot.schedule.rjdu.impl.TestScheduleImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
public class ViController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
  @Autowired
  @Qualifier("testScheduleImpl")
  TestScheduleImpl testSchedule;

//  @Autowired
//  @Qualifier("testScheduleImpl")
//  TestScheduleImpl testSchedule;


  @RequestMapping("/executefull")
  @ResponseBody
  public String startProductLineWorker() {
    testSchedule.needExecuteFull();
    return "true";
  }


}
