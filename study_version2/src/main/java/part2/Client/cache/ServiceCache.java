package part2.Client.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCache {

    private static Map<String, List<String>> cache = new HashMap<>();

    // 添加服务到本地缓存中
    public void addServiceToCache(String serviceName, String address) {

        if (cache.containsKey(serviceName)){
            List<String> addressList = cache.get(serviceName);
            addressList.add(address);
            System.out.println("将name为" + serviceName + "和地址为" + address + "的服务添加到本地缓存");
        }else {
            List<String> addressList = new ArrayList<>();
            addressList.add(address);
            cache.put(serviceName, addressList);
            System.out.println("第一次将name为" + serviceName + "和地址为" + address + "的服务添加到本地缓存");
        }

    }

    // 根据新服务地址替换掉老服务地址
    public void replaceServiceAddress(String serviceName, String oldAddress, String newAddress) {
        if (cache.containsKey(serviceName)){
            List<String> addressList = cache.get(serviceName);
            addressList.remove(oldAddress);
            addressList.add(newAddress);
            System.out.println("将name为" + serviceName + " 地址为" + oldAddress + "的服务从本地缓存修改为地址为" + newAddress);
        }else {
            System.out.println(serviceName + "服务不存在，修改失败");
        }
    }

    // 从本地缓存中获取地址
    public List<String> getServiceByCache(String serviceName) {

        if (!cache.containsKey(serviceName)) {
            System.out.println("本地缓存无 " + serviceName + "服务数据");
            return null;
        }
        System.out.println("本地缓存有 " + serviceName + "服务数据");
        return cache.get(serviceName);

    }

    // 从服务中删除指定地址
    public void deleteService(String serviceName, String address) {
        if (!cache.containsKey(serviceName)) {
            return;
        }
        List<String> addressList = cache.get(serviceName);
        addressList.remove(address);
        System.out.println("将name为" + serviceName + "和地址为" + address + "服务删除成功");
    }

}
