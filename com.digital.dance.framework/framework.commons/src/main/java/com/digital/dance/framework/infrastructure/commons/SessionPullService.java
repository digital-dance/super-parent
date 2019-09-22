package com.digital.dance.framework.infrastructure.commons;

public interface SessionPullService<T extends Object, SessionObj extends Object> {
    public  T getAttribute( String key, SessionObj sessionObj) ;

}
