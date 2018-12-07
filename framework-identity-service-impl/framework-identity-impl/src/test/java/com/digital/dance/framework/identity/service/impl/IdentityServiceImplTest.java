package com.digital.dance.framework.identity.service.impl;

import org.junit.Test;

import com.digital.dance.framework.identity.bo.IdentityBO;
import com.digital.dance.framework.commons.unittest.UnitTestBase;
import com.digital.dance.framework.identity.service.IdentityService;

public class IdentityServiceImplTest extends UnitTestBase {

	private IdentityService dictionaryService;
	private static final String CFG_PATH = "classpath*:**/framework-identity-impl-test-context.xml";

	@Test
	public void testGenerateId() {
		unitTestContext = getInstance(CFG_PATH);
		dictionaryService = unitTestContext.getBean(IdentityService.class);
		
		Boolean throwException = true;
		Long idVaue = null;
		try {
			idVaue = dictionaryService.generateId("hp", "serverroom", "identityService", "identity");
		} catch (Exception e) {
			throwException = false;
		}
		org.junit.Assert.assertTrue( idVaue != null);
	}

}
