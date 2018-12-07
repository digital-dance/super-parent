package com.digital.dance.framework.commons;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.crypto.Cipher;
import javax.servlet.http.Cookie;


/**
 * 
 * @author liuxiny
 *
 */
public class CookieUtil {
	private static Log logger = new Log(CookieUtil.class);
	
	public static String COOKIE_PATH = "/";
	public static final String SERVER_ROOM_USER = "SERVER_ROOM_USER";
	static String COOKIE_KYE = "aheaderscloudkeepercookie1234a";
	/**
	 * get user from cookie or session
	 * @param request
	 * @param isfromSession
	 * @return
	 * @throws Exception
	 */
	public static String getCookieOrSessionUser(HttpServletRequest request, Boolean isfromSession)throws Exception{
		String userStr = "";
		if(isfromSession && request.getSession() != null){
			Object userStrObj = request.getSession()
                    .getAttribute(CookieUtil.SERVER_ROOM_USER);
			userStr =  userStrObj != null ? userStrObj.toString() : null;
			logger.info("当前登陆的用户Session："+userStr);
		} else {
			userStr = CookieUtil.getCookieValue(request,CookieUtil.SERVER_ROOM_USER);
			logger.info("当前登陆的用户COOKIE："+userStr);
		}
		
		if( userStr != null && !userStr.equals("") ){
			
			String user = decode(userStr);
			request.getSession().setAttribute(CookieUtil.SERVER_ROOM_USER, userStr);
			return user;
		}
		return null;
	}
	
	public static String decode(String encodedStr){
		
		String decodedStr = encodedStr;
		
		String _COOKIE_KYE = AppPropsConfig.getPropertyValue("COOKIE_KYE");
		_COOKIE_KYE = _COOKIE_KYE == null || "".equals(_COOKIE_KYE) ? COOKIE_KYE : _COOKIE_KYE;
		
		if( encodedStr != null && !encodedStr.equals("") ){
			
			try {
				decodedStr = new String(AESEncryptUtil.aes(AESEncryptUtil.decodeHex(encodedStr), 
						Md5Util.hashEncode(_COOKIE_KYE, "UTF-8", "MD5", false)
				        .substring(8, 24)
				        .getBytes(), 
				        Cipher.DECRYPT_MODE), "UTF-8");
			} catch (Exception e) {
				
				logger.error("decode", e);
			}
			
			return decodedStr;
		}
		return decodedStr;
	}
	
	public static String encode(String dataStr){
		
		String _COOKIE_KYE = AppPropsConfig.getPropertyValue("COOKIE_KYE");
		_COOKIE_KYE = _COOKIE_KYE == null || "".equals(_COOKIE_KYE) ? COOKIE_KYE : _COOKIE_KYE;
		
		String result=AESEncryptUtil.encodeHex(AESEncryptUtil.aes(dataStr
                .getBytes(), Md5Util.hashEncode(_COOKIE_KYE, "UTF-8", "MD5", false)
                .substring(8, 24)
                .getBytes(),
                Cipher.ENCRYPT_MODE));
		return result;
	}
	
	public static boolean isHexString(String input) {
		if(input == null || "".equals(input)){
			return false;
		}
		String regex = "^[A-Fa-f0-9]+$";
		if (input.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * write user to cookie and session
	 * @param request
	 * @param response
	 * @param s
	 * @throws Exception
	 */
	public static void writeCookieAndSessionUser(HttpServletRequest request,HttpServletResponse response ,String s) throws Exception{

		String result = encode(s);
		CookieUtil.writeCookie(response,CookieUtil.SERVER_ROOM_USER, result);
		request.getSession().setAttribute(CookieUtil.SERVER_ROOM_USER, result);
	}
	
	public static String getCookieValueByName(HttpServletRequest request, String cookieKey) {
		Cookie cookieObj = getCookieByName(request, cookieKey);
		if (cookieObj != null && cookieObj.getValue()!=null) {
			return cookieObj.getValue();
		} else {
			return null;
		}
	}

	public static Cookie getCookieByName(HttpServletRequest request, String cookieKey) {
		Map<String, Cookie> cookieKV = ReadCookieMap(request);
		if (cookieKV.containsKey(cookieKey)) {
			Cookie cookieObj = (Cookie) cookieKV.get(cookieKey);
			return cookieObj;
		} else {
			return null;
		}
	}

	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookiecookieKVMap = new HashMap<String, Cookie>();
		Cookie[] cookieObjs = request.getCookies();
		if (null != cookieObjs) {
			for (Cookie cookie : cookieObjs) {
				cookiecookieKVMap.put(cookie.getName(), cookie);
			}
		}
		return cookiecookieKVMap;
	}

	public static void addCookie(HttpServletResponse response, Cookie cookieObj) {
		cookieObj.setValue(filterCookieValue(cookieObj.getValue()));
		response.addCookie(cookieObj);
	}

	public static String filterCookieValue(String valueStr) {
		if (valueStr != null) {
			valueStr = valueStr.replace("\n", "");
			valueStr = valueStr.replace("\r", "");
			valueStr = valueStr.replace("%0d", "");
			valueStr = valueStr.replace("%0D", "");
			valueStr = valueStr.replace("%0a", "");
			valueStr = valueStr.replace("%0A", "");
		}
		return valueStr;
	}
	
	public static Cookie getCookie(Cookie[] cookies, String cookieKey) {
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieKey.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	public static String getCookieValue(HttpServletRequest request, String cookieKey) {
		Cookie cookie = getCookie(request.getCookies(), cookieKey);
		if (cookie == null) {
			return getCookieValueByHeader(request, cookieKey);
		}else {
			return cookie.getValue();
		}
	}
	
	public static String getCookieValueByHeader(HttpServletRequest request, String cookieKey) {
		String cookieValue = null;
		
		String cookie = request.getHeader("Cookie");
		logger.info("cookie:" + cookie);
		if (cookie != null) {
			for (String e : cookie.split(";")) {
				String[] keyValue = e.split("=");
				if (keyValue.length != 2) {
					logger.info("item:"+e);
					continue;
				}
				String eKey = keyValue[0].trim();
				String eValue = keyValue[1].trim();
				if (eKey.equals(cookieKey)) {
					cookieValue = eValue;
					break;
				}
			}
		}
		return cookieValue;
	}

	public static boolean writeCookie(HttpServletResponse response, String cookieKey, String cookieValue){
		return writeCookie(response,cookieKey,cookieValue,-1);
	}

	public static boolean writeCookie(HttpServletResponse response, String cookieKey, String cookieValue,int expiry){
		Cookie cookieObj = new Cookie(cookieKey, filterCookieValue(cookieValue));
		cookieObj.setPath(COOKIE_PATH);
		if(expiry>=0){
			cookieObj.setMaxAge(expiry);
		}
		response.addCookie(cookieObj);
		return true;
	}
	
    

 
    public static String getCookieByName(String cookieKey, HttpServletRequest req){
    	return getCookieValue( req, cookieKey );
    }

                                                                                                                                
                                                                                                                               
    public static void clearCookie(HttpServletResponse response)throws Exception{
	   	Cookie cookieObj = new Cookie(SERVER_ROOM_USER, null);                                                                  
	   	cookieObj.setMaxAge(0);                                                                                                 
	   	response.addCookie(cookieObj);                                                                                          
   }

}
