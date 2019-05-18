package com.digital.dance.framework.infrastructure.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.text.DateFormat;

import com.alibaba.fastjson.JSON;

/**
 * 
 * JSON转换工具类
 * 
 * @author liuxiny
 *
 */
public class GsonUtils {

	private static Gson gson = null;

	/**
	 * Gson的初始化
	 */
	static {
		GsonBuilder gsonB = new GsonBuilder();
		gsonB.registerTypeAdapter(java.util.Date.class, new Date2LongTypeAdapter()).setDateFormat(DateFormat.LONG);
		gson = gsonB.create();
	}

	/**
	 * json串转换为pojo
	 * @param jsonStr
	 * @param classZ
	 * @return
	 */
	public static <T> T getJson(String jsonStr, Class<T> classZ) {
		return gson.fromJson(jsonStr, classZ);
	}

	public static <T> T getJson(String jsonStr, Type typeZ) {
		return gson.fromJson(jsonStr, typeZ);
	}

	/**
	 * 将pojo转换为JSON串
	 * 
	 * @param object
	 * @return String
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

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
		String sret = GsonUtils.toJson(null);
		System.out.println(sret);

	}
}
