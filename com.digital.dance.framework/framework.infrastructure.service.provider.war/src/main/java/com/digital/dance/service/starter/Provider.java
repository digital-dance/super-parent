package com.digital.dance.service.starter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.digital.dance.framework.infrastructure.commons.Log;

public class Provider extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    Log log = new Log(Provider.class);
    public void init() throws ServletException {

        try {
            log.info(this.getClass().getName() + "初始化dubbo服务端");
            DubboStart.init();
        }
        catch (Exception e) {
            String error = "初始化dubbo服务端";
            log.info(this.getClass().getName()+ error +":"+e.getMessage(), e);
            e.printStackTrace();
        }
    }
}