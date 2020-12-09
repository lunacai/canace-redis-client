package com.example.lettuce;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 哨兵模式
 */
@RestController
@RequestMapping("/lettcueGuard")
public class lettcueGuard {

    @GetMapping("test")
    public void lettcuetest(String key) {
        RedisURI redisUri = RedisURI.builder()
                .withSentinel("192.168.1.118", 26379)
                .withSentinel("192.168.1.118", 26380)
                .withSentinel("192.168.1.118", 26381)
                .withSentinelMasterId("mymaster")
                .build();


        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient,
                new Utf8StringCodec(), redisUri);
        // 只从主节点读取数据
        connection.setReadFrom(ReadFrom.SLAVE);

        RedisStringCommands<String, String> sync = connection.sync();
        sync.set("lettcueGuard"+key, key);

        String value = sync.get("lettcueGuard"+key);
        System.out.println("result>>>>>>>>>>>>>>" + value);
        // close connection
        connection.close();
        // shutdown
        redisClient.shutdown();
    }
}
