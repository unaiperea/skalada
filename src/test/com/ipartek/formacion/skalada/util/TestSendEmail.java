package com.ipartek.formacion.skalada.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestSendEmail {
	
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("skalada.ipartek@gmail.com","123ABC123");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("auraga@ipartek.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("auraga@ipartek.com,mikelalonsorojo@gmail.com,unaiperea@gmail.com,ieltxuorue@gmail.com,andertxo777@gmail.com,javi70@gmail.com,degar00@gmail.com,raulgf1992@gmail.com,laragonzalez.bm@gmail.com"));
			message.setSubject("Email enviado desde Skalada App");
			message.setText("No es Spam," +
					"\n\n Enviado email desde Java, dentro de poco el codigo en tu GIT!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}