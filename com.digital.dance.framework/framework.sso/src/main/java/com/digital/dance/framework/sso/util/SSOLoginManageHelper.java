package com.digital.dance.framework.sso.util;

import org.springframework.beans.factory.InitializingBean;

import com.digital.dance.commons.security.utils.RSACoderUtil;
import com.digital.dance.framework.codis.Codis;
//import com.digital.dance.framework.redis.Redis;


public class SSOLoginManageHelper implements InitializingBean {
	private Codis redis;
	private String casLoginurl;
	private String casErrorUrl;
	private String apiLoginUrl;

	private String loginServiceUrl;
	private String securitySalt;
	private String webSiteCode;
	private Long sessionDataExpiredSeconds;
	private Long sessionInfoExpiredSeconds;
	private Long cacheExpiredSeconds;
	public static final String USER_SESSION_KEY_CACHE = "user:session:key:";
	public static final String USER_APP_CACHE_KEY = "user:app:cache:key:";
	public static final String KEY_SEPERATOR = ":";
	public static final String TIMESTAMP = "timestamp";
	public static final String TOKEN = "token";
	public static final String SIGNATURE = "signature";
	public static final String BIZ_URL = "bizurl";
	public static final String HASH_VALUE = "hashValue";
	public static final String PUB_KEY = "pub_Key";
	public static final String MD5 = "md5";
	public static final String KEY_GROUP = "keygroup";
	public static final String KEY_VERSION = "keyversion";
	public static final String TTL = "ttl";
	public static final String CAS_SESSION_ID = "CasSessionId";
	public static final String SECURITY_DES = "security_des";
	public static final String WEB_SITE_CODE = "webSiteCode";
	public static final String WEB_QUERY_STRING = "querystring";

	public <T> T getSessionInfo(String sessionId, Class<T> clazz) {
		String key = USER_SESSION_KEY_CACHE + sessionId;
		if (sessionInfoExpiredSeconds != null) {
			this.redis.expire(key, sessionInfoExpiredSeconds);
		}
		return this.redis.get(key, clazz);
	}

	public void saveSessionInfo(String sessionId, Long seconds, Object object) {
		if (seconds != null) {
			sessionInfoExpiredSeconds = seconds;
		}
		this.redis.setex(USER_SESSION_KEY_CACHE + sessionId, sessionInfoExpiredSeconds, object);
	}

	public void deleteSessionInfo(String sessionId) {
		this.redis.delete(USER_SESSION_KEY_CACHE + sessionId);
	}

	public <T> T getSessionData(String sessionId, String dataKey, Class<T> clazz) {
		String key = USER_SESSION_KEY_CACHE + sessionId + KEY_SEPERATOR + dataKey;
		if (sessionDataExpiredSeconds != null) {
			this.redis.expire(key, sessionDataExpiredSeconds);
		}
		return this.redis.get(USER_SESSION_KEY_CACHE + sessionId + KEY_SEPERATOR + dataKey, clazz);
	}

	public void saveSessionData(String sessionId, String dataKey, Long seconds, Object object) {
		if (seconds != null) {
			sessionDataExpiredSeconds = seconds;
		}
		this.redis.setex(USER_SESSION_KEY_CACHE + sessionId + KEY_SEPERATOR + dataKey, seconds, object);
	}

	public void deleteSessionData(String sessionId, String dataKey) {
		this.redis.delete(USER_SESSION_KEY_CACHE + sessionId + KEY_SEPERATOR + dataKey);
	}
	
	public void expireSessionData(String sessionId, String dataKey, Long seconds) {
		String key = USER_SESSION_KEY_CACHE + sessionId + KEY_SEPERATOR + dataKey;
		if (seconds != null) {
			sessionDataExpiredSeconds = seconds;
		}
		if (sessionDataExpiredSeconds != null) {
			this.redis.expire(key, sessionDataExpiredSeconds);
		}
	}
	
	//for cas
	public void expireCacheData(String webSiteCode, String dataKey, Long seconds) {
		String key = USER_APP_CACHE_KEY + webSiteCode + KEY_SEPERATOR + dataKey;
		if (seconds != null) {
			cacheExpiredSeconds = seconds;
		}
		if (cacheExpiredSeconds != null) {
			this.redis.expire(key, cacheExpiredSeconds);
		}
	}
	
	/**
	 * getCacheData from cas
	 * @param webSiteCode
	 * @param dataKey
	 * @param clazz
	 * @return
	 */
	public <T> T getCacheData(String webSiteCode, String dataKey, Class<T> clazz) {
		String key = USER_APP_CACHE_KEY + webSiteCode + KEY_SEPERATOR + dataKey;
		if ( cacheExpiredSeconds != null && !SSOLoginManageHelper.PUB_KEY.equalsIgnoreCase( dataKey ) ) {
			this.redis.expire(key, cacheExpiredSeconds);
		}
		return this.redis.get(USER_APP_CACHE_KEY + webSiteCode + KEY_SEPERATOR + dataKey, clazz);
	}

	/**
	 * saveCacheData from cas 
	 * @param webSiteCode
	 * @param dataKey
	 * @param seconds
	 * @param object
	 */
	public void saveCacheData(String webSiteCode, String dataKey, Long seconds, Object object) {
		if ( seconds != null ) {
			cacheExpiredSeconds = seconds;
		}
		this.redis.setex(USER_APP_CACHE_KEY + webSiteCode + KEY_SEPERATOR + dataKey, seconds, object);
	}
	
	public void saveCacheData(String webSiteCode, String dataKey, Object object) {
		
		this.redis.set(USER_APP_CACHE_KEY + webSiteCode + KEY_SEPERATOR + dataKey, object);
	}

	/**
	 * deleteCacheData from cas 
	 * @param webSiteCode
	 * @param dataKey
	 */
	public void deleteCacheData(String webSiteCode, String dataKey) {
		this.redis.delete(USER_APP_CACHE_KEY + webSiteCode + KEY_SEPERATOR + dataKey);
	}
	
	//for app
	/**
	 * getCacheData from app
	 * @param dataKey
	 * @param clazz
	 * @return
	 */
	public <T> T getCacheData(String dataKey, Class<T> clazz) {
		
		return getCacheData(webSiteCode, dataKey, clazz);
	}

	/**
	 * saveCacheData from app
	 * @param dataKey
	 * @param seconds
	 * @param object
	 */
	public void saveCacheData(String dataKey, Long seconds, Object object) {
		saveCacheData(webSiteCode, dataKey, seconds, object);
	}

	/**
	 * deleteCacheData from app
	 * @param dataKey
	 */
	public void deleteCacheData(String dataKey) {
		deleteCacheData(webSiteCode, dataKey);
	}

	public Codis getRedis() {
		return this.redis;
	}

	public void setRedis(Codis redis) {
		this.redis = redis;
	}

	/**
	 * @return the casLoginurl
	 */
	public String getCasLoginurl() {
		return casLoginurl;
	}

	/**
	 * @param casLoginurl
	 *            the casLoginurl to set
	 */
	public void setCasLoginurl(String casLoginurl) {
		this.casLoginurl = casLoginurl;
	}

	/**
	 * @return the casErrorUrl
	 */
	public String getCasErrorUrl() {
		return casErrorUrl;
	}

	/**
	 * @param casErrorUrl
	 *            the casErrorUrl to set
	 */
	public void setCasErrorUrl(String casErrorUrl) {
		this.casErrorUrl = casErrorUrl;
	}

	public String getApiLoginUrl() {
		return this.apiLoginUrl;
	}

	public void setApiLoginUrl(String apiLoginUrl) {
		this.apiLoginUrl = apiLoginUrl;
	}

	public String getLoginServiceUrl() {
		return loginServiceUrl;
	}

	public void setLoginServiceUrl(String loginServiceUrl) {
		this.loginServiceUrl = loginServiceUrl;
	}

	public String getSecuritySalt() {
		return this.securitySalt;
	}

	public void setSecuritySalt(String securitySalt) {
		this.securitySalt = securitySalt;
	}

	/**
	 * @return the sessionDataExpiredSeconds
	 */
	public Long getSessionDataExpiredSeconds() {
		return sessionDataExpiredSeconds;
	}

	/**
	 * @param sessionDataExpiredSeconds
	 *            the sessionDataExpiredSeconds to set
	 */
	public void setSessionDataExpiredSeconds(Long sessionDataExpiredSeconds) {
		this.sessionDataExpiredSeconds = sessionDataExpiredSeconds;
	}

	/**
	 * @return the sessionInfoExpiredSeconds
	 */
	public Long getSessionInfoExpiredSeconds() {
		return sessionInfoExpiredSeconds;
	}

	/**
	 * @param sessionInfoExpiredSeconds
	 *            the sessionInfoExpiredSeconds to set
	 */
	public void setSessionInfoExpiredSeconds(Long sessionInfoExpiredSeconds) {
		this.sessionInfoExpiredSeconds = sessionInfoExpiredSeconds;
	}

	/**
	 * @return the cacheExpiredSeconds
	 */
	public Long getCacheExpiredSeconds() {
		return cacheExpiredSeconds;
	}

	/**
	 * @param cacheExpiredSeconds the cacheExpiredSeconds to set
	 */
	public void setCacheExpiredSeconds(Long cacheExpiredSeconds) {
		this.cacheExpiredSeconds = cacheExpiredSeconds;
	}

	/**
	 * @return the webSiteCode
	 */
	public String getWebSiteCode() {
		return webSiteCode;
	}

	/**
	 * @param webSiteCode the webSiteCode to set
	 */
	public void setWebSiteCode(String webSiteCode) {
		this.webSiteCode = webSiteCode;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String pubKey = (String)RSACoderUtil.getKey().get(RSACoderUtil.public_Key);
		this.saveCacheData(webSiteCode, SSOLoginManageHelper.PUB_KEY, pubKey);
		
	}

}