package com.digital.dance.commons.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

public abstract class CertificateCoder
{
  public static final String KEY_STORE = "JKS";
  public static final String X509 = "X.509";

  public static String getPrivateKeyBase64(String keyStorePath, String alias, String password)
    throws Exception
  {
    KeyStore ks = getKeyStore(keyStorePath, alias, password);
    PrivateKey key = (PrivateKey)ks.getKey(alias, password.toCharArray());
    return Base64Coder.encode2String(key.getEncoded());
  }

  public static PrivateKey getPrivateKey(String keyStorePath, String alias, String password) throws Exception {
    KeyStore ks = getKeyStore(keyStorePath, alias, password);
    PrivateKey key = (PrivateKey)ks.getKey(alias, password.toCharArray());
    return key;
  }

  public static PrivateKey getPrivateKey(InputStream in, String alias, String password) throws Exception {
    KeyStore ks = getKeyStore(in, alias, password);
    PrivateKey key = (PrivateKey)ks.getKey(alias, password.toCharArray());
    return key;
  }

  public static String getPublicKeyBase64(String certificatePath)
    throws Exception
  {
    Certificate certificate = getCertificate(certificatePath);
    PublicKey key = certificate.getPublicKey();
    return Base64Coder.encode2String(key.getEncoded());
  }

  public static String getPublicKeyBase64(InputStream stream) throws Exception {
    Certificate certificate = getCertificate(stream);
    PublicKey key = certificate.getPublicKey();
    return Base64Coder.encode2String(key.getEncoded());
  }

  public static PublicKey getPublicKey(String certificatePath) throws Exception {
    Certificate certificate = getCertificate(certificatePath);
    PublicKey key = certificate.getPublicKey();
    return key;
  }

  public static PublicKey getPublicKey(InputStream in) throws Exception {
    Certificate certificate = getCertificate(in);
    PublicKey key = certificate.getPublicKey();
    return key;
  }

  private static Certificate getCertificate(String certificatePath)
    throws Exception
  {
    FileInputStream in = new FileInputStream(certificatePath);
    Certificate certificate = getCertificate(in);
    in.close();
    return certificate;
  }

  private static Certificate getCertificate(InputStream in) throws Exception {
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    Certificate certificate = certificateFactory.generateCertificate(in);
    return certificate;
  }

  private static Certificate getCertificate(String keyStorePath, String alias, String password)
    throws Exception
  {
    KeyStore ks = getKeyStore(keyStorePath, alias, password);
    Certificate certificate = ks.getCertificate(alias);

    return certificate;
  }

  private static KeyStore getKeyStore(String keyStorePath, String alias, String password)
    throws Exception
  {
    FileInputStream is = new FileInputStream(keyStorePath);
    KeyStore keyStore = getKeyStore(is, alias, password);
    is.close();
    return keyStore;
  }

  private static KeyStore getKeyStore(InputStream in, String alias, String password) throws Exception {
    KeyStore ks = KeyStore.getInstance("JKS");
    ks.load(in, password.toCharArray());
    return ks;
  }

  public static boolean verifyCertificate(String certificatePath)
  {
    return verifyCertificate(new Date(), certificatePath);
  }

  public static boolean verifyCertificate(Date date, String certificatePath)
  {
    boolean status = true;
    try
    {
      Certificate certificate = getCertificate(certificatePath);

      status = verifyCertificate(date, certificate);
    } catch (Exception e) {
      status = false;
    }
    return status;
  }

  public static boolean verifyPublicKey(String publicKey, String certificatePath)
  {
    boolean status = true;
    try {
      Certificate certificate = getCertificate(certificatePath);

      byte[] keyBytes = Base64Coder.decode(publicKey);

      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance(certificate.getPublicKey().getAlgorithm());
      PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

      certificate.verify(pubKey);
    }
    catch (Exception e) {
      status = false;
    }
    return status;
  }

  private static boolean verifyCertificate(Date date, Certificate certificate)
  {
    boolean status = true;
    try {
      X509Certificate x509Certificate = (X509Certificate)certificate;
      x509Certificate.checkValidity(date);
    } catch (Exception e) {
      status = false;
    }
    return status;
  }

  public static String sign(byte[] sign, String keyStorePath, String privateKey, String alias, String password)
    throws Exception
  {
    X509Certificate x509Certificate = (X509Certificate)getCertificate(keyStorePath, alias, password);

    byte[] keyBytes = Base64Coder.decode(privateKey);

    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance(x509Certificate.getPublicKey().getAlgorithm());

    PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

    Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
    signature.initSign(priKey);
    signature.update(sign);
    return Base64Coder.encode2String(signature.sign());
  }

  public static boolean verify(byte[] data, String sign, String certificatePath)
    throws Exception
  {
    X509Certificate x509Certificate = (X509Certificate)getCertificate(certificatePath);

    PublicKey publicKey = x509Certificate.getPublicKey();

    Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
    signature.initVerify(publicKey);
    signature.update(data);

    return signature.verify(Base64Coder.decode(sign));
  }

  public static boolean verifyCertificate(Date date, String keyStorePath, String alias, String password)
  {
    boolean status = true;
    try {
      Certificate certificate = getCertificate(keyStorePath, alias, password);
      status = verifyCertificate(date, certificate);
    } catch (Exception e) {
      status = false;
    }
    return status;
  }

  public static boolean verifyCertificate(String keyStorePath, String alias, String password)
  {
    return verifyCertificate(new Date(), keyStorePath, alias, password);
  }
}