package com.digital.dance.commons.beans;

import java.io.Serializable;

public class PageParamData<T>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Integer pageNo;
  private Integer pageSize;
  private T data;

  public Integer getPageNo()
  {
    return this.pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
