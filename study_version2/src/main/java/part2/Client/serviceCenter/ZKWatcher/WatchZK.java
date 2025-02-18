package part2.Client.serviceCenter.ZKWatcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import part2.Client.cache.ServiceCache;

public class WatchZK {

    private CuratorFramework client;
    private ServiceCache cache;

    public WatchZK(CuratorFramework client, ServiceCache cache) {
        this.cache = cache;
        this.client = client;
    }

    public void watchToUpdate(String path) throws InterruptedException {

        CuratorCache curatorCache = CuratorCache.build(client, "/");

        curatorCache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData childData, ChildData childData1) {

                switch (type.name()) {
                    case "NODE_CREATED" :

                        String[] strings = parsePath(childData1);
                        if (strings.length <= 2) {
                            break;
                        }else {
                            String serviceName = strings[1];
                            String address = strings[2];
                            cache.addServiceToCache(serviceName, address);
                        }
                        break;
                    case "NODE_CHANGED" :

                        if (childData.getData() != null) {
                            System.out.println("修改前的数据" + new String(childData.getData()));
                        }else {
                            System.out.println("节点第一次赋值");
                        }
                        String[] oldPathList = parsePath(childData);
                        String[] newPathList = parsePath(childData1);
                        cache.replaceServiceAddress(oldPathList[1], oldPathList[2], newPathList[2]);
                        System.out.println("修改后的数据：" + new String(childData1.getData()));
                        break;
                    case "NODE_DELETED" :
                        String[] pathList = parsePath(childData);
                        if (pathList.length <= 2) break;
                        else cache.deleteService(pathList[1], pathList[2]);
                        break;
                    default:
                        break;
                }

            }
        });
        curatorCache.start();
    }

    private String[] parsePath(ChildData childData) {
        String s = new String(childData.getData());
        return s.split("/");
    }


}
