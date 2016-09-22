package dreamdiary.dto;

import java.io.Serializable;

public class FileUploadResultItem implements Serializable {
	
	private static final long serialVersionUID = 4449108162062685410L;
	
	private String name;
	
	public FileUploadResultItem() {
		
	}
	
	public FileUploadResultItem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}