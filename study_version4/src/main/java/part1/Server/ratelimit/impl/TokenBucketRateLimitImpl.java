package part1.Server.ratelimit.impl;


import part1.Server.ratelimit.RateLimit;

/**
 * @author WYL
 * @date 2025/2/26 10:53
 * @description: TODO
 */
public class TokenBucketRateLimitImpl implements RateLimit {

    private static int RATE; // 生成速率
    private static int CAPACITY; // 最大容量
    private volatile int curCapacity; // 当前容量
    private volatile long timeStamp = System.currentTimeMillis();

    public TokenBucketRateLimitImpl(int rate, int capacity) {
        RATE = rate;
        CAPACITY = capacity;
        curCapacity = capacity;
    }

    @Override
    public boolean getToken() {

        // 如果有令牌的话，直接返回
        if (curCapacity > 0) {
            curCapacity--;
            return true;
        }

        // 通过逻辑生成令牌
        long current = System.currentTimeMillis();
        if (current - timeStamp >= RATE) {

            // 计算在这段时间内可以生成多少令牌，如果大于两个增加当前令牌数量
            if ((current - timeStamp) / RATE >= 2) {
                curCapacity += (int) ((current - timeStamp) / RATE) - 1;
            }
            if (curCapacity > CAPACITY) {
                curCapacity = CAPACITY;
            }
            timeStamp = current;
            return true;
        }

        return false;
    }
}
