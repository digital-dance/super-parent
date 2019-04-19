package com.digital.dance.framework.identity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class IdentityEO extends IdentityServiceBaseEO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String system;
	private String subSys;
	private String module;
	private String table;
	private Long identity;
	private Date createTime;
	private Date updateTime;
	private String seqName;

	/**
	 * @return the system
	 */
	public String getSeqName() {
		this.seqName = (system == null ? "" : system) + "_" + (subSys == null ? "" : subSys)
				+ "_" + (module == null ? "" : module) + "_" + (table == null ? "" : table);
		return this.seqName;
	}

	/**
	 * @param seqName
	 *            the system to set
	 */
	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public void generateSeqName() {
		this.seqName = (system == null ? "" : system) + "_" + (subSys == null ? "" : subSys)
				+ "_" + (module == null ? "" : module) + "_" + (table == null ? "" : table);
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * @param system
	 *            the system to set
	 */
	public void setSystem(String system) {
		this.system = system;
		generateSeqName();
	}

	/**
	 * @return the subSys
	 */
	public String getSubSys() {
		return subSys;
	}

	/**
	 * @param subSys
	 *            the subSys to set
	 */
	public void setSubSys(String subSys) {
		this.subSys = subSys;
		generateSeqName();
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(String module) {
		this.module = module;
		generateSeqName();
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(String table) {
		this.table = table;
		generateSeqName();
	}

	/**
	 * @return the identity
	 */
	public Long getIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(Long identity) {
		this.identity = identity;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
