package com.wyl.Client.serviceCenter.balance;

import java.util.List;

public interface LoadBalance {

    // 负责具体的负载均衡算法，返回分配的地址
    String balance(List<String> addressList);

    void addNode(String node);

    void delNode(String node);

}
