package com.digital.dance.document.business.service;

import com.digital.dance.document.business.bo.BusinessDocBO;

public abstract interface TemplateService
{
  public abstract BusinessDocBO exportWord(Long paramLong)
    throws Exception;

  public abstract boolean removeCacheDir();

  public abstract String getD2PUrl(BusinessDocBO paramBusinessDocBO, String paramString);

  public abstract String getD2PToken(String paramString);
}