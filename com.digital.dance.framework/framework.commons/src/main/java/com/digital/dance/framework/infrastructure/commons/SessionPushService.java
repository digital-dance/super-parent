package com.digital.dance.framework.infrastructure.commons;

public interface SessionPushService <T extends Object, SessionObj extends Object> {
    public void setAttribute( String key, T t, SessionObj sessionObj) ;
}
