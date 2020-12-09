package com.example.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class JedisConfig extends CachingConfigurerSupport {

    private Logger logger= LoggerFactory.getLogger(JedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public JedisPool redisPoolFactory(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        JedisPool jedisPool=new JedisPool(jedisPoolConfig,host,port,timeout,password);
        logger.info("JedisPool连接成功："+host+":"+port);
        return jedisPool;
    }

//    @Bean
//    public JedisSentinelPool(String masterName, Set<String> sentinels,
//                             final GenericObjectPoolConfig poolConfig, final String password) {
//        this(masterName, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
//    }
//
//    @Bean
//    public ShardedJedisPool(final GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards,
//                            Hashing algo) {
//        this(poolConfig, shards, algo, null);
//    }
}