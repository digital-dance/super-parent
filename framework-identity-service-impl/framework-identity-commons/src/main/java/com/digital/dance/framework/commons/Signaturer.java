package com.digital.dance.framework.commons;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class Signaturer {
	private final static Log log = new Log(Signaturer.class);

	/**
	 * 
	 * 数字签名
	 * 
	 * @param priKeyText
	 * @param hashText
	 * @return
	 */
	public static byte[] sign(byte[] priKeyText, String hashText) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(hashText.getBytes());
			byte[] signed = Base64.encodeToByte(signet.sign());
			return signed;
		} catch (java.lang.Exception e) {
			log.error("签名失败", e);

		}
		return null;
	}

	/**
	 * 
	 * @param priKeyText
	 * @param plainText
	 * @return
	 */
	public static byte[] sign(byte[] priKeyText, byte[] plainText) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(plainText);
			byte[] signed = Base64.encodeToByte(signet.sign());
			return signed;
		} catch (java.lang.Exception e) {
			log.error("签名失败", e);
			e.printStackTrace();
		}
		return null;
	}
}