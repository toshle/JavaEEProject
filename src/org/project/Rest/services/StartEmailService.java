package org.project.Rest.services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.project.SendEmails.Scheduler;

@Path("/SendEmailsDaily")
public class StartEmailService {
	
	@PersistenceUnit
	private EntityManagerFactory emf ;
	
	
	public StartEmailService() {
		super();
	}

	@GET
	public void startSending(){
		
		Scheduler scheduler = new Scheduler(emf.createEntityManager());
	}

}
