package com.digital.dance.framework.sso.util;

import com.digital.dance.framework.sso.entity.LoginInfo;

public class LoginContext
{
  private static final ThreadLocal<LoginInfo> loginthreadLocal = new ThreadLocal<LoginInfo>();

  public static LoginInfo getLoginInfo() {
    return loginthreadLocal.get();
  }

  public static void setLoginInfo(LoginInfo loginInfo) {
    loginthreadLocal.set(loginInfo);
  }

  public static void removeLoginInfo() {
    loginthreadLocal.remove();
  }
}