package com.example.redissondemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 集群模式
 */
@RestController
@RequestMapping("/redissonAll")
public class redisController {
    static RedissonClient redisson = null;

    static {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress("redis://192.168.1.118:5001")
                .addNodeAddress("redis://192.168.1.118:5002")
                .addNodeAddress("redis://192.168.1.118:5003")
                .addNodeAddress("redis://192.168.1.118:5004")
                .addNodeAddress("redis://192.168.1.118:5005")
                .addNodeAddress("redis://192.168.1.118:5006");

        redisson = Redisson.create(config);
        System.err.println("ok");
    }

    @GetMapping("test")
    public void redisson(String key) {
        RLock lock = redisson.getLock("myLock-all");
        System.err.println("存入," + lock.getName());

        RMap<String, String> map = redisson.getMap("redissonAll");
        map.put(key, key + 1);
        System.err.println("存入," + map.keySet());
    }
}
