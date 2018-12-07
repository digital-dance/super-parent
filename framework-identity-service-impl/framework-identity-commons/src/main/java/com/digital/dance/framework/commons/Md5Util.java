package com.digital.dance.framework.commons;

import java.security.MessageDigest;

/**
 * 
 * @author liuxiny
 *
 */

public class Md5Util {
		private final static Log log = new Log(Md5Util.class);
		public final static String getMD5(String originalStr) {
			char hDs[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				byte[] byteTemp = originalStr.getBytes();
				MessageDigest msgTemp = MessageDigest.getInstance("MD5");
				
				msgTemp.update(byteTemp);
				
				byte[] msgBytes = msgTemp.digest();
				int msgBytesLength = msgBytes.length;
				char[] msgChars = new char[msgBytesLength * 2];
				int point = 0;
				for (int i = 0; i < msgBytesLength; i++) {
					byte byte0 = msgBytes[i];
					msgChars[point++] = hDs[byte0 >>> 4 & 0xf];
					msgChars[point++] = hDs[byte0 & 0xf];
				}
				return new String(msgChars);
			} catch (Exception e) {
				log.error("getMD5", e);
				return null;
			}
		}

		/** 16进制的字符数组 */
	    private final static String[] hexChars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
	            "e", "f" };

	    /**
	     * MD5摘要
	     * 
	     * @param source 需要加密的原字符串
	     * @param encoding 指定编码类型
	     * @param uppercase  是否转为大写字符串
	     * @return
	     */
	    public static String mD5Encode(String source, String encoding, boolean uppercase) {
	        String result = null;
	        try {
	            result = source;
	            
	            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	            
	            messageDigest.update(result.getBytes(encoding));
	            
	            result = byteArrayToHexString(messageDigest.digest());

	        } catch (Exception e) {

				log.error("mD5Encode", e);
	            e.printStackTrace();
	        }
	        return ( uppercase && ( result != null && !"".equals(result) ) ) ? result.toUpperCase() : result;
	    }
	    
	    /**
	     * hash摘要
	     * @param source
	     * @param encoding
	     * @param algorithName
	     * @param uppercase
	     * @return
	     */
	    public static String hashEncode(String source, String encoding, String algorithName, boolean uppercase) {
	        String result = null;
	        try {
	            result = source;
	            
	            MessageDigest messageDigest = MessageDigest.getInstance( algorithName );
	            
	            messageDigest.update(result.getBytes(encoding));
	            
	            result = byteArrayToHexString(messageDigest.digest());

	        } catch (Exception e) {

				log.error("hashEncode", e);
	            e.printStackTrace();
	        }
	        return uppercase ? result.toUpperCase() : result;
	    }
	    
	    /**
	     * hash encrypt
	     * @param source
	     * @param encoding
	     * @param algorithName
	     * @return
	     */
	    public static byte[] hashEncrypt(String source, String encoding, String algorithName) {
	    	byte[] result = null;
	        try {
	            result = source.getBytes(encoding);
	           
	            result = hashEncrypt(result, algorithName);

	        } catch (Exception e) {

				log.error("hashEncrypt", e);
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    /**
	     * hash encrypt
	     * @param bytes
	     * @param algorithName
	     * @return
	     */
	    public static byte[] hashEncrypt(byte[] bytes, String algorithName ) {
	    	byte[] result = null;
	        try {	            
	            // Hash摘要对象
	        	//MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
	            MessageDigest messageDigest = MessageDigest.getInstance(algorithName);
	            
	            messageDigest.update(bytes);
	            
	            result = messageDigest.digest();

	        } catch (Exception e) {

				log.error("hashEncrypt", e);
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    public final static String getHash(String originalStr) {
			char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				byte[] byteTemp = originalStr.getBytes();
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				
				messageDigest.update(byteTemp);
				
				byte[] msgBytes = messageDigest.digest();
				int msgBytesLength = msgBytes.length;
				char[] msgChars = new char[msgBytesLength * 2];
				int k = 0;
				for (int i = 0; i < msgBytesLength; i++) {
					byte byte0 = msgBytes[i];
					msgChars[k++] = hexChars[byte0 >>> 4 & 0xf];
					msgChars[k++] = hexChars[byte0 & 0xf];
				}
				return new String(msgChars);
			} catch (Exception e) {
				log.error("getMD5", e);
				return null;
			}
		}

	    /**
	     * 转换字节数组为16进制字符串
	     * 
	     * @param bytes
	     *            字节数组
	     * @return
	     */
	    public static String byteArrayToHexString(byte[] bytes) {
	        StringBuilder stringBuilder = new StringBuilder();
	        for (byte tem : bytes) {
	            stringBuilder.append(byteToHexString(tem));
	        }
	        return stringBuilder.toString();
	    }

	    /**
	     * 转换byte到16进制
	     * 
	     * @param digitByte
	     *            要转换的byte
	     * @return 16进制对应的字符
	     */
	    public static String byteToHexString(byte digitByte) {
	        int n = digitByte;
	        if (n < 0) {
	            n = 256 + n;
	        }
	        int d1 = n / 16;
	        int d2 = n % 16;
	        return hexChars[d1] + hexChars[d2];
	    }

}
