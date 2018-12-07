package com.digital.dance.framework.commons.unittest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import com.digital.dance.framework.commons.Log;

public abstract class UnitTestBase {
	private final static Log unitTestLog = new Log(UnitTestBase.class);
	
	public volatile static ApplicationContext unitTestContext = null;
	protected volatile static Object unitTestLockObj = new Object();
	public static ApplicationContext getInstance(String unitTestCfgPath) {
        if (unitTestContext == null) {
            synchronized (UnitTestBase.unitTestLockObj) {
                if (unitTestContext == null) {
                	unitTestLog.debug( 
					"-----------begin to load " + unitTestCfgPath + " -----------");
                    String[] unitTestConfigLocations = new String[1];
                    	unitTestConfigLocations[0] = unitTestCfgPath;
                    unitTestContext = new ClassPathXmlApplicationContext(unitTestConfigLocations);
                    unitTestLog.debug( 
        					"-----------end to load " + unitTestCfgPath + " -----------");
                }
            }
        }
        return unitTestContext;
    }
}

