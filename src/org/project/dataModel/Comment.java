package org.project.dataModel;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Comments
 *
 */
@Entity
@XmlRootElement
public class Comment implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Comment() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;
	@OneToOne(cascade=CascadeType.ALL)
	private User author;

	public Comment(long id, String content, Calendar createdTime, User author) {
		super();
		this.id = id;
		this.content = content;
		this.createdTime = createdTime;
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
   
}
