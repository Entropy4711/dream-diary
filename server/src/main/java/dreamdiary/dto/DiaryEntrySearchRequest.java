package dreamdiary.dto;

import java.io.Serializable;

public class DiaryEntrySearchRequest implements Serializable {
	
	private static final long serialVersionUID = -473430984534854544L;
	
	private String text;
	private int page;
	private int pageSize;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
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
}