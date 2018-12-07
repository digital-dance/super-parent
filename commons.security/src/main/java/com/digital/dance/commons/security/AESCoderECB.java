package com.digital.dance.commons.security;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCoderECB
{
  public static final String KEY_ALGORITHM = "AES";
  public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

  public static byte[] initkey()
  {
    return new byte[] { 8, 8, 4, 11, 2, 15, 11, 12, 1, 3, 9, 7, 12, 3, 7, 10, 4, 15, 6, 15, 14, 9, 5, 1, 10, 10, 1, 9, 6, 7, 9, 13 };
  }

  public static Key toKey(byte[] key)
    throws Exception
  {
    SecretKey secretKey = new SecretKeySpec(key, "AES");
    return secretKey;
  }

  public static byte[] encrypt(byte[] data, byte[] key)
    throws Exception
  {
    Key secretKey = toKey(key);
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

    cipher.init(1, secretKey);

    return cipher.doFinal(data);
  }

  public static byte[] decrypt(byte[] data, byte[] key)
    throws Exception
  {
    Key secretKey = toKey(key);
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

    cipher.init(2, secretKey);

    return cipher.doFinal(data);
  }
}
