package com.digital.dance.framework.commons;

public class SignProvider {
	private final static Log log = new Log(SignProvider.class);

	private SignProvider() {

	}

	/**
	 * 
	 * 校验数字签名
	 * 
	 * @param pubKeyText 公钥,base64编码
	 * @param plainText 明文
	 * @param signTest  数字签名的密文,base64编码
	 * @return 校验成功返回true 失败返回false
	 */
	public static boolean verify(byte[] pubKeyText, String plainText, byte[] signText) {
		try {
			
			java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
					Base64.decode(pubKeyText));
			
			java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
			
			java.security.PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			
			byte[] signed = Base64.decode(signText);
			java.security.Signature signatureChecker = java.security.Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			
			if (signatureChecker.verify(signed))
				return true;
			else
				return false;
		} catch (Throwable e) {
			log.error("校验签名失败", e);

			return false;
		}
	}
}
