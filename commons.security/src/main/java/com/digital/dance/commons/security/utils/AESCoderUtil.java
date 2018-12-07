package com.digital.dance.commons.security.utils;

import com.digital.dance.commons.security.AESCoderCBC1;
import com.digital.dance.commons.security.AESCoderCBC2;

//import java.io.UnsupportedEncodingException;
//import java.security.GeneralSecurityException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class AESCoderUtil
{
  //private static final Logger logger = LoggerFactory.getLogger(AESCoderUtil.class);

  public static String encrypt(String securityKey, String data) throws Exception
  {
    String result = null;
    //try {
      AESCoderCBC1 coder = new AESCoderCBC1(securityKey);
      result = coder.encrypt(data);
    //} catch (Exception e) {
    //  logger.error("encrypt data error.");
    //  throw new RuntimeException("encrypt data error.", e);
    //}
    return result;
  }

  public static String decrypt(String securityKey, String data) throws Exception
  {
    String result = null;
    //try {
      AESCoderCBC1 coder = new AESCoderCBC1(securityKey);
      result = coder.decrypt(data);
    //} catch (Exception e) {
    //  logger.error("decrypt data error.");
    //  throw new RuntimeException("decrypt data error.", e);
    //}
    return result;
  }

  public static String encrypt(String securityKey, String iv, String data) throws Exception
  {
    //try
    //{
      return AESCoderCBC2.encrypt(securityKey, iv, data);
    //} catch (Exception e) {
    //  logger.error("decrypt data error.");
    //  throw new RuntimeException("decrypt data error.", e);
    //}
  }

  public static String decrypt(String securityKey, String iv, String data) throws Exception
  {
    //try
    //{
      return AESCoderCBC2.decrypt(securityKey, iv, data);
    //} catch (Exception e) {
    //  logger.error("decrypt data error.");
    //  throw new RuntimeException("decrypt data error.", e);
    //}
  }
}