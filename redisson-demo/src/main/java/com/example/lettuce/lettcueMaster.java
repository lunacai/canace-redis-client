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

import java.util.List;

import org.assertj.core.util.Lists;

/**
 * 主从模式
 */
@RestController
@RequestMapping("/lettcueMaster")
public class lettcueMaster {

    @GetMapping("test")
    public void lettcuetest(String key) {
        List<RedisURI> uris = Lists.newArrayList(
                RedisURI.create("redis://192.168.1.118:9001"),
                RedisURI.create("redis://192.168.1.118:9002"),
                RedisURI.create("redis://192.168.1.118:9003")
        );

        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient,
                new Utf8StringCodec(), uris);
        // 只从主节点读取数据
        connection.setReadFrom(ReadFrom.MASTER);

        RedisStringCommands<String, String> sync = connection.sync();
        sync.set("lettcueMaster"+key, key);

        String value = sync.get("lettcueMaster"+key);
        System.out.println("result>>>>>>>>>>>>>>" + value);
        // close connection
        connection.close();
        // shutdown
        redisClient.shutdown();
    }
}
