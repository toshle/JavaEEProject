package org.project.SendEmails;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
	
	private static boolean ISSTARTED=false;
	
	public Scheduler(){
		if(!ISSTARTED){
			startService();
			ISSTARTED=true;
		}
		
	}
	   
	private void startService(){
		   
		   ScheduledExecutorService scheduler =
				     Executors.newScheduledThreadPool(1);
		   
		   SendEmail mailService = new SendEmail();
		   scheduler.scheduleAtFixedRate(mailService, 0, 1, TimeUnit.DAYS);
	   }

}
