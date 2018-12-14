package com.digital.dance.framework.codis.client.callback;

import com.digital.dance.framework.codis.client.RedisFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

public abstract interface RedisCommand<T>
{
//  public abstract T execute(Jedis operator);
  public abstract T execute(JedisCommands operator);
  public abstract T run(RedisFactory redisFactory);
}