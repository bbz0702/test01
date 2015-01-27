package com.thread;

import java.util.concurrent.CountDownLatch;

class Tickets {

	private int num;

	public Tickets(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}
	
	public int down() {
		int buyNum = num --;
		return buyNum;
	}
}

/**
 * 实例：卖火车票，卖两种座位票，无座和有座.必须等待有座卖完之后，再卖无座
 * @author wangzp
 */
class TrainTicket implements Runnable{
	// 0 : 无座；1：有座
	private int type;

	private Tickets tickets;
	
	private CountDownLatch latch;
	
	public TrainTicket(Tickets tickets, CountDownLatch latch, int type) {
		this.tickets = tickets;
		this.latch = latch;
		this.type = type;
	}
	public void run() {
		while(true){
			synchronized (this) {
				if (type == 1){
					if (tickets.getNum() > 0) {
						System.out.println("有座--" + Thread.currentThread().getName() + " : " + tickets.down()); 
					}else {
						//当有座卖完时，释放事件
						latch.countDown();
					}
				}else if (type == 0){
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (tickets.getNum() > 0) {
						System.out.println("无座--" + Thread.currentThread().getName() + " : " +tickets.down()); 
					}
				}
			}
		}
	}
}


public class TestCountDownLatch {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);
		
		TrainTicket seaTicket = new TrainTicket(new Tickets(100), latch, 1);
		TrainTicket noSeaTicket = new TrainTicket(new Tickets(100), latch, 0);
		
		Thread s1 = new Thread(seaTicket);
		Thread s2 = new Thread(seaTicket);
		Thread s3 = new Thread(seaTicket);
		Thread s4 = new Thread(seaTicket);
		
		Thread n1 = new Thread(noSeaTicket);
		Thread n2 = new Thread(noSeaTicket);
		Thread n3 = new Thread(noSeaTicket);
		Thread n4 = new Thread(noSeaTicket);
		
		s1.start();
		s2.start();
		s3.start();
		s4.start();
		
		n1.start();
		n2.start();
		n3.start();
		n4.start();
		
	}
}
