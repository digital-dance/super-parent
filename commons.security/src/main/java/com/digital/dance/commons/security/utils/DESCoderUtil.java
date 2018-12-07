package com.digital.dance.commons.security.utils;

import com.digital.dance.commons.security.DESCoder;
import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class DESCoderUtil
{
  //private static final Logger logger = LoggerFactory.getLogger(DESCoderUtil.class);

  public static String encrypt(String securityKey, String data) throws Exception
  {
    String result = null;
    //try {
      result = getCoder(securityKey).encrypt(data);
    //} catch (Exception e) {
      //logger.error("encrypt data error.");
      //throw new RuntimeException("encrypt data error.", e);
    //}
    return result;
  }

  public static String decrypt(String securityKey, String data) throws Exception
  {
    String result = null;
    //try {
      result = getCoder(securityKey).decrypt(data);
    //} catch (Exception e) {
    //  logger.error("decrypt data error.");
    //  throw new RuntimeException("decrypt data error.", e);
    //}
    return result;
  }

  private static DESCoder getCoder(String securityKey) throws Exception
  {
    DESCoder coder = null;
    //try {
      coder = StringUtils.isBlank(securityKey) ? new DESCoder() : new DESCoder(securityKey);
    //} catch (Exception e) {
    //  logger.error("new coder error.");
    //  throw new RuntimeException("new coder error.", e);
    //}
    return coder;
  }
}