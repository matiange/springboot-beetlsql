package com.matiange.cache;

import com.matiange.cache.service.impl.CacheServiceImpl;
import com.matiange.cache.service.impl.CacheServiceJedisImpl;
import com.matiange.utils.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("singleton")
public class CacheServiceFactory {
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceFactory.class);

	private JedisCluster jc;
	private Jedis jedis;
	private JedisPool jedisPool;
	private String cacheOn;

	@Value("${redis.servers}")
	private String servers;
	@Value("${redis.enable}")
	private String enable;
	@Value("${redis.maxWaitMillis}")
	private String maxWaitMillis;
	@Value("${redis.maxTotal}")
	private String maxTotal;
	@Value("${redis.minIdle}")
	private String minIdle;
	@Value("${redis.maxIdle}")
	private String maxIdle;
	@Value("${redis.connectionTimeout}")
	private String connectionTimeout;
	@Value("${redis.cluster.soTimeout}")
	private String soTimeout;
	@Value("${redis.cluster.maxRedirections}")
	private String maxRedirections;
    @Value("${redis.password}")
	private String password;

	public JedisCluster getJc() {
		if (null == jc) init();
		return this.jc;
	}

	public JedisPool getjedisPool() {
		if (null == jedisPool) init();
		return this.jedisPool;
	}

	private void init() {
		if (StringHelper.isEmpty(servers)) {
			logger.warn("Reids服务地址未设置, 放弃初始化Redis服务!");
			return;
		}
		String[] serverArr = servers.split(";");

		logger.info("redis.servers=" + servers);
		logger.info("redis.enable=" + enable);
		logger.info("redis.maxWaitMillis=" + maxWaitMillis);
		logger.info("redis.maxTotal=" + maxTotal);
		logger.info("redis.minIdle=" + minIdle);
		logger.info("redis.maxIdle=" + maxIdle);
		logger.info("redis.connectionTimeout=" + connectionTimeout);
		logger.info("redis.cluster.soTimeout=" + soTimeout);
		logger.info("redis.cluster.maxRedirections=" + maxRedirections);
        logger.info("redis.password=" + password);

		if ("true".equals(enable)) {
			cacheOn = CacheServiceImpl.CACHE_ON;
		} else {
			cacheOn = CacheServiceImpl.CACHE_OFF;
		}
		logger.info("初始化Jedis===>开始");
		initJedis(serverArr);
		logger.info("初始化Jedis===>结束");
	}

	private void initJedis(String[] serverArr) {
	    if (StringUtils.isEmpty(password)) password = null;
		if (serverArr.length == 1) {//单节点Reids
			logger.info("Redis 配置为单节点模式!");
			String[] ipAndPort = serverArr[0].split(":");
			if (ipAndPort != null && ipAndPort.length > 1) {
				String ip = ipAndPort[0];
				String port = ipAndPort[1];
				jedis = new Jedis(ip, Integer.parseInt(port));
				JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
				//最大空闲连接数, 默认8个
				jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
				//最大连接数, 默认8个
				jedisPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
				//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
				jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
				//最小空闲连接数, 默认0
				jedisPoolConfig.setMinIdle(Integer.parseInt(minIdle));
                jedisPool = new JedisPool(jedisPoolConfig, ip, Integer.parseInt(port), Integer.parseInt(connectionTimeout), password);
				logger.info("JedisPool=" + jedisPool);
			}
		} else {//Redis集群
			logger.info("Redis 配置为集群模式!");
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			for (int i = 0; i < serverArr.length; i++) {
				if (!StringHelper.isEmpty(serverArr[i]) && serverArr[i].indexOf(":") != -1) {
					String[] ipAndPort = serverArr[i].split(":");
					if (ipAndPort != null && ipAndPort.length > 1) {
						String ip = ipAndPort[0];
						String port = ipAndPort[1];
						jedisClusterNodes.add(new HostAndPort(ip, Integer.parseInt(port)));
					}
				}
			}
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
			poolConfig.setMaxTotal(Integer.parseInt(maxTotal));
			poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
			poolConfig.setMinIdle(Integer.parseInt(minIdle));
			poolConfig.setTestOnBorrow(true);
			jc = new JedisCluster(jedisClusterNodes, Integer.parseInt(connectionTimeout), Integer.parseInt(soTimeout),
					Integer.parseInt(maxRedirections), password, poolConfig);
			logger.info("JedisCluster=" + jc);
		}
	}

	public CacheService createCacheService() {
		try {
			if (null == jc && null == jedis) init();
			if (jc != null) {
				CacheServiceImpl cacheClusterService = new CacheServiceImpl();
				cacheClusterService.setJc(jc);
				cacheClusterService.setCacheOn(cacheOn);
				return cacheClusterService;
			} else if (jedis != null) {
				CacheServiceJedisImpl cacheJedisService = new CacheServiceJedisImpl();
				cacheJedisService.setJc(jedis);
				cacheJedisService.setCacheOn(cacheOn);
				return cacheJedisService;
			} else
				return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
