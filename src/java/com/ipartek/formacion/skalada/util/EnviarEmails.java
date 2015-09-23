package com.ipartek.formacion.skalada.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class EnviarEmails {
	
	public static final String direccionOrigen = "skalada.ipartek@gmail.com";
	private String passwordOrigen = "123ABC123";
	private String direccionFrom ="";
	private String direccionDestino ="";
	private String messageSubject=""; //Asunto
	private String messageText=""; //Cuerpo
	private Session session; //Para construir el mensaje
	
		/**	Construye el objeto {@code EnviarEmails} 
		*
		*/
		public EnviarEmails() {
		super();
		Properties props = new Properties(); //Propiedades para trabajar con gmail
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		//Autentificar y abre sesión externa con Gmail
		session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(direccionOrigen,passwordOrigen);
					}
				});			
		}

		
	/*********************** GETTERS Y SETTERS **************************************************/

	public String getDireccionOrigen() {
		return direccionOrigen;
	}


	/*public void setDireccionOrigen(String direccionOrigen) {
		this.direccionOrigen = direccionOrigen;
	}*/


	public String getPasswordOrigen() {
		return passwordOrigen;
	}


	/*public void setPasswordOrigen(String passwordOrigen) {
		this.passwordOrigen = passwordOrigen;
	}*/


	public String getDireccionFrom() {
		return direccionFrom;
	}


	public void setDireccionFrom(String direccionFrom) {
		this.direccionFrom = direccionFrom;
	}


	public String getDireccionDestino() {
		return direccionDestino;
	}


	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}


	public String getMessageSubject() {
		return messageSubject;
	}


	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}


	public String getMessageText() {
		return messageText;
	}


	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	
	
	@Override
	public String toString() {
		return "EnviarEmails [direccionOrigen=" + direccionOrigen
				+ ", passwordOrigen=" + passwordOrigen + ", direccionFrom="
				+ direccionFrom + ", direccionDestino=" + direccionDestino
				+ ", messageSubject=" + messageSubject + ", messageText="
				+ messageText + ", session=" + session + "]";
	}

	
	/*************************** METODO PUBLICO ****************************************************/
	
	public boolean enviar(){
	
		boolean resul = false;
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(direccionFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(direccionDestino)); //Destinatarios
			message.setSubject(MimeUtility.encodeText(messageSubject,"UTF-8","B"));
			message.setText(messageText); //Mensaje
			Transport.send(message);
			resul = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul; //Así controlamos si ha funcionado o no y se lo hacemos saber al usuario
	}
}