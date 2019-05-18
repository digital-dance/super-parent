package com.digital.dance.framework.sso.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginUserRole implements Serializable {
	private String userId ; 
	private String roleId ;
	private String userName;
//	private String userDisplayName;
//	private String userEmail;
//	private String userCategory;
//	private String userMobile;
	private String roleName ;
	private String orgId;
	private String departmentId;

	private String sysUserRoleId;

	private String sysOrgRoleId;

//	private String userId;
//
//	private String userName;

	private String password;

	private String email;

	private String phone;

	private Boolean rolePrivilegeInd;

//	private String orgId;
//
//	private String departmentId;
//
//	private String roleId;
//
//	private String userId;

	private Integer state;

	private Date insertOn;

	private String insertBy;

	private Date updateOn;

	private String updateBy;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
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
		return rolePrivilegeInd;
	}

	public void setRolePrivilegeInd(Boolean rolePrivilegeInd) {
		this.rolePrivilegeInd = rolePrivilegeInd;
	}

	public String getSysUserRoleId() {
		return sysUserRoleId;
	}

	public void setSysUserRoleId(String sysUserRoleId) {
		this.sysUserRoleId = sysUserRoleId == null ? null : sysUserRoleId.trim();
	}

	public String getSysOrgRoleId() {
		return sysOrgRoleId;
	}

	public void setSysOrgRoleId(String sysOrgRoleId) {
		this.sysOrgRoleId = sysOrgRoleId == null ? null : sysOrgRoleId.trim();
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
