package dreamdiary.dto;

import java.io.Serializable;

public class TagListResult implements Serializable {
	
	private static final long serialVersionUID = 7845455920376617106L;
	
	private String title;
	private int entryCount;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getEntryCount() {
		return entryCount;
	}
	
	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}
}