package org.springframework.session.web.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.session.web.http.Base64;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitalDefaultCookieSerializer extends DefaultCookieSerializer implements CookieSerializer {
    private static final Log logger = LogFactory.getLog(DefaultCookieSerializer.class);
    private String cookieName = "SESSION";
    private Boolean useSecureCookie;
    private boolean useHttpOnlyCookie = this.isServlet3();
    private String cookiePath;
    private int cookieMaxAge = -1;
    private String domainName;
    private Pattern domainNamePattern;
    private String jvmRoute;
    private boolean useBase64Encoding;
    private String rememberMeRequestAttribute;

    public Cookie getCookie(Cookie[] cookies, String cookieKey) {
        if(cookies != null) {

            int size = cookies.length;
            for(int i = size - 1; i >=0; i--){
                Cookie cookie = cookies[i];
                if (cookieKey.equals(cookie.getName())) {
                    return cookie;
                }
            }

        }
        return null;
    }

    @Override
    public void writeCookieValue(CookieValue cookieValue) {
        HttpServletRequest request = cookieValue.getRequest();
        Cookie sessionCookie = getCookie(request.getCookies(), cookieName);
        HttpServletResponse response = cookieValue.getResponse();
        String requestedCookieValue = cookieValue.getCookieValue();
        String actualCookieValue = this.jvmRoute == null ? requestedCookieValue : requestedCookieValue + this.jvmRoute;
        if(sessionCookie == null){
            sessionCookie = new Cookie(this.cookieName, this.useBase64Encoding ? this.base64Encode(actualCookieValue) : actualCookieValue);
        }

        sessionCookie.setSecure(this.isSecureCookie(request));
        sessionCookie.setPath(this.getCookiePath(request));
        String domainName = this.getDomainName(request);
        if (domainName != null) {
            sessionCookie.setDomain(domainName);
        }

        if (this.useHttpOnlyCookie) {
//            sessionCookie.setHttpOnly(true);
        }

        if ("".equals(requestedCookieValue)) {
            sessionCookie.setMaxAge(0);
        } else if (this.rememberMeRequestAttribute != null && request.getAttribute(this.rememberMeRequestAttribute) != null) {
            sessionCookie.setMaxAge(2147483647);
        } else {
            sessionCookie.setMaxAge(this.cookieMaxAge);
        }

        response.addCookie(sessionCookie);
    }

    private String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = org.springframework.session.web.http.Base64.decode(base64Value.getBytes());
            return new String(decodedCookieBytes);
        } catch (Exception var3) {
            logger.debug("Unable to Base64 decode value: " + base64Value);
            return null;
        }
    }

    private String base64Encode(String value) {
        byte[] encodedCookieBytes = org.springframework.session.web.http.Base64.encode(value.getBytes());
        return new String(encodedCookieBytes);
    }

    private String getDomainName(HttpServletRequest request) {
        if (this.domainName != null) {
            return this.domainName;
        } else {
            if (this.domainNamePattern != null) {
                Matcher matcher = this.domainNamePattern.matcher(request.getServerName());
                if (matcher.matches()) {
                    return matcher.group(1);
                }
            }

            return null;
        }
    }

    private String getCookiePath(HttpServletRequest request) {
        return this.cookiePath == null ? request.getContextPath() + "/" : this.cookiePath;
    }

    private boolean isSecureCookie(HttpServletRequest request) {
        return this.useSecureCookie == null ? request.isSecure() : this.useSecureCookie;
    }

    private boolean isServlet3() {
        try {
            ServletRequest.class.getMethod("startAsync");
            return true;
        } catch (NoSuchMethodException var2) {
            return false;
        }
    }
}
