package com.digital.dance.commons.utils;

import com.digital.dance.commons.exception.BizException;
import java.lang.reflect.InvocationTargetException;

public class BeanUtils
{
  public static void copyProperties(Object source, Object target)
  {
    try
    {
      org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    } catch (IllegalAccessException e) {
      throw new BizException("copy bean properties error.", e);
    } catch (InvocationTargetException e) {
      throw new BizException("copy bean properties error.", e);
    }
  }
}
