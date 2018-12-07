package com.digital.dance.commons.serialize.json.utils;

import com.alibaba.fastjson.JSON;

public class JSONUtils
{
  public static String toJson(Object obj)
  {
    return JSON.toJSONString(obj);
  }

  public static <T> T toObject(String json, Class<T> clazz) {
    if ((json == null) || (json.trim().length() == 0)) {
      return null;
    }
    return JSON.parseObject(json, clazz);
  }
}