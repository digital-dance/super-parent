package com.digital.dance.framework.codis.impl.client;

import com.digital.dance.base.exception.utils.AssertUtil;
import com.digital.dance.framework.codis.client.RedisFactory;
import com.digital.dance.framework.codis.client.callback.RedisCommand;
import com.digital.dance.framework.infrastructure.commons.Log;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

public abstract class JedisCommand<T> implements RedisCommand<T> {

    private static final Log logger = new Log(JedisCommand.class);

    @Override
    public T run(RedisFactory redisFactory) {
        AssertUtil.assertNotNull(redisFactory, "redis factory not set");
        Jedis jedis = null;
        JedisCluster jedisCluster = null;
        JedisCommands jedisCommands = null;
        T result = null;
        try {

            jedisCluster = redisFactory.getJedisCluster();
            if (jedisCluster == null){
                jedis = redisFactory.getResource();
                jedisCommands = jedis;
            } else {
                jedisCommands = jedisCluster;
                if (jedis != null) {
                    redisFactory.returnResource(jedis);
                }
            }

            try {

                if( jedisCommands != null ){
                    result = this.execute(jedisCommands);
                }

            } catch (Exception e) {
                logger.error("codis execute error", e);

                try {
                    if (jedis != null) {
                        redisFactory.returnResource(jedis);
                    }

                } catch (Exception ex){
                    logger.error("codis execute error", ex);
                }
                try {

                    if (jedisCluster != null){
                        jedisCluster.close();
                    }
                } catch (Exception ex){
                    logger.error("codis execute error", ex);
                }
            } finally {
                try {
                    if (jedis != null) {
                        redisFactory.returnResource(jedis);
                    }

                } catch (Exception ex){
                    logger.error("codis execute error", ex);
                }
                try {

                    if (jedisCluster != null){
                        jedisCluster.close();
                    }
                } catch (Exception ex){
                    logger.error("codis execute error", ex);
                }
            }


        } catch (Exception ex) {
            logger.error("codis execute error", ex);
            try {
                if (jedis != null) {
                    redisFactory.returnResource(jedis);
                }

            } catch (Exception e){
                logger.error("codis execute error", e);
            }
            try {

                if (jedisCluster != null){
                    jedisCluster.close();
                }
            } catch (Exception e){
                logger.error("codis execute error", e);
            }
        }finally {
            try {
                if (jedis != null) {
                    redisFactory.returnResource(jedis);
                }

            } catch (Exception ex){
                logger.error("codis execute error", ex);
            }
            try {

                if (jedisCluster != null){
                    jedisCluster.close();
                }
            } catch (Exception ex){
                logger.error("codis execute error", ex);
            }
        }
        return result;
    }

}
