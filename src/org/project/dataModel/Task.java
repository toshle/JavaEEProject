package org.project.dataModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findByNameDescription", query = "SELECT b FROM Task b WHERE b.name = :name AND b.description = :description"),
    @NamedQuery(name = "getAllTasks", query = "SELECT b FROM Task b")
})
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	public Task() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Size(min=2, max=64)
	private String name;
	
	@NotNull
	private String description;
	
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar finalDate;
	
	private String status;
	
	@Size(min=4, max=64)
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
