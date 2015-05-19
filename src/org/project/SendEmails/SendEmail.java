package org.project.SendEmails;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

import org.project.dao.TaskDAO;
import org.project.dataModel.Task;
import org.project.dataModel.User;

public class SendEmail implements Runnable {

	private EntityManager em;

	public SendEmail(EntityManager em) {
		this.em = em;
	}

	@Override
	public void run() {

		List<String> emailAddresses = getListOfEmailAddreses();
		
		String username = ""; // username account gmail
		String password = ""; // password account gmail
		String from = ""; // username account gmail

		Properties props = System.getProperties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			for (String emailAddress : emailAddresses) {

				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(emailAddress));

			}

			message.setSubject("This is the Subject Line!");

			message.setText("This is actual message");

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	private List<String> getListOfEmailAddreses() {

		List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
				.getResultList();
		List<String> emails = new ArrayList<String>();

		for (Task t : tasks) {

			if (t.isChanged() && t.getExecutor() != null) {

				User user = em.find(User.class, t.getExecutor());
				emails.add(user.getEmail());

			}
		}

		TaskDAO dao = new TaskDAO(em);
		dao.setChangetFalse();

		return emails;
	}

}
