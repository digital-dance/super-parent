package com.digital.dance.commons.net.utils;

public class URLUtil
{
  public static String addJsessionId(String url, String jsessionid)
  {
    int index_jsessionid = url.indexOf(";jsessionid=");
    int index_2 = url.indexOf("?");
    if (index_jsessionid != -1) {
      if (index_2 != -1) {
        return url.substring(0, index_jsessionid + 12) + jsessionid + url.substring(index_2);
      }
      return url.substring(0, index_jsessionid + 12) + jsessionid;
    }

    if (index_2 != -1) {
      return url + ";jsessionid=" + jsessionid + url.substring(index_2);
    }
    return url + ";jsessionid=" + jsessionid;
  }

  public static String getDomainAndPort(String url)
  {
    if ((url == null) || (url.trim().length() == 0)) {
      return null;
    }
    String url2 = url;
    int index1 = url.indexOf("://");
    if (index1 != -1) {
      url2 = url.substring(index1 + 3);
    }
    int index2 = url2.indexOf("/");
    if (index2 != -1) {
      return url2.substring(0, index2);
    }
    return url2;
  }

  public static String md5(String token, Long timestamp, String keygroup, String keyversion, String securitysalt)
  {
    StringBuffer stringbuffer = new StringBuffer();
    if (timestamp != null) {
      stringbuffer.append(timestamp);
    }
    if ((keygroup != null) && (keygroup.trim().length() > 0)) {
      stringbuffer.append(keygroup);
    }
    if ((keyversion != null) && (keyversion.trim().length() > 0)) {
      stringbuffer.append(keyversion);
    }
    if (token != null) {
      if (token.length() > 100)
        stringbuffer.append(token.substring(0, 100));
      else {
        stringbuffer.append(token);
      }
    }
    if ((securitysalt != null) && (securitysalt.trim().length() > 0)) {
      stringbuffer.append(securitysalt);
    }
    return stringbuffer.toString();
  }
}