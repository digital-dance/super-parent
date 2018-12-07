package com.digital.dance.framework.infrastructure.commons;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * 
 * @author liuxiny
 *
 */
public class KeyGenerater {
	 private byte[] priKey_Bytes;
	 private byte[] pubKey_Bytes;
	 private static Log logger = new Log(KeyGenerater.class);

	 public void generater() {
	  try {
	   java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator
	     .getInstance("RSA");
	   SecureRandom secrand = new SecureRandom();
	   // 初始化随机产生器
	   secrand.setSeed("ran".getBytes());
	   keyPairGenerator.initialize(1024, secrand);
	   KeyPair keys = keyPairGenerator.genKeyPair();

	   PublicKey publicKey = keys.getPublic();
	   PrivateKey privateKey = keys.getPrivate();

	   pubKey_Bytes = Base64.encodeToByte(publicKey.getEncoded());
	   priKey_Bytes = Base64.encodeToByte(privateKey.getEncoded());

	  } catch (java.lang.Exception e) {
	   
	   logger.error("生成密钥对时发生error," + e.getMessage(), e);
	  }
	 }

	 public byte[] getPriKey() {
	  return priKey_Bytes;
	 }

	 public byte[] getPubKey() {
	  return pubKey_Bytes;
	 }
	}
