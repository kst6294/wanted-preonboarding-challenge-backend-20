package com.wanted.challenge;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class MultiThreadTestProvider<T> implements Runnable {
    private final CountDownLatch countDownLatch;
    private final Result result;
    private final T target;
    private final Consumer<T> consumer;

    public MultiThreadTestProvider(CountDownLatch countDownLatch, Result result, T target, Consumer<T> consumer) {
        this.countDownLatch = countDownLatch;
        this.result = result;
        this.target = target;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            consumer.accept(target);
            result.success();
        } catch (Exception ignore) {
            result.fail();
        } finally {
            countDownLatch.countDown();
        }
    }

    public static class Result {
        private final AtomicInteger successCount = new AtomicInteger(0);
        private final AtomicInteger failCount = new AtomicInteger(0);

        public void success() {
            successCount.getAndIncrement();
        }

        public void fail() {
            failCount.getAndIncrement();
        }

        public int getSuccessCount() {
            return successCount.get();
        }

        public int getFailCount() {
            return failCount.get();
        }
    }
}
