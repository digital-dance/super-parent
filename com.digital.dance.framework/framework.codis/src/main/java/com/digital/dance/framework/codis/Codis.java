package com.digital.dance.framework.codis;

import com.digital.dance.framework.redis.Redis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Codis extends Redis{
    public Set<String> getKeysByPrefix(String prefix);
    public Integer getKeysCountByPrefix(String prefix);
    public void delKeysByPrefix(String prefix);

    public <K> Boolean expire(K key, int timeout, final TimeUnit unit);
    public <K> Boolean expire(K key, long milliseconds);
    public void expire(int dbIndex, byte[] key, int expireTime);
    public <T> T getVByMap(String mapkey,String key , Class<T> requiredType);
    public void setVByMap(String mapkey,String key ,Object value);

    /**
     * 删除Map里的值
     * @param mapKey
     * @param dkey
     * @return
     */
    public Object delByMapKey(String mapKey ,String...dkey);

    /**
     * 往redis里取set整个集合
     *
     * @param <T>
     * @param setKey

     * @param requiredType
     * @return
     */
    public <T> Set<T> getVByList(String setKey,Class<T> requiredType);

    /**
     * 获取Set长度
     * @param setKey
     * @return
     */
    public Long getLenBySet(String setKey);

    /**
     * 删除Set
     * @param dkey
     * @return
     */
    public Long delSetByKey(String key,String...dkey);

    /**
     * 随机 Set 中的一个值
     * @param key
     * @return
     */
    public String srandmember(String key);

    /**
     * 往redis里存Set
     * @param setKey
     * @param value
     */
    public void setVBySet(String setKey,String value);

    /**
     * 取set
     * @param key
     * @return
     */
    public Set<String> getSetByKey(String key);

    /**
     * 往redis里的List里存元素
     * @param listKey
     * @param value
     */
    public void setVByList(String listKey,Object value);
    public <T> List<T> getVByListKey(String listKey, Class<T> requiredType);
    public void setKVByList(String listKey,String value);
    /**
     * 往redis里存List
     * @param listKey
     * @param values
     */
    public <T> void setVByListMutiElements(String listKey, List<T> values);

    /**
     * 往redis里取list
     *
     * @param <T>
     * @param listKey
     * @param start
     * @param end
     * @param requiredType
     * @return
     */
    public <T> List<T> getVByList(String listKey, int start, int end, Class<T> requiredType);

    /**
     * 获取list长度
     * @param listKey
     * @return
     */
    public Long getLenByList(String listKey);

    /**
     * 删除
     * @param dkey
     * @return
     */
    public Long delByKey(String...dkey);

    /**
     * 判断是否存在
     * @param existskey
     * @return
     */
    public Boolean exists(String existskey);
}
