package com.digital.dance.framework.sso.util;

import com.digital.dance.commons.security.utils.MD5Util;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class BrowserContext
{
  public static String buildBrowserContextValue(String userType, HttpServletRequest request) throws NoSuchAlgorithmException
  {
    StringBuilder builder = new StringBuilder();
    builder.append(userType);
    String userAgent = request.getHeader("user-agent");
    if (!StringUtils.isBlank(userAgent)) {
      builder.append(userAgent);
    }
    return MD5Util.getMd5(builder.toString());
  }
}