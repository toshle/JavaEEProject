package org.project.Rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.project.SendEmails.Scheduler;

@Path("/SendEmailsDaily")
public class StartEmailService {
	
	
	@GET
	public void startSending(){
		
		Scheduler scheduler = new Scheduler();
	}

}
