package com.matiange.cache;

import java.util.List;
import java.util.Map;

public interface CacheService {
	public String getCacheOn();
    /**
     * 存储value，并提供是否覆盖选项
     * @param key
     * @param value
     * @param coverOnExist
     */
    public boolean set(String key, Object value, boolean coverOnExist);
      
    /**
     * 存储value，不覆盖
     * @param key
     * @param value
     */
    public boolean set(String key, Object value);
      
    /**
     * 存储value，并覆盖
     * @param key
     * @param value
     */
    public boolean replace(String key, Object value);
    
    /**
     * 覆盖存储value，并设置过期时间，单位秒
     * @param key
     * @param value
     * @param second
     */
    public boolean set(String key, Object value, int second);
      
    /** 
     * 为key设置超时时间，单位秒
     * @param key 
     * @return 
     */  
    public boolean expire(String key, int seconds);
      
    /** 
     * 名称为key的string的值附加append
     * @param key 
     * @return 
     */  
    public void append(String key, String append);
      
    /** 
     * 根据key删除对象
     * @param key 
     * @return 
     */  
    public boolean delete(String key);
      
    /** 
     * 返回值的类型
     * @param key 
     * @return 
     */  
    public String type(String key);
      
    /** 
     * 根据key和对象类型获取对象
     * @param key 
     * @return
     */  
    public String get(String key);
    
    /** 
     * 根据key和对象类型获取对象
     * @param key 
     * @param objectType 
     * @return 
     */  
    public <T> T getObject(String key, Class<T> objectType);
      
    /** 
     * 返回指定key的剩余有效时间 
     * @param key 
     * @return 
     */  
    public long ttl(String key);
    
    /**
     * 存储list
     * @param key
     * @param list
     * @param seconds
     * @return
     */
    public <T extends Object> boolean setList(String key, List<T> list, int seconds);
    
    /**
     * 获取list
     * @param key
     * @param objectType
     * @return
     */
    public <T extends Object> List<T> getList(String key, Class<T> objectType);
    
    /** 
     * redis hset 
     * @param tableName 
     * @param key 
     * @param value 
     */  
    public <T> boolean setMapValue(String tableName, String key, T value);
    
    /** 
     * redis hset 
     * @param tableName 
     * @param key 
     * @param value 
     */  
    public <T> boolean setMapValue(String tableName, String key, String value);
      
    /** 
     * 保存map 
     * @param tableName 
     * @param dataMap 
     */  
    public <T> boolean setMap(String tableName, Map<String, T> dataMap);
      
    /** 
     * 获取一个key下面的map的所有值
     * @param tableName 
     * @param objectType 
     * @param keys 
     * @return 
     */  
    public <T> List<T> getValueListFromMap(String tableName, Class<T> objectType, String... keys);
      
    /** 
     * 获取map中的某个值 
     * @param tableName 
     * @param key 
     * @param objectType 
     * @return 
     */  
    public <T> T getObjectFromMap(String tableName, String key, Class<T> objectType);
      
    /** 
     * 获取map中的某个值 
     * @param tableName 
     * @param key 
     * @return
     */
    public String getStringFromMap(String tableName, String key);
    
    /** 
     * 获取整个map(map的值是string)
     * @param tableName 
     * @return 
     */  
    public Map<String,String>  getMap(String tableName);
      
    /** 
     * 获取整个map（map的值是具体对象） 
     * @param tableName 
     * @param mapValueType
     * @return 
     */  
    public <T> Map<String, T> getMap(String tableName, Class<T> mapValueType);
      
    /** 
     * 从map中删除一个或多个域的值 
     * @param tableName 
     * @param keys 
     */  
    public long deleteFromMap(String tableName, String... keys);
    
    /** 
     * 计数器+1
     * @param key 
     * @return 
     */  
    public Long incr(String key);
    
    
    /** 
     * 计数器+1
     * @param key 
     * @return 
     */  
    public Long decr(String key);
    
    public Long incr(String key, Long value);
    
    public Long decr(String key, Long value);

	/**
	 * 将对象做为字节流保存,并设置有效时间
	 * 
	 * @param key
	 * @param value
	 * @param second
	 * @return
	 */
	public boolean setByBytes(String key, Object value, int second);

	/**
	 * 根据字节流查询对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getObjectByte(String key);

	/**
	 * 根据字节流删除
	 * 
	 * @param key
	 * @return
	 */
	public boolean deleteByte(String key);
	
	/**
	 * 判断key 是否存在
	 * @param key
	 * @return
	 */
	public boolean exist(String key);
	
	
	/**
	 * 操作 redis list对象，添加
	 * key的右边添加多个object
	 * @param key
	 * @param object
	 * @return
	 */
	public Long lpush(String key, Object object);
	

	/**
	 * 操作redis list 对象，删除
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String ltrim(String key, long start, long end);
	
	/***
	 * 获取redis 的list 对象
	 * @param key
	 * @param objectClass
	 * @return
	 */
	public <T> List<T> getRedisList(String key, long start, long end, Class<T> objectClass);
	
	/**
	 * 通过前缀获取redis所有的key
	 * @param keyPre
	 * @return
	 */
//	public Set<String> getRedisKeys(String key);
	
	/**
	 * 根据key的集合删掉对象
	 * @param keys
	 * @return
	 */
	public boolean deleteObjectByKeys(List<String> keys);
	
	/**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime);
	
	 /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
	public boolean releaseDistributedLock(String lockKey, String requestId);
}
