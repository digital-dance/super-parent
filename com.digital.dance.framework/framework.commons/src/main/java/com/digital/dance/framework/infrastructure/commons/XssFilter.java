package com.digital.dance.framework.infrastructure.commons;

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
import java.io.PrintWriter;

public class XssFilter implements Filter {
	Log log = new Log(XssFilter.class);
	FilterConfig xssFilterConfig = null;
	@Override
	public void init(FilterConfig xssFilterConfig) throws ServletException {
		this.xssFilterConfig = xssFilterConfig;
	}

	@Override
	public void doFilter(ServletRequest xssRequest, ServletResponse xssResponse, FilterChain xssChain) throws IOException, ServletException {
		try {
			xssChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)xssRequest),xssResponse);

		} catch (Throwable ex){
			log.error(ex.getCause().getMessage(), ex);

            Throwable rootException = ex;
            while (rootException.getCause() != null) {
                rootException = rootException.getCause();
            }
            String errorMsg = rootException.getMessage();
            errorMsg = (errorMsg == null) ? "Exception：" + rootException.getClass().getName() : errorMsg;

            xssRequest.setAttribute(rootException.getClass().getName() + ":error:", errorMsg);
            xssRequest.setAttribute(rootException.getClass().getName() + ":exception:", ex);
            ResponseVo responseVo = new ResponseVo();
            responseVo.setCode(Constants.ReturnCode.FAILURE.Code());
            responseVo.setMsg(errorMsg);
            log.error(errorMsg, ex);
            PrintWriter rsp = xssResponse.getWriter();
            cfgHeader( xssRequest, xssResponse, xssChain );
            responseOutput(GsonUtils.toJson(responseVo), rsp);
            return;
		}

        cfgHeader( xssRequest, xssResponse, xssChain );
		String scheme = xssRequest.getScheme();
        String serverName = xssRequest.getServerName();
        int port = xssRequest.getServerPort();
        String path = ((HttpServletRequest)xssRequest).getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        log.debug("baseUrl=" + basePath);
        xssRequest.setAttribute("baseUrl", basePath);
	}

	private void cfgHeader(ServletRequest xssRequest, ServletResponse xssResponse, FilterChain xssChain){
        ((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        ((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials, Pragma, Last-Modified, Cache-Control, Expires, Content-Type,Content-Language");
        ((HttpServletResponse)xssResponse).setHeader("Access-Control-Expose-Headers", "Origin, SESSION, Cookie, Set-Cookie, No-Cache, X-Requested-With, If-Modified-Since, X-E4M-With, Access-Control-Allow-Origin, Access-Control-Allow-Methods, X-Auth-Token, Access-Control-Allow-Credientials,X-Auth-Token");
        ((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Credientials", "true");
        //((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "X-Custom-Header");
//        ((HttpServletResponse)xssResponse).setHeader("Access-Control-Allow-Credientials", "true");
    }

    private void responseOutput(String data, PrintWriter rsp) {
        Throwable localThrowable0 = null;
        try {
            rsp.write(data);
        } catch (Throwable localThrowable) {
            localThrowable0 = localThrowable;
            log.error(localThrowable0.getMessage(), localThrowable0);
            throw localThrowable;
        } finally {
            if (rsp != null)

                try {
                    rsp.close();
                } catch (Throwable x2) {
                    if (localThrowable0 != null)
                        localThrowable0.addSuppressed(x2);
                    log.error(localThrowable0.getMessage(), localThrowable0);
                }
        }
    }

    @Override
	public void destroy() {
		xssFilterConfig = null;
	}

}
