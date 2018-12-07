package com.digital.dance.commons.exception;

public class BizException extends RuntimeException
{
  private static final long serialVersionUID = 6362782762086492531L;
  private int errorCode;
  private String message;
  private Throwable throwable;

  public BizException()
  {
  }

  public BizException(String message)
  {
    this.message = message;
  }

  public BizException(int errorCode, Throwable throwable)
  {
    this.errorCode = errorCode;
    this.throwable = throwable;
  }

  public BizException(String message, Throwable throwable)
  {
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