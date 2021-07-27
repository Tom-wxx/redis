package com.example.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class RedisUtils {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /*
     * 指定缓存失效时间
     * */
    public boolean expire(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 根据key获取过期时间
     * */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /*
     * 删除缓存
     * */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /*
     * 普通缓存获取
     * */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /*
     * 普通缓存放入
     * */
    public Object set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
     * 普通缓存放入并设置时间
     * */
    public Boolean set(String key, Object value,Long time) {
        try {
           if (time>0){
               redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
           } else {
               set(key,value);
           }
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
     * 递增
     * */
    public long incr(String key, long delta){

            if (delta<0){
               throw new RuntimeException("递增因子必须大于0");
            } else {
                return redisTemplate.opsForValue().increment(key,delta);
            }

    }
    /*
     * 递减
     * */
    public long decr(String key, long delta){

        if (delta<0){
            throw new RuntimeException("递减因子必须大于0");
        } else {
            return redisTemplate.opsForValue().increment(key,-delta);
        }
    }
    /*
     * hashGet
     * */
    public Object hget(String key, String item){

        return redisTemplate.opsForHash().get(key,item);
    }
    /*
     *获取 hashKey对应的键值
     * */
    public Map<Object,Object> hmget(String key){

        return redisTemplate.opsForHash().entries(key);
    }
    /*
     *hashSet
     * */
    public boolean hmset(String key,Map<String,Object> map){

        try {
             redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /*
     *向一张hash表中放入数据，如果不存在将创建
     * */
    public boolean hset(String key,String item,Object value){

        try {
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    /*
     *向一张hash表中放入数据，如果不存在将创建
     * */
    public boolean hmset(String key,String item,Object value,long time){

        try {
            redisTemplate.opsForHash().put(key,item,value);
            if (time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


}
