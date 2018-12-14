package com.digital.dance.framework.sso.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class LoginInfo implements Serializable {
	private static final long serialVersionUID = 3323939085933245086L;
	public Long tokenTimeOut = Long.valueOf(-1L);
	private String jsessionidCAS;
	private String browserContext;
	private String ldapUId;
	private String locationCode;
	
	private String userId;
	private String userName;
	private String userDisplayName;
//	private String userEmail;
	private String userCategory;
	private String userMobile;

	private String email;

	private String phone;

	private Boolean rolePrivilegeInd;

	private Integer state;

	private Date insertOn;

	private String insertBy;

	private Date updateOn;

	private String updateBy;
	private List<LoginUserRole> userRoles;

	private String openId;
	
	private String orgCode;
	// 密码
	private String password;

	// 短信验证码
	private String validCode;
		
	// 会话ID
	private String sessionId;

	// token创建时间
	private Timestamp tokenTimestamp = new Timestamp(new Date().getTime());

	public Timestamp getTokenTimestamp() {
		this.tokenTimestamp = new Timestamp(new Date().getTime());
		return this.tokenTimestamp;
	}

	public void setTokenTimestamp(Timestamp tokenTimestamp) {
		this.tokenTimestamp = new Timestamp(new Date().getTime());
	}

	/**
	 * @return the tokenTimeOut
	 */
	public Long getTokenTimeOut() {
		return tokenTimeOut;
	}

	/**
	 * @param tokenTimeOut the tokenTimeOut to set
	 */
	public void setTokenTimeOut(Long tokenTimeOut) {
		this.tokenTimeOut = tokenTimeOut;
	}

	/**
	 * @return the jsessionidCAS
	 */
	public String getJsessionidCAS() {
		return jsessionidCAS;
	}

	/**
	 * @param jsessionidCAS the jsessionidCAS to set
	 */
	public void setJsessionidCAS(String jsessionidCAS) {
		this.jsessionidCAS = jsessionidCAS;
	}

	/**
	 * @return the browserContext
	 */
	public String getBrowserContext() {
		return browserContext;
	}

	/**
	 * @param browserContext the browserContext to set
	 */
	public void setBrowserContext(String browserContext) {
		this.browserContext = browserContext;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userDisplayName
	 */
	public String getUserDisplayName() {
		return userDisplayName;
	}

	/**
	 * @param userDisplayName the userDisplayName to set
	 */
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	/**
	 * @return the userCategory
	 */
	public String getUserCategory() {
		return userCategory;
	}

	/**
	 * @param userCategory the userCategory to set
	 */
	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}

	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the ldapUId
	 */
	public String getLdapUId() {
		return ldapUId;
	}

	/**
	 * @param ldapUId the ldapUId to set
	 */
	public void setLdapUId(String ldapUId) {
		this.ldapUId = ldapUId;
	}

	/**
	 * @return the locationCode
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the validCode
	 */
	public String getValidCode() {
		return validCode;
	}

	/**
	 * @param validCode the validCode to set
	 */
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the userRoles
	 */
	public List<LoginUserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<LoginUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Boolean getRolePrivilegeInd() {
		return rolePrivilegeInd == null ? true : rolePrivilegeInd;
	}

	public void setRolePrivilegeInd(Boolean rolePrivilegeInd) {
		this.rolePrivilegeInd = rolePrivilegeInd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getInsertOn() {
		return insertOn;
	}

	public void setInsertOn(Date insertOn) {
		this.insertOn = insertOn;
	}

	public String getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy == null ? null : insertBy.trim();
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

}
