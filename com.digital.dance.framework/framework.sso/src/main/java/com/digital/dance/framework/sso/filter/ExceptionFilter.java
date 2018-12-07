package com.digital.dance.framework.sso.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.digital.dance.commons.serialize.json.utils.JSONUtils;
import com.digital.dance.framework.infrastructure.commons.Constants;
import com.digital.dance.framework.infrastructure.commons.Log;
import com.digital.dance.framework.infrastructure.commons.ResponseVo;

public class ExceptionFilter implements Filter {
	private static final Log logger = new Log(SSOLoginFilter.class);
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
          try {
                chain.doFilter(request, response);
          } catch (Exception e) {
        	  	//如果有异常则捕捉
                Throwable rootCause = e;
                while (rootCause.getCause() != null) {
                      rootCause = rootCause.getCause();
                }
                String errormessage = rootCause.getMessage();  //异常根本
                errormessage = ( errormessage == null ) ? "Exception：" + rootCause.getClass().getName()
                            : errormessage;
                //中止传递异常的原因
                request.setAttribute("errormessage", errormessage); 
                request.setAttribute("e", e);
                ResponseVo responsedata = new ResponseVo();
                responsedata.setCode(Constants.ReturnCode.FAILURE.Code());
				responsedata.setMsg(errormessage);
				logger.error(errormessage, e);
				PrintWriter rsp = response.getWriter();
				responseOutput(JSONUtils.toJson(responsedata), rsp);
          }
    }
    private void responseOutput(String data, PrintWriter rsp){
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