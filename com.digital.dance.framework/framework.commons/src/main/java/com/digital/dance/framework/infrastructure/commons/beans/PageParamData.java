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
	private static final long serialVersionUID = -3380887473513945943L;
	private Integer pageIndex;
	private Integer size;
	private T pageData;

	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return this.size;
	}

	public void setPageSize(Integer pageSize) {
		this.size = pageSize;
	}

	public T getPageData() {
		return this.pageData;
	}

	public void setPageData(T pageData) {
		this.pageData = pageData;
	}
}
