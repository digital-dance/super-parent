package com.digital.dance.framework.commons;

public class Constants {

	/**
	 * code for response
	 */
	public static final String RETURN_CODE_SUCCESS = new String("10000"); // 10000表示成功
	public static final String RETURN_CODE_FAILED = new String("10001"); // 10001表示不成
	public static final String RETURN_CODE_REDIRECT = new String("10002"); // 10002重定向
	public static final String SUCCESS_MSG = "SUCCESS";
	public static final String FAILED_MSG = "FAILED";
	/**
	 * status code
	 */
	public static enum ReturnCode {
		/**
		 * 成功标识
		 */
		SUCCESS("10000"),
		/**
		 * 失败标识
		 */
		FAILURE("10001"),
		/**
		 * 重定向标识
		 */
		REDIRECT("10002");

		private String statusCode;

		private ReturnCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String Code() {
			return this.statusCode;
		}
	}

	/**
	 * yes or no
	 * 
	 */
	public static enum NY {
		/**
		 * yes
		 */
		YES("Y"),
		/**
		 * false
		 */
		NO("N");

		private String statusCode;

		private NY(String statusCode) {
			this.statusCode = statusCode;
		}

		public String Code() {
			return this.statusCode;
		}
	}

	/**
	 * 操作类型
	 */
	public static enum ActionCode {
		/**
		 * 同意
		 */
		CONFIRM("00"),
		/**
		 * 不同意
		 */
		REFUSED("10");

		private String statusCode;

		private ActionCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String Code() {
			return this.statusCode;
		}
	}

	/**
	 * 产品条线 ALI_CLOUD-阿里云 TENXUN_CLOUD-腾讯云
	 */
	public static enum ProductLine {
		/**
		 * 阿里云
		 */
		ALI_CLOUD("ALI_CLOUD"),
		/**
		 * 腾讯云
		 */
		TENXUN_CLOUD("TENXUN_CLOUD");
		private String statusCode;

		private ProductLine(String code) {
			this.statusCode = code;
		}

		public String Code() {
			return this.statusCode;
		}
	}

}
