package com.digital.dance.framework.commons.enums;

import java.util.HashMap;
import java.util.Map;

public enum FrameworkStatus
{
  FAIL(new Integer(0), "失败"), SUCCESS(new Integer(1), "成功"), PROCESSING(new Integer(2), "处理中");

  private Integer key;
  private String value;
  public static final Map<String, String> map;

  private FrameworkStatus(Integer key, String value)
  {
    this.key = key;
    this.value = value;
  }

  public Integer getKey() {
    return this.key;
  }

  static
  {
    map = new HashMap<String, String>();

    for (FrameworkStatus s : values())
      map.put(String.valueOf(s.key), s.value);
  }
}