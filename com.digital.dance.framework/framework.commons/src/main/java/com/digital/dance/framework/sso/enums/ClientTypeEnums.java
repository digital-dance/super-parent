package com.digital.dance.framework.sso.enums;

public enum ClientTypeEnums
{
  APP(new String("1")), WEB(new String("2")), WEBAJAX(new String("3"));

  private String clientType;

  private ClientTypeEnums(String clientType) {
    this.clientType = clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getClientType() {
    return this.clientType;
  }
}