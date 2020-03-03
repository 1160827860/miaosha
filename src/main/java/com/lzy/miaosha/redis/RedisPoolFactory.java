package com.lzy.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description redis数据库连接池
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Service
public class RedisPoolFactory {

	@Autowired
	RedisConfig redisConfig;

	/**
	 * 创建一个数据库连接池
	 * @return 数据库连接池对象
	 */
	@Bean
	public JedisPool JedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
		poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()  );
		JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout(), redisConfig.getPassword(), 0);
		return jp;
	}
	
}
