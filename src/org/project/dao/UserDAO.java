package org.project.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

import org.project.dataModel.Task;
import org.project.dataModel.User;

import com.sun.org.apache.xerces.internal.util.Status;

public class UserDAO {

	private EntityManager em;

	public UserDAO(EntityManager em) {
		this.em = em;
	}

	public void addUser(User user) {

		User _user = em.find(User.class, user.getUserName());

		if (_user == null) {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		}
	}
	
	public List<User> getAllUsers() {
		return em.createNamedQuery("getAllUsers", User.class).getResultList();
	}

	// ako ima dve ili poveche zadachi v status inproces ili v nachalen status vrushta flagche za ui-q
	public boolean assignTask(String id, String user) {

		User _user = em.find(User.class, user);
		Task task = em.find(Task.class,Integer.parseInt(id));
		
		if (_user == null)
			return false;

		List<Task> tasks = _user.getTasks();
		int counter = 0;
		for (Task t : tasks) {

			if (t.getStatus() == "inprocess" || t.getStatus() == "initial") {
				counter++;
			}
		}
		em.getTransaction().begin();
		_user.getTasks().add(task);
		em.getTransaction().commit();

		if (counter > 1)
			return true;
		else
			return false;
	}

	public int login(String user, String passwd){
		
		String hashedPassword = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(passwd.getBytes(), 0, passwd.length());
			hashedPassword = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		User _user = em.find(User.class,user);
		if(_user == null) return -1; //Status.NOT_RECOGNIZED;
		
		if (_user.getPasswd().equals(hashedPassword)) {
			return 1; //Status.RECOGNIZED;
		}
		else {
			return 0; // Status.NOT_ALLOWED;
		}
	}
	
	public boolean register(User user){
		User exists = em.find(User.class, user.getUserName());
		
		if (exists != null) {
			return false;
		}
		else {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			
			return true;
		}
	}

	
	public List<Task> getAllTasksUser(String userName){
		
		User user = em.find(User.class, userName);
		
		return user.getTasks();
	}
}
