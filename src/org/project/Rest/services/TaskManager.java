package org.project.Rest.services;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.project.dao.TaskDAO;
import org.project.dataModel.Comment;
import org.project.dataModel.Task;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Task")
public class TaskManager {

	@PersistenceUnit
	private EntityManagerFactory emf;

	public TaskManager() {

	}

	@GET
	@Path("/All")
	@Produces("application/json")
	public List<Task> getAllBooks() {
		TaskDAO dao = new TaskDAO(emf.createEntityManager());

		return dao.getAllTasks();
	}

	@POST
	@Path("/Add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTask(Task task) {

		TaskDAO dao = new TaskDAO(emf.createEntityManager());

		try {
			dao.addTask(task);
		} catch (ConstraintViolationException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Bad input").build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Path("/Update")
	@Consumes(MediaType.APPLICATION_JSON)
	public void changeStatus(String json) {

		JsonElement element = new JsonParser().parse(json);
		JsonObject object = element.getAsJsonObject();

		String id = object.get("id").getAsString();
		String status = object.get("status").getAsString();

		TaskDAO dao = new TaskDAO(emf.createEntityManager());

		dao.changeStatus(id, status);

	}

	@POST
	@Path("/Comment/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(Comment comment, @PathParam("id") String id) {

		TaskDAO dao = new TaskDAO(emf.createEntityManager());
		try {
			dao.addComment(id, comment);
		} catch (ConstraintViolationException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Bad input").build();
		}
		return Response.status(Response.Status.OK).build();

	}

}
