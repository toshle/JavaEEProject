package org.project.Rest.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.project.dao.UserDAO;
import org.project.dataModel.Task;
import org.project.dataModel.User;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/User")
public class UserManager {

	@PersistenceUnit
	private EntityManagerFactory emf;

	public UserManager() {
	}
	
	@Path("/Login")
	@POST
	public Response login(String json){
		
		JsonElement element =  new JsonParser().parse(json);
		JsonObject object = element.getAsJsonObject();
		
		String user = object.get("user").getAsString();
		String passwd = object.get("passwd").getAsString();

		UserDAO dao = new UserDAO(emf.createEntityManager());
		
		User _user = dao.login(user,passwd);
		Gson gson = new Gson();
		String userJson = gson.toJson(_user);
		
		if(_user == null){
			return Response.status(Response.Status.NOT_FOUND).entity("User not found " + user).build();
		}else{
			return Response.ok(userJson, MediaType.APPLICATION_JSON).build();
		}
	}
	
	@Path("/Register")
	@POST
	public Response register(String user){
		
		JsonElement element =  new JsonParser().parse(user);
		JsonObject object = element.getAsJsonObject();
		
		User _user = new User();
		
		_user.setAdministrator(object.get("isAdministrator").getAsBoolean());
		_user.setEmail(object.get("email").getAsString());
		_user.setFullName(object.get("fullName").getAsString()); // append name and family in javascript
		_user.setUserName(object.get("userName").getAsString());
		
		byte[] hash=null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(object.get("passwd").getAsString().getBytes("UTF-8"));
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		_user.setPasswd(hash.toString());
		
		UserDAO dao = new UserDAO(emf.createEntityManager());
		
		boolean success = dao.register(_user);
		
		if(success){
			return Response.ok("success").build();
		}else{
			return Response.status(Response.Status.CONFLICT).entity("user exists").build();
		}
		
	}
	
	@Path("/AllTasks/{user}")
	@GET
	public List<Task> getTasksUser(@PathParam("user") String userName){
		
		UserDAO dao = new UserDAO(emf.createEntityManager());
		
		return dao.getAllTasksUser(userName);
	}
	
	
	
}
