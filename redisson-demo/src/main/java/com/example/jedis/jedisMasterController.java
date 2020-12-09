package com.example.jedis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.*;

/**
 * jedis实现主从
 */
@RestController
@RequestMapping("/jedisMaster")
public class jedisMasterController {
    private static JedisPool masterPool = null;
    private static JedisPool slave_1_pool = null;
    private static JedisPool slave_2_pool = null;

    static {
        masterPool = new JedisPool("192.168.1.118", 9001);
        slave_1_pool = new JedisPool("192.168.1.118", 9002);
        slave_2_pool = new JedisPool("192.168.1.118", 9003);
    }

    public Jedis getMaster() {
        return masterPool.getResource();
    }

    public Jedis getRead_1() {
        Jedis jedis = slave_1_pool.getResource();
        jedis.slaveof("192.168.1.118", 9001);
        return jedis;
    }

    public Jedis getRead_2() {
        Jedis jedis = slave_2_pool.getResource();
        jedis.slaveof("192.168.1.118", 9001);
        return jedis;
    }

    @GetMapping("test")
    public void jedisTest(String key) {
        Jedis master = getMaster();
        Jedis read1 = getRead_1();
        Jedis read2 = getRead_2();
        try {
            System.err.println("设置内容," + key);
            String name= "jedisMaster"+key;
            master.set(name, key);

            Thread.sleep(1000);
            String slave1_str = read1.get(name);
            System.err.println("slave1获取内容," + slave1_str);

            String slave2_str = read2.get(name);
            System.err.println("slave2获取内容," + slave2_str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            master.close();
            read1.close();
            read2.close();
        }
    }
}
