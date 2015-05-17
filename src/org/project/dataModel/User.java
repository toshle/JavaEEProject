package org.project.dataModel;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Users
 *
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getAllUsers", query = "SELECT b FROM User b")
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}
	
	@Id
	private String userName;
	
	private String passwd;
	private String fullName; // One field for family name and fathername
	private String email;
	private boolean isAdministrator;
	
//	@OneToMany(cascade=CascadeType.ALL)
	private List<Task> tasks;
	
	
	public User(String userName, String passwd, String fullName, String email,
			boolean isAdministrator, List<Task> tasksWorkingOn) {
		super();
		this.userName = userName;
		this.passwd = passwd;
		this.fullName = fullName;
		this.email = email;
		this.isAdministrator = isAdministrator;
		this.tasks= tasksWorkingOn;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasksWorkingOn) {
		this.tasks = tasksWorkingOn;
	}
   
}