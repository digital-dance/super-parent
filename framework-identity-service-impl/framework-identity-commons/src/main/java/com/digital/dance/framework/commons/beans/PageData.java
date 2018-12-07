/**
 * 
 */
package com.digital.dance.framework.commons.beans;

import java.util.List;

import java.io.Serializable;

/**
 * 
 * @author liuxiny
 *
 * @param <T>
 */
public class PageData<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5480226183457424820L;
	private Integer totalCount;
	private Integer totalPages;
	private Integer size;
	private Integer pageIndex;
	private List<T> datList;

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<T> getDataList() {
		return this.datList;
	}

	public void setDataList(List<T> datList) {
		this.datList = datList;
	}
}
