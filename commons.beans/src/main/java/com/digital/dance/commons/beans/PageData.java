package com.digital.dance.commons.beans;

import java.io.Serializable;
import java.util.List;

public class PageData<T>
  implements Serializable
{
  private static final long serialVersionUID = 7583013970905172965L;
  private Integer totalNum;
  private Integer totalPage;
  private Integer pageSize;
  private Integer pageNO;
  private List<T> list;

  public Integer getTotalNum()
  {
    return this.totalNum;
  }

  public void setTotalNum(Integer totalNum) {
    this.totalNum = totalNum;
  }

  public Integer getTotalPage() {
    return this.totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNO() {
    return this.pageNO;
  }

  public void setPageNO(Integer pageNO) {
    this.pageNO = pageNO;
  }

  public List<T> getList() {
    return this.list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }
}
