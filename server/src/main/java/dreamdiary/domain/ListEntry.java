package dreamdiary.domain;

import java.io.Serializable;

public class ListEntry implements Serializable {
	
	private static final long serialVersionUID = 620185597223007513L;
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}