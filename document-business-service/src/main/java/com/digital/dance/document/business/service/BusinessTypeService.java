package com.digital.dance.document.business.service;

import com.digital.dance.document.business.bo.BusinessTypeBO;
import java.util.List;
import java.util.Map;

public abstract interface BusinessTypeService
{
  public abstract List<BusinessTypeBO> queryBusinessTypeList(Long paramLong)
    throws Exception;

  public abstract BusinessTypeBO queryBusinessType(Long paramLong)
    throws Exception;

  public abstract void insertBusinessType(BusinessTypeBO paramBusinessTypeBO)
    throws Exception;

  public abstract void updateBusinessType(Long paramLong, String paramString1, String paramString2)
    throws Exception;

  public abstract void deleteBusinessType(Long paramLong)
    throws Exception;

  public abstract Map<String, String> getTypeMap(Long paramLong, String paramString)
    throws Exception;
}