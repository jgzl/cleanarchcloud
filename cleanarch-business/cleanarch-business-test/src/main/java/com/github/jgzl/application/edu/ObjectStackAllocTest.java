package com.github.jgzl.application.edu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectStackAllocTest {
	private static final Logger logger= LoggerFactory.getLogger(ObjectStackAllocTest.class);
	public static void main(String[] args) {
		countDownLatchTest();
		cyclicBarrierTest();
	}

	private static void cyclicBarrierTest() {
		AtomicInteger atomicInteger = new AtomicInteger();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				try {
					cyclicBarrier.await();
					logger.info("结束CyclicBarrier.await");
				} catch (BrokenBarrierException | InterruptedException e) {
					e.printStackTrace();
				}
				for (int j = 0; j < 1000; j++) {
					atomicInteger.incrementAndGet();
					logger.info(String.format("CyclicBarrier数值为%s",atomicInteger.get()));
				}
			}).start();
		}
		System.out.println(atomicInteger.get());
	}

	private static void countDownLatchTest() {
		AtomicInteger atomicInteger = new AtomicInteger();
		CountDownLatch countDownLatch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				try {
//					Unsafe unsafe1 = UnsafeUtils.getUnsafe();
//					Unsafe unsafe = Unsafe.getUnsafe();
					countDownLatch.await();
					logger.info("结束await");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int j = 0; j < 1000; j++) {
					atomicInteger.incrementAndGet();
					logger.info(String.format("数值为%s",atomicInteger.get()));
				}
			}).start();
			countDownLatch.countDown();
			logger.info("countDownLatch执行完毕");
		}
		System.out.println(atomicInteger.get());
	}
}
