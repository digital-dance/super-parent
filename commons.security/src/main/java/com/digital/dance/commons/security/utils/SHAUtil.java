package com.digital.dance.commons.security.utils;

import com.digital.dance.commons.exception.BizException;
import java.security.MessageDigest;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAUtil {
	private static final Logger logger = LoggerFactory.getLogger(SHAUtil.class);

	public static String getSHA256(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();

			MessageDigest mdInst = MessageDigest.getInstance("SHA-256");

			mdInst.update(btInput);

			byte[] md = mdInst.digest();

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			logger.error("message digest sha256 error.", e);
		}
		throw new BizException();
	}

	public static String getRandomSource(int length) {
		String str = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int max = str.length();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(max);
			sb.append(str.charAt(number));
		}
		
		return sb.toString();
	}
	
	public static String getRandomSourceWithTimeMillis(int length) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(getRandomSource(length));
		
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}
}