package com.digital.dance.document.business.service;

import com.digital.dance.document.business.bo.BusinessDocBO;
import java.util.List;

public abstract interface BusDubboService
{
  public abstract List<BusinessDocBO> queryBusinessDocList(Long paramLong, String paramString1, String paramString2)
    throws Exception;

  public abstract Long uploadBusinessDoc(BusinessDocBO paramBusinessDocBO, byte[] paramArrayOfByte)
    throws Exception;

  public abstract byte[] downloadBusinessDoc(Long paramLong)
    throws Exception;
}