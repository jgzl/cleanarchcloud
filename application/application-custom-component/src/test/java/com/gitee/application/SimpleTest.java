package com.gitee.application;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class SimpleTest {

	@BeforeClass
	public void setUp() {
		System.out.println("Initialize data!");
	}

	@Test(groups = { "fast" })
	public void aFastTest() {
		System.out.println("Fast test");
	}

	@Test(groups = { "slow" })
	public void aSlowTest() {
		System.out.println("Slow test");
	}

	@Test(groups = {"threadPool"})
	public void createThreadPool(){
		ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("test-threadPool").build();
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.MINUTES,
				new ArrayBlockingQueue<>(1000),factory);
		for (int i = 0; i < 1010; i++) {
			DemoTask demoTask = new DemoTask(i);
			threadPoolExecutor.execute(demoTask);
		}
		while (true){}
	}

	private class DemoTask implements Runnable {

		private int count;

		public DemoTask(int count) {
			this.count=count;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("执行DemoTask"+count);
		}
	}
}
