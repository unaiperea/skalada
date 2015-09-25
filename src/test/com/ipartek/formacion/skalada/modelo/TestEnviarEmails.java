package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.util.EnviarEmails;

public class TestEnviarEmails {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testEnviar() {
		
		EnviarEmails correo = new EnviarEmails();
		
		correo.setDireccionFrom("skalada.ipartek@gmail.com"); //Sin espacios
		correo.setDireccionDestino("unaiperea@gmail.com");
		correo.setMessageSubject("Email de prueba enviado desde Java");
		
		
		
		correo.setMessageText("Cuerpo del mensaje de texto"); //Se pueden tener plantillas de html y enviarlas
		
		assertTrue(
				 "Email no enviado " + correo.toString() ,
				  correo.enviar()
				);
		
	}
	
	@Test
	public void testEnviarRegistro() {
		
		String email   = "unaiperea@gmail.com";
		String url     = Constantes.SERVER + Constantes.CONTROLLER_SIGNUP+"?email="+email;
		String usuario = "Antton Gorriti";
		String contenido = "Gracias por registrarte. Para activar el usuario y verificar el email, clica en el enlace de debajo.";
		String submitButtonText = "Activa tu cuenta y logeate";
		String cuerpo  = "";
		
		/*
		 * Variables a reemplazar en la plantilla:
		 * ${usuario}  =>  Usuario Refistrado
		 * ${url}      =>  Enlace para validar la cuenta del usuario  
		 * 
		 * */
		
		EnviarEmails correo = new EnviarEmails();
		HashMap<String, String> parametros = new HashMap<String, String>();
		parametros.put("{usuario}", usuario); //Los {} pueden ser $ &, cualquier símbolo
		parametros.put("{url}", url);
		parametros.put("{contenido}", contenido);
		parametros.put("{btn_submit_text}", submitButtonText);
		try {
			cuerpo = correo.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO, parametros); //Le paso la ruta de la plantilla y un HashMap y me lo devuelve ya montado
		}catch(Exception e){
			e.printStackTrace();
			fail("no existe la plantilla" + Constantes.EMAIL_TEMPLATE_REGISTRO);
		}

		/*	O DE ESTA FORMA leyendo el fichero directamente desde una ubicación dentro de Webcontent
		archivo = new File (Constantes.EMAIL_TEMPLATE_REGISTRO);
		
		cuerpo = FileUtils.readFileToString(archivo, "UTF-8");
		
		cuerpo = cuerpo.replace("{usuario}", usuario); //Los {} pueden ser $ &, cualquier símbolo
		cuerpo = cuerpo.replace("{url}", url);*/
		
		correo.setDireccionFrom("skalada.ipartek@gmail.com");
		correo.setDireccionDestino("unaiperea@gmail.com");
		correo.setMessageSubject("Confirmar registro usuario");
		correo.setMessageContent( cuerpo );
		
		assertTrue(
				 "Email no enviado " + correo.toString() ,
				  correo.enviar()
				);

	}
			
			
			
			
}