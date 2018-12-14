package com.digital.dance.framework.infrastructure.commons.beans;

import java.io.Serializable;

/**
 * 
 * @author liuxiny
 *
 * @param <T>
 */
public class PageParamData<T> implements Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = -3380887473513945943L;
	protected Integer pageIndex;
	protected Integer pageSize;
	protected Integer offsetNum;
	private T pageData;

	public Integer getPageIndex() {
		return (pageIndex == null ? 0 : pageIndex);
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return (pageSize == null ? 0 : pageSize);
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the offsetNum
	 */
	public Integer getOffsetNum() {
		this.offsetNum = (pageIndex == null ? 0 : pageIndex) * (pageSize == null ? 0 : pageSize);
		return this.offsetNum;
	}
	/**
	 * @param offsetNum the offsetNum to set
	 */
	public void setOffsetNum(Integer offsetNum) {
		this.offsetNum = (pageIndex == null ? 0 : pageIndex) * (pageSize == null ? 0 : pageSize);
	}

	public T getPageData() {
		return this.pageData;
	}

	public void setPageData(T pageData) {
		this.pageData = pageData;
	}
}
