package com.digital.dance.document.business.bo;

import java.io.Serializable;
import java.util.Date;

public class BusinessTypeBO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long parentId;
  private String typeName;
  private String createBy;
  private Date createTime;
  private String updateBy;
  private Date updateTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getParentId() {
    return this.parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getCreateBy() {
    return this.createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getUpdateBy() {
    return this.updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}