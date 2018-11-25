package com.ycy.dubbo.ycyspringboot.schedule.ycy;


/*
* rjdu下的 框架存在的缺点
*
* 1/没有考虑到增量数据同步
* 2/ 还有线程安全的问题, 高并发的HTTP请求可能使得 同一个任务跑多次
*
*
* ycy包 会补充这一点
*
*
* 注意:
*
* */