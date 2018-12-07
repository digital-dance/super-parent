package com.digital.dance.commons.security;

import org.apache.commons.codec.binary.Base64;

public class Base64Coder {
	private static Base64 base64 = new Base64();

	public static String encodeString(String s) {
		byte[] bytes = s.getBytes();

		return new String(encode(bytes));
	}

	public static char[] encode(byte[] in) {
		return encode(in, in.length);
	}

	public static String encode2String(byte[] in) {

		return base64.encodeToString(in);
	}

	public static char[] encode(byte[] in, int iLen) {
		byte[] encodeBase64Bytes = Base64.encodeBase64(in);

		char[] out = new char[encodeBase64Bytes.length];
		for (int i = 0; i <= encodeBase64Bytes.length - 1; i++) {
			out[i] = (char) encodeBase64Bytes[i];
		}

		return out;
	}

	public static String decodeString(String s) {
		return new String(Base64.decodeBase64(s));
	}

	public static byte[] decode(String s) {
		return Base64.decodeBase64(s);
	}

	public static byte[] decode(char[] in) {
	
		return Base64.decodeBase64(new String(in));
	
	}

}
