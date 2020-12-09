package com.example.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 单机模式
 */
@RestController
@RequestMapping("/lettcueOne")
public class lettcueOne {

    @GetMapping("test")
    public void lettcuetest(String key) {
        RedisURI redisUri = RedisURI.builder()                    // <1> 创建单机连接的连接信息
                .withHost("192.168.1.118")
                .withPort(6379)
                .withPassword("123456")
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient client = RedisClient.create(redisUri);
        // connection, 线程安全的长连接，连接丢失时会自动重连，直到调用 close 关闭连接。
        StatefulRedisConnection<String, String> connection = client.connect();
        // sync, 默认超时时间为 60s.
        RedisStringCommands<String, String> sync = connection.sync();
        sync.set("lettcueOne"+key, key);

        String value = sync.get("lettcueOne"+key);
        System.out.println("result>>>>>>>>>>>>>>" + value);
        // close connection
        connection.close();
        // shutdown
        client.shutdown();
    }
}
