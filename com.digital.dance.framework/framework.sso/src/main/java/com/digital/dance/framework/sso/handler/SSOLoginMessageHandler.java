package com.digital.dance.framework.sso.handler;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.digital.dance.framework.message.handler.MQMessageHandler;
import com.digital.dance.framework.sso.entity.LoginedSessionList;
import com.digital.dance.framework.sso.entity.LogoutInfo;
import com.digital.dance.framework.sso.util.SSOLoginManageHelper;

import com.digital.dance.commons.serialize.json.utils.JSONUtils;
import com.digital.dance.framework.infrastructure.commons.StringTools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSOLoginMessageHandler implements MQMessageHandler {
	private static Logger logger = LoggerFactory.getLogger(SSOLoginMessageHandler.class);
	private SSOLoginManageHelper ssologinManageHelper;

	public void handlerMessage(MessageExt msg) throws Exception {
		logger.info("message from CAS");

		if (msg == null) {
			logger.error("message is empty");
			return;
		}

		logger.info(String.format("message:id:%s topic:%s tags:%s message:%s",
				new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
		
		String msgObjsStr = StringTools.getStrFromBytes(msg.getBody());		

		LogoutInfo logoutInfo = (LogoutInfo) JSONUtils.toObject(msgObjsStr, LogoutInfo.class);

		if ((logoutInfo != null) && (StringUtils.isNotEmpty(logoutInfo.getJsessionidCAS()))) {
			ssologinManageHelper.deleteSessionData(logoutInfo.getJsessionidCAS(),
					SSOLoginManageHelper.TOKEN);
			ssologinManageHelper.deleteSessionData(logoutInfo.getJsessionidCAS(),
					SSOLoginManageHelper.SECURITY_DES);
			
			String loginedSessionsJson = ssologinManageHelper.getSessionData(LoginedSessionList.GLOBAL_SESSIONS, logoutInfo.getJsessionidCAS(), String.class);
			LoginedSessionList loginedSessions = (LoginedSessionList) JSONUtils.toObject(loginedSessionsJson, LoginedSessionList.class);
			
			if (loginedSessions != null && loginedSessions.getLoginedSessions() != null) {
				List<String> sessionsList = loginedSessions.getLoginedSessions();
				
				for (String id : sessionsList) {
					ssologinManageHelper.deleteSessionData(id, SSOLoginManageHelper.TOKEN);
					ssologinManageHelper.deleteSessionData(id, SSOLoginManageHelper.SECURITY_DES);
					ssologinManageHelper.deleteSessionData(id, SSOLoginManageHelper.CAS_SESSION_ID);
				}

			}

		}
	}

	public void setSsologinManageHelper(SSOLoginManageHelper ssologinManageHelper) {
		this.ssologinManageHelper = ssologinManageHelper;
	}
}