package com.digital.dance.framework.codis;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 
 * @author liuxiny
 *
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

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

	/**
	 *
	 * 字符串替换 s 搜索字符串 s1 要查找字符串 s2 要替换字符串
	 *
	 * @param s
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}
			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
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

	/**
	 *
	 * 将字符串数组合并成一个以 delim 分隔的字符串
	 *
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String combineArray(String[] array, String delim) {
		if (array == null || array.length == 0) {
			return "";
		}
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	public static String fillString(char charactor, int len) {
		String result = "";
		for (int i = 0; i < len; i++) {
			result += charactor;
		}
		return result;
	}

	/**
	 *
	 * 字符串数组中是否包含指定的字符串
	 *
	 * @param stringArray
	 *
	 * @param str
	 *
	 * @param isIgnoreCase
	 *
	 * @return
	 */
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

	/**
	 * 得到字符串的字节长度
	 *
	 * @param str
	 * @return
	 */
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

	/**
	 * 判断字符是否为双字节字符，如中文
	 *
	 * @param c
	 * @return
	 */
	public static boolean isDoubleByte(char c) {
		return !((c >>> 8) == 0);
	}

	/**
	 * 字符串Like判断，支持%
	 *
	 * @param pattern
	 * @param string
	 * @return
	 */
	public static boolean matchString(String pattern, String string) {
		int stringLength = string.length();
		int stringIndex = 0;
		for (int patternIndex = 0; patternIndex < pattern.length(); ++patternIndex) {
			char c = pattern.charAt(patternIndex);
			if (c == '%') {
				while (stringIndex < stringLength) {
					if (matchString(pattern.substring(patternIndex + 1), string
							.substring(stringIndex))) {
						return true;
					}
					++stringIndex;
				}
			} else {
				if (stringIndex >= stringLength
						|| c != string.charAt(stringIndex)) {
					return false;
				}
				++stringIndex;
			}
		}
		return stringIndex == stringLength;
	}

	/**
	 *
	 * 将字符串追加至目标字符串并通过seperator分隔，不考虑重复
	 *
	 * @param destString
	 * @param sourceString
	 * @param seperator
	 * @return
	 */
	public static String addtoTotalString(String destString,
										  String sourceString, String seperator) {
		if (isEmpty(destString)) {
			return sourceString;
		} else {
			return destString + seperator + sourceString;
		}
	}

	/**
	 *
	 * 获取字符串的位置
	 *
	 * @param destString
	 * @param remString
	 * @param separator
	 * @return
	 */
	private static int getIndexOfTotalString(String destString,
											 String remString, String separator) {
		if (destString == null) {
			return -1;
		}
		String tempTotalString = separator + destString + separator;
		int index = tempTotalString.indexOf(separator + remString + separator);
		if (index != 0 && index != -1) {
			index = index - 1;
		}
		return index;
	}

	/**
	 *
	 * 将一个字符串从一个通过separator连接的字符串中移除，只移除一次
	 *
	 *
	 * @param destString
	 * @param remString
	 * @return
	 */
	public static String removeFromTotalString(String destString,
											   String remString, String separator) {
		if (destString == null) {
			return "";
		}
		int index = getIndexOfTotalString(destString, remString, separator);
		if (index == -1) {
			return destString;
		} else if (index == 0) {
			if (destString.equals(remString)) {
				return "";
			} else {
				return destString.substring(remString.length() + 1);
			}
		} else {
			if (destString.length() == index + remString.length()) {
				return destString.substring(0, index - 1);
			} else {
				return destString.substring(0, index)
						+ destString.substring(remString.length() + 1 + index);
			}
		}
	}


	/**
	 * 功能描述：判断输入的字符串是否为纯汉字
	 *
	 *
	 * @param str
	 *            传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
	 *
	 * @param str
	 *            String 原始字符串
	 *
	 * @return String 替换后的字符串
	 *
	 */
	public static String htmlencode(String str) {
		if (str == null) {
			return null;
		}
		return replace("\"", "&quot;", replace("<", "&lt;", str));
	}

	/**
	 * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
	 *
	 *
	 * @param str
	 *            String
	 * @return String
	 */
	public static String htmldecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("&quot;", "\"", replace("&lt;", "<", str));
	}

	/**
	 * 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
	 *
	 * @param str
	 *            String 原始字符串
	 *
	 * @return String 替换后的字符串
	 *
	 */
	public static String htmlshow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "&lt;", str);
		str = replace(" ", "&nbsp;", str);
		str = replace("\r\n", "<br/>", str);
		str = replace("\n", "<br/>", str);
		str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
		return str;
	}

	/**
	 * 功能描述：返回指定字节长度的字符串
	 *
	 *
	 * @param str
	 *            String 字符串
	 *
	 * @param length
	 *            int 指定长度
	 * @return String 返回的字符串
	 */
	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception e) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	public static String format(String fmtString, Object...value){
		String formatedStr = "";
		if(isEmpty(fmtString)){
			return fmtString;
		}
		if(null != value && value.length != 0){
			formatedStr = String.format(fmtString, value);
		}
		return formatedStr;
	}
}
