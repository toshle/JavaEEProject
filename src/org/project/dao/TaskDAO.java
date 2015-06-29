package org.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.project.dataModel.Comment;
import org.project.dataModel.Task;


public class TaskDAO {

	private EntityManager em;
	
	public TaskDAO(EntityManager em){
		this.em = em;
	}

	public void addTask(Task task) {
		em.getTransaction().begin();
		TypedQuery<Task> query = em
						.createNamedQuery("findByNameDescription", Task.class)
						.setParameter("name", task.getName()).setParameter("description", task.getDescription());
		
		List<Task> result = query.getResultList();
		
		if(result.isEmpty()){
			em.persist(task);
		}
		
		em.getTransaction().commit();
	}

	public List<Task> getAllTasks() {
		return em.createNamedQuery("getAllTasks", Task.class).getResultList();
	}

	public void changeStatus(int id,String status){
		
		Task update = em.find(Task.class, id);
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
	
	public void setChangetFalse(){
		
		List<Task> tasks = getAllTasks();
		
		em.getTransaction().begin();
		
		for(Task t: tasks){
			
			if(t.isChanged())
				t.setChanged(false);
		}
		
		em.getTransaction().commit();
	}
	
}
