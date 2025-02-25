package part1.Client.serviceCenter.balance.impl;

import part1.Client.serviceCenter.balance.LoadBalance;

import java.util.List;

public class RoundLoadBalance implements LoadBalance {

    private int choose = -1;

    @Override
    public String balance(List<String> addressList) {

        choose++;
        choose = choose % addressList.size();
        String address = addressList.get(choose);
        System.out.println("负载均衡选择了" + address + "服务器进行调用");

        return address;
    }

    @Override
    public void addNode(String node) {

    }

    @Override
    public void delNode(String node) {

    }
}
