package com.digital.dance.framework.commons;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.Filter;

import javax.servlet.ServletException;

/**
 * 
 * @author liuxiny
 *
 */
import javax.servlet.FilterConfig;
import java.io.IOException;

public class XssFilter implements Filter {
	Log log = new Log(XssFilter.class);
	FilterConfig xssFilterConfig = null;
	@Override
	public void init(FilterConfig xssFilterConfig) throws ServletException {
		this.xssFilterConfig = xssFilterConfig;
	}

	@Override
	public void doFilter(ServletRequest xssRequest, ServletResponse xssResponse, FilterChain xssChain) throws IOException, ServletException {
		
		xssChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)xssRequest),xssResponse);

		((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials, Pragma, Last-Modified, Cache-Control, Expires, Content-Type,Content-Language");
		((HttpServletResponse)xssResponse).setHeader("Access-Control-Expose-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials,X-Auth-Token");
		((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Credientials", "true");
		//((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "X-Custom-Header");
//		((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Credientials", "true");
		String scheme = xssRequest.getScheme();
        String serverName = xssRequest.getServerName();
        int port = xssRequest.getServerPort();
        String path = ((HttpServletRequest)xssRequest).getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        log.debug("baseUrl=" + basePath);
        xssRequest.setAttribute("baseUrl", basePath);
	}

	@Override
	public void destroy() {
		xssFilterConfig = null;
	}

}
