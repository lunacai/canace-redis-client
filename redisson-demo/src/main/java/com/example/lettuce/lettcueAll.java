package com.example.lettuce;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 集群模式
 */
@RestController
@RequestMapping("/lettcueAll")
public class lettcueAll {

    @GetMapping("test")
    public void lettcuetest(String key) {
        ArrayList<RedisURI> list = new ArrayList<>();
        list.add(RedisURI.create("redis://192.168.1.118:5001"));
        list.add(RedisURI.create("redis://192.168.1.118:5002"));
        list.add(RedisURI.create("redis://192.168.1.118:5003"));
        list.add(RedisURI.create("redis://192.168.1.118:5004"));
        list.add(RedisURI.create("redis://192.168.1.118:5005"));
        list.add(RedisURI.create("redis://192.168.1.118:5006"));
        RedisClusterClient client = RedisClusterClient.create(list);
        // connection, 线程安全的长连接，连接丢失时会自动重连，直到调用 close 关闭连接。
        StatefulRedisClusterConnection<String, String> connect = client.connect();

        /* 同步执行的命令 */
        RedisAdvancedClusterCommands<String, String> commands = connect.sync();
        commands.set("lettcueAll"+key, key);

        String value = commands.get("lettcueAll"+key);
        System.out.println(value);

        connect.close();
        // shutdown
        client.shutdown();
    }
}
