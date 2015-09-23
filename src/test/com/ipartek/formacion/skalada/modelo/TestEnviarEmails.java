package com.ipartek.formacion.skalada.modelo;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.util.EnviarEmails;

public class TestEnviarEmails {


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		EnviarEmails correo = new EnviarEmails();
		
		correo.setDireccionFrom("skalada.ipartek@gmail.com"); //Sin espacios
		correo.setDireccionDestino("unaiperea@gmail.com");
		correo.setMessageSubject("Email de prueba enviado desde Java");
		correo.setMessageText("Cuerpo del mensaje"); //Se pueden tener plantillas de html y enviarlas
		
		assertTrue("Email no enviado " + correo.toString(), correo.enviar());
	}

}
