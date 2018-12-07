package com.digital.dance.framework.commons.enums;

import java.util.HashMap;
import java.util.Map;

public enum FrameworkSystem
{
  CloudsKeeper(new Integer(0), "CloudsKeeper"), Other(new Integer(1), "Other");

  private Integer sysKey;
  private String sysValue;
  public static final Map<String, String> sysMap;

  private FrameworkSystem(Integer sysKey, String sysValue)
  {
    this.sysKey = sysKey;
    this.sysValue = sysValue;
  }

  public Integer getSysKey() {
    return this.sysKey;
  }

  static
  {
    sysMap = new HashMap();

    for (FrameworkSystem sys : values())
      sysMap.put(String.valueOf(sys.sysKey), sys.sysValue);
  }
}