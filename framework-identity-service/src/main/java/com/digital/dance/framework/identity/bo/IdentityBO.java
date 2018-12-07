package com.digital.dance.framework.identity.bo;

import java.io.Serializable;
import java.util.Date;

public class IdentityBO extends IdentityServiceBaseBO
  implements Serializable
{
  private static final long serialVersionUID = -1191506725365424595L;
  private Long id;
  private String system;
  private String subSys;
  private String module;
  private String table;
  private Long identity;
  private Date createTime;
  private Date updateTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSystem() {
    return this.system;
  }

  public void setSystem(String system) {
    this.system = system;
  }

  public String getSubSys() {
    return this.subSys;
  }

  public void setSubSys(String subSys) {
    this.subSys = subSys;
  }

  public String getModule() {
    return this.module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getTable() {
    return this.table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public Long getValue() {
    return this.identity;
  }

  public Long getIdentity() {
    return this.identity;
  }

  public void setIdentity(Long identity) {
    this.identity = identity;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}