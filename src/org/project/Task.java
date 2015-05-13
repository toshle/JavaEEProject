package org.project;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {
	
	private String name;
	
	public Task() {
		
	}
	
	public Task(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
