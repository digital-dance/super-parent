package com.digital.dance.framework.identity.service.impl;

import com.digital.dance.framework.identity.dao.IdentityDao;
import org.springframework.beans.factory.annotation.Autowired;

import com.digital.dance.framework.identity.dao.IdentityDao;
import com.digital.dance.framework.identity.entity.IdentityEO;

import com.digital.dance.framework.identity.service.IdentityService;

//@Service
public class IdentityServiceImpl implements IdentityService {
	
	@Autowired
	private IdentityDao identityDao;

	@Override
	public Long generateId(String paramString1, String paramString2, String paramString3, String paramString4) {
		IdentityEO identityEO = new IdentityEO();
		identityEO.setSystem(paramString1);
		identityEO.setSubSys(paramString2);
		identityEO.setModule(paramString3);
		identityEO.setTable(paramString4);
		return identityDao.findNextIdentityValue(identityEO);
	}
}
