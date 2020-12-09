package com.example.redissondemo;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 哨兵模式
 */
@RestController
@RequestMapping("/redissonGuard")
public class redisControllerGuard {
    static RedissonClient redisson = null;

    static {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://192.168.1.118:26379")
                .addSentinelAddress("redis://192.168.1.118:26380", "redis://192.168.1.118:26381");

        redisson = Redisson.create(config);
        System.err.println("ok");
    }

    @GetMapping("test")
    public void redisson(String key) {
        RLock lock = redisson.getLock("myLock-guard");
        System.err.println("存入," + lock.getName());

        RMap<String, String> map = redisson.getMap("redissonGuard");
        map.put(key, key + 1);
        System.err.println("存入," + map.keySet());
    }
}
