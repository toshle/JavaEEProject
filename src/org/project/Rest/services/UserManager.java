package org.project.Rest.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import org.apache.openjpa.json.JSONObject;
import org.project.dao.UserDAO;
import org.project.dataModel.Task;
import org.project.dataModel.User;

import com.sun.org.apache.xerces.internal.util.Status;


@Path("/User")
public class UserManager {

	@PersistenceUnit
	private EntityManagerFactory emf;

	public UserManager() {
	}
	
	@POST
	@Path("/Login")
	@Consumes("application/json")
	public Response login(User user){
		String userName = user.getUserName();
		String passwd = user.getPasswd();

		System.out.println("logging in with user: " + userName + " and password: " + passwd);
		
		UserDAO userDao = new UserDAO(emf.createEntityManager());

		int authenticationResult = userDao.login(userName, passwd);
		switch (authenticationResult) {
		case -1:
			return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();			
		case 0:
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid password.").build();			
		default:
			return Response.ok("Logged in.", MediaType.APPLICATION_JSON).build();
		}
	}
	
	@POST
	@Path("/Register")
	@Consumes("application/json")
	public Response register(User user){
		String userPassword = user.getPasswd();
		String hashedPassword = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(userPassword.getBytes(), 0, userPassword.length());
			hashedPassword = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		System.out.println("REGISTERING WITH PASSWORD " + user.getPasswd());
		
		user.setPasswd(hashedPassword);
		
		UserDAO dao = new UserDAO(emf.createEntityManager());
		
		boolean userCreated = false;
		try {
			userCreated = dao.register(user);
		} catch (ConstraintViolationException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Bad input").build();
		}
		
		if (userCreated) {
			return Response.ok("Registered." ,MediaType.APPLICATION_JSON).build();
		}
		else {
			return Response.status(Response.Status.CONFLICT).entity("User already exists.").build();
		}
	}
	
	@Path("/AllTasks/{user}")
	@GET
	public List<Task> getTasksUser(@PathParam("user") String userName){
		
		UserDAO dao = new UserDAO(emf.createEntityManager());
		
		return dao.getAllTasksUser(userName);
	}
	
	@Path("/AllUsers")
	@GET
	@Produces("application/json")
	public List<User> getAllUsers(){
		
		UserDAO dao = new UserDAO(emf.createEntityManager());
		
		return dao.getAllUsers();
	}
	
}
