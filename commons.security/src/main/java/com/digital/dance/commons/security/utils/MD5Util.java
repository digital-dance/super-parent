package com.digital.dance.commons.security.utils;

import com.digital.dance.commons.exception.BizException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util
{
  //private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

  public static String getMd5(String plainText) throws NoSuchAlgorithmException
  {
    //try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(plainText.getBytes());
      byte[] b = md.digest();

      StringBuffer buf = new StringBuffer();
      for (int offset = 0; offset < b.length; offset++) {
        int count = b[offset];
        if (count < 0) {
          count += 256;
        }
        if (count < 16) {
          buf.append("0");
        }
        buf.append(Integer.toHexString(count));
      }
      return buf.toString();
    //} catch (Exception e) {
    //  logger.error("message digest md5 error.", e);
    //}throw new BizException();
  }
}