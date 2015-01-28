package com.thread;


/**
 * 同步静态方法，所持有的锁是当前类的字节码对象锁
 * 等价于:synchronized(类.class) 或者synchronized(this.getClass())；而不是this对象的锁
 * @author wangzp
 *
 */

class Ticket2 implements Runnable{
	
	private static int num = 200;
	
	public boolean flag = false;
	
	@Override
	public void run() {
		if (flag) {
			while(true){
				synchronized (Ticket2.class) {
//				synchronized (this) {
					if (num > 0) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " : " + num --);
					}
				}
			}
		}else {
			while(true){
				sale();
			}
		}
		
	}
	
	public synchronized static void sale() {
		if (num > 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " : " + num --);
		}
	}
}

/**
 * 验证静态方法的锁
 */
public class SyncStaticThread {
	public static void main(String[] args) {
		
		Ticket2 tic1 = new Ticket2();
		tic1.flag = true;
		
		Ticket2 tic2 = new Ticket2();
		tic2.flag = false;
		
		Thread t1 = new Thread(tic1);
		Thread t2 = new Thread(tic2);
		
		t1.start();
		t2.start();
		
	}
}
