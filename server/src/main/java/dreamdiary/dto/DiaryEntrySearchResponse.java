package dreamdiary.dto;

import java.io.Serializable;
import java.util.List;

import dreamdiary.domain.DiaryEntry;

public class DiaryEntrySearchResponse implements Serializable {
	
	private static final long serialVersionUID = 7171649424624237707L;
	
	private List<DiaryEntry> entries;
	private long totalElements;
	
	public List<DiaryEntry> getEntries() {
		return entries;
	}
	
	public void setEntries(List<DiaryEntry> entries) {
		this.entries = entries;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
}