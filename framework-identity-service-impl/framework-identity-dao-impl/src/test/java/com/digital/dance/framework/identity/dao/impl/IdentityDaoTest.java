package com.digital.dance.framework.identity.dao.impl;

import org.junit.Test;

import com.digital.dance.framework.commons.unittest.UnitTestBase;
import com.digital.dance.framework.identity.dao.IdentityDao;
import com.digital.dance.framework.identity.entity.IdentityEO;

public class IdentityDaoTest extends UnitTestBase {

	private IdentityDao identityDao;
private static final String CFG_PATH = "classpath*:**/framework-identity-dao-test.xml";

	@Test
	public void testFindNextIdentityValue() {
		unitTestContext = getInstance(CFG_PATH);
		identityDao = unitTestContext.getBean(IdentityDao.class);
		
		Boolean throwException = true;
		
		IdentityEO identityEO = new IdentityEO();
		identityEO.setSystem("hp01");
		identityEO.setSubSys("serverroom");
		identityEO.setModule("identityService");
		identityEO.setTable("identity");
		Long idValue = null;
		try {
			idValue = identityDao.findNextIdentityValue(identityEO);
		} catch (Exception e) {
			throwException = false;
		}
		org.junit.Assert.assertTrue( idValue != null);
	}

	@Test
	public void testResetIdentityValue() {
		unitTestContext = getInstance(CFG_PATH);
		identityDao = unitTestContext.getBean(IdentityDao.class);
		
		Boolean throwException = true;
		IdentityEO identityEO = new IdentityEO();
		identityEO.setSystem("HP");
		identityEO.setSubSys("serverroom");
		identityEO.setModule("identityService");
		identityEO.setTable("identity");
		identityEO.setIdentity(2018000000000l);
		Long idValue = null;
		try {
			idValue = identityDao.resetIdentityValue(identityEO);
		} catch (Exception e) {
			throwException = false;
		}
		org.junit.Assert.assertTrue( idValue != null);
	}

}
