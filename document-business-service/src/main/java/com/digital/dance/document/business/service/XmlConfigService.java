package com.digital.dance.document.business.service;

import com.digital.dance.document.business.bo.FSizeCfgBO;
import java.util.List;
import java.util.Map;

public abstract interface XmlConfigService
{
  public abstract List<FSizeCfgBO> getFSizeCfgList()
    throws Exception;

  public abstract boolean updateCfg(Map<String, String> paramMap)
    throws Exception;

  public abstract String getFSizeCfg(String paramString)
    throws Exception;
}