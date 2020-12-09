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
 * 单例模式
 */
@RestController
@RequestMapping("/redissonOne")
public class redisControllerOne {
    static RedissonClient redisson = null;

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.118:6379").setPassword("123456");
        redisson = Redisson.create(config);
        System.err.println("ok");
    }

    @GetMapping("test")
    public void redisson(String key) {
        RLock lock = redisson.getLock("myLockz-only");
        System.err.println("存入," + lock.getName());

        RMap<String, String> map = redisson.getMap("redissonOne");
        map.put(key, key + 1);
        System.err.println("存入," + map.keySet());
    }
}
