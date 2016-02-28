package dreamdiary.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import dreamdiary.constants.MongoDbConstants;

@Document(collection = MongoDbConstants.diary_entry)
public class DiaryEntry implements Serializable {
	
	private static final long serialVersionUID = 7079137733899782495L;
	
	@Id
	private String id;
	
	private Date createdDate;
	private String title;
	private String content;
	private List<ListEntry> tags;
	private List<ListEntry> images;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<ListEntry> getTags() {
		return tags;
	}
	
	public void setTags(List<ListEntry> tags) {
		this.tags = tags;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public List<ListEntry> getImages() {
		return images;
	}
	
	public void setImages(List<ListEntry> images) {
		this.images = images;
	}
}