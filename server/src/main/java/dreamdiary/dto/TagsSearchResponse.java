package dreamdiary.dto;

import java.io.Serializable;
import java.util.List;

public class TagsSearchResponse implements Serializable {
	
	private static final long serialVersionUID = 7171649424624237707L;
	
	private List<TagListResult> tags;
	private long totalElements;
	private long pageCount;
	private int page;
	
	public List<TagListResult> getTags() {
		return tags;
	}
	
	public void setTags(List<TagListResult> tags) {
		this.tags = tags;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	
	public long getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
}