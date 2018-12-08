package com.digital.dance.framework.sso.filter;

import com.digital.dance.framework.infrastructure.commons.HttpClientUtil;
import com.digital.dance.framework.sso.entity.LoginInfo;
import com.digital.dance.framework.sso.entity.LoginedSessionList;
import com.digital.dance.framework.sso.enums.ClientTypeEnums;

import com.digital.dance.framework.sso.util.BrowserContext;
import com.digital.dance.framework.sso.util.LoginContext;
import com.digital.dance.framework.sso.util.SSOLoginManageHelper;

import com.digital.dance.commons.exception.BizException;

import com.digital.dance.commons.security.utils.RSACoderUtil;
import com.digital.dance.commons.serialize.json.utils.JSONUtils;
import com.digital.dance.framework.codis.utils.CodisUtil;
import com.digital.dance.framework.infrastructure.commons.Constants;
import com.digital.dance.framework.infrastructure.commons.Log;
import com.digital.dance.framework.infrastructure.commons.ResponseVo;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SSOLoginFilter implements Filter {
	private static final Log logger = new Log(SSOLoginFilter.class);
	private SSOLoginManageHelper ssologinManageHelper;
	//private SecurityKeyService securityKeyService;
	private String[] passedPaths = null;
	private String[] allowSuffix = null;
	private String[] bizloginUrl = null;
	private String[] readonlyUrls = null;
	private String currentWebSiteName = null;
	private String casWebSiteName = null;
	private String homePageUrl = null;
	private static final String headerName = "X-Auth-Token";
	private static final String init_param_allowUrl = "allowUrl";
	private static final String init_param_readonlyUrl = "readonlyUrl";
	private static final String init_param_homePageUrl = "homePageUrl";
	private static final String init_param_cas_web_site_name = "cas_web_site_name";
	private static final String init_param_allowSuffix = "allowSuffix";
	private static final String init_param_bizloginUrl = "bizloginUrl";
	
	public static final String CAS_COOKIE_NAME = "cas";

	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext applicationcontext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig.getServletContext());
		this.ssologinManageHelper = ((SSOLoginManageHelper) applicationcontext.getBean("ssologinManageHelper",
				SSOLoginManageHelper.class));
//		this.securityKeyService = ((SecurityKeyService) applicationcontext.getBean("securityKeyService",
//				SecurityKeyService.class));
		homePageUrl = filterConfig.getInitParameter(init_param_homePageUrl);
		currentWebSiteName = ssologinManageHelper.getWebSiteCode();
		
		casWebSiteName = filterConfig.getInitParameter(init_param_cas_web_site_name);
		
		String allowUrls = filterConfig.getInitParameter(init_param_allowUrl);
		passedPaths = (StringUtils.isNotBlank(allowUrls)) ? allowUrls.split(";") : new String[0];
		
		String allowSuffixs = filterConfig.getInitParameter(init_param_allowSuffix);
		allowSuffix = prepareAllowSuffixs( allowSuffixs );
		
		String bizloginUrls = filterConfig.getInitParameter(init_param_bizloginUrl);
		bizloginUrl = (StringUtils.isNotBlank(bizloginUrls)) ? bizloginUrls.split(";") : new String[0];
		
		String readonlyUrl = filterConfig.getInitParameter(init_param_readonlyUrl);
		readonlyUrls = (StringUtils.isNotBlank(readonlyUrl)) ? readonlyUrl.split(";") : new String[0];
			
	}
	
	private String[] prepareAllowSuffixs(String allowSuffixs) {
		return (StringUtils.isNotBlank(allowSuffixs)) ? (("(\\."+allowSuffixs +")$").replace(";", ")$;(\\.")).split(";") : new String[0];
	}
	private boolean isPassedRequest(String[] passedPaths, HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException{
		boolean flag = false;
		String requestPath = request.getRequestURL().toString().toLowerCase();
		//Matcher matcher = Pattern.matches(regex, input);
		for (String passedPath : passedPaths) {
			if(StringUtils.isBlank(passedPath)) continue;
			Pattern pattern = Pattern.compile( passedPath.toLowerCase() );  
	        Matcher matcher = pattern.matcher(requestPath);
			flag = matcher.find();
			if (flag) {
				logger.info(
						"sso client request path '" + requestPath + "'is matched,filter chain will be continued.");
				
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		HttpServletResponse response = (HttpServletResponse) rep;
		//begin
		(response).setHeader("Access-Control-Allow-Origin", "*");
		(response).setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		(response).setHeader("Access-Control-Allow-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials");
		(response).setHeader("Access-Control-Expose-Headers", "X-Auth-Token");
		response.setHeader("Access-Control-Allow-Credientials", "true");
		//end
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String requestPath = request.getRequestURL().toString();
		String casLoginurl = this.ssologinManageHelper.getCasLoginurl();

		LoginInfo loginInfo = null;

		if (requestPath.toLowerCase().indexOf(casLoginurl.split("\\?")[0].toLowerCase()) > -1){
			logger.info(
					"sso client request path '" + requestPath + "'is in login page, filter chain will be continued.");
			chain.doFilter(request, response);
			return;
		} else {
			loginInfo = getLoginInfoFromSession(request);
			if( loginInfo != null ){
				LoginContext.setLoginInfo(loginInfo);
				chain.doFilter(request, response);
				return;
			}
		}
		
		Throwable localThrowable0 = null;
		String clientType = setClientType(request);


		//String timestamp = null;
		String token = null;
		
		String pubKey = "";
		try {
//			pubKey = callCasPubKeyService;
			//TO DO get CasPubKey begin
			Map<String, String> map = new HashMap<String, String>();

			String pubKeyResponse = HttpClientUtil.executePostRequest(ssologinManageHelper.getLoginServiceUrl()+"/pubKey", new HashMap<String, String>(), map);

			ResponseVo pubKeyResponseVo = (ResponseVo) JSONUtils.toObject(pubKeyResponse, ResponseVo.class);
			pubKey = pubKeyResponseVo.getResult().toString();
			//TO DO get CasPubKey end
		} catch (Exception e1) {
			
			logger.error(e1.getMessage(), e1);
			throw new BizException("get pubKey error.", e1);
		}
//		String securitykey = "";
		String tokenJson = "";
		//String md5 = null;
		
		boolean passedFlag = isPassedRequest( passedPaths, request, response, chain )
					|| isPassedRequest( allowSuffix, request, response, chain )
					|| isPassedRequest( bizloginUrl, request, response, chain );
		if( passedFlag ){
			chain.doFilter(request, response);
			return;
		}

		//验证只读页
		passedFlag = isPassedRequest( readonlyUrls, request, response, chain );
		if( passedFlag ){
			String fakeToken = request.getParameter("fakeToken");
			try {
				if(StringUtils.isNotEmpty(fakeToken)){
//					fakeToken = URLDecoder.decode(fakeToken, "UTF-8");
					tokenJson = RSACoderUtil.decryptByPublicKey(pubKey, fakeToken);
					loginInfo = (LoginInfo) JSONUtils.toObject(tokenJson, LoginInfo.class);
					LoginContext.setLoginInfo(loginInfo);
					chain.doFilter(request, response);
					return;
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
		} else {
			token = request.getParameter("token");
			try {
				ResponseVo responseVo = new ResponseVo();
				if( StringUtils.isNotBlank( token ) ){
//					token = URLDecoder.decode(token, "UTF-8");
							//TO DO validationToken begin
					Map<String, String> pMap = new HashMap<String, String>();
					pMap.put("token", token);
					String validationToken = HttpClientUtil.executePostRequest(ssologinManageHelper.getLoginServiceUrl()+"/token/validation", new HashMap<String, String>(), pMap);
					responseVo = (ResponseVo) JSONUtils.toObject(validationToken, ResponseVo.class);
					//TO DO validationToken end
				}
				if( Constants.RETURN_CODE_SUCCESS.equals(responseVo.getCode()) ){

					tokenJson = RSACoderUtil.decryptByPublicKey(pubKey, token);

					loginInfo = (LoginInfo) JSONUtils.toObject(tokenJson, LoginInfo.class);

					if ((loginInfo == null) || (loginInfo.getUserId() == null)) {
						throw new BizException("decode token error.");
					}

					//TO DO save token to db token table begin
					Map<String, String> map = new HashMap<String, String>();
					map.put("token", token);
					String releaseToken = HttpClientUtil.executePostRequest(ssologinManageHelper.getLoginServiceUrl()+"/token/persistence", new HashMap<String, String>(), map);
					responseVo = (ResponseVo) JSONUtils.toObject(releaseToken, ResponseVo.class);
					//TO DO save token to db token table end
					if( Constants.RETURN_CODE_SUCCESS.equals(responseVo.getCode()) ){
						setLoginInfo2Session(request, loginInfo);
						LoginContext.setLoginInfo(loginInfo);
						chain.doFilter(request, response);
						return;
					}

				}
			} catch (Exception ex){
				logger.error("sso client error.", ex);
				throw new BizException("decode token error.");
			}
		}

		if (loginInfo == null) {
			
			ResponseVo responsedata = new ResponseVo();
			String loginUrl = "";
			
			try {
				loginUrl = casLoginurl + "?" + SSOLoginManageHelper.BIZ_URL + "=" + getAuthWebBizurl(request, clientType);
				if( ( StringUtils.isBlank( request.getServletPath() ) || "/index.html".equalsIgnoreCase( request.getServletPath() ) ) && StringUtils.isNotEmpty(homePageUrl)){
					loginUrl = homePageUrl;
				}
				logger.info("sendRedirect loginUrl:" + loginUrl);

			} catch (Exception ex) {
				String errorMsg = "sendRedirect, getAuthWebBizurl error:" + ex.getMessage();
				responsedata.setCode(Constants.ReturnCode.FAILURE.Code());
				responsedata.setMsg(errorMsg);
				logger.error(errorMsg, ex);						
			}
			if ((clientType == null) || (ClientTypeEnums.WEB.getClientType().equals(clientType))) {
			
					//发送http认证请求到CAS  
					response.sendRedirect(loginUrl);
					
					return;
				
			} else {
				responsedata.setCode(Constants.ReturnCode.REDIRECT.Code());
				responsedata.setMsg("sendRedirect from browser.");
				responsedata.setResult(loginUrl);
			}
			
			PrintWriter rsp = response.getWriter();
			responseOutput(JSONUtils.toJson(responsedata), rsp, localThrowable0);
			
			return;
		}

		//检查sso 认证错误
		String browserContext = "";
		try {
			browserContext = BrowserContext
					.buildBrowserContextValue(String.valueOf(ClientTypeEnums.WEB.getClientType()), request);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		LoginContext.setLoginInfo(loginInfo);
		chain.doFilter(request, response);
	}

	public static LoginedSessionList supportLoginSessions(String casSessionid, String appSessionId, SSOLoginManageHelper ssologinManageHelper){
		String loginedSessionsJson = ssologinManageHelper.getSessionData(LoginedSessionList.GLOBAL_SESSIONS, casSessionid, String.class);
		LoginedSessionList loginedSessions = (LoginedSessionList) JSONUtils.toObject(loginedSessionsJson, LoginedSessionList.class);
		if(loginedSessions == null){
			
			loginedSessions = new LoginedSessionList();
			List<String> sessions = new ArrayList<String>();
			sessions.add( appSessionId );
			loginedSessions.setLoginedSessions(sessions);
			
		} else if(loginedSessions.getLoginedSessions() == null) {
			
			List<String> sessions = new ArrayList<String>();
			sessions.add( appSessionId );
			loginedSessions.setLoginedSessions(sessions);
			
		} else if(!loginedSessions.getLoginedSessions().contains(appSessionId)) {
			
			loginedSessions.getLoginedSessions().add( appSessionId );
		}
		ssologinManageHelper.saveSessionData(LoginedSessionList.GLOBAL_SESSIONS, casSessionid, ssologinManageHelper.getSessionDataExpiredSeconds(), JSONUtils.toJson( loginedSessions ));
		return loginedSessions;
	}

	public static void responseOutput(String data, PrintWriter rsp, Throwable localThrowable0){
		try {			
			rsp.write(data);
		} catch (Throwable localThrowable) {
			localThrowable0 = localThrowable;
			logger.error("sso login error.invalide session." + localThrowable0.getMessage(), localThrowable0);
			throw localThrowable;
		} finally {
			if (rsp != null)

				try {
					rsp.close();
				} catch (Throwable x2) {					
					if (localThrowable0 != null)
						localThrowable0.addSuppressed(x2);
					logger.error("sso login error.invalide session." + localThrowable0.getMessage(), localThrowable0);
				}				
		}
	}

	public static String getAuthWebBizurl(HttpServletRequest request, String clientType) throws Exception{
		
		String bizUrlencoder = "";
		StringBuffer bizurlbuffer = new StringBuffer("");
		String bizurl;
		StringBuffer urlBuff = request.getRequestURL();
		
		String ajaxbizurl = request.getHeader("ajax-bizurl");
		bizurl = StringUtils.isNotBlank(ajaxbizurl) ? ajaxbizurl : urlBuff.toString();
		
		bizurlbuffer.append(bizurl);
		if (request.getQueryString() != null) {
			bizurlbuffer.append("?");
			bizurlbuffer.append(request.getQueryString());
		}
		
		bizUrlencoder = URLEncoder.encode(bizurlbuffer.toString(), "UTF-8");
		return bizUrlencoder + "&clientType="+setClientType(request);
	}

	public static String setClientType(HttpServletRequest request) {
		String clientType = request.getParameter("clientType");
		String requestType = request.getHeader("X-Requested-With");
		if (null == clientType && requestType == null ) {
			
			request.setAttribute("clientType", ClientTypeEnums.WEB.getClientType());
		} else if ( null == clientType && requestType != null ) { 
			
			if( requestType.toLowerCase().contains( "xmlhttprequest") ){
				request.setAttribute("clientType", ClientTypeEnums.WEBAJAX.getClientType());
			}
		} else if( StringUtils.isNotBlank( clientType ) ) {
			
			request.setAttribute( "clientType", clientType );
		} else {
			
			request.setAttribute( "clientType", ClientTypeEnums.WEB.getClientType() );
		}
		return request.getAttribute("clientType").toString();
	}

	public static void addCookie(HttpServletRequest request, HttpServletResponse response, Integer clientType,
			LoginInfo loginInfo, String sessionid) {
		Cookie cookie = new Cookie("clientType", String.valueOf(clientType));
		cookie.setMaxAge(loginInfo.getTokenTimeOut().intValue());
		response.addCookie(cookie);

		Cookie casCookie = new Cookie(CAS_COOKIE_NAME, sessionid);
		logger.error("cas session." + sessionid);
		casCookie.setMaxAge(-1);
		response.addCookie(casCookie);
//		if ((loginInfo != null) && (StringUtils.isNotEmpty(loginInfo.getButtJoint()))) {
//			Cookie mipCookie = new Cookie("buttJoint", loginInfo.getButtJoint());
//			mipCookie.setMaxAge(loginInfo.getTokenTimeOut().intValue());
//			response.addCookie(mipCookie);
//		}
		if ((loginInfo != null) && (StringUtils.isNotEmpty(loginInfo.getLdapUId()))) {
			Cookie uidCookie = new Cookie("uid", loginInfo.getUserId());
			uidCookie.setMaxAge(loginInfo.getTokenTimeOut().intValue());
			response.addCookie(uidCookie);
		}
	}

	public static HttpSession changeSession(HttpServletRequest request) {
		HttpSession oldSession = request.getSession();
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		Enumeration<String> e = oldSession.getAttributeNames();
		while ((e != null) && (e.hasMoreElements())) {
			String key = (String) e.nextElement();
			Object value = oldSession.getAttribute(key);
			map.put(key, value);
		}

		oldSession.invalidate();
		HttpSession newSession = request.getSession();

		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> stringObjectEntry = (Map.Entry<String, Object>) iter.next();
			newSession.setAttribute((String) stringObjectEntry.getKey(), stringObjectEntry.getValue());
		}
		return newSession;
	}

	/**
	 * app的认证请求通过CAS认证后，CAS会把自己的认证会话的标识存入当前app会话缓存中[以当前app会话标识包含在认证请求数据中为前提]
	 * 从本app会话缓存中取得CAS认证会话标识
	 * @param request
	 * @param clientType
	 * @return
	 */
	public static String getCasSessionId(HttpServletRequest request, String clientType, SSOLoginManageHelper ssologinManageHelper) {
		String casSessionId = null;
		String appSessionId = getAppSessionId(request, clientType);
		//app的认证请求通过CAS认证后，CAS会把自己的认证会话的标识存入当前app会话缓存中[以当前app会话标识包含在认证请求数据中为前提]
		//从本app会话缓存中取得CAS认证会话标识
		casSessionId = ssologinManageHelper.getSessionData(appSessionId, SSOLoginManageHelper.CAS_SESSION_ID, String.class);
		
		return casSessionId;
	}

	public static String getAppSessionId(HttpServletRequest request, String clientType) {
		HttpSession appSession = request.getSession();
		String appSessionId = (appSession != null) ? request.getSession().getId() : "";
		String headId = request.getHeader(headerName);
		String urlId = request.getParameter(headerName);
		
		if( "true".equalsIgnoreCase( CodisUtil.getIsUrlSessionName() ) ){
			if(headId != null && !"".equals(headId)){
				appSessionId = headId;
			} else if( urlId != null && !"".equals(urlId) ){
				appSessionId = urlId;
			}
		}
		return appSessionId;
	}

	public static LoginInfo getLoginInfoFromSession(HttpServletRequest request) {
		HttpSession appSession = request.getSession();
		Object loginInfo = (appSession != null) ? appSession.getAttribute("login_info") : null;
		if(loginInfo == null)return null;
		return (LoginInfo)loginInfo;
	}

	public static void setLoginInfo2Session(HttpServletRequest request, Object loginInfo) {
		HttpSession appSession = request.getSession();
		if(appSession != null) appSession.setAttribute("login_info", loginInfo);
	}

	public void destroy() {
	}
}