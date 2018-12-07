package com.digital.dance.framework.sso.entity;

import java.util.ArrayList;
import java.util.List;

public class LoginedSessionList {
	public final static String GLOBAL_SESSIONS = "GLOBAL_SESSIONS";
	private List<String> loginedSessions = new ArrayList<String>();

	/**
	 * @return the loginedSessions
	 */
	public List<String> getLoginedSessions() {
		return loginedSessions;
	}

	/**
	 * @param loginedSessions the loginedSessions to set
	 */
	public void setLoginedSessions(List<String> loginedSessions) {
		this.loginedSessions = loginedSessions;
	}
}
