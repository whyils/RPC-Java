package com.wyl.Client.circuitBreaker;

import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreaker {

    private CircuitBreakerState state = CircuitBreakerState.CLOSE; // 熔断器的状态，默认是关闭的

    private AtomicInteger failureCount = new AtomicInteger(0); // 处于半开状态下 失败的次数
    private AtomicInteger successCount = new AtomicInteger(0); // 处于半开状态下 成功的次数
    private AtomicInteger requestCount = new AtomicInteger(0); // 处于半开状态下 请求的次数
    private final int failureThreshold; // 失败的阈值
    private final double halfOpenSuccessRate; // 半开状态下成功的比例
    private final long restTimePeriod; // 重置的时间
    private long lastFailureTime; // 上一次失败的时间

    public CircuitBreaker(int failureThreshold, double halfOpenSuccessRate, long restTimePeriod) {
        this.failureThreshold = failureThreshold;
        this.halfOpenSuccessRate = halfOpenSuccessRate;
        this.restTimePeriod = restTimePeriod;
    }

    public synchronized boolean allowRequest() {
        long current = System.currentTimeMillis();

        switch (state) {
            case Open :
                if (current - lastFailureTime > restTimePeriod) {
                    state = CircuitBreakerState.HALF_OPEN;
                    resetCounts();
                    return true; // 允许请求
                }
                return false; // 继续熔断
            case HALF_OPEN:
                requestCount.incrementAndGet();
                return true;
            case CLOSE:
            default:
                return true; // 默认状态和关闭状态允许请求
        }

    }

    public synchronized void recordSuccess() {

        if (state == CircuitBreakerState.HALF_OPEN) {
            successCount.incrementAndGet();
            // 如果成功的比例大于半开规定的关闭比例，就将熔断器关闭
            if (successCount.get() > halfOpenSuccessRate * requestCount.get()) {
                state = CircuitBreakerState.CLOSE;
                resetCounts();
            }
        }else {
            resetCounts();
        }

    }

    public synchronized void recordFailure() {

        failureCount.incrementAndGet();
        lastFailureTime = System.currentTimeMillis();

        if (state == CircuitBreakerState.HALF_OPEN) { // 如果是半开的状态，错误一次就将熔断器打开
            state = CircuitBreakerState.Open;
        }else if (failureCount.get() > failureThreshold) { // 如果是关闭的状态，错误的次数大于阈值就打开熔断器
            state = CircuitBreakerState.Open;
        }

    }

    public void resetCounts() {
        failureCount.set(0);
        successCount.set(0);
        requestCount.set(0);
    }

}
