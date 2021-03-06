package com.digital.dance.framework.infrastructure.commons;

import com.digital.dance.framework.infrastructure.commons.constants.FrameworkConstants;
import com.digital.dance.framework.sso.entity.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

    protected static SessionPullService sessionPullService;
    protected static SessionPushService sessionPushService;

    public static <SessionObj extends Object>  LoginInfo getLoginInfoFromSession(SessionObj appSession) {

        if( sessionPullService == null && ( appSession instanceof HttpSession ) ){
            sessionPullService = new SessionPullService<LoginInfo, HttpSession>(){

                @Override
                public LoginInfo getAttribute(String key, HttpSession httpSession) {

                    Object loginInfo = (httpSession != null) ? httpSession.getAttribute( FrameworkConstants.LOGIN_INFO ) : null;
                    if(loginInfo == null)return null;
                    return (LoginInfo)loginInfo;
                }
            };
        }
        Object loginInfo = (appSession != null) ? sessionPullService.getAttribute( FrameworkConstants.LOGIN_INFO, appSession ) : null;
        if(loginInfo == null)return null;
        return (LoginInfo)loginInfo;
    }

    public static LoginInfo getLoginInfoFromSession(HttpServletRequest request) {
        HttpSession appSession = request.getSession();
//        Object loginInfo = (appSession != null) ? appSession.getAttribute("login_info") : null;
        Object loginInfo = (appSession != null) ? appSession.getAttribute( FrameworkConstants.LOGIN_INFO ) : null;
        if(loginInfo == null)return null;
        return (LoginInfo)loginInfo;
    }

//    public static void setLoginInfo2Session(HttpServletRequest request, Object loginInfo) {
//        HttpSession appSession = request.getSession();
////        if(appSession != null) appSession.setAttribute("login_info", loginInfo);
//        if(appSession != null) appSession.setAttribute( FrameworkConstants.LOGIN_INFO, loginInfo );
//    }

    public static <T extends Object> T getFromSession(HttpServletRequest request, String key) {
        HttpSession appSession = request.getSession();
        Object object = (appSession != null) ? appSession.getAttribute(key) : null;
        if(object == null)return null;
        return (T)object;
    }

    public static <T extends Object> void save2Session(HttpServletRequest request, String key, T t) {
        HttpSession appSession = request.getSession();
        if(appSession != null) appSession.setAttribute(key, t);
    }

}
