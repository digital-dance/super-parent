package com.digital.dance.framework.sso.enums;

public enum UserTypeEnums
{
  BUTT_JOINT(new Integer(1)), CAS(new Integer(2));

  private Integer userType;

  private UserTypeEnums(Integer userType) {
    this.userType = userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public Integer getUserType() {
    return this.userType;
  }
}