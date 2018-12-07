package com.digital.dance.framework.commons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

/**
 * 
 * @author liuxiny
 *
 */
public class ExceptionFilter implements Filter {
	private static final Log logger = new Log(ExceptionFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			
			Throwable rootException = e;
			while (rootException.getCause() != null) {
				rootException = rootException.getCause();
			}
			String errorMsg = rootException.getMessage();
			errorMsg = (errorMsg == null) ? "Exception：" + rootException.getClass().getName() : errorMsg;
			
			request.setAttribute(rootException.getClass().getName() + ":error:", errorMsg);
			request.setAttribute(rootException.getClass().getName() + ":exception:", e);
			ResponseVo responseVo = new ResponseVo();
			responseVo.setCode(Constants.ReturnCode.FAILURE.Code());
			responseVo.setMsg(errorMsg);
			logger.error(errorMsg, e);
			PrintWriter rsp = response.getWriter();
			responseOutput(GsonUtils.toJson(responseVo), rsp);
		}
	}

	private void responseOutput(String data, PrintWriter rsp) {
		Throwable localThrowable0 = null;
		try {
			rsp.write(data);
		} catch (Throwable localThrowable) {
			localThrowable0 = localThrowable;
			logger.error(localThrowable0.getMessage(), localThrowable0);
			throw localThrowable;
		} finally {
			if (rsp != null)

				try {
					rsp.close();
				} catch (Throwable x2) {
					if (localThrowable0 != null)
						localThrowable0.addSuppressed(x2);
					logger.error(localThrowable0.getMessage(), localThrowable0);
				}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}