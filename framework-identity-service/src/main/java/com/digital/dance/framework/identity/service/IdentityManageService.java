package com.digital.dance.framework.identity.service;

import com.digital.dance.framework.identity.bo.IdentityBO;
import com.digital.dance.framework.identity.bo.IdentityBO;
import com.digital.dance.commons.beans.PageData;

public abstract interface IdentityManageService
{
  public abstract PageData<IdentityBO> queryIdentities(String paramString1, String paramString2, String paramString3, Integer paramInteger1, Integer paramInteger2)
    throws Exception;
}