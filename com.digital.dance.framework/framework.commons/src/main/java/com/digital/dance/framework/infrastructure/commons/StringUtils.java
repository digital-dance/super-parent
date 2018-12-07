package com.digital.dance.framework.infrastructure.commons;


import java.util.Random;

public class StringUtils {

	public static final String NUMERIC_CHARS = "0123456789";
	public static final String LOWERCASE_NUMBER_AND_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
	
	public static final String NUMBER_AND_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final Random randomObj = new Random();

	public static String roundCode() {
		return getRandomNumCode(6);
	}
	
	public static String getRandomNumCode(int len) {
		StringBuilder sBuilder = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sBuilder.append(NUMERIC_CHARS.charAt(randomObj.nextInt(10)));
		}
		return sBuilder.toString();
	}
	
	public static String getRandomCode(int len) {
		StringBuilder sBuilder = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sBuilder.append(LOWERCASE_NUMBER_AND_CHARS.charAt(randomObj.nextInt(36)));
		}
		return sBuilder.toString();
	}

	public static String getRandomNum(int len) {
		StringBuilder sBuilder = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sBuilder.append(randomObj.nextInt(10));
		}
		return sBuilder.toString();
	}

	public static String getRandomString(int len) {
		StringBuilder sBuilder = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sBuilder.append(NUMBER_AND_CHARS.charAt(randomObj.nextInt(62)));
		}
		return sBuilder.toString();
	}

}
