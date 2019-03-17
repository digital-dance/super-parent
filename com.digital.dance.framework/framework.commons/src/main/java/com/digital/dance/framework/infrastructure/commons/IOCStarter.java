package com.digital.dance.framework.infrastructure.commons;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCStarter {
private final static Log log = new Log(IOCStarter.class);

    public volatile static ClassPathXmlApplicationContext iOCStarterContext = null;
    protected volatile static Object iOCStarterLockObj = new Object();
    public static ClassPathXmlApplicationContext startIOCInstance(String... iOCCfgPath) {
        if (iOCStarterContext == null) {
            synchronized (IOCStarter.iOCStarterLockObj) {
                if (iOCStarterContext == null) {
                    log.debug(
                            "-----------begin to load " + iOCCfgPath + " -----------");
                    String[] unitTestConfigLocations = new String[iOCCfgPath.length];
                    unitTestConfigLocations = iOCCfgPath;
                    iOCStarterContext = new ClassPathXmlApplicationContext(unitTestConfigLocations);
                    iOCStarterContext.start();
                    log.debug(
                            "-----------end to load " + iOCCfgPath + " -----------");

                }
            }
        }
        return iOCStarterContext;
    }
}

