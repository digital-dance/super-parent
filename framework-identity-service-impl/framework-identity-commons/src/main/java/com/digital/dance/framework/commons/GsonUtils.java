package com.digital.dance.framework.commons;

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
		return JSON.toJSONString(object);
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}
		return JSON.parseObject(json, clazz);
	}

}
