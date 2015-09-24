package com.ipartek.formacion.skalada.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;

public class SendMail {

	private Properties props = new Properties();
	private String mensaje = null;
	private String asunto = "";
	private String destinatario = "";
	private String emisor = "skalada.ipartek@gmail.com";
	private Session session = null;

	public SendMail() {
		super();
		this.props.put("mail.smtp.host", "smtp.gmail.com");
		this.props.put("mail.smtp.socketFactory.port", "465");
		this.props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.port", "465");
		session = Session.getDefaultInstance(this.props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"skalada.ipartek@gmail.com", "123ABC123");
					}
				});

	}

	/**
	 * @param props
	 */
	public SendMail(String destinatario, String asunto, String Mensaje) {
		super();
		this.props.put("mail.smtp.host", "smtp.gmail.com");
		this.props.put("mail.smtp.socketFactory.port", "465");
		this.props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.port", "465");
		session = Session.getDefaultInstance(this.props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"skalada.ipartek@gmail.com", "123ABC123");
					}
				});

		this.setAsunto(asunto);
		this.setDestinatario(destinatario);
		this.setMensaje(mensaje);
	}

	/**
	 * @return the props
	 */
	public Properties getProps() {
		return props;
	}

	/**
	 * @param props
	 *            the props to set
	 */
	public void setProps(Properties props) {
		this.props = props;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * @param asunto
	 *            the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public boolean enviarMail() {
		boolean resul = false;
		try {

			if (this.mensaje == null) {

			}
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.getEmisor()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(this.getDestinatario()));
			message.setSubject(this.getAsunto());
			message.setContent(this.getMensaje(), "text/html");

			Transport.send(message);
			resul=true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return resul;
	}

	public String generarPlantilla(String plantilla, HashMap<String,String> parametros)
			throws IOException {
		String resul = "";

		ClassLoader classLoader = getClass().getClassLoader();
		resul = (IOUtils.toString(classLoader
				.getResourceAsStream(plantilla),"UTF-8"));

		Iterator<Map.Entry<String, String>> it = parametros.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,String> e = (Map.Entry<String,String>)it.next();
			resul = resul.replace(e.getKey(), e.getValue());
		
		}
		return resul;
	}

}
