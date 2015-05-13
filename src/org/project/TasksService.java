package org.project;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("tasks")
public class TasksService {

    public TasksService() {
    	
    }

    @GET
    @Produces({ APPLICATION_JSON })
    public List<Task> getAllTasks() {
    	List<Task> tasks = new ArrayList<Task>();
    	tasks.add(new Task("Create starting point for project."));
    	tasks.add(new Task("Create Tasks service."));
    	tasks.add(new Task("Create Users service."));
    	tasks.add(new Task("Create Task service."));
        return tasks;
    }
}