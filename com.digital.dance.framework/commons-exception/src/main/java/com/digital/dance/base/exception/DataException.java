package com.digital.dance.base.exception;

public class DataException  extends RuntimeException {
	private static final long serialVersionUID = 6362782762086492531L;
	private int errorCode;
	private String message;
	private Throwable throwable;

	public DataException() {
	}

	public DataException(String message) {
		this.message = message;
	}

	public DataException(int errorCode, Throwable throwable) {
		this.errorCode = errorCode;
		this.throwable = throwable;
	}

	public DataException(String message, Throwable throwable) {
		this.message = message;
		this.throwable = throwable;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getThrowable() {
		return this.throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
