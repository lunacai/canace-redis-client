//package com.example.redissondemo;
//
//import org.redisson.Redisson;
//import org.redisson.api.RLock;
//import org.redisson.api.RMap;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 云托管模式
// */
//@RestController
//@RequestMapping("/yun")
//public class redisControllerYun {
//    static RedissonClient redisson = null;
//
//    static {
//        Config config = new Config();
//        config.useReplicatedServers()
//                .setScanInterval(2000)
//                .addNodeAddress("redis://8.136.143.91:7001", "redis://8.136.143.91:7002", "redis://8.136.143.91:7003");
////                .addNodeAddress("redis://8.136.143.91:7004", "redis://8.136.143.91:7005", "redis://8.136.143.91:7006");
//
//        redisson = Redisson.create(config);
//        System.err.println("ok");
//    }
//
//    @GetMapping("test")
//    public void redisson(String key) {
//        RLock lock = redisson.getLock("myLock-all");
//        System.err.println("存入," + lock.getName());
//
//        RMap<String, String> map = redisson.getMap("canace");
//        map.put(key, key + 1);
//        System.err.println("存入," + map.keySet());
//    }
//}
