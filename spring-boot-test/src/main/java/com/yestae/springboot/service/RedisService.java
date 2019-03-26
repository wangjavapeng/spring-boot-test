package com.yestae.springboot.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 操作redis的service
 * @author wangpeng
 *
 */
@Service
public class RedisService {
	
	private static final ThreadLocal<Integer> redisDatabaseHolder = new ThreadLocal<Integer>();
	
	@Autowired
	private JedisPool jedisPool;
	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	private synchronized Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				Integer db = getDatabase();
				if (db != null) {
					resource.select(db);
				}
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	/**
	 * 设置当前Redis db
	 * 
	 * @param redisDatabase
	 */
	public static void setDatabase(Integer redisDatabase) {
		redisDatabaseHolder.set(redisDatabase);
	}

	/**
	 * 清空当前Redis db
	 */
	public static void clearDatabase() {
		redisDatabaseHolder.remove();
	}

	/**
	 * 获取当前Redis db
	 * 
	 * @return
	 */
	private static Integer getDatabase() {
		return redisDatabaseHolder.get();
	}
	
	/**
	 * 返还到连接池
	 * 
	 * @param jedisPool
	 * @param redis
	 */
	public static void returnResource(JedisPool jedisPool, Jedis jedis) {
		if (jedis != null) {
			// jedisPool.close();
			jedis.close();
		}
	}
    
	//限流
	public Boolean isLimit(String key, String limitCount, String sec){
		
		String filePath;
		Jedis jedis = null;
		try {
			filePath = RedisService.class.getResource("/").toURI().getPath() + "properties/limit.lua";
			File luaFile = new File(filePath);
			String luaScript = FileUtils.readFileToString(luaFile);
			
			jedis = getJedis();
			
			return 0 == (Long) jedis.eval(luaScript, Lists.newArrayList(key), Lists.newArrayList(limitCount, sec));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			returnResource(jedisPool, jedis);
		}
	}
									/*********************************************/
									/*				      公共方法					     */
									/*********************************************/
	
	
	public Boolean exists(String key){
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key);
		} finally {
			returnResource(jedisPool, jedis);
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
	public String get(String key){
		Jedis jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			//将连接返回到池中
			returnResource(jedisPool, jedis);
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
			returnResource(jedisPool, jedis);
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
		jedis.select(getDatabase());
		try {
			Long rec = jedis.lpush(key, value.toArray(new String[value.size()]));
			return rec;
		} finally {
			returnResource(jedisPool, jedis);
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
			returnResource(jedisPool, jedis);
		}
	}
}
