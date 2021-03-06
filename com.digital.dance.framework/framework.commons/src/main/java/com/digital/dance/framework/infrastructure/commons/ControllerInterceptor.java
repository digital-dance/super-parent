package com.digital.dance.framework.infrastructure.commons;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 在FreeMarker模板中可以通过${baseUri}获取站点根的URl
 * @author liuxiny
 *
 */
public class ControllerInterceptor  extends HandlerInterceptorAdapter {
	
	public static final String CONTEXT_PATH = "baseUri"; 
	private Log log = new Log(ControllerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		log.info("拦截地址为"+request.getRequestURL());
		
		String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = ((HttpServletRequest)request).getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        log.debug("baseUrl=" + basePath);
        request.setAttribute(CONTEXT_PATH, basePath);

		super.preHandle(request, response, handler);
		
		//request.setAttribute("basePath",request.getContextPath());
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials, Pragma, Last-Modified, Cache-Control, Expires, Content-Type,Content-Language");
		((HttpServletResponse)response).setHeader("Access-Control-Expose-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials,X-Auth-Token");
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Credientials", "true");
		//response.setHeader("Access-Control-Allow-Headers", "X-Custom-Header");
//		response.setHeader("Access-Control-Allow-Credientials", "true");
		return true;
	}

}