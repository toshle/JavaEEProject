package org.project.dao;

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
		boolean flag = false;
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

}
