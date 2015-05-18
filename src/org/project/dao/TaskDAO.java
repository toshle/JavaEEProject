package org.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.project.dataModel.Comment;
import org.project.dataModel.Task;


public class TaskDAO {

	private EntityManager em;
	
	public TaskDAO(EntityManager em){
		this.em = em;
	}

	public Task findByNameAndExecutor(String name, String executor) {

		TypedQuery<Task> query = em
				.createNamedQuery("findByNameExecutor", Task.class)
				.setParameter("name", name).setParameter("executor", executor);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addTask(Task task) {
		em.getTransaction().begin();
		
		Task found = findByNameAndExecutor(task.getName(), task.getExecutor());
		if (found == null) {
			em.persist(task);
		}
		em.getTransaction().commit();
	}

	public List<Task> getAllTasks() {
		return em.createNamedQuery("getAllTasks", Task.class).getResultList();
	}

	public void changeStatus(String id,String status){
		
		Task update = em.find(Task.class,Integer.parseInt(id));
		if(update == null)
			return;
		
		if(update != null){
			em.getTransaction().begin();
			update.setChanged(true);
			update.setStatus(status);
			em.getTransaction().commit();
		}
	}
	
	public void addComment(String id,Comment comment){
		
		Task task = em.find(Task.class,Integer.parseInt(id));
		if(task == null)
			return;
		
		em.getTransaction().begin();
		task.setChanged(true);
		task.getComments().add(comment);
		em.getTransaction().commit();
		
	}
	
}
