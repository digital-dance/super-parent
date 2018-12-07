package com.digital.dance.framework.codis.impl;

import com.digital.dance.framework.codis.Codis;
import com.digital.dance.framework.codis.client.RedisFactory;
import com.digital.dance.framework.codis.impl.client.JedisCommad;
import com.digital.dance.framework.infrastructure.commons.GsonUtils;
import com.digital.dance.framework.infrastructure.commons.Log;
import com.digital.dance.framework.infrastructure.commons.SerializeUtil;
import com.digital.dance.framework.infrastructure.commons.StringTools;
import com.digital.dance.framework.redis.client.listener.MessageListener;

import org.springframework.data.redis.core.TimeoutUtils;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CodisImpl
  implements Codis
{
  private static final Log logger = new Log(CodisImpl.class);
  private RedisFactory redisFactory;
  private String salt;

  public void set(final String key, final Object value)
  {
    new JedisCommad<Object>()
    {
      public String execute(JedisCommands jedis)
      {
        jedis.set(GsonUtils.toJsonStr(CodisImpl.this.salt + key), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public void setnx(final String key, final Object value)
  {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis)
      {
        jedis.setnx(GsonUtils.toJsonStr(CodisImpl.this.salt + key), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public void setex(final String key, final long seconds, final Object value)
  {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis)
      {
        Number secondsNum = Long.valueOf(seconds);
        jedis.setex(GsonUtils.toJsonStr(CodisImpl.this.salt + key), secondsNum.intValue(), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public void hset(final String key, final String field, final Object value)
  {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        jedis.hset(GsonUtils.toJsonStr(CodisImpl.this.salt + key), GsonUtils.toJsonStr(CodisImpl.this.salt + field), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public <T> T getSet(final String key, final Class<T> clazz)
  {
    return new JedisCommad<T>()
    {
      public T execute(JedisCommands jedis) {
        String bytes = jedis.get(GsonUtils.toJsonStr(CodisImpl.this.salt + key));
        return GsonUtils.toObject(bytes, clazz);
      }
    }.run(redisFactory);
  }

  public <T> T get(final String key, final Class<T> clazz)
  {
    return new JedisCommad<T>()
    {
      public T execute(JedisCommands jedis)
      {
        String bytes = jedis.get(GsonUtils.toJsonStr(CodisImpl.this.salt + key));
        return GsonUtils.toObject(bytes, clazz);
      }
    }.run(redisFactory);
  }

  public <T> T hgetObject(final String key, String field, final Class<T> clazz)
  {
    return new JedisCommad<T>()
    {
      public T execute(JedisCommands jedis)
      {
        String bytes = jedis.get(GsonUtils.toJsonStr(CodisImpl.this.salt + key));
        return GsonUtils.toObject(bytes, clazz);
      }
    }.run(redisFactory);
  }

  public Long expire(final String key, final long seconds)
  {
    return (Long) new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis)
      {
        Number secondsNum = Long.valueOf(seconds);
        jedis.expire(GsonUtils.toJsonStr(CodisImpl.this.salt + key), secondsNum.intValue());
        return null;
      }
    }.run(redisFactory);
  }

  public void delete(final String key)
  {
    new JedisCommad<Object>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.del(GsonUtils.toJsonStr(CodisImpl.this.salt + key));
      }
    }.run(redisFactory);
  }

  public void hdel(final String key, final String field)
  {
    new JedisCommad<Object>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.hdel(GsonUtils.toJsonStr(CodisImpl.this.salt + key), new String[] { GsonUtils.toJsonStr(CodisImpl.this.salt + field) });
      }
    }.run(redisFactory);
  }

  public Long lpush(final String key, final Object value)
  {
    return (Long) new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.lpushx(GsonUtils.toJsonStr(CodisImpl.this.salt + key), new String[] { GsonUtils.toJsonStr(value) });
      }
    }.run(redisFactory);
  }

  public Long rpush(final String key, final Object value)
  {
    return (Long) new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.rpushx(GsonUtils.toJsonStr(CodisImpl.this.salt + key), new String[] { GsonUtils.toJsonStr(value) });
      }
    }.run(redisFactory);
  }

  public String lpop(final String key)
  {
    return (String) new JedisCommad<String>()
    {
      public String execute(JedisCommands jedis)
      {
        return new String(jedis.lpop(GsonUtils.toJsonStr(CodisImpl.this.salt + key)));
      }
    }.run(redisFactory);
  }

  public String rpop(final String key)
  {
    return (String) new JedisCommad<String>()
    {
      public String execute(JedisCommands jedis)
      {
        return new String(jedis.rpop(GsonUtils.toJsonStr(CodisImpl.this.salt + key)));
      }
    }.run(redisFactory);
  }

  public void publish(final String channel, final String msg)
  {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis)
      {
        if(jedis instanceof Jedis)
          ((Jedis)jedis).publish(GsonUtils.toJsonStr(channel), GsonUtils.toJsonStr(msg));
        return null;
      }
    }.run(redisFactory);
  }

  public void subscribe(final BinaryJedisPubSub binaryJedisPubSub, final byte[]... channels)
  {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        ((Jedis)jedis).subscribe(binaryJedisPubSub, channels);
        return null;
      }
    }.run(redisFactory);
  }

  public void setSalt(String salt)
  {
    this.salt = salt;
  }

  public void setRedisFactory(RedisFactory redisFactory)
  {
    this.redisFactory = redisFactory;
  }

  @Override
  public Set<String> getKeysByPrefix(final String prefix) {
    return new JedisCommad<Set<String>>()
    {
      public Set<String> execute(JedisCommands jds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
          jedis = ((Jedis)jds);
          Set<String> set = jedis.keys(prefix +"*");
          return set;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
        }

        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Integer getKeysCountByPrefix(final String prefix) {
    return new JedisCommad<Integer>()
    {
      public Integer execute(JedisCommands jds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
          jedis = ((Jedis)jds);
          Set<String> set = jedis.keys("*" + prefix +"*");
          return set == null ? 0 : set.size();
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
        }

        return 0;
      }
    }.run(redisFactory);
  }

  @Override
  public void delKeysByPrefix(final String prefix) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jds) {
        Jedis jedis = null;
        boolean isBroken = false;
        String[] keys = null;
        int i = 0;
        try {
          jds = ((Jedis)jedis);
          Set<String> set = jedis.keys("*" + prefix + "*");
          Iterator<String> it = set.iterator();

          keys = new String[set.size()];


          while(it.hasNext()){
            String keyStr = it.next();
            keys[i] = keyStr;
            i++;

            //jedis.del(keyStr);
            //delByKey(keyStr);
          }
          if(i > 0){
            jedis.del(keys);
            delByKey(keys);
          }
          logger.debug(StringTools.format(" [%s] redis key: del成功" , i));
        } catch (Exception e) {
          isBroken = true;
          logger.error(StringTools.format( " [%s] redis key: del失败" , i), e);
          e.printStackTrace();
        }
        return null;
      }

    }.run(redisFactory);
  }

  @Override
  public <K> Boolean expire(final K key, final int timeout, final TimeUnit unit) {
    return new JedisCommad<Boolean>()
    {
      public Boolean execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] skey = SerializeUtil.serialize(key);
          long rawTimeout = TimeoutUtils.toSeconds(timeout, unit);
          //jds.expire(skey, (int)rawTimeout);
          jds.pexpire(skey, rawTimeout);
          isBroken = true;
        } catch (Exception e) {

          e.printStackTrace();
        }

        return isBroken;
      }
    }.run(redisFactory);
  }

  @Override
  public <K> Boolean expire(final K key, final long milliseconds) {
    return new JedisCommad<Boolean>()
    {
      public Boolean execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] skey = SerializeUtil.serialize(key);

          //jds.expire(skey, (int)rawTimeout);
          jds.pexpire(skey, milliseconds);
          isBroken = true;
        } catch (Exception e) {

          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return isBroken;
      }
    }.run(redisFactory);
  }

  @Override
  public void expire(final int dbIndex, final byte[] key, final int expireTime) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
          jedis = ((Jedis)jds);
          jedis.select(dbIndex);
          //jedis.set(key, value);
          if (expireTime > 0)
            jedis.expire(key, expireTime);
        } catch (Exception e) {
          isBroken = true;
          throw e;

        }
        return null;
      }

    }.run(redisFactory);
  }

  @Override
  public <T> T getVByMap(final String mapkey, final String key, final Class<T> requiredType) {
    return new JedisCommad<T>()
    {
      public T execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] mkey = SerializeUtil.serialize(mapkey);
          byte[] skey = SerializeUtil.serialize(key);
          List<byte[]> result = jds.hmget(mkey, skey);
          if(null != result && result.size() > 0 ){
            byte[] x = result.get(0);
            T resultObj = SerializeUtil.deserialize(x, requiredType);
            return resultObj;
          }

        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public void setVByMap(final String mapkey, final String key, final Object value) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] mkey = SerializeUtil.serialize(mapkey);
          byte[] skey = SerializeUtil.serialize(key);
          byte[] svalue = SerializeUtil.serialize(value);
          jds.hset(mkey, skey,svalue);
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Object delByMapKey(final String mapKey, final String... dkey) {
    return new JedisCommad<Object>()
    {
      public Object execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[][] dx = new byte[dkey.length][];
          for (int i = 0; i < dkey.length; i++) {
            dx[i] = SerializeUtil.serialize(dkey[i]);
          }
          byte[] mkey = SerializeUtil.serialize(mapKey);
          Long result = jds.hdel(mkey, dx);
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return new Long(0);
      }
    }.run(redisFactory);
  }

  @Override
  public <T> Set<T> getVByList(final String setKey, final Class<T> requiredType) {
    return new JedisCommad<Set<T>>()
    {
      public Set<T> execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] lkey = SerializeUtil.serialize(setKey);
          Set<T> set = new TreeSet<T>();
          Set<byte[]> xx = jds.smembers(lkey);
          for (byte[] bs : xx) {
            T t = SerializeUtil.deserialize(bs, requiredType);
            set.add(t);
          }
          return set;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Long getLenBySet(final String setKey) {
    return new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          Long result = jds.scard(setKey);
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Long delSetByKey(final String key, final String... dkey) {
    return new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          Long result = 0L;
          if(null == dkey){
            result = jds.srem(key);
          }else{
            result = jds.del(key);
          }
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return new Long(0);
      }
    }.run(redisFactory);
  }

  @Override
  public String srandmember(final String key) {
    return new JedisCommad<String>()
    {
      public String execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          String result = jds.srandmember(key);
          return result;
        } catch (Exception e){
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public void setVBySet(final String setKey, final String value) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          jds.sadd(setKey, value);
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Set<String> getSetByKey(final String key) {
    return new JedisCommad<Set<String>>()
    {
      public Set<String> execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          Set<String> result = jds.smembers(key);
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public void setVByList(final String listKey, final Object value) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] lkey = SerializeUtil.serialize(listKey);
          byte[] svalue = SerializeUtil.serialize(value);
          jds.rpush(lkey, svalue);
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public <T> List<T> getVByListKey(final String listKey, final Class<T> requiredType) {
    return new JedisCommad<List<T>>()
    {
      public List<T> execute(JedisCommands jedis) {
        long len = getLenByList(listKey);
        List<T> lRB = getVByList(listKey, 0, (int)len, requiredType);
        return lRB;
      }
    }.run(redisFactory);
  }

  @Override
  public void setKVByList(final String listKey, final String value) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);

          jds.rpush(listKey, value);
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public <T> void setVByListMutiElements(final String listKey, final List<T> values) {
    new JedisCommad<Object>()
    {
      public Void execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] lkey = SerializeUtil.serialize(listKey);
          //List<byte[]> valueBs = new ArrayList<>();
          for(Object obj : values){
            byte[] svalue = SerializeUtil.serialize(obj);
            //valueBs.add(svalue);
            jds.rpush(lkey, svalue);
          }

        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public <T> List<T> getVByList(final String listKey, final int start, final int end, final Class<T> requiredType) {
    return new JedisCommad<List<T>>()
    {
      public List<T> execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] lkey = SerializeUtil.serialize(listKey);
          List<T> list = new ArrayList<T>();
          List<byte[]> xx = jds.lrange(lkey,start,end);
          for (byte[] bs : xx) {
            T t = SerializeUtil.deserialize(bs, requiredType);
            list.add(t);
          }
          return list;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }

        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Long getLenByList(final String listKey) {
    return new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] lkey = SerializeUtil.serialize(listKey);
          Long result = jds.llen(lkey);
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return null;
      }
    }.run(redisFactory);
  }

  @Override
  public Long delByKey(final String... dkey) {
    return new JedisCommad<Long>()
    {
      public Long execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[][] dx = new byte[dkey.length][];
          for (int i = 0; i < dkey.length; i++) {
            dx[i] = SerializeUtil.serialize(dkey[i]);
          }
          Long result = jds.del(dx);
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return new Long(0);
      }
    }.run(redisFactory);
  }

  @Override
  public Boolean exists(final String existskey) {
    return new JedisCommad<Boolean>()
    {
      public Boolean execute(JedisCommands jedis) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
          jds = ((Jedis)jedis);
          jds.select(0);
          byte[] lkey = SerializeUtil.serialize(existskey);
          return jds.exists(lkey);
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return false;
      }
    }.run(redisFactory);
  }
}