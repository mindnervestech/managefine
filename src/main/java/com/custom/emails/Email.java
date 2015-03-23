package com.custom.emails;

import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import models.MailSetting;
import utils.EmailExceptionHandler;
import akka.dispatch.Futures;

public class Email {
	
	public static void sendOnlyMail(MailSetting smtpSettings, final String recipient, final  String subject, final  String body){
		
		final String username;
		final String password;
		
		
		Properties props = new Properties();
		
		if(smtpSettings !=null && smtpSettings.hostName != null && smtpSettings.portNumber != null ){
			username = smtpSettings.userName;
			password = smtpSettings.password;
			
			props = new Properties();
			
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			
			props.put("mail.smtp.host", smtpSettings.hostName);
			props.put("mail.smtp.port", smtpSettings.portNumber);
		}
		else{
			username = "";
			password = "";
			props = new Properties();
			
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			
			props.put("mail.smtp.host", "");
			props.put("mail.smtp.port", "");
		}
		
		final Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 


		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			EmailExceptionHandler.handleException(e);
		}
			
			
 
 
		
	}
}
