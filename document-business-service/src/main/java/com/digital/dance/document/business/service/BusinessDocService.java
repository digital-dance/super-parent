package com.digital.dance.document.business.service;

import com.digital.dance.document.business.bo.BusinessDocBO;
import java.util.List;

public abstract interface BusinessDocService
{
  public abstract List<BusinessDocBO> queryBusinessDocList(Long paramLong, String paramString1, String paramString2, String paramString3)
    throws Exception;

  public abstract void removeBusinessDoc(Long paramLong1, Long paramLong2, String paramString)
    throws Exception;

  public abstract void upload(BusinessDocBO paramBusinessDocBO)
    throws Exception;

  public abstract BusinessDocBO download(Long paramLong)
    throws Exception;

  public abstract BusinessDocBO getTemplate(Long paramLong)
    throws Exception;

  public abstract String getUploadPath(BusinessDocBO paramBusinessDocBO);

  public abstract void checkValidUpload(BusinessDocBO paramBusinessDocBO)
    throws Exception;

  public abstract String getUploadFilePath(BusinessDocBO paramBusinessDocBO);

  public abstract String getUploadFileName(BusinessDocBO paramBusinessDocBO);
}
