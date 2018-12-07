package com.digital.dance.framework.commons;

import java.util.regex.Pattern;
import java.util.StringTokenizer;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author liuxiny
 *
 */
public class StringTools {

	public final static String Separate_SEMICOLON = ";";

	public final static String Separate_VERTICAL_BAR = "\\|";

	public final static String Separate_COLON = ":";

	public final static String Separate_IS_EQUAL_TO = "=";

	public final static String Separate_COMMA = ",";

	public final static String Separate_UNDERLINE = "_";

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str.trim().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	public static String null2Empty(String str) {
		String ret = str;
		if (isEmpty(str)) {
			ret = "";
		}
		return ret;
	}

	public static String empty2Zero(String inputStr) {
		if (isEmpty(inputStr))
			return "0";
		else
			return inputStr.trim();
	}

	public static Long parseLong(String numStr) {
		try {
			if (numStr == null || numStr.trim().equals("") || numStr.trim().equals("0")) {
				return null;
			} else {
				return Long.parseLong(numStr);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] getBytesFromStr(String str) throws UnsupportedEncodingException {
		byte[] bytes = null;
		bytes = str.getBytes("unicode");
		return bytes;
	}

	public static String getStrFromBytes(byte[] bytes) throws UnsupportedEncodingException {
		String returnStr = null;
		returnStr = new String(bytes, "unicode");
		return returnStr;
	}

	public static final String subStr(String str, int beginIdex, int subStrLen) {
		String newStr = null2Empty(str);
		String retStr = "";

		/*
		 * Returns a new string that is a substring of this string. The
		 * substring begins at the specified beginIndex and extends to the
		 * character at index endIndex - 1. Thus the length of the substring is
		 * endIndex-beginIndex. endIdex - beginIdex = subStrLen; endIdex =
		 * subStrLen + beginIdex;
		 */
		int endIdex = subStrLen + beginIdex;
		if (newStr.length() >= subStrLen) {
			retStr = newStr.substring(beginIdex, endIdex);
		} else {
			retStr = newStr;
		}
		return retStr;
	}

	public static String[] split(String pSource, String pSeperator) {
		String[] words;
		if (pSource == null) {
			words = new String[1];
			words[0] = pSource;
			return words;
		}
		StringTokenizer sTokenizer = new StringTokenizer(pSource, pSeperator);
		int tokensCount = sTokenizer.countTokens();
		words = new String[tokensCount];
		for (int index = 0; index < tokensCount; index++) {
			words[index] = sTokenizer.nextToken();
		}
		return words;
	}

	public static String[] split(String pSource, char seperateCharactor) {
		return split(pSource, String.valueOf(seperateCharactor));
	}

	public static String fillString(char charactor, int len) {
		String result = "";
		for (int i = 0; i < len; i++) {
			result += charactor;
		}
		return result;
	}

	public static boolean contains(String[] stringArray, String str, boolean isIgnoreCase) {
		int len = stringArray.length;
		for (int index = 0; index < len; index++) {
			if (isIgnoreCase == true) {
				if (stringArray[index].equals(str)) {
					return true;
				}
			} else {
				if (stringArray[index].equalsIgnoreCase(str)) {
					return true;
				}
			}
		}

		return false;
	}

	public static int getByteLength(String str) {
		int length = 0;
		for (int i = 0; i < str.length(); i++) {
			char charactor = str.charAt(i);
			int highByte = charactor >>> 8;
			length += highByte == 0 ? 1 : 2;
		}
		return length;
	}

	public static boolean isInteger(String integerStr) {
		Pattern pat = Pattern.compile("^[-\\+]?[\\d]+$");
		return pat.matcher(integerStr).matches();
	}

	public static boolean isDouble(String doubleStr) {
		Pattern pat = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pat.matcher(doubleStr).matches();
	}

	public static boolean isEmail(String emailAddr) {
		if (emailAddr == null || emailAddr.length() < 1 || emailAddr.length() > 256) {
			return false;
		}
		Pattern pat = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pat.matcher(emailAddr).matches();
	}

	public static String encoding(String sourceStr) {
		if (sourceStr == null)
			return "";
		StringBuilder ret = new StringBuilder();
		if (sourceStr != null) {
			sourceStr = sourceStr.trim();
			for (int pos = 0; pos < sourceStr.length(); pos++) {
				switch (sourceStr.charAt(pos)) {
				case '?':
					ret.append("&ques;");
					break;
				case '%':
					ret.append("&pc;");
					break;
				case '\'':
					ret.append("&apos;");
					break;
				case '#':
					ret.append("&shap;");
					break;
				case '<':
					ret.append("&lt;");
					break;
				case '_':
					ret.append("&ul;");
					break;
				case '>':
					ret.append("&gt;");
					break;
				case '&':
					ret.append("&amp;");
					break;
				case '\"':
					ret.append("&quot;");
					break;
				default:
					ret.append(sourceStr.charAt(pos));
					break;
				}
			}
		}
		return ret.toString();
	}

	public static String decoding(String encodedStr) {
		if (encodedStr == null)
			return "";
		String ret = encodedStr;
		ret = ret.replace("&shap;", "#").replace("&ques", "?");
		ret = ret.replace("&lt;", "<").replace("&gt;", ">");
		ret = ret.replace("&quot;", "\"").replace("&apos;", "\'");
		ret = ret.replace("&pc;", "%").replace("&ul", "_");
		ret = ret.replace("&amp;", "&");
		return ret;
	}

	public static String uniteTwoStringBySemicolon(String target, String pStr) {
		if (pStr == null || pStr.equals("")) {
		} else {
			if (target.equals("")) {
				target = pStr;
			} else {
				target = target + ";" + pStr;
			}
		}
		return target;
	}

	public static String uniteTwoStringBySemicolon(String target, String pStr, String pSeparator) {
		if (isEmpty(pSeparator))
			pSeparator = ";";
		if (!isEmpty(pStr)) {
			if (isEmpty(target)) {
				target = pStr;
			} else {
				target = target + pSeparator + pStr;
			}
		}
		return target;
	}

	public static String handleSpecilChar(String pParam) {
		pParam = pParam.replaceAll("<", "﹤");
		pParam = pParam.replaceAll("'", "’");

		pParam = pParam.replaceAll(">", "﹥");
		pParam = pParam.replaceAll("\"", "”");
		return pParam;
	}

	public static String removeEndStr(String pStr, String endStr) {
		if (pStr == null || "".equals(pStr) || endStr == null || "".equals(endStr)) {
			return pStr;
		} else {
			int endStrLength = endStr.length();
			return pStr.substring(0, pStr.length() - endStrLength);
		}
	}
}
