package com.ycy.dubbo.ycyspringboot.schedule.task;

import java.util.concurrent.atomic.AtomicBoolean;

/**
	*
	* @author Administrator
	*
	*/
public abstract class AbstractTask implements Runnable {

	/**
		* 运行标识
		*/
	protected AtomicBoolean isvalid = new AtomicBoolean(false);

	/**
		* 运行标识
		*/
	protected AtomicBoolean isWait = new AtomicBoolean(true);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void run() {
		while (true) {
			System.out.println("run");
			System.out.println(getIsvalid());

			while (!getIsvalid()) {

			}
			if (getToken()) {
				// 令牌只有一个,同一时间只允许一个任务执行
				try {
					excutorTask();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 释放令牌
					realeseToken();
				}
			}
		}
	}

	public abstract void excutorTask();

	public boolean getIsvalid() {
		return isvalid.get();
	}

	public void setIsvalid(boolean isvalid) {
		this.isvalid.set(isvalid);
	}

	public boolean getToken() {
		return isWait.getAndSet(false);
	}

	public void realeseToken() {
		isWait.set(true);
	}

	public  abstract boolean isTimeOver();

}
