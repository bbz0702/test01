package com.thread;

import java.util.concurrent.Semaphore;

/**
 * 以售票为例，使用信号量，取代之前的同步锁
 * @author wangzp
 */

class Ticket1 implements Runnable{
	
	private int num = 100;
	// 只分配一个许可，那么但一个线程获取许可之后，在没有释放之前，其他线程都无法访问
	private Semaphore sm = new Semaphore(1);
	
	@Override
	public void run() {
		while(true){
			try {
				sm.acquire(); // 获取一个许可
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (num > 0) {
				System.out.println(num --);
			}
			
			sm.release();
		}
	}
	
}

/**
 * 测试信号量的用法
 * @author wangzp
 *
 */
public class TestSemaphore {
	public static void main(String[] args) {
		Ticket1 t = new Ticket1();
		
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		Thread t3 = new Thread(t);
		Thread t4 = new Thread(t);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
