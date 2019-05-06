package com.cn.ycy.schedule.task;


/**
	*
	* @author Administrator
	*
	*/
public class XCSchedule  implements Runnable{


	AbstractTask[] atTaskArr ;

	public XCSchedule(AbstractTask [] atTaskArr){
		this.atTaskArr = atTaskArr;
	}

	public void run() {

		for(AbstractTask at : atTaskArr){
			//启动
			Thread th = new Thread(at);
			th.start();
		}
		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (AbstractTask at : atTaskArr) {
				//获取执行任务的标识
				if(at.isTimeOver()){
					at.setIsvalid(true);
				}else{
					at.setIsvalid(false);
				}
			}

		}


	}

}
