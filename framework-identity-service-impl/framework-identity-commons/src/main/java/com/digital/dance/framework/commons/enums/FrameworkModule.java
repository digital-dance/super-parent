package com.digital.dance.framework.commons.enums;

import java.util.Map;
import java.util.HashMap;

public enum FrameworkModule
{
  Sms(new Integer(0), "sms"), Mail(new Integer(1), "mail"), Other(new Integer(2), "Other");

  private Integer moduleKey;
  private String moduleValue;
  public static final Map<String, String> moduleMap;

  private FrameworkModule(Integer moduleKey, String moduleValue)
  {
    this.moduleKey = moduleKey;
    this.moduleValue = moduleValue;
  }

  public Integer getModuleKey() {
    return this.moduleKey;
  }

  static
  {
    moduleMap = new HashMap<String, String>();

    for (FrameworkModule module : values())
      moduleMap.put(String.valueOf(module.moduleKey), module.moduleValue);
  }
}