package com.digital.dance.framework.codis.impl;

import com.alibaba.fastjson.JSON;


/**
 * 
 * JSON转换工具类
 * 
 * @author liuxiny
 *
 */
public class GsonUtils {

	public static String toJsonStr(Object object) {
		if( object == null ) return "null";
		return JSON.toJSONString(object);
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.trim().length() == 0 || "null".equalsIgnoreCase(json)) {
			return null;
		}
		return JSON.parseObject(json, clazz);
	}

	public static void main(String... args){
		java.util.Map hashMap = new java.util.HashMap();
		hashMap.put("aa", 1);
		hashMap.put("bb", 2);
		hashMap.put("cc", 3);
		hashMap.put("dd", 4);
		String sret1 = toJsonStr(hashMap);
		System.out.println(sret1);
		java.util.Map ret = toObject(toJsonStr(hashMap), java.util.Map.class);
		System.out.println(ret);
		String sret = GsonUtils.toJsonStr(null);
		System.out.println(sret);

	}
}
