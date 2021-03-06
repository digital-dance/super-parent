package com.digital.dance.framework.codis.impl;

import com.digital.dance.framework.codis.Codis;
import com.digital.dance.framework.codis.StringUtils;
import com.digital.dance.framework.codis.client.SuperRedisFactory;
import com.digital.dance.framework.codis.impl.client.JedisCommand;

import com.google.common.collect.Lists;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CodisImpl implements Codis {

  private static final Log logger = new Log(CodisImpl.class);
  private SuperRedisFactory redisFactory;
  private String salt;

  public String buildKey(String key){
    return GsonUtils.toJsonStr(CodisImpl.this.salt + key);
  }

  public void set(final String key, final Object value)
  {
    new JedisCommand<Object>()
    {
      public String execute(JedisCommands jedis)
      {
        jedis.set(buildKey( key ), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public void setnx(final String key, final Object value)
  {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis)
      {
        jedis.setnx(buildKey( key ), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public void setex(final String key, final long seconds, final Object value)
  {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis)
      {
        Number secondsNum = Long.valueOf(seconds);
        jedis.setex(buildKey( key ), secondsNum.intValue(), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public void hset(final String key, final String field, final Object value)
  {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {
        jedis.hset(buildKey( key ), GsonUtils.toJsonStr(CodisImpl.this.salt + field), GsonUtils.toJsonStr(value));
        return null;
      }
    }.run(redisFactory);
  }

  public <T> T getSet(final String key, final Class<T> clazz)
  {
    return new JedisCommand<T>()
    {
      public T execute(JedisCommands jedis) {
        String bytes = jedis.get(buildKey( key ));
        return GsonUtils.toObject(bytes, clazz);
      }
    }.run(redisFactory);
  }

  public <T> T get(final String key, final Class<T> clazz)
  {
    return new JedisCommand<T>()
    {
      public T execute(JedisCommands jedis)
      {
        String bytes = jedis.get(buildKey( key ));
        return GsonUtils.toObject(bytes, clazz);
      }
    }.run(redisFactory);
  }

  public <T> T hgetObject(final String key, String field, final Class<T> clazz)
  {
    return new JedisCommand<T>()
    {
      public T execute(JedisCommands jedis)
      {
        String bytes = jedis.get(buildKey( key ));
        return GsonUtils.toObject(bytes, clazz);
      }
    }.run(redisFactory);
  }

  public Long expire(final String key, final long seconds)
  {
    return (Long) new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis)
      {
        Number secondsNum = Long.valueOf(seconds);
        jedis.expire(buildKey( key ), secondsNum.intValue());
        return null;
      }
    }.run(redisFactory);
  }

  public void delete(final String key)
  {
    new JedisCommand<Object>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.del(buildKey( key ));
      }
    }.run(redisFactory);
  }

  public void hdel(final String key, final String field)
  {
    new JedisCommand<Object>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.hdel(buildKey( key ), new String[] { GsonUtils.toJsonStr(CodisImpl.this.salt + field) });
      }
    }.run(redisFactory);
  }

  public Long lpush(final String key, final Object value)
  {
    return (Long) new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.lpush(buildKey( key ), new String[] { GsonUtils.toJsonStr(value) });
      }
    }.run(redisFactory);
  }

  public Long rpush(final String key, final Object value)
  {
    return (Long) new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis)
      {
        return jedis.rpush(buildKey( key ), new String[] { GsonUtils.toJsonStr(value) });
      }
    }.run(redisFactory);
  }

  public String lpop(final String key)
  {
    return (String) new JedisCommand<String>()
    {
      public String execute(JedisCommands jedis)
      {
        return new String(jedis.lpop(buildKey( key )));
      }
    }.run(redisFactory);
  }

  public String rpop(final String key)
  {
    return (String) new JedisCommand<String>()
    {
      public String execute(JedisCommands jedis)
      {
        return new String(jedis.rpop(buildKey( key )));
      }
    }.run(redisFactory);
  }

  public void publish(final String channel, final String msg)
  {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis)
      {
        if(jedis instanceof Jedis)
            ((Jedis)jedis).publish(GsonUtils.toJsonStr(channel), GsonUtils.toJsonStr(msg));
        else if(jedis instanceof JedisCluster)
            ((JedisCluster)jedis).publish(GsonUtils.toJsonStr(channel), GsonUtils.toJsonStr(msg));
        return null;
      }
    }.run(redisFactory);
  }

  public void subscribe(final BinaryJedisPubSub binaryJedisPubSub, final byte[]... channels)
  {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {

        if( (jedis instanceof Jedis) )
          ((Jedis)jedis).subscribe(binaryJedisPubSub, channels);
        else if( (jedis instanceof JedisCluster) )
          ((JedisCluster)jedis).subscribe(binaryJedisPubSub, channels);

        return null;
      }
    }.run(redisFactory);
  }

  public void setSalt(String salt)
  {
    this.salt = salt;
  }

  public void setRedisFactory(SuperRedisFactory redisFactory)
  {
    this.redisFactory = redisFactory;
  }

  @Override
  public Set<String> getKeysByPrefix(final String prefix) {
    return new JedisCommand<Set<String>>()
    {
      public Set<String> execute(JedisCommands jds) {

        boolean isBroken = false;
        try {
          return keys("*" + prefix + "*", jds );

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
    return new JedisCommand<Integer>()
    {
      public Integer execute(JedisCommands jds) {

        boolean isBroken = false;
        try {

          Set<String> set = keys("*" + prefix + "*", jds);
          return set == null ? 0 : set.size();
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
        }

        return 0;
      }
    }.run(redisFactory);
  }

  public String[] delKeysByPattern(String pattern, JedisCommands jds){
    String[] delkeys = null;
    Set<String> keys = new TreeSet<String>();
    logger.debug("Start getting keys...");
    if( jds instanceof Jedis ){
      logger.debug("Keys gotten from Jedis!");
      keys = ((Jedis)jds).keys( pattern );
      delkeys = new String[keys != null ? keys.size() : 0];

      ( (Jedis)jds ).del( keys.toArray(delkeys) );

      return delkeys;
    } else if( !(jds instanceof JedisCluster) ){
      return delkeys;
    }
    Map<String, List<String>> map = new HashMap<>(6600);
    Map<String, JedisPool> clusterNodes = ( (JedisCluster)jds ).getClusterNodes();
    for( String k : clusterNodes.keySet() ){
      logger.debug("Getting keys from: " + k );
      JedisPool jp = clusterNodes.get(k);
      Jedis jedis = null;
      try {
        jedis = jp.getResource();
        if (!jedis.info("replication").contains("role:slave")) {
          Set<String> nodeKeys = jedis.keys( pattern );
          if ( nodeKeys != null && ( nodeKeys.size() > 0 ) ) {

            keys.addAll(nodeKeys);
            for ( String key : nodeKeys ){

              Integer slot = JedisClusterCRC16.getSlot( key );
              if ( map.containsKey( slot.toString() ) ) {
                map.get( slot.toString() ).add(key);
              } else {
                map.put( slot.toString(), Lists.newArrayList(key) );
              }

            }

          }
        }
      } catch(Exception e){
        logger.error( "Getting keys error: {}", e );
      } finally{
        logger.debug("jedis closed." );
        if ( jedis != null ) jedis.close();//用完一定要close这个链接！！！
      }

    }
    for (String slotItem : map.keySet()) {
      List<String> slotKeys = map.get(slotItem);
      try {
        ( (JedisCluster)jds ).del( slotKeys.toArray( new String[ slotKeys.size() ] ) );
        logger.debug( "slotItem:" + slotItem + ",del '" + slotKeys.size() + "'" + slotKeys.toString() );
      } catch (Exception ex) {
        logger.error( "slotItem:" + slotItem + ",del '" + slotKeys.size() + "' error: {}", ex );
      }
    }
    logger.debug("Keys deleted from JedisCluster!");
    delkeys = new String[ keys != null ? keys.size() : 0 ];
    return keys.toArray( delkeys );
  }

  public String[] delClusterKeys( JedisCommands jds, String... keys ){
        String[] delkeys = keys;

        logger.debug("Start deleting keys..." );
        if( jds instanceof Jedis ){
            logger.debug("Keys deleting from Jedis!" );

            ( (Jedis)jds ).del( keys );

            logger.debug("Keys deleted from Jedis!" );
            return delkeys;
        } else if( !( jds instanceof JedisCluster ) ){
            return delkeys;
        }
        Map<String, List<String>> map = new HashMap<>(6600);

        if ( keys != null && ( keys.length > 0 ) ) {

            for ( String key : keys ){

                Integer slot = JedisClusterCRC16.getSlot( key );
                if ( map.containsKey( slot.toString() ) ) {
                    map.get( slot.toString() ).add(key);
                } else {
                    map.put( slot.toString(), Lists.newArrayList(key) );
                }

            }

        }

        for ( String slotItem : map.keySet() ) {
            List<String> slotKeys = map.get( slotItem );
            try {
                ( (JedisCluster)jds ).del( slotKeys.toArray( new String[ slotKeys.size() ] ) );
                logger.debug( "slotItem:" + slotItem + ",del '" + slotKeys.size() + "'" + slotKeys.toString() );
            } catch ( Exception ex ) {
                logger.error( "slotItem:" + slotItem + ",del '" + slotKeys.size() + "' error: {}", ex );
            }
        }
        logger.debug("Keys deleted from JedisCluster!" );

        return keys;
  }

  public Set<String> keys(String pattern, JedisCommands jds){
    Set<String> keys = new TreeSet<String>();
    logger.debug("Start getting keys...");
    if( jds instanceof Jedis ){
      logger.debug("Keys gotten from Jedis!");
       return ((Jedis)jds).keys( pattern );
    } else if( !(jds instanceof JedisCluster) ){
      return keys;
    }

    Map<String, JedisPool> clusterNodes = ( (JedisCluster)jds ).getClusterNodes();
    for(String k : clusterNodes.keySet()){
      logger.debug("Getting keys from: k" );
      JedisPool jp = clusterNodes.get(k);
      Jedis connection = null;
      try {
        connection = jp.getResource();
        Set<String> nodeKeys = connection.keys(pattern);
        for (String nK : nodeKeys){
          if(!keys.contains(nK)){
            keys.add(nK);
          }
        }
//        keys.addAll(connection.keys(pattern));
      } catch(Exception e){
        logger.error("Getting keys error: {}", e);
      } finally{
        logger.debug("Connection closed.");
        if( connection != null ) connection.close();//用完一定要close这个链接！！！
      }
    }
    logger.debug("Keys gotten from JedisCluster!");
    return keys;
  }

  @Override
  public void delKeysByPrefix(final String prefix) {

    String[] prefixKeys = new JedisCommand<String[]>()
    {
      public String[] execute(JedisCommands jds) {

        boolean isBroken = false;
        String[] delKeys = null;
        int i = 0;
        try {
          delKeys = delKeysByPattern("*" + prefix + "*", jds);

//          Iterator<String> it = set.iterator();
//
//          keys = new String[set.size()];
//
//          while(it.hasNext()){
//            String keyStr = it.next();
//            keys[i] = keyStr;
//            i++;
//          }
//          if(i > 0){
//            if( jds instanceof Jedis ){
//              ((Jedis)jds).del(keys);
//            } else if( (jds instanceof JedisCluster) ){
//              ((JedisCluster)jds).del(keys);
//            }
//
//            delByKey(keys);
//          }
          if( delKeys != null ) i = delKeys.length;
          logger.debug(StringUtils.format(" [%s] redis key: del成功" , i));
        } catch (Exception e) {
          isBroken = true;
          logger.error(StringUtils.format( " [%s] redis key: del失败" , i), e);
          e.printStackTrace();
        }
        return delKeys;
      }

    }.run(redisFactory);
//    if( prefixKeys != null && prefixKeys.length > 0 ){
//      delByKey(prefixKeys);
//    }

  }

  @Override
  public <K> Boolean expire(final String key, final int timeout, final TimeUnit unit) {
    return new JedisCommand<Boolean>()
    {
      public Boolean execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          long rawTimeout = TimeoutUtils.toSeconds(timeout, unit);

          jedis.pexpire( buildKey( key ), rawTimeout );
          isBroken = true;
        } catch (Exception e) {

          e.printStackTrace();
        }

        return isBroken;
      }
    }.run(redisFactory);
  }

  @Override
  public <K> Boolean expire(final String key, final int milliseconds) {
    return new JedisCommand<Boolean>()
    {
      public Boolean execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          jedis.pexpire( buildKey( key ), milliseconds );
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
  public void expire(final int dbIndex, final String key, final int expireTime) {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jds) {
        if((jds instanceof Jedis))
          ((Jedis)jds).select(dbIndex);

        boolean isBroken = false;
        try {

          //jedis.set(key, value);
          if (expireTime > 0)
            jds.expire( buildKey( key ), expireTime );
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
    return new JedisCommand<T>()
    {
      public T execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          List<String> result = jedis.hmget( buildKey( mapkey ), key );
          if(null != result && result.size() > 0 ){
            String x = result.get(0);
            T resultObj = GsonUtils.toObject(x, requiredType);
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
  public void setVByMap(final String mapName, final String key, final Object value) {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          jedis.hset( buildKey( mapName ), key, GsonUtils.toJsonStr(value) );
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
  public Object delByMapKey(final String mapName, final String... dkey) {
    return new JedisCommand<Object>()
    {
      public Object execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          Long result = jedis.hdel( buildKey( mapName ), dkey );
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
    return new JedisCommand<Set<T>>()
    {
      public Set<T> execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          Set<T> set = new TreeSet<T>();
          Set<String> xx = jedis.smembers( buildKey( setKey ) );
          for (String bs : xx) {
            T t = GsonUtils.toObject(bs, requiredType);
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
    return new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          Long result = jedis.scard( buildKey( setKey ) );
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
    return new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          Long result = 0L;
          if(null == dkey){
            result = jedis.srem( buildKey( key ) );
          }else{
            result = jedis.del( buildKey( key ) );
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
    return new JedisCommand<String>()
    {
      public String execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          String result = jedis.srandmember( buildKey( key ) );
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
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          jedis.sadd( buildKey( setKey ), value );
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
    return new JedisCommand<Set<String>>()
    {
      public Set<String> execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          Set<String> result = jedis.smembers( buildKey( key ) );
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
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          jedis.rpush( buildKey( listKey ),  GsonUtils.toJsonStr(value) );
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

      long len = getLenByList(listKey);
      List<T> lRB = getVByList(listKey, 0, (int)len, requiredType);
      return lRB;
  }

  @Override
  public void setKVByList(final String listKey, final String value) {
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          jedis.rpush(buildKey( listKey ), value);
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
    new JedisCommand<Object>()
    {
      public Void execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          String[] vs = new String[values.size()];
          for(int i = 0; i <  values.size(); i++){
            vs[i] = GsonUtils.toJsonStr(values.get(i));
          }
          jedis.rpush( buildKey( listKey ), vs );
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
    return new JedisCommand<List<T>>()
    {
      public List<T> execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          List<T> list = new ArrayList<T>();
          List<String> xx = jedis.lrange( buildKey( listKey ),start,end );
          for (String bs : xx) {
            T t = GsonUtils.toObject(bs, requiredType);
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
    return new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          Long result = jedis.llen( buildKey( listKey ) );
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
    return new JedisCommand<Long>()
    {
      public Long execute(JedisCommands jedis) {
        Long result = 0L;
        boolean isBroken = false;
        try {

          if( (jedis instanceof JedisCluster) ){
//            for(String key : dkey){
//              result = ((JedisCluster)jedis).del(key);
//            }
            String[] rets = delClusterKeys( jedis, dkey );
            result =  new Long( rets.length );
          } else if( jedis instanceof Jedis ){
//            for(String key : dkey){
//              result = ((Jedis)jedis).del(key);
//            }
            result = ( (Jedis)jedis ).del( dkey );
          }
          return result;
        } catch (Exception e) {
          isBroken = true;
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        return result;
      }
    }.run(redisFactory);
  }

  @Override
  public Boolean exists(final String existskey) {
    return new JedisCommand<Boolean>()
    {
      public Boolean execute(JedisCommands jedis) {

        boolean isBroken = false;
        try {

          return jedis.exists( buildKey( existskey ) );
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