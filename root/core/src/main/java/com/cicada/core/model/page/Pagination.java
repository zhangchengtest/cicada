package com.cicada.core.model.page;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {

	private int totalRows;
	private int pageSize;
	private int currPageNum;
	private int totalPages;
	private int startRow;
	private int endRow;
	private List<T> pageData;

	public int getTotalRows() {
		return totalRows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrPageNum() {
		return currPageNum;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public Pagination(int totalRows, int currPageNum, int pageSize) {
		this.totalRows = Math.max(0, totalRows);
		this.pageSize = Math.max(1, pageSize);

		// calculation
		this.totalPages = this.totalRows / this.pageSize;
		if (this.totalRows % this.pageSize != 0) {
			++this.totalPages;
		}

		this.currPageNum = Math.max(1, Math.min(Math.max(1, currPageNum), this.totalPages));
		this.startRow = (this.currPageNum - 1) * this.pageSize;
		this.endRow = Math.min(this.totalRows, this.startRow + this.pageSize);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pagination [totalRows=");
		builder.append(totalRows);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", currPageNum=");
		builder.append(currPageNum);
		builder.append(", totalPages=");
		builder.append(totalPages);
		builder.append(", startRow=");
		builder.append(startRow);
		builder.append(", endRow=");
		builder.append(endRow);
		builder.append("]");
		return builder.toString();
	}

}
