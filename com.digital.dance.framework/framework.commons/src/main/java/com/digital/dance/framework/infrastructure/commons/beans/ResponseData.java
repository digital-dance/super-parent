package com.digital.dance.framework.infrastructure.commons.beans;

import java.io.Serializable;

/**
 * 
 * @author liuxiny
 *
 * @param <T>
 */
public class ResponseData<T> implements Serializable
{
  private static final long serialVersionUID = -7576926819530318554L;
  private Integer msgCode;
  private String msg;
  private T resData;
  public static final int OK = 1;
  public static final int FAIL = -1;
  private Boolean isOk;

  public ResponseData()
  {
  }

  public ResponseData(Boolean isOk, T resData)
  {
    this.isOk = isOk;
    this.resData = resData;
  }

  public ResponseData(Boolean isOk, Integer msgCode, String msg)
  {
    this.isOk = isOk;
    this.msgCode = msgCode;
    this.msg = msg;
  }

  public Boolean getIsOk() {
    return this.isOk;
  }

  public void setIsOk(Boolean isOk) {
    this.isOk = isOk;
  }

  public Integer getMsgCode() {
    return this.msgCode;
  }

  public void setMsgCode(Integer msgCode) {
    this.msgCode = msgCode;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getResData() {
    return this.resData;
  }

  public void setResData(T resData) {
    this.resData = resData;
  }
}
