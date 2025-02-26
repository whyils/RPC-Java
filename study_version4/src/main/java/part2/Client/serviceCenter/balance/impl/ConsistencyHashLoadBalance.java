package part2.Client.serviceCenter.balance.impl;

import part2.Client.serviceCenter.balance.LoadBalance;

import java.util.*;

public class ConsistencyHashLoadBalance implements LoadBalance {

    // 虚拟节点
    private static final int VIRTUAL_NUM = 5;
    // 保存虚拟节点的有序map，key是hash值，value是虚拟节点名称
    private SortedMap<Integer,String> shards = new TreeMap<>();
    // 真实节点的集合
    private List<String> realNodes = new ArrayList<>();
    // 模拟真实环境服务器
    private String[] servers = null;

    // 初始化服务节点，将真实的服务节点初始化为虚拟节点
    private void init(List<String> serviceList) {

        for (String service : serviceList) {

            realNodes.add(service);
            System.out.println("真实节点【" + service + "】被添加");

            for (int i = 0; i < VIRTUAL_NUM; i++) {

                // 虚拟节点命名
                String virtualNode = service + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.put(hash, virtualNode);
                System.out.println("虚拟节点【" + virtualNode + "】被添加");

            }

        }

    }

    public String getService(String node, List<String> serviceList) {
        init(serviceList);
        // 根据请求标识获取对应hash值
        int hash = getHash(node);
        Integer key = null;
        SortedMap<Integer, String> tailedMap = shards.tailMap(hash);

        // 如果没有找到，说明该hash比集合中所有的虚拟节点hash值都大，取第一个虚拟节点（环状）
        // 如果找到了，取第一个虚拟节点即可
        if (tailedMap.isEmpty()) {
            key = shards.lastKey();
        }else {
            key = tailedMap.firstKey();
        }

        String virtualNode = shards.get(key);
        return virtualNode.substring(0, virtualNode.indexOf("&&"));

    }

    // FNV1_32_HASH算法
    //该方法用于计算字符串的哈希值，哈希算法基于一个常见的算法，使用了 FNV-1a 哈希和一些额外的位运算，确保哈希值均匀分布
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    @Override
    public String balance(List<String> addressList) {

        String random = UUID.randomUUID().toString();
        return getService(random,addressList);
    }

    @Override
    public void addNode(String node) {

        if (!realNodes.contains(node)) {
            realNodes.add(node);
            System.out.println("真实节点【" + node + "】上线添加");
            for (int i = 0; i < VIRTUAL_NUM; i++) {

                // 虚拟节点命名
                String virtualNode = node + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.put(hash, virtualNode);
                System.out.println("虚拟节点【" + virtualNode + "】hash : " + hash + ",被添加");

            }
        }
    }

    @Override
    public void delNode(String node) {
        if (realNodes.contains(node)) {
            realNodes.remove(node);
            System.out.println("真实节点【" + node + "】被删除");
            for (int i = 0; i < VIRTUAL_NUM; i++) {

                // 虚拟节点命名
                String virtualNode = node + "&&VN" + i;
                int hash = getHash(virtualNode);
                shards.remove(hash);
                System.out.println("虚拟节点【" + virtualNode + "】hash : " + hash + ",被删除");

            }
        }
    }
}
