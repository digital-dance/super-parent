package com.digital.dance.commons.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Enumeration;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCoderCBC1
{
  private static final String JCE_EXCEPTION_MESSAGE = "Please make sure \"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" (http://java.sun.com/javase/downloads/index.jsp) is installed on your JRE.";
  private SecureRandom random;
  private static final String RANDOM_ALG = "SHA1PRNG";
  private MessageDigest digest;
  private static final String DIGEST_ALG = "SHA-256";
  private Mac hmac;
  private static final String HMAC_ALG = "HmacSHA256";
  private static final byte[] DEFAULT_MAC = { 1, 35, 69, 103, -119, -85, -51, -17 };
  private Cipher cipher;
  private static final String CRYPT_ALG = "AES";
  private static final String CRYPT_TRANS = "AES/CBC/PKCS5Padding";
  private static final int BLOCK_SIZE = 16;
  private static final int KEY_SIZE = 16;
  private static final int SHA_SIZE = 32;
  private byte[] password;
  private IvParameterSpec ivSpec1;
  private IvParameterSpec ivSpec2;
  private SecretKeySpec aesKey1;
  private SecretKeySpec aesKey2;

  public AESCoderCBC1(String password)
    throws GeneralSecurityException, UnsupportedEncodingException
  {
    try
    {
      setPassword(password);
      this.random = SecureRandom.getInstance("SHA1PRNG");
      this.digest = MessageDigest.getInstance("SHA-256");
      this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.hmac = Mac.getInstance("HmacSHA256");
    } catch (GeneralSecurityException e) {
      throw new GeneralSecurityException("Please make sure \"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" (http://java.sun.com/javase/downloads/index.jsp) is installed on your JRE.", e);
    }
  }

  public void setPassword(String password) throws UnsupportedEncodingException {
    this.password = password.getBytes("UTF-16LE");
  }

  public String encrypt(String data) throws Exception {
    return byteArr2HexStr(encrypt(data.getBytes()));
  }

  public byte[] encrypt(byte[] data) throws Exception
  {
    try {
      this.ivSpec1 = new IvParameterSpec(generateIV1());
      this.ivSpec2 = new IvParameterSpec(generateIV2());
      this.aesKey1 = new SecretKeySpec(generateAESKey1(this.ivSpec1.getIV(), this.password), "AES");
      this.aesKey2 = new SecretKeySpec(generateAESKey2(), "AES");

      OutputStream out = new ByteArrayOutputStream();
      out.write("AES".getBytes("UTF-8"));
      out.write(0);
      out.write(this.ivSpec1.getIV());

      byte[] text = new byte[48];
      byte[] temp = new byte[32];
      this.cipher.init(1, this.aesKey1, this.ivSpec1);
      System.arraycopy(this.ivSpec2.getIV(), 0, temp, 0, 16);
      System.arraycopy(this.aesKey2.getEncoded(), 0, temp, 16, 16);
      this.cipher.doFinal(temp, 0, 32, text);
      out.write(text);

      this.hmac.init(new SecretKeySpec(this.aesKey1.getEncoded(), "HmacSHA256"));
      text = this.hmac.doFinal(text);
      out.write(text);

      this.cipher.init(1, this.aesKey2, this.ivSpec2);
      this.hmac.init(new SecretKeySpec(this.aesKey2.getEncoded(), "HmacSHA256"));
      text = new byte[16];
      temp = new byte[32];
      int last = 0;

      InputStream in = new ByteArrayInputStream(data);
      int len;
      while ((len = in.read(text)) > 0) {
        temp = this.cipher.doFinal(text);
        this.hmac.update(temp);
        out.write(temp);
        last = len;
      }
      last &= 15;
      out.write(last);

      text = this.hmac.doFinal();
      out.write(text);

      return ((ByteArrayOutputStream)out).toByteArray();
    }
    catch (InvalidKeyException e) {
      throw new GeneralSecurityException("Please make sure \"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" (http://java.sun.com/javase/downloads/index.jsp) is installed on your JRE.", e);
    }
  }

  public void encrypt(String fromPath, String toPath) throws IOException, GeneralSecurityException {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new BufferedInputStream(new FileInputStream(fromPath));
      out = new BufferedOutputStream(new FileOutputStream(toPath));
      encrypt(in, out);
    } finally {
      if (in != null) {
        in.close();
      }
      if (out != null)
        out.close();
    }
  }

  public void encrypt(InputStream in, OutputStream out) throws IOException, GeneralSecurityException
  {
    try
    {
      this.ivSpec1 = new IvParameterSpec(generateIV1());
      this.ivSpec2 = new IvParameterSpec(generateIV2());
      this.aesKey1 = new SecretKeySpec(generateAESKey1(this.ivSpec1.getIV(), this.password), "AES");
      this.aesKey2 = new SecretKeySpec(generateAESKey2(), "AES");

      out.write("AES".getBytes("UTF-8"));
      out.write(0);
      out.write(this.ivSpec1.getIV());

      byte[] text = new byte[48];
      byte[] temp = new byte[32];
      this.cipher.init(1, this.aesKey1, this.ivSpec1);
      System.arraycopy(this.ivSpec2.getIV(), 0, temp, 0, 16);
      System.arraycopy(this.aesKey2.getEncoded(), 0, temp, 16, 16);
      this.cipher.doFinal(temp, 0, 32, text);
      out.write(text);

      this.hmac.init(new SecretKeySpec(this.aesKey1.getEncoded(), "HmacSHA256"));
      text = this.hmac.doFinal(text);
      out.write(text);

      this.cipher.init(1, this.aesKey2, this.ivSpec2);
      this.hmac.init(new SecretKeySpec(this.aesKey2.getEncoded(), "HmacSHA256"));
      text = new byte[16];
      temp = new byte[32];
      int last = 0;
      int len;
      while ((len = in.read(text)) > 0) {
        temp = this.cipher.doFinal(text);
        this.hmac.update(temp);
        out.write(temp);
        last = len;
      }
      last &= 15;
      out.write(last);

      text = this.hmac.doFinal();
      out.write(text);
    }
    catch (InvalidKeyException e) {
      throw new GeneralSecurityException("Please make sure \"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" (http://java.sun.com/javase/downloads/index.jsp) is installed on your JRE.", e);
    }
  }

  public String decrypt(String strIn) throws Exception {
    return new String(decrypt(hexStr2ByteArr(strIn)));
  }

  public byte[] decrypt(byte[] data) throws Exception {
    try {
      byte[] text = null; byte[] backup = null;
      long total = 117L;
      InputStream in = new ByteArrayInputStream(data);
      OutputStream out = new ByteArrayOutputStream();

      text = new byte[3];
      readBytes(in, text);

      if (!new String(text, "UTF-8").equals("AES")) {
        throw new IOException("Invalid file header");
      }

      in.read();

      text = new byte[16];
      readBytes(in, text);
      this.ivSpec1 = new IvParameterSpec(text);
      this.aesKey1 = new SecretKeySpec(generateAESKey1(this.ivSpec1.getIV(), this.password), "AES");
      this.cipher.init(2, this.aesKey1, this.ivSpec1);
      this.hmac.init(new SecretKeySpec(this.aesKey1.getEncoded(), "HmacSHA256"));

      backup = new byte[48];
      readBytes(in, backup);
      text = this.cipher.doFinal(backup);
      this.ivSpec2 = new IvParameterSpec(text, 0, 16);
      this.aesKey2 = new SecretKeySpec(text, 16, 16, "AES");
      backup = this.hmac.doFinal(backup);

      text = new byte[32];
      readBytes(in, text);
      if (!Arrays.equals(backup, text)) {
        throw new IOException("Message has been altered or password incorrect");
      }

      total = data.length - total;
      if (total % 16L != 0L) {
        throw new IOException("Input file is corrupt");
      }
      if (total == 0L) {
        in.read();
      }

      this.cipher.init(2, this.aesKey2, this.ivSpec2);
      this.hmac.init(new SecretKeySpec(this.aesKey2.getEncoded(), "HmacSHA256"));
      backup = new byte[32];
      text = new byte[32];
      for (int block = (int)(total / 32L); block > 0; block--) {
        int len = 32;
        if (in.read(backup, 0, len) != len) {
          throw new IOException("Unexpected end of file contents");
        }
        text = this.cipher.doFinal(backup);
        this.hmac.update(backup, 0, len);
        if (block == 1) {
          int last = in.read();
          len = last > 0 ? 2 * last : 32;
        }
        out.write(text, 0, len / 2);
      }
      out.write(this.cipher.doFinal());
      backup = this.hmac.doFinal();

      text = new byte[32];
      readBytes(in, text);
      if (!Arrays.equals(backup, text)) {
        throw new IOException("Message has been altered or password incorrect");
      }

      return ((ByteArrayOutputStream)out).toByteArray();
    }
    catch (InvalidKeyException e) {
      throw new GeneralSecurityException("Please make sure \"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" (http://java.sun.com/javase/downloads/index.jsp) is installed on your JRE.", e);
    }
  }

  public void decrypt(String fromPath, String toPath) throws IOException, GeneralSecurityException {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new BufferedInputStream(new FileInputStream(fromPath));
      out = new BufferedOutputStream(new FileOutputStream(toPath));
      decrypt(new File(fromPath).length(), in, out);
    } finally {
      if (in != null) {
        in.close();
      }
      if (out != null)
        out.close();
    }
  }

  public void decrypt(long inSize, InputStream in, OutputStream out) throws IOException, GeneralSecurityException
  {
    try {
      byte[] text = null; byte[] backup = null;
      long total = 117L;

      text = new byte[3];
      readBytes(in, text);
      if (!new String(text, "UTF-8").equals("AES")) {
        throw new IOException("Invalid file header");
      }

      in.read();

      text = new byte[16];
      readBytes(in, text);
      this.ivSpec1 = new IvParameterSpec(text);
      this.aesKey1 = new SecretKeySpec(generateAESKey1(this.ivSpec1.getIV(), this.password), "AES");
      this.cipher.init(2, this.aesKey1, this.ivSpec1);
      this.hmac.init(new SecretKeySpec(this.aesKey1.getEncoded(), "HmacSHA256"));

      backup = new byte[48];
      readBytes(in, backup);
      text = this.cipher.doFinal(backup);
      this.ivSpec2 = new IvParameterSpec(text, 0, 16);
      this.aesKey2 = new SecretKeySpec(text, 16, 16, "AES");
      backup = this.hmac.doFinal(backup);

      text = new byte[32];
      readBytes(in, text);
      if (!Arrays.equals(backup, text)) {
        throw new IOException("Message has been altered or password incorrect");
      }

      total = inSize - total;
      if (total % 16L != 0L) {
        throw new IOException("Input file is corrupt");
      }
      if (total == 0L) {
        in.read();
      }

      this.cipher.init(2, this.aesKey2, this.ivSpec2);
      this.hmac.init(new SecretKeySpec(this.aesKey2.getEncoded(), "HmacSHA256"));
      backup = new byte[32];
      text = new byte[32];
      for (int block = (int)(total / 32L); block > 0; block--) {
        int len = 32;
        if (in.read(backup, 0, len) != len) {
          throw new IOException("Unexpected end of file contents");
        }
        text = this.cipher.doFinal(backup);
        this.hmac.update(backup, 0, len);
        if (block == 1) {
          int last = in.read();
          len = last > 0 ? 2 * last : 32;
        }
        out.write(text, 0, len / 2);
      }
      out.write(this.cipher.doFinal());
      backup = this.hmac.doFinal();

      text = new byte[32];
      readBytes(in, text);
      if (!Arrays.equals(backup, text))
        throw new IOException("Message has been altered or password incorrect");
    }
    catch (InvalidKeyException e)
    {
      throw new GeneralSecurityException("Please make sure \"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" (http://java.sun.com/javase/downloads/index.jsp) is installed on your JRE.", e);
    }
  }

  protected byte[] generateIV1() {
    byte[] iv = new byte[16];
    long time = System.currentTimeMillis();
    byte[] mac = null;
    try {
      Enumeration ifaces = NetworkInterface.getNetworkInterfaces();
      while ((mac == null) && (ifaces.hasMoreElements()))
        mac = ((NetworkInterface)ifaces.nextElement()).getHardwareAddress();
    }
    catch (Exception e)
    {
    }
    if (mac == null) {
      mac = DEFAULT_MAC;
    }

    for (int i = 0; i < 8; i++) {
      iv[i] = ((byte)(int)(time >> i * 8));
    }
    System.arraycopy(mac, 0, iv, 8, mac.length);
    digestRandomBytes(iv, 256);
    return iv;
  }

  protected byte[] generateIV2() {
    byte[] iv = generateRandomBytes(16);
    digestRandomBytes(iv, 256);
    return iv;
  }

  protected byte[] generateAESKey1(byte[] iv, byte[] password) {
    byte[] aesKey = new byte[16];
    System.arraycopy(iv, 0, aesKey, 0, iv.length);
    for (int i = 0; i < 8192; i++) {
      this.digest.reset();
      this.digest.update(aesKey);
      this.digest.update(password);
      aesKey = this.digest.digest();
    }
    return aesKey;
  }

  protected byte[] generateAESKey2() {
    byte[] aesKey = generateRandomBytes(16);
    digestRandomBytes(aesKey, 32);
    return aesKey;
  }

  protected byte[] generateRandomBytes(int len) {
    byte[] bytes = new byte[len];
    this.random.nextBytes(bytes);
    return bytes;
  }

  protected void digestRandomBytes(byte[] bytes, int num) {
    assert (bytes.length <= 32);

    this.digest.reset();
    this.digest.update(bytes);
    for (int i = 0; i < num; i++) {
      this.random.nextBytes(bytes);
      this.digest.update(bytes);
    }
    System.arraycopy(this.digest.digest(), 0, bytes, 0, bytes.length);
  }

  protected void readBytes(InputStream in, byte[] bytes) throws IOException {
    if (in.read(bytes) != bytes.length)
      throw new IOException("Unexpected end of file");
  }

  private static String byteArr2HexStr(byte[] arrB) throws Exception
  {
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
