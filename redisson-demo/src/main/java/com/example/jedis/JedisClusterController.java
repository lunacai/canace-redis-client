package com.example.jedis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import java.util.HashSet;
import java.util.Set;

/**
 * jedis集群模式
 */
@RestController
@RequestMapping("/jedisAll")
public class JedisClusterController {

    private static JedisCluster jedis;

    static {
        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
        // 添加节点
        hostAndPortsSet.add(new HostAndPort("192.168.1.118", 5001));
        hostAndPortsSet.add(new HostAndPort("192.168.1.118", 5002));
        hostAndPortsSet.add(new HostAndPort("192.168.1.118", 5003));
        hostAndPortsSet.add(new HostAndPort("192.168.1.118", 5004));
        hostAndPortsSet.add(new HostAndPort("192.168.1.118", 5005));
        hostAndPortsSet.add(new HostAndPort("192.168.1.118", 5006));
        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        jedis = new JedisCluster(hostAndPortsSet, jedisPoolConfig);
    }

    @GetMapping("test")
    public void jedisTest(String key) {
        //使用jedisCluster操作redis
        jedis.set("jedisAll"+key, key);
        System.out.println("输入内容：" + key);

        String str = jedis.get("jedisAll"+key);
        System.out.println("获取内容：" + str);
    }

    @ResponseBody
    @RequestMapping("/get")
    public Object getKey(String key) {
        jedis.set(key, "wushaung");
        return jedis.get(key);
    }
}
