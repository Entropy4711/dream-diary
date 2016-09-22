package dreamdiary.dto;

import java.io.Serializable;
import java.util.List;

public class FileUploadResponse implements Serializable {
	
	private static final long serialVersionUID = -1102656791635155861L;
	
	private List<FileUploadResultItem> files;
	
	public FileUploadResponse() {
		
	}
	
	public FileUploadResponse(List<FileUploadResultItem> files) {
		this.files = files;
	}
	
	public List<FileUploadResultItem> getFiles() {
		return files;
	}
	
	public void setFiles(List<FileUploadResultItem> files) {
		this.files = files;
	}
}