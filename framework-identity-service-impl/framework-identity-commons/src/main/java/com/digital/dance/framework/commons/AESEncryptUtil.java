package com.digital.dance.framework.commons;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;

/**
 * 
 * @author liuxiny
 *
 */
public class AESEncryptUtil {
	static Log logger = new Log(AESEncryptUtil.class);
	private static final String AES = "AES";
	/**
	 * AES加密或解密.
	 * 
	 * @param originalBytes 原始字节数组
	 * @param secretKey AES密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	public static byte[] aes(byte[] originalBytes, byte[] secretKey, int mode) {
		Cipher aesCipher;
		SecretKey sKey = new SecretKeySpec(secretKey, AES);
		try {
			aesCipher = Cipher.getInstance(AES);
			aesCipher.init(mode, sKey);
			return aesCipher.doFinal(originalBytes);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw  new RuntimeException(ex);
		}
	}
	
	/**
	 * Hex encode.
	 */
	public static String encodeHex(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}
	
	/**
	 * Hex decode.
	 */
	public static byte[] decodeHex(String sourceStr) {
		try {
			return Hex.decodeHex(sourceStr.toCharArray());
		} catch (DecoderException e) {
			
			throw  new RuntimeException();
		}
	}	

	/**
	 * 转换16进制码串为字节数组
	 * @param hex
	 * @return
	 */
    public static byte[] hexStringToByte(String hex) {  
        int len = (hex.length() / 2);  
        byte[] byte2Return = new byte[len];  
        char[] charArray = hex.toCharArray();  
        for (int i = 0; i < len; i++) {  
            int points = i * 2;  
            byte2Return[i] = (byte) (toByte(charArray[points]) << 4 | toByte(charArray[points + 1]));  
        }  
        return byte2Return;  
    }  
  
    private static byte toByte(char aChar) {  
        byte aByte = (byte) "0123456789ABCDEF".indexOf(aChar);  
        return aByte;  
    }  
  
    /** 
     * 把字节数组转换成16进制码串 
     *  
     * @param byteArray 
     * @return 
     */  
    public static String bytesToHexString(byte[] byteArray) {  
        String tmpStr;  
        StringBuffer sBuffer = new StringBuffer(byteArray.length);
        for (int i = 0; i < byteArray.length; i++) {  
            tmpStr = Integer.toHexString(0xFF & byteArray[i]);  
            if (tmpStr.length() < 2)  
                sBuffer.append(0);  
            sBuffer.append(tmpStr.toUpperCase());  
        }  
        return sBuffer.toString();  
    }
}
