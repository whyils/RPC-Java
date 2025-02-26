package part2.Client.circuitBreaker;

import java.util.HashMap;
import java.util.Map;

public class CircuitBreakerProvider {

    private Map<String, CircuitBreaker> circuitBreakerMap = new HashMap<>();

    public synchronized CircuitBreaker getCircuitBreaker(String serviceName) {

        if (!circuitBreakerMap.containsKey(serviceName)) {
            System.out.println("serviceName = " + serviceName + "，创建了一个新的熔断器");
            CircuitBreaker circuitBreaker = new CircuitBreaker(1, 0.5, 10000);
            circuitBreakerMap.put(serviceName, circuitBreaker);
        }
        return circuitBreakerMap.get(serviceName);
    }

}
