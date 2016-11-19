package dreamdiary.dto;

import java.io.Serializable;

public class TagsSearchRequest implements Serializable {
	
	private static final long serialVersionUID = -9049743454354337083L;
	
	private String term;
	private int page;
	private int pageSize;
	private String sortField;
	private boolean sortAscending;
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSortField() {
		return sortField;
	}
	
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	public boolean isSortAscending() {
		return sortAscending;
	}
	
	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}
}