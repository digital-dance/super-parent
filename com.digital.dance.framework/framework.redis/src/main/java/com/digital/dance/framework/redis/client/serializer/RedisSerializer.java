package com.digital.dance.framework.redis.client.serializer;

public abstract interface RedisSerializer<T>
{
  public abstract byte[] serialize(T paramT);

  public abstract T deserialize(byte[] paramArrayOfByte);
}
