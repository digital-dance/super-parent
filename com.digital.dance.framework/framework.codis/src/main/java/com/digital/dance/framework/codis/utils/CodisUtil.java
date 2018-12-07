package com.digital.dance.framework.codis.utils;

import com.digital.dance.framework.codis.Codis;
import com.digital.dance.framework.codis.Codis;
import com.digital.dance.framework.redis.Redis;

public class CodisUtil {
	private static Redis redis;
	private static Codis codis;
	private static Long maxInactiveInterval;
	private static String isUrlSessionName;
	public static Redis getRedis() {
		return redis;
	}
	public static void setRedis(Redis redis) {
		CodisUtil.redis = redis;
	}
	public static Codis getCodis() {
		return codis;
	}
	public static void setCodis(Codis codis) {
		CodisUtil.codis = codis;
	}
	public static Long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	public static void setMaxInactiveInterval(Long maxInactiveInterval) {
		CodisUtil.maxInactiveInterval = maxInactiveInterval;
	}
	public static String getIsUrlSessionName() {
		return isUrlSessionName;
	}
	public static void setIsUrlSessionName(String isUrlSessionName) {
		CodisUtil.isUrlSessionName = isUrlSessionName;
	}
}
