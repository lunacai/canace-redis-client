package com.example.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单例模式
 */
@RestController
@RequestMapping("/jedisOne")
public class jedisController {
    @Autowired
    JedisPool jedisPool;

    @GetMapping("test")
    public void jedisTest(String key) {
        Jedis jedis=jedisPool.getResource();
        System.err.println("设置内容," + key);
        jedis.set("jedisOne"+key,key);

        String str = jedis.get("jedisOne"+key);
        System.err.println("获取内容," + str);

        jedis.close();
    }

}
