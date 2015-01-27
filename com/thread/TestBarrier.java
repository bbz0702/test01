package com.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CountWait extends Thread{
	
	private int count;
	
	private CyclicBarrier barrier;
	
	public CountWait(int count, CyclicBarrier barrier) {
		super();
		this.count = count;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		while (count < 10) {
			count ++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			barrier.await();
			
			System.out.println(Thread.currentThread().getName() + " : arrive at");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
	}
}

/**
 * 测试栅栏的使用方法
 * 等待线程到达之后再执行某件事情
 * @author wangzp
 *
 */
public class TestBarrier {
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
			@Override
			public void run() {
				System.out.println("全部达到");
			}
		});
		CountWait wait1 = new CountWait(0, barrier);
		CountWait wait2 = new CountWait(0, barrier);
		CountWait wait3 = new CountWait(0, barrier);
		wait1.start();
		wait2.start();
		wait3.start();
	}
}
