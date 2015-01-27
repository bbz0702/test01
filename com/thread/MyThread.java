package com.thread;

/**
 * 模拟四个窗口销售100张票 
 */
class Ticket implements Runnable{
	
	private int num = 100;
	
	private Object obj = new Object();
	
	public void run(){
		while(true){
			synchronized(obj) {
				if(num > 0) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
					 System.out.println(Thread.currentThread().getName() + " : "  + num --);
				}
			}
		}
	}
}

class Bank {
	
	private int sum;
	
	public synchronized void add(int num) {
		sum += num;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " : " + sum);
	}
}

class Customer implements Runnable{
	
	private Bank bank;
	public Customer(Bank bank) {
		this.bank = bank;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 3; i ++) {
			bank.add(100);
		}
	}
}

public class MyThread {
	public static void main(String[] args) {
//		Ticket t = new Ticket();
		Customer t = new Customer(new Bank());
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		
		t1.start();
		t2.start();
		
	}
}
