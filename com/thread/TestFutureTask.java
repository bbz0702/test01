package com.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用FutureTask计算数组求和
 * @author wangzp
 */
class SumCal {
	
	private final List<Integer> nums = new ArrayList<Integer>();
	
	private final FutureTask<Integer> calTask = new FutureTask<Integer>(new Callable<Integer>() {
		@Override
		public Integer call() throws Exception {
			Thread.sleep(5000);
			return cal();
		}
	});
	
	public SumCal(Integer...nums) {
		this.nums.addAll(Arrays.asList(nums));
		Thread thread = new Thread(calTask);
		thread.start();
	}
	
	public int getSum() {
		try {
			// 阻塞等待，直到计算完成，或者程序异常
			return calTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int cal() {
		int sum = 0;
		for (Integer num : nums) {
			sum += num;
		}
		return sum;
	}
}

public class TestFutureTask {
	public static void main(String[] args) {
		SumCal cal = new SumCal(1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,12,132 );
		System.out.println(cal.getSum());
	}
}
