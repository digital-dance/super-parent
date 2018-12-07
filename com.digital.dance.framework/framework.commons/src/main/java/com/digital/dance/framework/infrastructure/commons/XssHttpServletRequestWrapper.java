package com.digital.dance.framework.infrastructure.commons;

import javax.servlet.http.HttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author liuxiny
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	public XssHttpServletRequestWrapper(HttpServletRequest xssServletRequest) {
        super(xssServletRequest);
    }

    private String cleanXSS(String xssValue) {

        xssValue = xssValue.replaceAll("'", "& #39;");
        xssValue = xssValue.replaceAll("eval\\((.*)\\)", "");
        xssValue = xssValue.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        xssValue = xssValue.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        xssValue = xssValue.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        xssValue = xssValue.replaceAll("script", "");
        return xssValue;
    }

    public String[] getParameterValues(String xssParameter) {
      String[] xssParams = super.getParameterValues(xssParameter);
      if (xssParams==null)  {
           return null;
      }
      int count = xssParams.length;
      String[] encodedParamValues = new String[count];
      for (int i = 0; i < count; i++) {
          encodedParamValues[i] = cleanXSS(xssParams[i]);
       }
      return encodedParamValues;
    }
    
    public String getHeader(String xssName) {
        String xssValue = super.getHeader(xssName);
        if (xssValue == null)
            return null;
        return cleanXSS(xssValue);
    }

    public String getParameter(String xssParameter) {
          String xssValue = super.getParameter(xssParameter);
          if (xssValue == null) {
                 return null;
                  }
          return cleanXSS(xssValue);
    }
}
