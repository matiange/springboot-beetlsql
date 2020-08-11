package com.matiange.cache.service.impl;

import com.alibaba.fastjson.JSON;
import com.matiange.cache.CacheService;
import com.matiange.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheServiceJedisImpl implements CacheService {
	public static final String CACHE_ON = "1";
	public static final String CACHE_OFF = "0";
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceJedisImpl.class);
	private String cacheOn;
	private Jedis jc;



	public Jedis getJc() {
		return jc;
	}

	public void setJc(Jedis jc) {
		this.jc = jc;
	}

	// 只在键不存在时，才对键进行设置操作
	private static final String NX = "NX";

	// 时间单位秒
	private static final String EX = "EX";

	public String getCacheOn() {
		return cacheOn;
	}

	public void setCacheOn(String cacheOn) {
		this.cacheOn = cacheOn;
	}

	/**
	 * 存储value，并提供是否覆盖选项
	 *
	 * @param key
	 * @param value
	 * @param coverOnExist
	 */
	public boolean set(String key, Object value, boolean coverOnExist) {
		logger.debug("key :" + key + " | value:" + value + " | coverOnExist:" + coverOnExist);
		if (StringHelper.isEmpty(key))
			return false;

		String result = "";

		if (coverOnExist)
			if ("java.lang.String".equals(value.getClass().getName()))
				result = jc.set(key, value.toString());
			else
				result = jc.set(key, JSON.toJSONString(value));
		else if ("java.lang.String".equals(value.getClass().getName()))
			result = String.valueOf(jc.setnx(key, value.toString()));
		else
			result = String.valueOf(jc.setnx(key, JSON.toJSONString(value)));

		if (StringHelper.isEmpty(result)) {
			return false;
		} else {
			if ("1".equals(result))
				result = "OK";
			if ("OK".equals(result.toUpperCase())) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 存储value，不覆盖
	 *
	 * @param key
	 * @param value
	 */
	public boolean set(String key, Object value) {
		return set(key, value, false);
	}

	/**
	 * 存储value，并覆盖
	 *
	 * @param key
	 * @param value
	 */
	public boolean replace(String key, Object value) {
		return set(key, value, true);
	}

	/**
	 * 覆盖存储value，并设置过期时间，单位秒
	 *
	 * @param key
	 * @param value
	 * @param second
	 */
	public boolean set(String key, Object value, int second) {
		if (StringHelper.isEmpty(key))
			return false;
		logger.debug("opt:setwithtime | key :" + key + " | value:" + value + " | second:" + second);

		String result = "";
		if (jc.exists(key)) {
			if ("java.lang.String".equals(value.getClass().getName())) {
				if (second <= 0) {
					result = jc.set(key, value.toString());
				} else {
					result = jc.setex(key, second, value.toString());
				}
			} else {
				if (second <= 0) {
					result = jc.set(key, JSON.toJSONString(value));
				} else {
					result = jc.setex(key, second, JSON.toJSONString(value));
				}
			}
		} else {
			if ("java.lang.String".equals(value.getClass().getName())) {
				if (second <= 0) {
					result = jc.set(key, value.toString());
				} else {
					result = jc.set(key, value.toString(), NX, EX, new Long(second).longValue());
				}
			} else {
				if (second <= 0) {
					result = jc.set(key, JSON.toJSONString(value));
				} else {
					result = jc.set(key, JSON.toJSONString(value), NX, EX, new Long(second).longValue());
				}
			}
		}
		if (StringHelper.isEmpty(result)) {
			return false;
		} else {
			if ("OK".equals(result.toUpperCase())) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 为key设置超时时间，单位秒
	 *
	 * @param key
	 * @return
	 */
	public boolean expire(String key, int seconds) {
		return jc.expire(key, seconds) == 1;
	}

	/**
	 * 名称为key的string的值附加append
	 *
	 * @param key
	 * @return
	 */
	public void append(String key, String append) {
		jc.append(key, append);
	}

	/**
	 * 根据key删除对象
	 *
	 * @param key
	 * @return
	 */
	public boolean delete(String key) {
		return jc.del(key).longValue() > 0;
	}

	/**
	 * 返回值的类型
	 *
	 * @param key
	 * @return
	 */
	public String type(String key) {
		return jc.type(key);
	}

	/**
	 * 根据key和对象类型获取对象
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		try {
			if (!CACHE_ON.equals(this.getCacheOn()))
				return null;
			if (StringHelper.isEmpty(key))
				return null;
			return jc.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * 根据key和对象类型获取对象
	 *
	 * @param key
	 * @param objectType
	 * @return
	 */
	public <T> T getObject(String key, Class<T> objectType) {
		try {
			if (!CACHE_ON.equals(this.getCacheOn()))
				return null;
			if (StringHelper.isEmpty(key))
				return null;

			String result = jc.get(key);
			return JSON.parseObject(result, objectType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * 返回指定key的剩余有效时间
	 *
	 * @param key
	 * @return
	 */
	public long ttl(String key) {
		return jc.ttl(key);
	}

	/**
	 * 存储list
	 *
	 * @param key
	 * @param list
	 * @param seconds
	 * @return
	 */
	public <T extends Object> boolean setList(String key, List<T> list, int seconds) {
		if (list != null) {
			boolean ret = false;
			if (seconds > 0)
				set(key, list, seconds);
			else
				replace(key, list);
//			logger.debug("setList key:" + key);
			return ret;
		} else {
			logger.error("缓存list出错，list为null");
		}
//		logger.debug("Cache List: [" + key + "]");
		return false;
	}

	/**
	 * 获取list
	 *
	 * @param key
	 * @param objectType
	 * @return
	 */
	public <T extends Object> List<T> getList(String key, Class<T> objectType) {
		List<T> list = null;
		try {
			if (!CACHE_ON.equals(this.getCacheOn()))
				return null;
			if (!StringHelper.isEmpty(key)) {
				String result = get(key);
				list = JSON.parseArray(result, objectType);
			} else {
				logger.error("获取list出错，key为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Load List: [" + key + "]");
		return list;
	}

	/**
	 * redis hset
	 *
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public <T> boolean setMapValue(String tableName, String key, T value) {
		if (StringHelper.isEmpty(tableName) || StringHelper.isEmpty(key))
			return false;
		long result = jc.hset(tableName, key, JSON.toJSONString(value));

		if (result == 1)
			return true;
		else
			return false;
	}

	/**
	 * redis hset
	 *
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public <T> boolean setMapValue(String tableName, String key, String value) {
		if (StringHelper.isEmpty(tableName) || StringHelper.isEmpty(key))
			return false;
		long result = jc.hset(tableName, key, value);

		if (result == 1)
			return true;
		else
			return false;
	}

	/**
	 * 保存map
	 *
	 * @param tableName
	 * @param dataMap
	 */
	public <T> boolean setMap(String tableName, Map<String, T> dataMap) {
		if (tableName == null || dataMap == null || dataMap.isEmpty())
			return false;
		Map<String, String> sMap = new HashMap<String, String>();
		for (String key : dataMap.keySet()) {
			Object value = dataMap.get(key);
			try {
				sMap.put(key, JSON.toJSONString(value));
			} catch (Exception e) {
				logger.error("class not cast ", e);
			}
		}
		String result = jc.hmset(tableName, sMap);

		if (StringHelper.isEmpty(result)) {
			return false;
		} else {
			if ("OK".equals(result.toUpperCase())) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 获取一个key下面的map的所有值
	 *
	 * @param tableName
	 * @param objectType
	 * @param keys
	 * @return
	 */
	public <T> List<T> getValueListFromMap(String tableName, Class<T> objectType, String... keys) {
		if (!CACHE_ON.equals(this.getCacheOn()))
			return null;
		if (tableName == null)
			return null;

		List<String> list = jc.hmget(tableName, keys);
		List<T> resultList = new ArrayList<T>();
		for (String value : list) {
			resultList.add(JSON.parseObject(value, objectType));
		}
		return resultList;
	}

	/**
	 * 获取map中的某个值
	 */
	public <T> T getObjectFromMap(String tableName, String key, Class<T> objectType) {
		if (!CACHE_ON.equals(this.getCacheOn()))
			return null;
		if (tableName == null) {
			return null;
		}
		String result = jc.hget(tableName, key);
		return JSON.parseObject(result, objectType);
	}

	/**
	 * 获取map中的某个值
	 */
	public String getStringFromMap(String tableName, String key) {
		if (!CACHE_ON.equals(this.getCacheOn()))
			return null;
		if (tableName == null) {
			return null;
		}
		String result = jc.hget(tableName, key);
		return result;
	}

	/**
	 * 获取整个map(map的值是string)
	 *
	 * @param tableName
	 * @return
	 */
	public Map<String, String> getMap(String tableName) {
		if (!CACHE_ON.equals(this.getCacheOn()))
			return null;
		if (StringHelper.isEmpty(tableName)) {
			return null;
		}
		return jc.hgetAll(tableName);
	}

	/**
	 * 获取整个map（map的值是具体对象）
	 *
	 * @param tableName
	 * @param mapValueType
	 * @return
	 */
	public <T> Map<String, T> getMap(String tableName, Class<T> mapValueType) {
		if (!CACHE_ON.equals(this.getCacheOn()))
			return null;
		if (tableName == null)
			return null;
		Map<String, String> tmp = jc.hgetAll(tableName);
		Map<String, T> resultMap = new HashMap<String, T>();
		for (String key : tmp.keySet()) {
			String value = tmp.get(key);
			resultMap.put(key, JSON.parseObject(value, mapValueType));
		}
		return resultMap;
	}

	/**
	 * 从map中删除一个或多个域的值
	 *
	 * @param tableName
	 * @param keys
	 */
	public long deleteFromMap(String tableName, String... keys) {
		if (StringHelper.isEmpty(tableName) || keys == null || keys.length < 1)
			return -1;
		return jc.hdel(tableName, keys);
	}

	/**
	 * 计数器+1
	 *
	 * @param key
	 * @return
	 */
	public Long incr(String key) {
		return jc.incrBy(key, 1);
	}

	/**
	 * 计数器+1
	 *
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		return jc.decrBy(key, 1);
	}

	public Long incr(String key, Long value) {
		if (value != null) {
			return jc.incrBy(key, value);
		} else {
			return jc.incrBy(key, 0);
		}
	}

	public Long decr(String key, Long value) {
		if (value != null) {
			return jc.decrBy(key, value);
		} else {
			return jc.decrBy(key, 0);
		}
	}

	/**
	 * 将对象做为字节流保存,并设置有效时间
	 */
	public boolean setByBytes(String key, Object value, int second) {
		boolean flag = false;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			if (StringHelper.isEmpty(key)) {
				flag = false;
			} else {
				logger.debug("opt:setex | key :" + key + " | value:" + value + " | second:" + second);
				oos = new ObjectOutputStream(bos);
				oos.writeObject(value);
				String result = "";
				result = jc.setex(key.getBytes(), second, bos.toByteArray());
				if (StringHelper.isEmpty(result)) {
					flag = false;
				} else {
					if ("OK".equals(result.toUpperCase())) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			flag = false;
		} finally {
			try {
				if (null != oos)
					oos.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 根据字节流查询对象
	 *
	 * @param key
	 * @return
	 */
	public Object getObjectByte(String key) {
		try {
			if (!CACHE_ON.equals(this.getCacheOn()))
				return null;
			if (StringHelper.isEmpty(key))
				return null;
			byte[] obj_byte = jc.get(key.getBytes());
			if (null == obj_byte || obj_byte.length <= 0) {
				return null;
			}
			ByteArrayInputStream bais = new ByteArrayInputStream(obj_byte);
			ObjectInputStream ois = null;
			Object obj = null;
			try {
				ois = new ObjectInputStream(bais);
				obj = ois.readObject();
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				try {
					if (null != ois)
						ois.close();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
			return obj;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * 根据字节流删除
	 *
	 * @param key
	 * @return
	 */
	public boolean deleteByte(String key) {
		boolean flag = false;
		if (StringHelper.isEmpty(key)) {
			flag = false;
		} else {
			logger.debug("deleteByte by key" + key);
			flag = jc.del(key.getBytes()).longValue() > 0;
		}
		return flag;
	}

	/**
	 * 判断key是否存在
	 */
	@Override
	public boolean exist(String key) {
		boolean flag = false;
		if (StringHelper.isEmpty(key)) {
			flag = false;
		} else {
			flag = jc.exists(key);
		}
		return flag;
	}

	@Override
	public Long lpush(String key, Object object) {
		return jc.lpush(key, JSON.toJSONString(object));
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return jc.ltrim(key, start, end);
	}

	@Override
	public <T> List<T> getRedisList(String key, long start, long end, Class<T> objectClass) {
		List<T> rtnList = new ArrayList<T>();
		List<String> list = jc.lrange(key, start, end);
		for (String value : list) {
			T t = JSON.parseObject(value, objectClass);
			rtnList.add(t);
		}
		return rtnList;
	}

	/**
	 * 通过前缀获取redis所有的key
	 *
	 * @param keyPre
	 * @return
	 */
//	@Override
//	public Set<String> getRedisKeys(String key) {
//		if (key == null || key.equals(""))
//			return jedis.keys("*");
//		else
//			return jedis.keys("*" + key + "*");
//	}

	/**
	 * 根据key的集合删掉对象
	 *
	 * @param keys
	 * @return
	 */
	@Override
	public boolean deleteObjectByKeys(List<String> keys) {
		int i = 0;
		for (String key : keys)
			if (jc.del(key).longValue() == 1)
				i++;
			else
				return false;

		return keys.size() == i;
	}

	@Override
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseDistributedLock(String lockKey, String requestId) {
		// TODO Auto-generated method stub
		return false;
	}
}