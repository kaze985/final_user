package com.geekgame.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    /**
     * 设置redisson缓存工厂，由于下面的工厂都是用的是redisson所以注意配置redissonclient
     *
     * @param client
     * @return
     */
    @Bean(name = "redissonconnectionfactory")
    public RedissonConnectionFactory getfactory(RedissonClient client) {
        return new RedissonConnectionFactory(client);
    }

    /**
     * redis单机配置
     *
     * @return
     */
    public RedissonClient getClient() {
        Config config = new Config();

        config.useSingleServer().setAddress("redis://${spring.redis.host}:${spring.redis.port}")
                .setTimeout(1000)
                .setRetryAttempts(3)
                .setRetryInterval(1000)
                .setPingConnectionInterval(1000)//解决redisson的timeout问题
                .setDatabase(3);
        return Redisson.create(config);
    }
}

