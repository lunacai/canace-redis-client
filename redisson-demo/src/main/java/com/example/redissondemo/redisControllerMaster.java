package com.example.redissondemo;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主从模式
 */
@RestController
@RequestMapping("/redissonMaster")
public class redisControllerMaster {
    static RedissonClient redisson = null;

    static {
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("redis://192.168.1.118:9001")
                .addSlaveAddress("redis://192.168.1.118:9002", "redis://192.168.1.118:9003");
        redisson = Redisson.create(config);
        System.err.println("ok");
    }

    @GetMapping("test")
    public void redisson(String key) {
        RLock lock = redisson.getLock("myLock-master");
        System.err.println("存入," + lock.getName());

        RMap<String, String> map = redisson.getMap("redissonMaster");
        map.put(key, key + 1);
        System.err.println("存入," + map.keySet());
    }
}
