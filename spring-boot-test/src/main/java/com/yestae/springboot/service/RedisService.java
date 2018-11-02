package com.yestae.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 操作redis的service
 * @author wangpeng
 *
 */
@Service
public class RedisService {
	
	private final ThreadLocal<Integer> dbIndex = new ThreadLocal<Integer>();
	
	/**
	 * redis连接池
	 */
	@Autowired
    private JedisPool jedisPool;
    
	/**
	 * 获取jedis
	 * @return Jedis   jedis实例
	 */
	private Jedis getJedis(){
		int db = getDb();
		Jedis jedis = jedisPool.getResource();
		jedis.select(db);
		return jedis;
	}
									/*********************************************/
									/*				      公共方法					     */
									/*********************************************/
	
	public void selectDb(Integer db){
		if(db == null)
			db = 0;
		dbIndex.set(db);
	}

	private int getDb(){
		return dbIndex.get() == null ? 0 : dbIndex.get();
	}
	
	public Boolean exists(String key){
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key);
		} finally {
			jedis.close();
		}
	}
	
	
									/*********************************************/
									/*				      操作String					 */
									/*********************************************/
	
	/**
	 * 获取redis中的值
	 * @param key redis的key
	 * @return
	 */
	public Object get(String key){
		Jedis jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			//将连接返回到池中
			jedis.close();
		}
	}
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	public void set(String key, String value){
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
		} finally {
			//将连接返回到池中
			jedis.close();
		}
	}
	
						/*********************************************/
						/*				      操作list					 */
						/*********************************************/
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return list中现在的数量
	 */
	public Long lpush(String key, List<String> value){
		if(CollectionUtils.isEmpty(value))
			return null;
		
		Jedis jedis = getJedis();
		jedis.select(getDb());
		try {
			Long rec = jedis.lpush(key, value.toArray(new String[value.size()]));
			return rec;
		} finally {
			jedis.close();
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param start 开始下标
	 * @param end   结束下标
	 * 	start为0，end为-1时获取全部数据
	 * @return 指定redis中的返回数据
	 */
	public List<String> lrange(String key, long start, long end){
		Jedis jedis = getJedis();
		try {
			return jedis.lrange(key, start, end);
		} finally {
			jedis.close();
		}
	}
}
