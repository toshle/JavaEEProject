package org.project.dataModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findByNameExecutor", query = "SELECT b FROM Task b WHERE b.name = :name AND b.executor = :executor"),
    @NamedQuery(name = "getAllTasks", query = "SELECT b FROM Task b")
})
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	public Task() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar finalDate;
	
	private String status;
//	@OneToOne(cascade=CascadeType.ALL)
	private String executor;  
	
//	@OneToMany(cascade=CascadeType.ALL)
	private List<Comment> comments;
	private boolean isImportant;
	private boolean isChanged;
	

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Calendar finalDate) {
		this.finalDate = finalDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public Task(String name, String description, Calendar finalDate,
			String status, String executor, List<Comment> comments,
			boolean isImportant, boolean isChanged) {
		super();
		this.name = name;
		this.description = description;
		this.finalDate = finalDate;
		this.status = status;
		this.executor = executor;
		this.comments = comments;
		this.isImportant = isImportant;
		this.isChanged = isChanged;
	}



   
}
