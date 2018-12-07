package com.digital.dance.commons.beans;

import java.io.Serializable;

public class ResponseData<T>
  implements Serializable
{
  private static final long serialVersionUID = -7576926819530318554L;
  public static final int OK = 1;
  public static final int NO = -1;
  private Boolean isSuccess;
  private Integer errorCode;
  private String message;
  private T data;

  public ResponseData()
  {
  }

  public ResponseData(Boolean isSuccess, T data)
  {
    this.isSuccess = isSuccess;
    this.data = data;
  }

  public ResponseData(Boolean isSuccess, Integer errorCode, String message)
  {
    this.isSuccess = isSuccess;
    this.errorCode = errorCode;
    this.message = message;
  }

  public Boolean getIsSuccess() {
    return this.isSuccess;
  }

  public void setIsSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public Integer getErrorCode() {
    return this.errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
