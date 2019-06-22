package com.digital.dance.framework.redis;

//import com.digital.dance.framework.redis.client.listener.MessageListener;
import redis.clients.jedis.BinaryJedisPubSub;

public abstract interface Redis
{
  public abstract void set(String paramString, Object paramObject);

  public abstract void setnx(String paramString, Object paramObject);

  public abstract void setex(String paramString, long paramLong, Object paramObject);

  public abstract <T> T getSet(String paramString, Class<T> paramClass);

  public abstract <T> T get(String paramString, Class<T> paramClass);

  public abstract void hset(String paramString1, String paramString2, Object paramObject);

  public abstract <T> T hgetObject(String paramString1, String paramString2, Class<T> paramClass);

  public abstract void delete(String paramString);

  public abstract void hdel(String paramString1, String paramString2);

  public abstract Long expire(String paramString, long paramLong);

  public abstract Long lpush(String paramString, Object paramObject);

  public abstract Long rpush(String paramString, Object paramObject);

  public abstract String lpop(String paramString);

  public abstract String rpop(String paramString);

  public abstract void publish(String paramString1, String paramString2);

  public abstract void subscribe( BinaryJedisPubSub binaryJedisPubSub, byte[]... channels );
}