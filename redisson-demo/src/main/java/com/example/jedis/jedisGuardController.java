package com.example.jedis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 哨兵模式
 */
@RestController
@RequestMapping("/jedisGuard")
public class jedisGuardController {

    private static Jedis jedis;
    private static JedisSentinelPool pool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        Set<String> sentinels = new HashSet<>(Arrays.asList("192.168.1.118:26379",
                "192.168.1.118:26380","192.168.1.118:26381"));
        // 创建连接池
        pool = new JedisSentinelPool("mymaster", sentinels,jedisPoolConfig);
    }

    @GetMapping("test")
    public void jedisTest(String key) {
        jedis = pool.getResource();
        System.err.println("设置内容," + key);
        jedis.set("jedisGuard"+key,key);

        String str = jedis.get("jedisGuard"+key);
        System.err.println("获取内容," + str);

        jedis.close();
    }

}
