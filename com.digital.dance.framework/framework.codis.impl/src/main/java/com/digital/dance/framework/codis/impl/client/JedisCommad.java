package com.digital.dance.framework.codis.impl.client;

import com.digital.dance.base.exception.utils.AssertUtil;
import com.digital.dance.framework.codis.client.RedisFactory;
import com.digital.dance.framework.codis.client.callback.RedisCommad;
import com.digital.dance.framework.codis.impl.CodisImpl;
import com.digital.dance.framework.infrastructure.commons.Log;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

public abstract class JedisCommad<T> implements RedisCommad<T> {

    private static final Log logger = new Log(JedisCommad.class);

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
            }

            try {
                result = this.execute(jedisCommands);
            } catch (Exception e) {
                logger.error("codis execute error", e);
                if (jedis != null) {
                    redisFactory.returnResource(jedis);
                }
            } finally {
                if (jedis != null) {
                    redisFactory.returnResource(jedis);
                }
            }


        } catch (Exception ex) {
            logger.error("codis execute error", ex);
            if (jedis != null) {
                redisFactory.returnResource(jedis);
            }
        }
        return result;
    }

}
