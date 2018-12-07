package com.digital.dance.commons.security;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCoderCBC2
{
  private static final String KEY_ALGORITHM = "AES";
  public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

  public static byte[] initkey()
  {
    return new byte[] { 8, 8, 4, 11, 2, 15, 11, 12, 1, 3, 9, 7, 12, 3, 7, 10, 4, 15, 6, 15, 14, 9, 5, 1, 10, 10, 1, 9, 6, 7, 9, 13 };
  }

  private static Key createKey(byte[] key) {
    byte[] arrB = new byte[16];
    for (int i = 0; (i < key.length) && (i < arrB.length); i++) {
      arrB[i] = key[i];
    }
    SecretKey secretKey = new SecretKeySpec(arrB, "AES");
    return secretKey;
  }

  private static IvParameterSpec createIV(String ivParam) throws UnsupportedEncodingException {
    if (ivParam == null) {
      ivParam = "iv.midea.com";
    }
    byte[] data = new byte[16];
    byte[] iv = ivParam.getBytes();
    for (int i = 0; (i < iv.length) && (i < data.length); i++) {
      data[i] = iv[i];
    }
    return new IvParameterSpec(data);
  }

  public static String encrypt(String securityKey, String iv, String data) throws Exception {
    return byteArr2HexStr(encrypt(data.getBytes(), securityKey.getBytes(), iv));
  }

  public static byte[] encrypt(byte[] content, byte[] password, String iv) throws Exception
  {
    Key key = createKey(password);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(1, key, createIV(iv));
    byte[] result = cipher.doFinal(content);
    return result;
  }

  public static String decrypt(String securityKey, String iv, String data) throws Exception {
    return new String(decrypt(hexStr2ByteArr(data), securityKey.getBytes(), iv));
  }

  public static byte[] decrypt(byte[] content, byte[] password, String iv) throws Exception
  {
    Key key = createKey(password);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(2, key, createIV(iv));
    byte[] result = cipher.doFinal(content);
    return result;
  }

  private static String byteArr2HexStr(byte[] arrB) throws Exception {
    int iLen = arrB.length;

    StringBuffer sb = new StringBuffer(iLen * 2);
    for (int i = 0; i < iLen; i++) {
      int intTmp = arrB[i];

      while (intTmp < 0) {
        intTmp += 256;
      }

      if (intTmp < 16) {
        sb.append("0");
      }
      sb.append(Integer.toString(intTmp, 16));
    }
    return sb.toString();
  }

  private static byte[] hexStr2ByteArr(String strIn) throws Exception {
    byte[] arrB = strIn.getBytes();
    int iLen = arrB.length;

    byte[] arrOut = new byte[iLen / 2];
    for (int i = 0; i < iLen; i += 2) {
      String strTmp = new String(arrB, i, 2);
      arrOut[(i / 2)] = ((byte)Integer.parseInt(strTmp, 16));
    }
    return arrOut;
  }
}