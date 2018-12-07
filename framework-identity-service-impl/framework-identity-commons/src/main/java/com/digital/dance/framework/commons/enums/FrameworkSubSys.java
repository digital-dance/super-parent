package com.digital.dance.framework.commons.enums;

import java.util.HashMap;
import java.util.Map;

public enum FrameworkSubSys
{
  UserCenter(new Integer(0), "user.center"), Other(new Integer(1), "Other");

  private Integer subSysKey;
  private String subSysValue;
  public static final Map<String, String> subSysMap;

  private FrameworkSubSys(Integer subSysKey, String subSysValue)
  {
    this.subSysKey = subSysKey;
    this.subSysValue = subSysValue;
  }

  public Integer getSubSysKey() {
    return this.subSysKey;
  }

  static
  {
    subSysMap = new HashMap();

    for (FrameworkSubSys subSys : values())
      subSysMap.put(String.valueOf(subSys.subSysKey), subSys.subSysValue);
  }
}