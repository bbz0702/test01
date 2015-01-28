package com.thread;

/*
 * 死锁测试，死锁情况经常发生在嵌套锁的使用关系中
 * 例如：锁A，锁B，如果使得A需要B的锁，同时B需要A的锁，那么就会导致死锁的情况
 */

class Ticket3 implements Runnable{
	
	public boolean flag;
	
	@Override
	public void run() {
		if(flag) {
			synchronized(MyLock.lockA){
				System.out.println(Thread.currentThread().getName() + " : lockA");
				synchronized(MyLock.lockB) {
					System.out.println(Thread.currentThread().getName() + " : lockB");
				}
			}
		}else {
			synchronized(MyLock.lockB) {
				System.out.println(Thread.currentThread().getName() + " : lockB");
				synchronized (MyLock.lockA) {
					System.out.println(Thread.currentThread().getName() + " : lockA");
				}
			}
		}
	}
}


class MyLock{
	// 锁A
	public final static Object lockA = new Object();
	// 锁B
	public final static Object lockB = new Object();
}


public class DeadLockTest {
	public static void main(String[] args) {
		Ticket3 tic1 = new Ticket3();
		tic1.flag = true;
		Ticket3 tic2 = new Ticket3();
		tic2.flag = false;
		
		Thread t1 = new Thread(tic1);
		Thread t2 = new Thread(tic2);
		t1.start();
		t2.start();
		
	}
}
