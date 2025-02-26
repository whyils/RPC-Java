package part1.Server.ratelimit.provider;


import part1.Server.ratelimit.RateLimit;
import part1.Server.ratelimit.impl.TokenBucketRateLimitImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WYL
 * @date 2025/2/26 11:19
 * @description: TODO
 */
public class RateLimitProvider {

    private Map<String, RateLimit> rateLimitMap = new HashMap<>();

    public RateLimit getRateLimit(String interfaceName) {

        if (!rateLimitMap.containsKey(interfaceName)) {

            RateLimit rateLimit = new TokenBucketRateLimitImpl(100,10);
            rateLimitMap.put(interfaceName, rateLimit);
        }

        return rateLimitMap.get(interfaceName);

    }

}
