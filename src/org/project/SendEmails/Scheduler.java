package org.project.SendEmails;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

public class Scheduler {

	private static boolean ISSTARTED = false;

	public Scheduler(EntityManager em) {
		if (!ISSTARTED) {
			startService(em);
			ISSTARTED = true;
		}

	}

	private void startService(EntityManager em) {
		ScheduledExecutorService scheduler = null;

		try {
			scheduler = Executors.newScheduledThreadPool(1);
			SendEmail mailService = new SendEmail(em);
			scheduler.scheduleAtFixedRate(mailService, 0, 1, TimeUnit.DAYS);

		} finally {
			scheduler.shutdown();
		}

	}
}
