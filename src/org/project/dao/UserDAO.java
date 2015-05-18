package org.project.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;

import org.project.dataModel.Task;
import org.project.dataModel.User;

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

	public User login(String user,String passwd){
		 
		byte[] hash=null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(passwd.getBytes("UTF-8"));
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		User _user = em.find(User.class,user);
		
		if(_user.getPasswd() == hash.toString()){
			return _user;
		}else
			return null;
		
	}
	
	public boolean register(User user){
		
		User exists = em.find(User.class, user.getUserName());
		
		if(exists != null){
			return false;
		}else{
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
