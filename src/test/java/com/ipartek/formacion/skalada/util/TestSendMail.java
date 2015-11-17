package com.ipartek.formacion.skalada.util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.Constantes;

public class TestSendMail {

	private static final String PATH_TEMPLATE_REGISTRO = Constantes.EMAIL_TEMPLATE_REGISTRO;

	@BeforeClass()
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass()
	public static void tearDownAfterClass() throws Exception {
	}

	@Before()
	public void setUp() throws Exception {
	}

	@After()
	public void tearDown() throws Exception {
	}

	@Test()
	public void testEnviar() {

		SendMail correo = new SendMail();

		correo.setEmisor("skalada.ipartek@gmail.com");
		correo.setDestinatario("ander.ipartek@gmail.com");
		correo.setAsunto("Email de prueba enviado desde Java");
		correo.setMensaje("Cuerpo del mensaje de texto");

		try {
			assertTrue("Email no enviado " + correo.toString(),
					correo.enviarMail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test()
	public void testEnviarRegistro() {

		String email = "ander.ipartek@gmail.com";
		String url = Constantes.SERVER + Constantes.CONTROLLER_SIGNUP
				+ "?accion=" + Constantes.ACCION_CONFIRM + "&email=" + email;
		String usuario = "Antton Gorriti";

		/*
		 * Variables a reemplazar en la plantilla: {usuario} => Usuario
		 * Refistrado {url} => Enlace para validar la cuenta del usuario
		 */

		// Constantes.EMAIL_TEMPLATE_REGISTRO);

		// @see: http://memorynotfound.com/load-file-resources-folder-java/

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("{usuario}", usuario);
		params.put("{url}", url);
		params.put(
				"{contenido}",
				"Gracias por registrarte. Para activar el usuario y verificar el email, clica en el enlace de debajo.");
		params.put("{txt_btn}", "Activa tu cuenta y logeate");

		SendMail correo = new SendMail();

		correo.setEmisor("skalada.ipartek@gmail.com");
		correo.setDestinatario("ander.ipartek@gmail.com");
		correo.setAsunto("Confirmar registro usuario");
		try {
			correo.setMensaje(correo.generarPlantilla(PATH_TEMPLATE_REGISTRO,
					params));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			assertTrue("Email no enviado " + correo.toString(),
					correo.enviarMail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
