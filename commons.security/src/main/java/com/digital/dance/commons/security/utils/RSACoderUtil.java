package com.digital.dance.commons.security.utils;

import com.digital.dance.commons.security.Base64Coder;
import com.digital.dance.commons.security.RSACoder;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSACoderUtil
{
  //private static final Logger logger = LoggerFactory.getLogger(RSACoderUtil.class);
  public static final String public_Key = "publicKey";
  public static final String private_Key = "privateKey";
  public static final String public_Key_O = "publicKey_O";
  public static final String private_Key_O = "privateKey_O";

  public static Map<String, Object> getKey() throws Exception
  {
    String publicKey = "";
    String privateKey = "";
    RSAPrivateKey RSAPrivateKey = null;
    RSAPublicKey RSAPublicKey = null;
    Map<String, Object> map = new HashMap<String, Object>();
    //try {
      KeyPair keyPair = RSACoder.initKey();
      RSAPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
      RSAPublicKey = (RSAPublicKey)keyPair.getPublic();
      publicKey = RSACoder.getPublicKey(keyPair);
      privateKey = RSACoder.getPrivateKey(keyPair);
    //} catch (Exception e) {
    //  logger.error("get key error.");
    //  throw new RuntimeException("get key error.", e);
    //}
    
    map.put(public_Key, publicKey);
    map.put(private_Key, privateKey);
    
    map.put(public_Key_O, RSAPublicKey);
    map.put(private_Key_O, RSAPrivateKey);
    return map;
  }

  public static String encryptByPublicKey(String publicKey, String data) throws Exception
  {
    byte[] temp = null;
    //try {
      temp = RSACoder.encryptByPublicKey(data.getBytes(), publicKey);
    //} catch (Exception e) {
    //  logger.error("encrypt data error.");
    //  throw new RuntimeException("encrypt data error.", e);
    //}
    String result = Base64Coder.encode2String(temp);
    return result;
  }

  public static String decryptByPrivateKey(String privateKey, String data) throws Exception
  {
    byte[] dataBytes = Base64Coder.decode(data);
    byte[] temp = null;
    //try {
      temp = RSACoder.decryptByPrivateKey(dataBytes, privateKey);
    //} catch (Exception e) {
    //  logger.error("decrypt data error.");
    //  throw new RuntimeException("decrypt data error.", e);
    //}
    String result = new String(temp);
    return result;
  }

  public static String encryptByPrivateKey(String privateKey, String data) throws Exception
  {
    byte[] temp = null;
    //try {
      temp = RSACoder.encryptByPrivateKey(data.getBytes(), privateKey);
    //} catch (Exception e) {
    //  logger.error("encrypt data error.");
    //  throw new RuntimeException("encrypt data error.", e);
    //}
    String result = Base64Coder.encode2String(temp);
    return result;
  }

  public static String decryptByPublicKey(String publicKey, String data) throws Exception
  {
    byte[] dataBytes = Base64Coder.decode(data);
    byte[] temp = null;
    //try {
      temp = RSACoder.decryptByPublicKey(dataBytes, publicKey);
    //} catch (Exception e) {
    //  logger.error("decrypt data error.");
    //  throw new RuntimeException("decrypt data error.", e);
    //}
    String result = new String(temp);
    return result;
  }
}