package com.lxp.shiro.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class JedisUtil {

    @Resource
    private JedisPool jedisPool;

    private Jedis getJedisResource(){
        return jedisPool.getResource();
    }

    public void set(byte[] key, byte[] value) {
        Jedis jedis = getJedisResource();
        try {
            jedis.set(key, value);
        }catch (Exception e){

        }finally {
            jedis.close();
        }
    }

    public void setTimeout(byte[] key, int i) {
        Jedis jedis = getJedisResource();
        try {
            jedis.expire(key, i);
        }catch (Exception e){

        }finally {
            jedis.close();
        }
    }

    public byte[] getValue(byte[] key) {
        Jedis jedis = getJedisResource();
        try {
            return jedis.get(key);
        }catch (Exception e){

        }finally {
            jedis.close();
        }
        return null;
    }

    public byte[] delete(byte[] key) {
        Jedis jedis = getJedisResource();
        try {
            jedis.del(key);
        }catch (Exception e){

        }finally {
            jedis.close();
        }
        return null;
    }

    public Set<byte[]> getKeys(String prefix){

        Jedis jedis = getJedisResource();
        try {
            return jedis.keys((prefix + "*").getBytes());
        }catch (Exception e){

        }finally {
            jedis.close();
        }
        return null;
    }
}
