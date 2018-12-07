package com.digital.dance.framework.commons;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author liuxiny
 *
 */
public class EncryptUtil {

	private static final String ENCRYPT_ALGORITHM_DESEDE = "DESede";
	private static final String UTF8_CODE = "utf-8";

	public String md5Digest(String src) throws Exception {
		
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		byte[] bytes = mDigest.digest(src.getBytes(UTF8_CODE));

		return this.byte2HexStr(bytes);
	}

	public String desedeEncoder(String srcStr, String desKey) throws Exception {
		SecretKey desSecretKey = new SecretKeySpec(convertDesKey(desKey),
				ENCRYPT_ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM_DESEDE);
		cipher.init(Cipher.ENCRYPT_MODE, desSecretKey);
		byte[] bbytes = cipher.doFinal(srcStr.getBytes(UTF8_CODE));

		return byte2HexStr(bbytes);
	}

	public String desedeDecoder(String destStr, String desKey) throws Exception {
		SecretKey desSecretKey = new SecretKeySpec(convertDesKey(desKey),
				ENCRYPT_ALGORITHM_DESEDE);
		Cipher desCipher = Cipher.getInstance(ENCRYPT_ALGORITHM_DESEDE);
		desCipher.init(Cipher.DECRYPT_MODE, desSecretKey);
		byte[] bytes = desCipher.doFinal(str2ByteArray(destStr));

		return new String(bytes, UTF8_CODE);

	}

	public String base64Encoder(String src) throws Exception {
		
		return Base64.encodeBase64String(src.getBytes(UTF8_CODE));
	}

	public String base64Decoder(String dest) throws Exception {
		
		return new String(Base64.decodeBase64(dest),UTF8_CODE);
	}

	public String byte2HexStr(byte[] bytes) {
		StringBuilder stringB = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hexCodesStr = Integer.toHexString(bytes[i] & 0xFF);
			//奇数个16进制字符，高位先补0
			if (hexCodesStr.length() == 1) {
				stringB.append("0");
			}

			stringB.append(hexCodesStr.toUpperCase());
		}

		return stringB.toString();
	}

	public byte[] str2ByteArray(String str) {
		int bytesLength = str.length() / 2;
		byte[] resultBs = new byte[bytesLength];
		for (int k = 0; k < bytesLength; k++) {
			byte b0 = (byte) Integer.valueOf(str.substring(k * 2, k * 2 + 2), 16)
					.intValue();
			resultBs[k] = b0;
		}

		return resultBs;
	}

	private byte[] convertDesKey(String desKeyStr) throws Exception {
		byte[] desKey = new byte[24];
		byte[] tempDesKey = desKeyStr.getBytes(UTF8_CODE);
		if (desKey.length > tempDesKey.length) {
			System.arraycopy(tempDesKey, 0, desKey, 0, tempDesKey.length);
		} else {
			System.arraycopy(tempDesKey, 0, desKey, 0, desKey.length);
		}

		return desKey;
	}
}