package part1.Client.retry;

import com.github.rholder.retry.*;
import part1.Client.rpcClient.RpcClient;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GuavaRetry {

    private RpcClient rpcClient;

    public RpcResponse sendServiceWithRetry(RpcRequest request, RpcClient rpcClient) {
        this.rpcClient = rpcClient;

        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfException()
                .retryIfResult(a -> Objects.equals(a.getCode(), 500))
                .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        System.out.println("RetryListener：第" + attempt.getAttemptNumber() + "次调用");
                    }
                })
                .build();
        try {
            return retryer.call(() -> rpcClient.sentRequest(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RpcResponse.fail();
    }

}
