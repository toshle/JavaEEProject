package dataModel;

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
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	public Task() {
		super();
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
	private Users executor;  
	
//	@OneToMany(cascade=CascadeType.ALL)
	private List<Comments> comments;
	private boolean isImportant;

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

	public Users getExecutor() {
		return executor;
	}

	public void setExecutor(Users executor) {
		this.executor = executor;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public Task(long id, String name, String description, Calendar finalDate,
			String status, Users executor, List<Comments> comments,
			boolean isImportant) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.finalDate = finalDate;
		this.status = status;
		this.executor = executor;
		this.comments = comments;
		this.isImportant = isImportant;
	}
   
}
